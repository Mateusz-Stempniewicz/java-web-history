package com.xcactus.libs.history.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xcactus.libs.history.repositories.configuration.HistoryRepositoryConfiguration;

/**
 * @author maciej.sowa
 *
 */
@Configuration
@ComponentScan({
	"com.xcactus.libs.history.aspect",
	"com.xcactus.libs.history.services"
	})
@Import({
    HistoryRepositoryConfiguration.class
})
public class HistoryLibraryConfiguration {

}
