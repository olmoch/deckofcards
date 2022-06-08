package ch.olmo.deckofcards.boot.configuration;

import java.security.SecureRandom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeckOfCardsConfiguration {
  @Bean
  SecureRandom secureRandom() {
    return new SecureRandom();
  }
}
