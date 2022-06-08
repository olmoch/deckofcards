package ch.olmo.deckofcards.domain.service;

import static ch.olmo.deckofcards.domain.entities.Rank.ACE;
import static ch.olmo.deckofcards.domain.entities.Suit.SPADES;
import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

import ch.olmo.deckofcards.domain.entities.Card;
import ch.olmo.deckofcards.domain.entities.Deck;
import ch.olmo.deckofcards.domain.entities.Game;
import ch.olmo.deckofcards.domain.repository.GameRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CroupierServiceImplTest {
  @Mock
  private GameRepository gameRepository;
  @Mock
  private PokerDeckFactory pokerDeckFactory;

  private CroupierService croupierService;

  @BeforeEach
  void beforeEach() {
    this.croupierService = new CroupierServiceImpl(gameRepository, pokerDeckFactory);
  }

  @Test
  void givenACroupier_whenStartingAGame_thenItShouldRetrieveADeckFromTheFactoryAndSaveTheGame() {
    // given
    Deck deck = new Deck(null, emptyList());
    willReturn(deck).given(pokerDeckFactory).createDeck();

    // when
    Game createdGame = croupierService.startGame();

    // then
    then(pokerDeckFactory).should().createDeck();
    then(gameRepository).should().save(createdGame);
    assertThat(createdGame.getDeck()).isEqualTo(deck);
  }

  @Test
  void givenACroupierAndAnId_whenGettingAGame_thenItShouldRetrieveTheGameFromTheRepository() {
    // given
    UUID id = randomUUID();
    Game game = new Game(id, null);
    willReturn(game).given(gameRepository).retrieve(any(UUID.class));

    // when
    Game retrievedGame = croupierService.getGame(id);

    // then
    then(gameRepository).should().retrieve(id);
    assertThat(retrievedGame).isEqualTo(game);
  }

  @Test
  void givenACroupier_whenShuffling_thenItShouldRetrieveGameFromTheRepositoryAndShuffleItsDeck() {
    // given
    UUID id = randomUUID();
    Deck deck = mock(Deck.class);
    Game game = new Game(id, deck);

    willReturn(game).given(gameRepository).retrieve(any(UUID.class));

    // when
    croupierService.shuffle(id);

    // then
    then(gameRepository).should().retrieve(id);
    then(deck).should().shuffle();
  }

  @Test
  void givenACroupier_whenDealing_thenItShouldRetrieveGameFromTheRepositoryAndDealOneCard() {
    // given
    UUID id = randomUUID();
    Deck deck = mock(Deck.class);
    Game game = new Game(id, deck);

    willReturn(game).given(gameRepository).retrieve(any(UUID.class));
    willReturn(new Card(SPADES, ACE)).given(deck).dealOneCard();

    // when
    Card dealtCard = croupierService.deal(id);

    // then
    then(gameRepository).should().retrieve(id);
    then(deck).should().dealOneCard();
    assertThat(dealtCard).isEqualTo(new Card(SPADES, ACE));
  }
}