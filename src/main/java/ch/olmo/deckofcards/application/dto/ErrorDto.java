package ch.olmo.deckofcards.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
  String message;
}
