package ch.olmo.deckofcards.domain.service;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;

import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Game;
import ch.olmo.deckofcards.domain.repository.GameRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CroupierServiceImpl implements CroupierService {
  private final GameRepository gameRepository;
  private final PokerDeckFactory pokerDeckFactory;

  @Override
  public Game startGame() {
    Game game = new Game(randomUUID(), pokerDeckFactory.createDeck());
    gameRepository.save(game);

    return game;
  }

  @Override
  public Game getGame(UUID tableId) {
    return gameRepository.retrieve(tableId);
  }

  @Override
  public void shuffle(UUID tableId) {
    Game game = gameRepository.retrieve(tableId);
    game.getDeck().shuffle();
    game.addToHistory("shuffled");
  }

  @Override
  public Card deal(UUID tableId) {
    Game game = gameRepository.retrieve(tableId);
    Card card = game.getDeck().dealOneCard();
    game.addToHistory(format("dealt %s of %s", card.rank().getValue(), card.suit().getName()));
    return card;
  }
}
