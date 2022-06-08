package ch.olmo.deckofcards.domain.service;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import ch.olmo.deckofcards.boot.DeckOfCardsApplication;
import ch.olmo.deckofcards.domain.entities.poker.PokerCard;
import ch.olmo.deckofcards.domain.exception.NoCardsRemainingException;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DeckOfCardsApplication.class)
class PokerDealerIntegrationTest {
  private static final int POKER_DECK_SIZE = 52;

  @Autowired
  DeckFactory<PokerCard> pokerDeckFactory;

  @Autowired
  DealerFactory dealerFactory;

  @Test
  void givenAPokerDeck_whenDealing52Cards_thenWeShouldReceiveAllCards() {
    // given
    List<PokerCard> pokerDeck = pokerDeckFactory.createDeck();
    Dealer<PokerCard> dealer = dealerFactory.create(pokerDeck);

    // when
    List<PokerCard> dealtCards = range(0, POKER_DECK_SIZE)
        .boxed()
        .map(i -> dealer.dealOneCard())
        .toList();

    // then
    assertThat(dealtCards).hasSize(POKER_DECK_SIZE)
        .doesNotHaveDuplicates()
        .containsExactlyElementsOf(pokerDeck);
  }

  @Test
  void givenAPokerDeck_whenDealing53Cards_thenItShouldThrowANoCardsRemainingException() {
    // given
    List<PokerCard> pokerDeck = pokerDeckFactory.createDeck();
    Dealer<PokerCard> dealer = dealerFactory.create(pokerDeck);

    // when
    range(0, POKER_DECK_SIZE)
        .forEach(i -> dealer.dealOneCard());

    Throwable throwable = catchThrowable(dealer::dealOneCard);

    // then
    assertThat(throwable).isInstanceOf(NoCardsRemainingException.class);
  }

  /**
   * This test should fail one time out of every 4 503 599 627 370 496 runs.
   */
  @Test
  void givenAPokerDeck_whenShufflingThenDealing52Cards_thenItShouldReturnDeckInADifferentOrder() {
    // given
    List<PokerCard> pokerDeck = pokerDeckFactory.createDeck();
    Dealer<PokerCard> dealer = dealerFactory.create(pokerDeck);

    // when
    dealer.shuffle();
    List<PokerCard> dealtCards = range(0, POKER_DECK_SIZE)
        .boxed()
        .map(i -> dealer.dealOneCard())
        .toList();

    // then
    assertThat(dealtCards).hasSize(POKER_DECK_SIZE)
        .containsExactlyInAnyOrderElementsOf(pokerDeck)
        .doesNotContainSequence(pokerDeck);
  }
}
