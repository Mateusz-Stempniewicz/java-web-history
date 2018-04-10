package com.xcactus.libs.history.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import javax.persistence.Id;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

import com.xcactus.libs.commons.annotation.HistoryObject;
import com.xcactus.libs.commons.annotation.HistoryParentObject;
import com.xcactus.libs.commons.annotation.HistoryProperty;
import com.xcactus.libs.commons.enums.HistoryOperationType;
import com.xcactus.libs.commons.security.DefaultAuthenticatedUser;
import com.xcactus.libs.history.dto.HistoryItemDto;
import com.xcactus.libs.history.services.HistoryService;

/**
 * @author maciej.sowa
 *
 */
@Component
@Aspect
public class HistoryAspects {
	
	private final String REPOSITORY_SAVE_POINTCUT = "execution(* *.save(..)) && @args(historyAnnotation)";
	private final String REPOSITORY_SAVE_AND_FLUSH_POINTCUT = "execution(* *.saveAndFlush(..)) && @args(historyAnnotation)";
	private final String REPOSITORY_DELETE_POINTCUT = "execution(* *.delete(..)) && @args(historyAnnotation)";

	@Autowired
	HistoryService historyService;
	
	@Pointcut(REPOSITORY_DELETE_POINTCUT)
	public void repositoryDelete(HistoryObject historyAnnotation) {
	}
	
	@Pointcut(REPOSITORY_SAVE_POINTCUT)
	public void repositorySave(HistoryObject historyAnnotation) {
	}

	@Pointcut(REPOSITORY_SAVE_AND_FLUSH_POINTCUT)
	public void repositorySaveAndFlush(HistoryObject historyAnnotation) {
	}
	
	@Around("repositorySave(historyAnnotation)")
	public Object repositorySaveHistory(ProceedingJoinPoint pjp, HistoryObject historyAnnotation) throws Throwable {
		return processObject(pjp, historyAnnotation, pjp.getSignature().getName(), HistoryOperationType.SAVE.getId());
	}
/*	
	@After("repositorySave(historyAnnotation)")
	public void repositorySaveHistory(JoinPoint jp, HistoryObject historyAnnotation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		processObject(jp.getArgs(), historyAnnotation, jp.getSignature().getName());
	}

	@After("repositorySaveAndFlush(historyAnnotation)")
	public void repositorySaveAndFlushHistory(JoinPoint jp, HistoryObject historyAnnotation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		processObject(jp.getArgs(), historyAnnotation, jp.getSignature().getName());
	}
*/	
	@Around("repositorySaveAndFlush(historyAnnotation)")
	public Object repositorySaveAndFlushHistory(ProceedingJoinPoint pjp, HistoryObject historyAnnotation) throws Throwable {
		return processObject(pjp, historyAnnotation, pjp.getSignature().getName(), HistoryOperationType.SAVE.getId());
	}

	@Around("repositoryDelete(historyAnnotation)")
	public Object repositoryDeleteHistory(ProceedingJoinPoint pjp, HistoryObject historyAnnotation) throws Throwable {
		return processObject(pjp, historyAnnotation, pjp.getSignature().getName(), HistoryOperationType.DELETE.getId());
	}

	private Integer extractId(Object value) {
		if (value instanceof Number) {
    		return (Integer) value;
    	} else {
    		return null;
    	}
	}
	
	private Integer extractUserId(Authentication authentication) {
		if (authentication != null)
			return ((DefaultAuthenticatedUser) authentication.getPrincipal()).getUser().getId();
		
		return null;
	}
	
	private String extractUserName(Authentication authentication) {
		if (authentication != null)
			return authentication.getName();
		
		return null;
	}
	
	private Object processObject(ProceedingJoinPoint pjp, HistoryObject historyAnnotation, String signatureName, int operationType) throws Throwable {
		Object historyObject = pjp.proceed();
		processObject(historyObject, historyAnnotation, signatureName, operationType);
		return historyObject;
	}
	
	private void processObject(Object object, HistoryObject historyAnnotation, String signatureName, int operationType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Integer userId = extractUserId(SecurityContextHolder.getContext().getAuthentication()); 
		String userName = extractUserName(SecurityContextHolder.getContext().getAuthentication()); 
		
			String objectClassType = object.getClass().getName();
			String objectHistoryName = historyAnnotation.historyName();
			HashMap<String, String> historyValues = new HashMap<>();
			HashMap<String, Integer> objectIds = new HashMap<>();
			HashMap<String, String> parentClasses = new HashMap<>();
			
			if (object.getClass().isAnnotationPresent(HistoryObject.class)) {
				for (Method method : ReflectionUtils.getAllDeclaredMethods(object.getClass())) {
					method.setAccessible(true);

					if (method.isAnnotationPresent(HistoryParentObject.class))
						parentClasses.put(objectClassType, method.getReturnType().getName());

					if (method.isAnnotationPresent(Id.class))
						objectIds.put(objectClassType, extractId(method.invoke(object)));
					
					if (method.isAnnotationPresent(HistoryProperty.class))
		            	historyValues.put(method.getAnnotation(HistoryProperty.class).historyName(), method.invoke(object).toString());
				}
				
				ReflectionUtils.doWithFields(object.getClass(),
					    new FieldCallback(){

					        @Override
					        public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
				            	field.setAccessible(true);
				            	
								if (field.isAnnotationPresent(HistoryParentObject.class))
									parentClasses.put(objectClassType, field.getType().getName());
								
								if (field.isAnnotationPresent(Id.class)) 
				            		objectIds.put(objectClassType, extractId(field.get(object)));

								if (field.isAnnotationPresent(HistoryProperty.class))
					        		historyValues.put(field.getAnnotation(HistoryProperty.class).historyName(), field.get(object).toString());
					        }
					    },
					    new FieldFilter(){

					        @Override
					        public boolean matches(final Field field){
					            final int modifiers = field.getModifiers();
					            return !Modifier.isStatic(modifiers);
					        }
					    });
				
				for (String field : historyValues.keySet()) {
					HistoryItemDto hid = new HistoryItemDto();
					hid.setOperationTypeId(operationType);
					hid.setObjectClassName(objectHistoryName);
					hid.setObjectId(objectIds.get(objectClassType));
					hid.setPropertyName(field);
					hid.setValue(historyValues.get(field));
					hid.setUserId(userId);
					hid.setUserName(userName);
					//TODO dodac obsluge parent object (wyszukiwanie ID obiektu parent)
					hid.setParentClassType(parentClasses.get(objectClassType));
					
					historyService.saveHistoryItem(hid);
				}
			}
	}
	
}

