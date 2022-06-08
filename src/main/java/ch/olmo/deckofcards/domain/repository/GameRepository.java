package ch.olmo.deckofcards.domain.repository;

import ch.olmo.deckofcards.domain.entities.Game;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository {

  void save(Game game);

  Optional<Game> retrieve(UUID id);
}
