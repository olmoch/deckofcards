package ch.olmo.deckofcards.domain.entities.poker;

public record PokerCard(Suit suit, Rank rank) implements ch.olmo.deckofcards.domain.entities.Card {
  @Override
  public String getSuit() {
    return suit.getName();
  }

  @Override
  public int getValue() {
    return rank.getValue();
  }
}
