/**
 * 
 */
package com.xcactus.libs.history.enums;

/**
 * @author maciej.sowa
 *
 */
public enum HistoryOperationType {
	SAVE(0),
	UPDATE(1);

	private int id;
	
	HistoryOperationType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
