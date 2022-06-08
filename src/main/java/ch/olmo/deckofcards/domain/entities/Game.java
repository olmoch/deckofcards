package ch.olmo.deckofcards.domain.entities;

import static org.springframework.util.StringUtils.hasText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Value;

@Value
public class Game {
  UUID id;
  Deck deck;
  List<String> history = new ArrayList<>();

  public void addToHistory(String action) {
    if (hasText(action)) {
      history.add(action);
    }
  }
}
