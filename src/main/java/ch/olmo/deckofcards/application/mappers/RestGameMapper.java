package ch.olmo.deckofcards.application.mappers;

import static org.mapstruct.ReportingPolicy.ERROR;

import ch.olmo.deckofcards.application.dto.CardDto;
import ch.olmo.deckofcards.application.dto.GameDto;
import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface RestGameMapper {
  @Mapping(target = "history", source = "history")
  @Mapping(target = "cards", source = "deck.cards")
  GameDto toGameDto(Game game);

  @Mapping(target = "suit", source = "suit.name")
  @Mapping(target = "rank", source = "rank.value")
  CardDto toCardDto(Card card);
}
