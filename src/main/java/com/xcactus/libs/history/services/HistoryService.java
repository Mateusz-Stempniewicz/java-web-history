/**
 * 
 */
package com.xcactus.libs.history.services;

import com.xcactus.libs.history.dto.HistoryItemDto;

/**
 * Interfejs uslugi do obsługi historii
 * 
 * @author maciej.sowa
 */
public interface HistoryService {

	void saveHistoryItem(HistoryItemDto historyItemDto);
	
}
