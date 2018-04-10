/**
 * 
 */
package com.xcactus.libs.history.dto;

import java.util.Date;

/**
 * @author maciej.sowa
 *
 */
public class HistoryItemDto {
	
	private long id;
	private int operationTypeId;
	private String objectClassName;
	private int objectId;
	private String propertyName;
	private String value;
	private String userName;
	private Integer userId;
	private Date date;
	private String parentClassType;
	private Integer parentObjectId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getOperationTypeId() {
		return operationTypeId;
	}
	public void setOperationTypeId(int operationTypeId) {
		this.operationTypeId = operationTypeId;
	}
	public String getObjectClassName() {
		return objectClassName;
	}
	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}
	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getParentClassType() {
		return parentClassType;
	}
	public void setParentClassType(String parentClassType) {
		this.parentClassType = parentClassType;
	}
	public Integer getParentObjectId() {
		return parentObjectId;
	}
	public void setParentObjectId(Integer parentObjectId) {
		this.parentObjectId = parentObjectId;
	}
	
}
