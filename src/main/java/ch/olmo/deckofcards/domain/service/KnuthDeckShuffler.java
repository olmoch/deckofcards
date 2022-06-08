package ch.olmo.deckofcards.domain.service;

import static java.util.stream.IntStream.range;
import static org.springframework.util.Assert.notNull;

import ch.olmo.deckofcards.domain.entities.Card;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A {@link DeckShuffler} implementation that uses the Knuth shuffle.
 */
@Component
@RequiredArgsConstructor
public class KnuthDeckShuffler implements DeckShuffler {
  private final SecureRandom random;

  /**
   * Implements the Knuth shuffle (the Fisher-Yates algorithm). In order to avoid issues with
   * {@link UnsupportedOperationException} modifying the input list, a new {@link ArrayList} will
   * be created.
   *
   * @param deck a non-null {@link List} of {@link Card}s
   * @return the shuffled list
   */
  @Override
  public <C extends Card> List<C> shuffle(List<C> deck) {
    notNull(deck, "The deck cannot be null");

    ArrayList<C> shuffled = new ArrayList<>(deck);

    int size = shuffled.size();

    range(0, size - 1)
        .forEachOrdered(firstIndex -> {
          int exchangeIndex = random.nextInt(firstIndex, size);

          C firstCard = shuffled.get(firstIndex);
          C exchangeCard = shuffled.get(exchangeIndex);

          shuffled.set(firstIndex, exchangeCard);
          shuffled.set(exchangeIndex, firstCard);
        });

    return shuffled;
  }
}
