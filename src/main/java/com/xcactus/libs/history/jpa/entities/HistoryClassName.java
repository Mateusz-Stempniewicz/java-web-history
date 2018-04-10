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
@Table(name = "history_classes")
public class HistoryClassName extends AbstractBaseEntity<Integer> {

	private static final long serialVersionUID = -3282176907682297458L;
	private String className;
	
	public HistoryClassName() {
	}

	public HistoryClassName(int id, String className) {
		super(id);
		this.className = className;
	}

	@NotNull
	@Size(max = 256)
	@Column(name = "class_name")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	

	
}
