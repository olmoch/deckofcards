package ch.olmo.deckofcards.domain.service;

import static java.util.stream.IntStream.range;
import static org.springframework.util.Assert.notNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A {@link Shuffler} implementation that uses the Knuth shuffle.
 */
@Component
@RequiredArgsConstructor
public class KnuthShuffler implements Shuffler {
  private final SecureRandom random;

  /**
   * Implements the Knuth shuffle (the Fisher-Yates algorithm). In order to avoid issues with
   * {@link UnsupportedOperationException} modifying the input list, a new {@link ArrayList} will
   * be created.
   *
   * @param deck a non-null {@link List} of {@link Object}s
   * @return the shuffled list
   */
  @Override
  public <T> List<T> shuffle(List<T> deck) {
    notNull(deck, "The deck cannot be null");

    ArrayList<T> shuffled = new ArrayList<>(deck);

    int size = shuffled.size();

    range(0, size - 1)
        .forEachOrdered(firstIndex -> {
          int exchangeIndex = random.nextInt(firstIndex, size);

          T firstCard = shuffled.get(firstIndex);
          T exchangeCard = shuffled.get(exchangeIndex);

          shuffled.set(firstIndex, exchangeCard);
          shuffled.set(exchangeIndex, firstCard);
        });

    return shuffled;
  }
}
