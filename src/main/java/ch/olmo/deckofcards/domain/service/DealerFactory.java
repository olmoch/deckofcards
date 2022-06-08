package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.Card;
import java.util.List;

public interface DealerFactory {
  <C extends Card> Dealer<C> create(List<C> deck);
}
