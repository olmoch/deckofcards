package ch.olmo.deckofcards.domain.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.exception.NoCardsRemainingException;
import ch.olmo.deckofcards.domain.exception.NoDeckException;
import java.util.ArrayList;
import java.util.List;

public class DealerImpl<C extends Card> implements Dealer<C> {
  private final DeckShuffler shuffler;

  private List<C> deck;

  DealerImpl(DeckShuffler shuffler, List<C> deck) {
    this.shuffler = shuffler;
    if (nonNull(deck)) {
      this.deck = new ArrayList<>(deck);
    }
  }

  @Override
  public void shuffle() {
    if (isNull(deck)) {
      throw new NoDeckException();
    }

    this.deck = new ArrayList<>(shuffler.shuffle(deck));
  }

  @Override
  public C dealOneCard() {
    if (isNull(deck)) {
      throw new NoDeckException();
    } else if (deck.isEmpty()) {
      throw new NoCardsRemainingException();
    }
    return deck.remove(0);
  }
}
