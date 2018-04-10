/**
 * 
 */
package com.xcactus.libs.history.jpa.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author maciej.sowa
 *
 */
@Entity
@Table(name = "history_items")
public class HistoryItem {

	private long id;
	private int operationTypeId;
	private int objectClassId;
	private int objectId;
	private int propertyId;
	private String value;
	private String userName;
	private Integer userId;
	private Date date;
	private Integer parentClassTypeId;
	private Integer parentObjectId;

	public HistoryItem() {
	}

	public HistoryItem(int operationTypeId, int objectClassId, int objectId, int propertyId, String value,
			String userName, Integer userId, Date date, Integer parentClassTypeId,
			Integer parentObjectId) {
		super();
		this.operationTypeId = operationTypeId;
		this.objectClassId = objectClassId;
		this.objectId = objectId;
		this.propertyId = propertyId;
		this.value = value;
		this.userName = userName;
		this.userId = userId;
		this.date = date;
		this.parentClassTypeId = parentClassTypeId;
		this.parentObjectId = parentObjectId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@NotNull
	@Column(name = "operation_type_id")
	public int getOperationTypeId() {
		return operationTypeId;
	}

	public void setOperationTypeId(int operationTypeId) {
		this.operationTypeId = operationTypeId;
	}

	@NotNull
	@Column(name = "object_class_id")
	public int getObjectClassId() {
		return objectClassId;
	}

	public void setObjectClassId(int objectClassId) {
		this.objectClassId = objectClassId;
	}

	@NotNull
	@Column(name = "object_id")
	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	@NotNull
	@Column(name = "property_id")
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	@Size(max = 512)
	@Column(name = "value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Size(max = 256)
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "parent_class_type_id")
	public Integer getParentClassTypeId() {
		return parentClassTypeId;
	}

	public void setParentClassTypeId(Integer parentClassTypeId) {
		this.parentClassTypeId = parentClassTypeId;
	}

	@Column(name = "parent_object_id")
	public Integer getParentObjectId() {
		return parentObjectId;
	}

	public void setParentObjectId(Integer parentObjectId) {
		this.parentObjectId = parentObjectId;
	}

}
