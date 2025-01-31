package ua.hiikkolab.noteune.domain.dto;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import ua.hiikkolab.noteune.persistence.entity.Entity;
import ua.hiikkolab.noteune.persistence.entity.ErrorTemplates;
import ua.hiikkolab.noteune.persistence.exceptions.EntityArgumentException;

public final class UserDTO extends Entity {
  private final String username;
  private final String password;
  private final String email;
  private final String avatarPath;

  public UserDTO(UUID id, String userName, String email, String password, Optional<String> avatar) {
    super(id, new HashSet<>());
    this.username = userName;
    this.email = email;
    this.password = validatedPassword(password);
    this.avatarPath = avatar.orElse("");
  }
  public UserDTO(UUID id, String userName, String email, String hashPassword, Optional<String> avatar, int i) {
    super(id, new HashSet<>());
    this.username = userName;
    this.email = email;
    this.password = hashPassword;
    this.avatarPath = avatar.orElse("");
  }
  private String validatedPassword(String rawPassword) {
    final String templateName = "паролю";

    if (rawPassword.isBlank()) {
      errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
    }
    if (rawPassword.length() < 8) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
    }
    if (rawPassword.length() > 32) {
      errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 32));
    }
    var pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$");
    if (!pattern.matcher(rawPassword).matches()) {
      errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName, 24));
    }

    if (!this.errors.isEmpty()) {
      throw new EntityArgumentException(errors);
    }

    return rawPassword;
  }

  public String username() {
    return username;
  }

  public String validPassword() {
    return password;
  }

  public String email() {
    return email;
  }

  public String avatarPath() {
    return avatarPath;
  }
}
