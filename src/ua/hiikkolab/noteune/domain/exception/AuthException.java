package ua.hiikkolab.noteune.domain.exception;

public class AuthException extends RuntimeException {

  public AuthException() {
    super("Невірний логін чи пароль.");
  }
}
