package ch.olmo.deckofcards.domain.service;

import static ch.olmo.deckofcards.domain.entities.Suit.DIAMONDS;
import static ch.olmo.deckofcards.domain.entities.Suit.HEARTS;
import static ch.olmo.deckofcards.domain.entities.Suit.SPADES;
import static ch.olmo.deckofcards.domain.entities.Rank.ACE;
import static ch.olmo.deckofcards.domain.entities.Rank.JACK;
import static ch.olmo.deckofcards.domain.entities.Rank.QUEEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import ch.olmo.deckofcards.domain.entities.Card;
import java.security.SecureRandom;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnuthShufflerTest {
  private SecureRandom random;
  private KnuthShuffler deckShuffler;

  @BeforeEach
  void beforeEach() {
    this.random = mock(SecureRandom.class, withSettings().withoutAnnotations());
    this.deckShuffler = new KnuthShuffler(random);
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
    Card card1 = new Card(SPADES, ACE);
    Card card2 = new Card(DIAMONDS, JACK);
    Card card3 = new Card(HEARTS, QUEEN);

    willReturn(1, 2).given(random).nextInt(anyInt(), anyInt());

    // when
    List<Card> shuffled = deckShuffler.shuffle(List.of(card1, card2, card3));

    // then
    assertThat(shuffled).containsExactly(card2, card3, card1);
  }
}