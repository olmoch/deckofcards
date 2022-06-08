package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Deck;
import ch.olmo.deckofcards.domain.entities.Rank;
import ch.olmo.deckofcards.domain.entities.Suit;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A {@link DeckFactory} that returns the full list of all 52 {@link Card}s.
 */
@Component
@RequiredArgsConstructor
public class PokerDeckFactory implements DeckFactory {
  private final Shuffler shuffler;

  @Override
  public Deck createDeck() {
    return createDeck(getPokerCards());
  }

  @Override
  public Deck createDeck(List<Card> cards) {
    return new Deck(shuffler, cards);
  }

  public static List<Card> getPokerCards() {
    return Stream.of(Suit.values())
        .flatMap(PokerDeckFactory::createFor)
        .toList();
  }

  private static Stream<Card> createFor(Suit suit) {
    return Stream.of(Rank.values())
        .map(value -> new Card(suit, value));
  }
}
