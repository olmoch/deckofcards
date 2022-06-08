package ch.olmo.deckofcards.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ch.olmo.deckofcards")
public class DeckOfCardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeckOfCardsApplication.class, args);
	}
}
