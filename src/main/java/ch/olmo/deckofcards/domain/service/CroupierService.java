package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Game;
import java.util.UUID;

public interface CroupierService {
  Game startGame();
  Game getGame(UUID gameId);
  void shuffle(UUID tableId);
  Card deal(UUID tableId);
}
