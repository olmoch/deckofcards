package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.Card;
import java.util.List;

/**
 * A generic class that creates a complete deck of cards. The choice of what class of {@link Card}
 * to use is left to the implementation.
 */
public interface DeckFactory<C extends Card> {

  /**
   * Creates the deck of cards of the class' type. There are no guarantees on whether the list
   * is exhaustive, i.e. contains all possible cards.
   *
   * @return the {@link List} of {@link Card}s
   */
  List<C> createDeck();
}
