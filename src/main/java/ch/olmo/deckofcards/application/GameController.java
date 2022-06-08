package ch.olmo.deckofcards.application;

import ch.olmo.deckofcards.application.dto.CardDto;
import ch.olmo.deckofcards.application.dto.GameDto;
import ch.olmo.deckofcards.application.mappers.RestGameMapper;
import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Game;
import ch.olmo.deckofcards.domain.service.CroupierService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameController implements GameApi {
  private final CroupierService croupierService;
  private final RestGameMapper restGameMapper;

  @Override
  public UUID create(boolean shuffle) {
    Game game = croupierService.startGame();
    if (shuffle) {
      croupierService.shuffle(game.getId());
    }
    return game.getId();
  }

  @Override
  public GameDto read(UUID id) {
    Game game = croupierService.getGame(id);
    return restGameMapper.toGameDto(game);
  }

  @Override
  public GameDto shuffle(UUID id) {
    croupierService.shuffle(id);
    Game game = croupierService.getGame(id);
    return restGameMapper.toGameDto(game);
  }

  @Override
  public CardDto deal(UUID id) {
    Card card = croupierService.deal(id);
    return restGameMapper.toCardDto(card);
  }
}
