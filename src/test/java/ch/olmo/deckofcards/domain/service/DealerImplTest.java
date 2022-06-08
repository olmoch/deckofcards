package ch.olmo.deckofcards.domain.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;

import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.poker.PokerCard;
import ch.olmo.deckofcards.domain.entities.poker.Suit;
import ch.olmo.deckofcards.domain.entities.poker.Rank;
import ch.olmo.deckofcards.domain.exception.NoCardsRemainingException;
import ch.olmo.deckofcards.domain.exception.NoDeckException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DealerImplTest {
  @Mock
  private DeckShuffler shuffler;

  private Dealer<Card> dealer;

  @Test
  void givenNoDeck_whenDealingACard_thenItShouldThrowANoDeckException() {
    // given
    this.dealer = new DealerImpl<>(shuffler, null);

    // when
    Throwable throwable = catchThrowable(dealer::dealOneCard);

    // then
    assertThat(throwable).isInstanceOf(NoDeckException.class);
  }

  @Test
  void givenAnEmptyDeck_whenDealingACard_thenItShouldThrowANoCardsRemainingException() {
    // given
    this.dealer = new DealerImpl<>(shuffler, emptyList());

    // when
    Throwable throwable = catchThrowable(dealer::dealOneCard);

    // then
    assertThat(throwable).isInstanceOf(NoCardsRemainingException.class);
  }

  @Test
  void givenADeckWithOneCard_whenDealingACard_thenItShouldReturnThatCard() {
    // given
    Card card = new PokerCard(Suit.SPADES, Rank.ACE);
    this.dealer = new DealerImpl<>(shuffler, singletonList(card));

    // when
    Card dealtCard = dealer.dealOneCard();

    // then
    assertThat(dealtCard).isEqualTo(card);
  }

  @Test
  void givenADeckWithOneCard_whenDealingTwoCards_thenItShouldReturnTheCardAndThenThrowNoCardsRemainingException() {
    // given
    Card card = new PokerCard(Suit.SPADES, Rank.ACE);
    this.dealer = new DealerImpl<>(shuffler, singletonList(card));

    // when
    Card dealtCard = dealer.dealOneCard();
    Throwable throwable = catchThrowable(dealer::dealOneCard);

    // then
    assertThat(dealtCard).isEqualTo(card);
    assertThat(throwable).isInstanceOf(NoCardsRemainingException.class);
  }

  @Test
  void givenNoDeck_whenShuffling_thenItShouldThrowANoDeckException() {
    // given
    this.dealer = new DealerImpl<>(shuffler, null);

    // when
    Throwable throwable = catchThrowable(dealer::shuffle);

    // then
    assertThat(throwable).isInstanceOf(NoDeckException.class);
  }

  @Test
  void givenADeck_whenShuffling_thenItShouldCallShufflerAndUpdateTheDeck() {
    // given
    Card card = new PokerCard(Suit.SPADES, Rank.ACE);
    this.dealer = new DealerImpl<>(shuffler, singletonList(card));
    Card shuffledCard = new PokerCard(Suit.SPADES, Rank.ACE);

    willReturn(singletonList(shuffledCard)).given(shuffler).shuffle(anyList());

    // when
    dealer.shuffle();

    // then
    then(shuffler).should().shuffle(singletonList(card));
    assertThat(dealer.dealOneCard()).isEqualTo(shuffledCard);
  }

  @Test
  void givenADeck_whenDealingOneCardAndShuffling_thenTheDealtCardShouldNotBeShuffled() {
    // given
    Card card = new PokerCard(Suit.SPADES, Rank.ACE);
    this.dealer = new DealerImpl<>(shuffler, singletonList(card));

    willReturn(emptyList()).given(shuffler).shuffle(anyList());

    // when
    Card dealtCard = dealer.dealOneCard();
    dealer.shuffle();

    // then
    then(shuffler).should().shuffle(emptyList());
    assertThat(dealtCard).isEqualTo(card);
  }
}