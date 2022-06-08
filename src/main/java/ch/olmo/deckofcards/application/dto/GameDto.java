package ch.olmo.deckofcards.application.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDto {
  List<String> history;
  List<CardDto> cards;
}
