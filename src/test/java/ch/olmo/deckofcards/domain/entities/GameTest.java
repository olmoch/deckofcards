package ch.olmo.deckofcards.domain.entities;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameTest {
  @Mock
  private Deck deck;

  private Game game;

  @BeforeEach
  void beforeEach() {
    this.game = new Game(randomUUID(), deck);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void givenAGame_whenAddingNullOrBlankToHistory_thenItShouldNotModifyHistory(String entry) {
    // when
    game.addToHistory(entry);

    // then
    Assertions.assertThat(game.getHistory()).isEmpty();
  }

  @Test
  void givenAGame_whenAddingEntryToHistory_thenItShouldModifyHistory() {
    // given
    String entry = "added to history";

    // when
    game.addToHistory(entry);

    // then
    Assertions.assertThat(game.getHistory()).isNotEmpty().containsExactly(entry);
  }
}