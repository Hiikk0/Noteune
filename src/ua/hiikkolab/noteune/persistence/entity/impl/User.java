package ua.hiikkolab.noteune.persistence.entity.impl;

import java.util.Optional;
import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.Entity;

public class User extends Entity {

  private final String username;
  private final String email;
  private final String password;
  private final String avatar;
  public User(UUID id, String userName, String email, String password, Optional<String> avatar) {
    super(id);
    this.username = userName;
    this.email = email;
    this.password = password;
    this.avatar = avatar.orElse("");
  }
  public String getUsername() {
    return username;
  }
  public String getEmail() {
    return email;
  }
  public String getPassword() {
    return password;
  }
  public String getAvatar() {
    return avatar;
  }
  @Override
  public String toString() {
    return "User{" +
        "password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", username='" + username + '\'' +
        ", avatar='" + avatar + '\'' +
        ", id=" + id +
        '}';
  }
}
