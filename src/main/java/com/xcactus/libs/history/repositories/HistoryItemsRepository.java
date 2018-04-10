package com.xcactus.libs.history.repositories;
/**
 * 
 */


import java.util.List;

import org.springframework.stereotype.Repository;

import com.xcactus.libs.commons.jpa.repository.base.FindByIdJpaRepository;
import com.xcactus.libs.history.jpa.entities.HistoryItem;

/**
 * Repozytorium obsługujące hitorie.<br />
 * Rozszerza nasze repozytorium {@link FindByIdJpaRepository}, które zawiera
 * generyczne metody do wyciągania encji po ID i kolekcji ID.
 * 
 * 
 * @author maciej.sowa
 */
@Repository
public interface HistoryItemsRepository extends FindByIdJpaRepository<HistoryItem, Long> {
	
	/**
	 * Metoda zwracająca listę objektów historii dla zadanego typu objektu z ID.<br />
	 * 
	 */
	List<HistoryItem> findByObjectClassIdAndObjectId(int objectClassId, int objectId);

	HistoryItem findFirst1ByObjectClassIdAndPropertyIdAndObjectIdOrderByIdDesc(int objectClassId, int propertyId, int objectId);
}
