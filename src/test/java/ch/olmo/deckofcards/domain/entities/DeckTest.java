package ch.olmo.deckofcards.domain.entities;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;

import ch.olmo.deckofcards.domain.exception.NoCardsRemainingException;
import ch.olmo.deckofcards.domain.exception.NoDeckException;
import ch.olmo.deckofcards.domain.service.Shuffler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeckTest {
  @Mock
  private Shuffler shuffler;

  private Deck deck;

  @Test
  void givenNoDeck_whenDealingACard_thenItShouldThrowANoDeckException() {
    // given
    this.deck = new Deck(shuffler, null);

    // when
    Throwable throwable = catchThrowable(deck::dealOneCard);

    // then
    assertThat(throwable).isInstanceOf(NoDeckException.class);
  }

  @Test
  void givenAnEmptyDeck_whenDealingACard_thenItShouldThrowANoCardsRemainingException() {
    // given
    this.deck = new Deck(shuffler, emptyList());

    // when
    Throwable throwable = catchThrowable(deck::dealOneCard);

    // then
    assertThat(throwable).isInstanceOf(NoCardsRemainingException.class);
  }

  @Test
  void givenADeckWithOneCard_whenDealingACard_thenItShouldReturnThatCard() {
    // given
    Card card = new Card(Suit.SPADES, Rank.ACE);
    this.deck = new Deck(shuffler, singletonList(card));

    // when
    Card dealtCard = deck.dealOneCard();

    // then
    assertThat(dealtCard).isEqualTo(card);
  }

  @Test
  void givenADeckWithOneCard_whenDealingTwoCards_thenItShouldReturnTheCardAndThenThrowNoCardsRemainingException() {
    // given
    Card card = new Card(Suit.SPADES, Rank.ACE);
    this.deck = new Deck(shuffler, singletonList(card));

    // when
    Card dealtCard = deck.dealOneCard();
    Throwable throwable = catchThrowable(deck::dealOneCard);

    // then
    assertThat(dealtCard).isEqualTo(card);
    assertThat(throwable).isInstanceOf(NoCardsRemainingException.class);
  }

  @Test
  void givenNoDeck_whenShuffling_thenItShouldThrowANoDeckException() {
    // given
    this.deck = new Deck(shuffler, null);

    // when
    Throwable throwable = catchThrowable(deck::shuffle);

    // then
    assertThat(throwable).isInstanceOf(NoDeckException.class);
  }

  @Test
  void givenADeck_whenShuffling_thenItShouldCallShufflerAndUpdateTheDeck() {
    // given
    Card card = new Card(Suit.SPADES, Rank.ACE);
    this.deck = new Deck(shuffler, singletonList(card));
    Card shuffledCard = new Card(Suit.SPADES, Rank.ACE);

    willReturn(singletonList(shuffledCard)).given(shuffler).shuffle(anyList());

    // when
    deck.shuffle();

    // then
    then(shuffler).should().shuffle(singletonList(card));
    assertThat(deck.dealOneCard()).isEqualTo(shuffledCard);
  }

  @Test
  void givenADeck_whenDealingOneCardAndShuffling_thenTheDealtCardShouldNotBeShuffled() {
    // given
    Card card = new Card(Suit.SPADES, Rank.ACE);
    this.deck = new Deck(shuffler, singletonList(card));

    willReturn(emptyList()).given(shuffler).shuffle(anyList());

    // when
    Card dealtCard = deck.dealOneCard();
    deck.shuffle();

    // then
    then(shuffler).should().shuffle(emptyList());
    assertThat(dealtCard).isEqualTo(card);
  }
}