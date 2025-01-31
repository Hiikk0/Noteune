package ua.hiikkolab.noteune.domain.exception;

public class InvalidMenuSelectionException extends RuntimeException {
  public InvalidMenuSelectionException(String message) {
    super(message);
  }
}
