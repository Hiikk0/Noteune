package ua.hiikkolab.noteune.domain.exception;

public class MailSenderException extends RuntimeException {

  public MailSenderException(String message) {
    super(message);
  }
}
