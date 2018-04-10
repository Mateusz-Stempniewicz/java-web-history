package com.xcactus.libs.history.repositories;
/**
 * 
 */


import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.xcactus.libs.commons.jpa.repository.base.FindByIdJpaRepository;
import com.xcactus.libs.history.jpa.entities.HistoryClassName;

/**
 * Repozytorium obsługujące zbiór klas, które śledzone są przez mechanizm historii.<br />
 * Rozszerza nasze repozytorium {@link FindByIdJpaRepository}, które zawiera
 * generyczne metody do wyciągania encji po ID i kolekcji ID.
 * 
 * 
 * @author maciej.sowa
 */
@Repository
public interface HistoryClassNamesRepository extends FindByIdJpaRepository<HistoryClassName, Integer> {
	
	/**
	 * Metoda zwracająca objekt klasy historii.<br />
	 * 
	 */
	Optional<HistoryClassName> findByClassName(String className);

}
