package ch.olmo.deckofcards.infrastructure.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import ch.olmo.deckofcards.domain.entities.Rank;
import ch.olmo.deckofcards.domain.entities.Suit;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DbGameMapperTest {
  private DbGameMapper dbGameMapper;

  @BeforeEach
  void beforeEach() {
    this.dbGameMapper = new DbGameMapperImpl();
  }

  @ParameterizedTest
  @MethodSource
  void givenASuitName_whenMappingToSuit_thenItShouldMapCorrectly(Suit suit, String name) {
    // when
    Suit mapped = dbGameMapper.toSuit(name);

    // then
    assertThat(mapped).isEqualTo(suit);
  }

  private static Stream<Arguments> givenASuitName_whenMappingToSuit_thenItShouldMapCorrectly() {
    return Stream.of(Suit.values())
        .map(suit -> Arguments.of(suit, suit.getName()));
  }

  @ParameterizedTest
  @MethodSource
  void givenARankName_whenMappingToRank_thenItShouldMapCorrectly(Rank rank, int value) {
    // when
    Rank mapped = dbGameMapper.toRank(value);

    // then
    assertThat(mapped).isEqualTo(rank);
  }

  private static Stream<Arguments> givenARankName_whenMappingToRank_thenItShouldMapCorrectly() {
    return Stream.of(Rank.values())
        .map(rank -> Arguments.of(rank, rank.getValue()));
  }
}