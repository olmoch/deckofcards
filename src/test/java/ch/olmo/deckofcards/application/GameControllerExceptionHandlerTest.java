package ch.olmo.deckofcards.application;

import static org.assertj.core.api.Assertions.assertThat;

import ch.olmo.deckofcards.application.dto.ErrorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerExceptionHandlerTest {
  private GameControllerExceptionHandler exceptionHandler;

  @BeforeEach
  void beforeEach() {
    this.exceptionHandler = new GameControllerExceptionHandler();
  }

  @Test
  void givenAGameNotFoundException_whenHandling_thenItShouldReturnErrorMessage() {
    // when
    ErrorDto errorDto = exceptionHandler.handleGameNotFoundException();

    // then
    assertThat(errorDto.getMessage()).isEqualTo("Unable to find the requested game");
  }

  @Test
  void givenANoCardsRemainingException_whenHandling_thenItShouldReturnErrorMessage() {
    // when
    ErrorDto errorDto = exceptionHandler.handleNoCardsRemainingException();

    // then
    assertThat(errorDto.getMessage()).isEqualTo("No cards remaining in the requested game");
  }

  @Test
  void givenANoDeckException_whenHandling_thenItShouldReturnErrorMessage() {
    // when
    ErrorDto errorDto = exceptionHandler.handleNoDeckException();

    // then
    assertThat(errorDto.getMessage()).isEqualTo("Invalid deck");
  }

  @Test
  void givenAGenericException_whenHandling_thenItShouldReturnRawMessageFromException() {
    // when
    ErrorDto errorDto = exceptionHandler.handleException(new RuntimeException("Error!"));

    // then
    assertThat(errorDto.getMessage()).isEqualTo("Error!");
  }
}