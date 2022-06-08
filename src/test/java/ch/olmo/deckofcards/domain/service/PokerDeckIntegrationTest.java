package ch.olmo.deckofcards.domain.service;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import ch.olmo.deckofcards.boot.DeckOfCardsApplication;
import ch.olmo.deckofcards.domain.entities.Deck;
import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.exception.NoCardsRemainingException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DeckOfCardsApplication.class)
class PokerDeckIntegrationTest {
  private static final List<Card> POKER_DECK_CARDS = PokerDeckFactory.getPokerCards();
  private static final int POKER_DECK_SIZE = POKER_DECK_CARDS.size();

  @Autowired
  private DeckFactory pokerDeckFactory;

  private Deck pokerDeck;

  @BeforeEach
  void beforeEach() {
    this.pokerDeck = pokerDeckFactory.createDeck();
  }

  @Test
  void givenAPokerDeck_whenDealing52Cards_thenWeShouldReceiveAllCards() {
    // when
    List<Card> dealtCards = range(0, POKER_DECK_SIZE)
        .boxed()
        .map(i -> pokerDeck.dealOneCard())
        .toList();

    // then
    assertThat(dealtCards).hasSize(POKER_DECK_SIZE)
        .doesNotHaveDuplicates()
        .containsExactlyElementsOf(POKER_DECK_CARDS);
  }

  @Test
  void givenAPokerDeck_whenDealing53Cards_thenItShouldThrowANoCardsRemainingException() {
    // when
    range(0, POKER_DECK_SIZE)
        .forEach(i -> pokerDeck.dealOneCard());

    Throwable throwable = catchThrowable(pokerDeck::dealOneCard);

    // then
    assertThat(throwable).isInstanceOf(NoCardsRemainingException.class);
  }

  /**
   * This test should fail one time out of every 4 503 599 627 370 496 runs.
   */
  @Test
  void givenAPokerDeck_whenShufflingThenDealing52Cards_thenItShouldReturnDeckInADifferentOrder() {
    // when
    pokerDeck.shuffle();
    List<Card> dealtCards = range(0, POKER_DECK_SIZE)
        .boxed()
        .map(i -> pokerDeck.dealOneCard())
        .toList();

    // then
    assertThat(dealtCards).hasSize(POKER_DECK_SIZE)
        .containsExactlyInAnyOrderElementsOf(POKER_DECK_CARDS)
        .doesNotContainSequence(POKER_DECK_CARDS);
  }
}
