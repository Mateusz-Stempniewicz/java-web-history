package com.xcactus.libs.history.repositories;
/**
 * 
 */


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.xcactus.libs.commons.jpa.repository.base.FindByIdJpaRepository;
import com.xcactus.libs.history.jpa.entities.HistoryClassProperty;

/**
 * Repozytorium obsługujące zbiór klas, które śledzone są przez mechanizm historii.<br />
 * Rozszerza nasze repozytorium {@link FindByIdJpaRepository}, które zawiera
 * generyczne metody do wyciągania encji po ID i kolekcji ID.
 * 
 * 
 * @author maciej.sowa
 */
@Repository
public interface HistoryClassPropertiesRepository extends FindByIdJpaRepository<HistoryClassProperty, Integer> {
	
	/**
	 * Metoda zwracająca listę properties w klasie historii.<br />
	 * 
	 */
	Optional<HistoryClassProperty> findByPropertyNameAndClassId(String propertyName, int classId);
	
	/**
	 * Metoda zwracająca listę objektów klas historii.<br />
	 * 
	 */
	List<HistoryClassProperty> findByClassId(int classId);

}
