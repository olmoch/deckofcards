package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.Card;
import java.util.List;
import org.springframework.lang.NonNull;

public interface DeckShuffler {

  /**
   * The method that defines the specific shuffling that the class will perform. The choice of
   * modifying the list in place or returning a new one is left to the implementation.
   */
  <C extends Card> List<C> shuffle(@NonNull List<C> deck);
}
