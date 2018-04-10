/**
 * 
 */
package com.xcactus.libs.history.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.xcactus.libs.commons.jpa.entity.base.AbstractBaseEntity;

/**
 * @author maciej.sowa
 *
 */
@Entity
@Table(name = "history_class_properties")
public class HistoryClassProperty extends AbstractBaseEntity<Integer> {

	private static final long serialVersionUID = 3937572093271237223L;
	private int classId;
	private String propertyName;
	
	public HistoryClassProperty() {
		super();
	}

	public HistoryClassProperty(int id, int classId, String propertyName) {
		super(id);
		this.classId = classId;
		this.propertyName = propertyName;
	}

	@Column(name = "class_id")
	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	@NotNull
	@Size(max = 256)
	@Column(name = "property_name")
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}
