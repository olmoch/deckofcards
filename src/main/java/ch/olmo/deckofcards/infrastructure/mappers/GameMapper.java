package ch.olmo.deckofcards.infrastructure.mappers;

import static java.util.Collections.emptyList;
import static org.mapstruct.ReportingPolicy.ERROR;

import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Deck;
import ch.olmo.deckofcards.domain.entities.Game;
import ch.olmo.deckofcards.domain.entities.Rank;
import ch.olmo.deckofcards.domain.entities.Suit;
import ch.olmo.deckofcards.domain.service.DeckFactory;
import ch.olmo.deckofcards.infrastructure.dto.CardDto;
import ch.olmo.deckofcards.infrastructure.dto.GameDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public abstract class GameMapper {
  @Autowired
  DeckFactory deckFactory;

  @Mapping(target = "history", source = "history")
  @Mapping(target = "cards", source = "deck.cards")
  public abstract GameDto toGameDto(Game game);

  @Mapping(target = "suit", source = "suit.name")
  @Mapping(target = "rank", source = "rank.value")
  public abstract CardDto toCardDto(Card card);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "history", source = "gameDto.history")
  @Mapping(target = "deck", source = "gameDto")
  public abstract Game toGame(GameDto gameDto, UUID id);

  public abstract Card toCard(CardDto cardDto);

  public Deck toDeck(GameDto gameDto) {
    return deckFactory.createDeck(Optional.ofNullable(gameDto)
        .map(GameDto::getCards)
        .map(List::stream)
        .map(s -> s.map(this::toCard))
        .map(Stream::toList)
        .orElse(emptyList()));
  }

  public Suit toSuit(String name) {
    return Stream.of(Suit.values())
        .filter(suit -> suit.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  public Rank toRank(int value) {
    return Stream.of(Rank.values())
        .filter(suit -> suit.getValue() == value)
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
