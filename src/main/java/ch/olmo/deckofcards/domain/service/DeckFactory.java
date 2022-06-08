package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Deck;
import java.util.List;

/**
 * A generic class that creates a complete deck of cards. The choice of what class of {@link Card}
 * to use is left to the implementation.
 */
public interface DeckFactory {

  /**
   * Creates a deck of cards. There are no guarantees on whether the created list contains all
   * possible cards.
   *
   * @return the {@link Deck}
   */
  Deck createDeck();

  /**
   * Creates a deck of cards from the provided list.
   *
   * @param cards a {@link List} of {@link Card}s
   * @return the {@link Deck}
   */
  Deck createDeck(List<Card> cards);
}
