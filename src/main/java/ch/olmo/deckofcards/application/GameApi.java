package ch.olmo.deckofcards.application;

import ch.olmo.deckofcards.application.dto.CardDto;
import ch.olmo.deckofcards.application.dto.GameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
@Tag(name = "Poker API")
public interface GameApi {
  @PostMapping("")
  @Operation(summary = "Starts a new game")
  UUID create(@RequestParam(required = false, defaultValue = "false") boolean shuffle);

  @GetMapping("/{id}")
  @Operation(summary = "Retrieves the information of the given game")
  GameDto read(@PathVariable UUID id);

  @PostMapping("/{id}/shuffle")
  @Operation(summary = "Shuffles the deck of cards of the given game")
  GameDto shuffle(@PathVariable UUID id);

  @PostMapping("/{id}/deal")
  @Operation(summary = "Deals one card to the caller")
  CardDto deal(@PathVariable UUID id);
}
