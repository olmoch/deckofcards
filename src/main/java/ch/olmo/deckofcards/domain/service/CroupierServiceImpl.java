package ch.olmo.deckofcards.domain.service;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;

import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Game;
import ch.olmo.deckofcards.domain.exception.GameNotFoundException;
import ch.olmo.deckofcards.domain.repository.GameRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CroupierServiceImpl implements CroupierService {
  private final GameRepository gameRepository;
  private final DeckFactory deckFactory;

  @Override
  public Game startGame() {
    Game game = new Game(randomUUID(), deckFactory.createDeck());
    gameRepository.save(game);

    return game;
  }

  @Override
  public Game getGame(UUID id) {
    return gameRepository.retrieve(id)
        .orElseThrow(GameNotFoundException::new);
  }

  @Override
  public void shuffle(UUID id) {
    Game game = gameRepository.retrieve(id)
        .orElseThrow(GameNotFoundException::new);
    game.getDeck().shuffle();
    game.addToHistory("shuffled");
    gameRepository.save(game);
  }

  @Override
  public Card deal(UUID id) {
    Game game = gameRepository.retrieve(id)
        .orElseThrow(GameNotFoundException::new);
    Card card = game.getDeck().dealOneCard();
    game.addToHistory(format("dealt %s of %s", card.rank().getValue(), card.suit().getName()));
    gameRepository.save(game);
    return card;
  }
}
