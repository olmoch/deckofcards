package ch.olmo.deckofcards.infrastructure.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDto {
  private List<CardDto> cards;
  private List<String> history;
}
