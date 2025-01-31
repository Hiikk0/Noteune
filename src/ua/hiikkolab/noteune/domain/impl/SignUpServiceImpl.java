package ua.hiikkolab.noteune.domain.impl;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import ua.hiikkolab.noteune.domain.contract.SignUpService;
import ua.hiikkolab.noteune.domain.contract.UserService;
import ua.hiikkolab.noteune.domain.dto.UserDTO;
import ua.hiikkolab.noteune.domain.exception.MailSenderException;
import ua.hiikkolab.noteune.domain.exception.SignUpException;

final class SignUpServiceImpl implements SignUpService {

  private static final int VERIFICATION_CODE_EXPIRATION_MINUTES = 5;
  private static LocalDateTime codeCreationTime;
  private final UserService userService;


  SignUpServiceImpl(UserService userService) {
    this.userService = userService;
  }

  private static final String HOST_NAME = "sandbox.smtp.mailtrap.io";
  private static final int SMTP_PORT = 2525;
  private static final String FROM_EMAIL = "from@example.com";
  public static void sendVerificationCodeEmail(String email, String verificationCode) {
    try {
      // provide Mailtrap's username
      final String username = "a91dd8d4449818";
      final String password = "2ebc0228ea4959";

      // configure Mailtrap's SMTP details
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", HOST_NAME);
      props.put("mail.smtp.port", SMTP_PORT);

      // create the Session object
      Session session = Session.getInstance(props,
          new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(username, password);
            }
          });

      try {
        // create a MimeMessage object
        Message message = new MimeMessage(session);
        // set From email field
        message.setFrom(new InternetAddress(FROM_EMAIL));
        // set To email field
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        // set email subject field
        message.setSubject("Your verification code");
        String body = String.format("Your verification code is: %s. Enjoy using Noteune!", verificationCode);
        // set the content of the email message
        message.setText(body);

        // send the email message
        Transport.send(message);

        System.out.println("Email Message Sent Successfully!");

      } catch (MessagingException e) {
        throw new MailSenderException(e.getMessage());
      }
    } catch (MailSenderException e) {
      throw new MailSenderException("Помилка при відправці електронного листа: " + e.getMessage());
    }
  }
  public void sendVerificationCode(String email) {
    generateAndSendVerificationCode(email);
  }
  public static String generateAndSendVerificationCode(String email) {
    // Генерація 6-значного коду
    String verificationCode = String.valueOf((int) (Math.random() * 900000 + 100000));

    sendVerificationCodeEmail(email, verificationCode);

    codeCreationTime = LocalDateTime.now();

    generatedCode = verificationCode;

    return verificationCode;
  }

  // Перевірка введеного коду
  private static void verifyCode(String inputCode, String generatedCode) {
    LocalDateTime currentTime = LocalDateTime.now();
    long minutesElapsed = ChronoUnit.MINUTES.between(codeCreationTime, currentTime);

    if (minutesElapsed > VERIFICATION_CODE_EXPIRATION_MINUTES) {
      throw new SignUpException("Час верифікації вийшов. Спробуйте ще раз.");
    }

    if (!inputCode.equals(generatedCode)) {
      throw new SignUpException("Невірний код підтвердження.");
    }

    // Скидання часу створення коду
    codeCreationTime = null;
  }

  private String username;
  private String password;
  private static String generatedCode;
  private UserDTO user;
  public void setUsername(String username) {
    this.username = username;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }
  public UserDTO getTmpUserDTO(){
    return user;
  }
  public void setTmpUserDTO(UserDTO user) {
    this.user = user;
  }
  public void signUp(UserDTO userAddDto, String userInput) {
    //Закоментувати для тестування
    //String verificationCode = generateAndSendVerificationCode(userAddDto.email());

    verifyCode(userInput, generatedCode);

    userService.add(userAddDto);

    ServiceFactory.getInstance().getAuthService().setUsername(getUsername());
    ServiceFactory.getInstance().getAuthService().authenticate(getPassword());
  }
}
