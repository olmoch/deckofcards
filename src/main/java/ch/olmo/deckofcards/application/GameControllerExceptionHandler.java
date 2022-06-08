package ch.olmo.deckofcards.application;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import ch.olmo.deckofcards.application.dto.ErrorDto;
import ch.olmo.deckofcards.domain.exception.GameNotFoundException;
import ch.olmo.deckofcards.domain.exception.NoCardsRemainingException;
import ch.olmo.deckofcards.domain.exception.NoDeckException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GameControllerExceptionHandler {

  @ExceptionHandler(GameNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public ErrorDto handleGameNotFoundException() {
    return ErrorDto.builder().message("Unable to find the requested game").build();
  }

  @ExceptionHandler(NoCardsRemainingException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorDto handleNoCardsRemainingException() {
    return ErrorDto.builder().message("No cards remaining in the requested game").build();
  }

  @ExceptionHandler(NoDeckException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ErrorDto handleNoDeckException() {
    return ErrorDto.builder().message("Invalid deck").build();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ErrorDto handleException(Exception exception) {
    return ErrorDto.builder().message(exception.getMessage()).build();
  }
}
