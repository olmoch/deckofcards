package ch.olmo.deckofcards.domain.entities;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import ch.olmo.deckofcards.domain.exception.NoCardsRemainingException;
import ch.olmo.deckofcards.domain.exception.NoDeckException;
import ch.olmo.deckofcards.domain.service.Shuffler;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Deck {
  private final Shuffler shuffler;

  @Getter
  private ArrayList<Card> cards;

  public Deck(Shuffler shuffler, List<Card> cards) {
    this.shuffler = shuffler;
    if (nonNull(cards)) {
      this.cards = new ArrayList<>(cards);
    }
  }

  public void shuffle() {
    if (isNull(cards)) {
      throw new NoDeckException();
    }

    this.cards = new ArrayList<>(shuffler.shuffle(cards));
  }

  public Card dealOneCard() {
    if (isNull(cards)) {
      throw new NoDeckException();
    } else if (cards.isEmpty()) {
      throw new NoCardsRemainingException();
    }
    return cards.remove(0);
  }
}
