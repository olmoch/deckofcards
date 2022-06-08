package ch.olmo.deckofcards.infrastructure;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;

import ch.olmo.deckofcards.domain.entities.Deck;
import ch.olmo.deckofcards.domain.entities.Game;
import ch.olmo.deckofcards.domain.repository.GameRepository;
import ch.olmo.deckofcards.infrastructure.dto.GameDto;
import ch.olmo.deckofcards.infrastructure.mappers.GameMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InMemoryGameRepositoryTest {
  @Mock
  private GameMapper gameMapper;

  private GameRepository gameRepository;

  @BeforeEach
  void beforeEach() {
    this.gameRepository = new InMemoryGameRepository(gameMapper);
  }

  @Test
  void givenAGame_whenSavingAndRetrieving_thenItShouldReturnTheSameClaim() {
    // given
    UUID id = randomUUID();
    Game game = new Game(id, new Deck(null, emptyList()));

    GameDto gameDto = GameDto.builder().build();

    willReturn(gameDto).given(gameMapper).toGameDto(game);
    willReturn(game).given(gameMapper).toGame(gameDto, id);

    // when
    gameRepository.save(game);
    Optional<Game> retrieved = gameRepository.retrieve(id);

    // then
    assertThat(retrieved).isNotEmpty().contains(game);
  }
}