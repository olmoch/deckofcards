package ch.olmo.deckofcards.domain.service;

import ch.olmo.deckofcards.domain.entities.Card;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DealerFactoryImpl implements DealerFactory {
  private final DeckShuffler deckShuffler;

  @Override
  public <C extends Card> Dealer<C> create(List<C> deck) {
    return new DealerImpl<>(deckShuffler, deck);
  }
}
