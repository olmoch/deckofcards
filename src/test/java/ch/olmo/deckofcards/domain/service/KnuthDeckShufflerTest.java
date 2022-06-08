package ch.olmo.deckofcards.domain.service;

import static ch.olmo.deckofcards.domain.entities.poker.Suit.DIAMONDS;
import static ch.olmo.deckofcards.domain.entities.poker.Suit.HEARTS;
import static ch.olmo.deckofcards.domain.entities.poker.Suit.SPADES;
import static ch.olmo.deckofcards.domain.entities.poker.Rank.ACE;
import static ch.olmo.deckofcards.domain.entities.poker.Rank.JACK;
import static ch.olmo.deckofcards.domain.entities.poker.Rank.QUEEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import ch.olmo.deckofcards.domain.entities.poker.PokerCard;
import java.security.SecureRandom;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnuthDeckShufflerTest {
  private SecureRandom random;
  private KnuthDeckShuffler deckShuffler;

  @BeforeEach
  void beforeEach() {
    this.random = mock(SecureRandom.class, withSettings().withoutAnnotations());
    this.deckShuffler = new KnuthDeckShuffler(random);
  }

  @Test
  void givenAShuffler_whenShufflingANullList_thenItShouldThrowAnIllegalArgumentException() {
    // when
    Throwable throwable = Assertions.catchThrowable(() -> deckShuffler.shuffle(null));

    // then
    assertThat(throwable).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The deck cannot be null");
  }

  @Test
  void givenAShuffler_whenShufflingADeck_thenItShouldReturnAListWithShuffledCards() {
    // given
    PokerCard card1 = new PokerCard(SPADES, ACE);
    PokerCard card2 = new PokerCard(DIAMONDS, JACK);
    PokerCard card3 = new PokerCard(HEARTS, QUEEN);

    willReturn(1, 2).given(random).nextInt(anyInt(), anyInt());

    // when
    List<ch.olmo.deckofcards.domain.entities.Card> shuffled = deckShuffler.shuffle(List.of(card1, card2, card3));

    // then
    assertThat(shuffled).containsExactly(card2, card3, card1);
  }
}