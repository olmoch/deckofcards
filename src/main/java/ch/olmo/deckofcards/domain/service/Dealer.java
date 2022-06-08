package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.Card;

public interface Dealer<C extends Card> {

  void shuffle();

  C dealOneCard();
}
