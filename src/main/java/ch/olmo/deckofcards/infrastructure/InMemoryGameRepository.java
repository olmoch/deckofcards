package ch.olmo.deckofcards.infrastructure;

import ch.olmo.deckofcards.domain.entities.Game;
import ch.olmo.deckofcards.domain.repository.GameRepository;
import ch.olmo.deckofcards.infrastructure.dto.GameDto;
import ch.olmo.deckofcards.infrastructure.mappers.DbGameMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InMemoryGameRepository implements GameRepository {
  private static final Map<UUID, GameDto> GAME_DATABASE = new HashMap<>();
  private final DbGameMapper dbGameMapper;

  @Override
  public void save(Game game) {
    GAME_DATABASE.put(game.getId(), dbGameMapper.toGameDto(game));
  }

  @Override
  public Optional<Game> retrieve(UUID id) {
    return Optional.ofNullable(GAME_DATABASE.get(id))
        .map(gameDto -> dbGameMapper.toGame(gameDto, id));
  }
}
