package ch.olmo.deckofcards.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import ch.olmo.deckofcards.domain.entities.Deck;
import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Suit;
import ch.olmo.deckofcards.domain.entities.Rank;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

class PokerDeckFactoryTest {
  @Mock
  private Shuffler shuffler;

  private PokerDeckFactory pokerDeckFactory;

  @BeforeEach
  void beforeEach() {
    this.pokerDeckFactory = new PokerDeckFactory(shuffler);
  }

  @Test
  void givenAPokerDeckFactory_whenCreatingADeck_thenItWillReturnADeckWith52Cards() {
    // when
    Deck deck = pokerDeckFactory.createDeck();

    // then
    assertThat(deck.getCards()).hasSize(52);
  }

  @ParameterizedTest
  @MethodSource
  void givenAPokerDeckFactory_whenCreatingADeck_thenItWillReturnADeckWithTheCorrectCards(Suit suit, Rank value) {
    // when
    Deck deck = pokerDeckFactory.createDeck();

    // then
    assertThat(deck.getCards()).contains(new Card(suit, value));
  }

  public static Stream<Arguments> givenAPokerDeckFactory_whenCreatingADeck_thenItWillReturnADeckWithTheCorrectCards() {
    return Stream.of(Suit.values())
        .flatMap(suit -> Stream.of(Rank.values())
            .map(value -> Arguments.of(suit, value)));
  }
}