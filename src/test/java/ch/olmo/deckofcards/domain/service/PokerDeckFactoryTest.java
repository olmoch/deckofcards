package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.poker.PokerCard;
import ch.olmo.deckofcards.domain.entities.poker.Suit;
import ch.olmo.deckofcards.domain.entities.poker.Rank;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PokerDeckFactoryTest {
  private PokerDeckFactory pokerDeckFactory;

  @BeforeEach
  void beforeEach() {
    this.pokerDeckFactory = new PokerDeckFactory();
  }

  @Test
  void givenAPokerDeckFactory_whenCreatingADeck_thenItWillReturnADeckWith52Cards() {
    // when
    List<PokerCard> deck = pokerDeckFactory.createDeck();

    // then
    Assertions.assertThat(deck).hasSize(52);
  }

  @ParameterizedTest
  @MethodSource
  void givenAPokerDeckFactory_whenCreatingADeck_thenItWillReturnADeckWithTheCorrectCards(Suit suit, Rank value) {
    // when
    List<PokerCard> deck = pokerDeckFactory.createDeck();

    // then
    Assertions.assertThat(deck).contains(new PokerCard(suit, value));
  }

  private static Stream<Arguments> givenAPokerDeckFactory_whenCreatingADeck_thenItWillReturnADeckWithTheCorrectCards() {
    return Stream.of(Suit.values())
        .flatMap(suit -> Stream.of(Rank.values())
            .map(value -> Arguments.of(suit, value)));
  }
}