package ch.olmo.deckofcards.infrastructure.mappers;

import static ch.olmo.deckofcards.domain.entities.Rank.ACE;
import static ch.olmo.deckofcards.domain.entities.Suit.SPADES;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import ch.olmo.deckofcards.boot.DeckOfCardsApplication;
import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Deck;
import ch.olmo.deckofcards.domain.entities.Game;
import ch.olmo.deckofcards.infrastructure.dto.CardDto;
import ch.olmo.deckofcards.infrastructure.dto.GameDto;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DeckOfCardsApplication.class)
class GameMapperIntegrationTest {
  @Autowired
  private GameMapper gameMapper;

  @Test
  void givenAGame_whenMapping_thenItShouldReturnGameDto() {
    // given
    Game game = new Game(randomUUID(), new Deck(null, singletonList(new Card(SPADES, ACE))));
    game.addToHistory("entry");

    // when
    GameDto gameDto = gameMapper.toGameDto(game);

    // then
    assertThat(gameDto.getHistory()).containsExactly("entry");
    assertThat(gameDto.getCards()).containsExactly(CardDto.builder().rank(1).suit("Spades").build());
  }

  @Test
  void givenAGameDto_whenMapping_thenItShouldReturnGame() {
    // given
    GameDto gameDto = GameDto.builder()
        .history(singletonList("entry"))
        .cards(singletonList(
            CardDto.builder()
                .suit("Spades")
                .rank(1).build())).build();
    UUID id = randomUUID();

    // when
    Game game = gameMapper.toGame(gameDto, id);

    // then
    assertThat(game.getId()).isEqualTo(id);
    assertThat(game.getHistory()).containsExactly("entry");
    assertThat(game.getDeck().getCards()).containsExactly(new Card(SPADES, ACE));
  }

  @Test
  void givenAGameDto_whenMappingToDeck_thenItShouldCallDeckFactory() {
    // given
    CardDto cardDto = CardDto.builder().rank(1).suit("Spades").build();
    GameDto gameDto = GameDto.builder().cards(singletonList(cardDto)).build();

    // when
    Deck mapped = gameMapper.toDeck(gameDto);

    // then
    assertThat(mapped.getCards()).containsExactly(new Card(SPADES, ACE));
  }
}
