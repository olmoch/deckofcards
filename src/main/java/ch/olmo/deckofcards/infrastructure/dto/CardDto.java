package ch.olmo.deckofcards.infrastructure.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDto {
  String suit;
  int rank;
}
