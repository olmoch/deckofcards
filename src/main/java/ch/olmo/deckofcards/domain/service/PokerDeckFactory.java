package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.poker.PokerCard;
import ch.olmo.deckofcards.domain.entities.poker.Suit;
import ch.olmo.deckofcards.domain.entities.poker.Rank;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 * A {@link DeckFactory} that returns the full list of all 52 {@link PokerCard}s.
 */
@Component
public class PokerDeckFactory implements DeckFactory<PokerCard> {

  @Override
  public List<PokerCard> createDeck() {
    return Stream.of(Suit.values())
        .flatMap(this::createFor)
        .toList();
  }

  private Stream<PokerCard> createFor(Suit suit) {
    return Stream.of(Rank.values())
        .map(value -> new PokerCard(suit, value));
  }
}
