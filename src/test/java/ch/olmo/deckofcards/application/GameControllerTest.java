package ch.olmo.deckofcards.application;

import static ch.olmo.deckofcards.domain.entities.Rank.ACE;
import static ch.olmo.deckofcards.domain.entities.Suit.SPADES;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;

import ch.olmo.deckofcards.application.dto.CardDto;
import ch.olmo.deckofcards.application.dto.GameDto;
import ch.olmo.deckofcards.application.mappers.RestGameMapper;
import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Game;
import ch.olmo.deckofcards.domain.service.CroupierService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {
  private static final UUID ID = randomUUID();
  private static final Game GAME = new Game(ID, null);

  @Mock
  private CroupierService croupierService;
  @Mock
  private RestGameMapper restGameMapper;

  private GameController gameController;

  @BeforeEach
  void beforeEach() {
    this.gameController = new GameController(croupierService, restGameMapper);
  }

  @Test
  void givenACreateRequestWithNoShuffle_whenExecuting_thenItShouldReturnTheId() {
    // given
    UUID id = randomUUID();
    Game game = new Game(id, null);

    willReturn(game).given(croupierService).startGame();

    // when
    UUID created = gameController.create(false);

    // then
    then(croupierService).should(never()).shuffle(any(UUID.class));
    assertThat(created).isEqualTo(id);
  }

  @Test
  void givenACreateRequestWithShuffle_whenExecuting_thenItShouldCreateThenShuffleThenReturnTheId() {
    // given
    willReturn(GAME).given(croupierService).startGame();
    willDoNothing().given(croupierService).shuffle(any(UUID.class));

    // when
    UUID created = gameController.create(true);

    // then
    InOrder inOrder = inOrder(croupierService);
    then(croupierService).should(inOrder).startGame();
    then(croupierService).should(inOrder).shuffle(ID);

    assertThat(created).isEqualTo(ID);
  }

  @Test
  void givenAReadRequest_whenExecuting_thenItShouldReturnTheMappedResultFromTheCroupier() {
    // given
    GameDto gameDto = GameDto.builder().history(List.of("entry")).build();
    willReturn(GAME).given(croupierService).getGame(ID);
    willReturn(gameDto).given(restGameMapper).toGameDto(GAME);

    // when
    GameDto read = gameController.read(ID);

    // then
    assertThat(read).isEqualTo(gameDto);
  }

  @Test
  void givenAShuffleRequest_whenExecuting_thenItShouldShuffleAndThenReadAndReturnTheMappedResultFromTheCroupier() {
    // given
    GameDto gameDto = GameDto.builder().history(List.of("entry")).build();
    willReturn(GAME).given(croupierService).getGame(any(UUID.class));
    willDoNothing().given(croupierService).shuffle(any(UUID.class));
    willReturn(gameDto).given(restGameMapper).toGameDto(GAME);

    // when
    GameDto read = gameController.shuffle(ID);

    // then
    InOrder inOrder = inOrder(croupierService, restGameMapper);
    then(croupierService).should(inOrder).shuffle(ID);
    then(croupierService).should(inOrder).getGame(ID);
    then(restGameMapper).should(inOrder).toGameDto(GAME);
    inOrder.verifyNoMoreInteractions();

    assertThat(read).isEqualTo(gameDto);
  }

  @Test
  void givenADealRequest_whenExecuting_thenItShouldDealAndReturnTheMappedResultFromTheCroupier() {
    // given
    CardDto cardDto = CardDto.builder().suit("Spades").rank(1).build();
    willReturn(new Card(SPADES, ACE)).given(croupierService).deal(ID);
    willReturn(cardDto).given(restGameMapper).toCardDto(new Card(SPADES, ACE));

    // when
    CardDto read = gameController.deal(ID);

    // then
    assertThat(read).isEqualTo(cardDto);
  }
}