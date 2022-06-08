package ch.olmo.deckofcards.domain.service;

import java.util.List;
import org.springframework.lang.NonNull;

public interface Shuffler {

  /**
   * The method that defines the specific shuffling that the class will perform. The choice of
   * modifying the list in place or returning a new one, as well as the choice of which generic
   * types to support, is left to the implementation.
   */
  <T> List<T> shuffle(@NonNull List<T> list);
}
