package ch.olmo.deckofcards.domain.entities.poker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Suit {
  HEARTS("Hearts"),
  SPADES("Spades"),
  CLUBS("Clubs"),
  DIAMONDS("Diamonds");

  private final String name;
}
