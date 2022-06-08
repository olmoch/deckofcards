package ch.olmo.deckofcards.domain.repository;

import ch.olmo.deckofcards.domain.entities.Game;
import java.util.UUID;

public interface GameRepository {

  void save(Game game);

  Game retrieve(UUID id);
}
