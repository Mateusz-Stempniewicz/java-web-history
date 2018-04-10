/**
 * 
 */
package com.xcactus.libs.history.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.xcactus.libs.history.dto.HistoryItemDto;
import com.xcactus.libs.history.jpa.entities.HistoryClassName;
import com.xcactus.libs.history.jpa.entities.HistoryClassProperty;
import com.xcactus.libs.history.jpa.entities.HistoryItem;
import com.xcactus.libs.history.repositories.HistoryClassNamesRepository;
import com.xcactus.libs.history.repositories.HistoryClassPropertiesRepository;
import com.xcactus.libs.history.repositories.HistoryItemsRepository;

/**
 * @author maciej.sowa
 * @param <E>
 */
@Service
public class HistoryServiceImpl implements HistoryService {
	
	@Autowired
	private HistoryItemsRepository historyItemsRepository;
	
	@Autowired
	private HistoryClassNamesRepository historyClassNamesRepository;
	
	@Autowired
	private HistoryClassPropertiesRepository historyClassPropertiesRepository;

	@Override
	@Async
	@Transactional
	public void saveHistoryItem(HistoryItemDto historyItemDto) {

		int classTypeId = processClassName(historyItemDto.getObjectClassName());
		int propertyId = processClassProperty(historyItemDto.getPropertyName(), classTypeId);
		
		HistoryItem lastHI = historyItemsRepository.findFirst1ByObjectClassIdAndPropertyIdAndObjectIdOrderByIdDesc(classTypeId, propertyId, historyItemDto.getObjectId());
		
		if (lastHI == null || (!lastHI.getValue().equals(historyItemDto.getValue()) || lastHI.getOperationTypeId() != historyItemDto.getOperationTypeId())) {
			HistoryItem historyItem = createHistoryItem(historyItemDto.getOperationTypeId(), classTypeId, historyItemDto.getObjectId(), historyItemDto.getValue(), propertyId, historyItemDto.getUserId(), historyItemDto.getUserName());
	
			if (historyItemDto.getParentClassType() != null && historyItemDto.getParentObjectId() != null) {
				int parentClassId = processClassName(historyItemDto.getParentClassType());
				historyItem.setParentClassTypeId(parentClassId);
				historyItem.setParentObjectId(historyItemDto.getParentObjectId());
			}
			
			historyItemsRepository.save(historyItem);
		}
	}

	
	private int processClassName(String className) {
		Optional<HistoryClassName> historyClassName = historyClassNamesRepository.findByClassName(className);
		if (historyClassName.isPresent()) {
			return historyClassName.get().getId();
		} else {
			HistoryClassName newHCT = new HistoryClassName();
			newHCT.setClassName(className);
			historyClassNamesRepository.save(newHCT);
			return newHCT.getId();
		}
	}
	
	private int processClassProperty(String propertyName, int classId) {
		Optional<HistoryClassProperty> historyClassProperty = historyClassPropertiesRepository.findByPropertyNameAndClassId(propertyName, classId);
		if (historyClassProperty.isPresent()) {
			return historyClassProperty.get().getId();
		} else {
			HistoryClassProperty newHCP = new HistoryClassProperty();
			newHCP.setClassId(classId);
			newHCP.setPropertyName(propertyName);
			historyClassPropertiesRepository.save(newHCP);
			return newHCP.getId();
		}
	}
	
	private HistoryItem createHistoryItem(int operationType, int classId, int objectId, String value, int propertyId, Integer userId, String userName) {
		HistoryItem historyItem = new HistoryItem();
		historyItem.setOperationTypeId(operationType);
		historyItem.setObjectClassId(classId);
		historyItem.setObjectId(objectId);
		historyItem.setValue(value);
		historyItem.setDate(new Date());
		historyItem.setPropertyId(propertyId);
		historyItem.setUserId(userId);
		historyItem.setUserName(userName);
		
		return historyItem;
	}
}
