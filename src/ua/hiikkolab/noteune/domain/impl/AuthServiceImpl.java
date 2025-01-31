package ua.hiikkolab.noteune.domain.impl;

import ua.hiikkolab.noteune.domain.contract.AuthService;
import ua.hiikkolab.noteune.persistence.entity.impl.User;
import ua.hiikkolab.noteune.domain.exception.UserAlreadyAuthException;
import ua.hiikkolab.noteune.domain.exception.AuthException;
import ua.hiikkolab.noteune.persistence.repository.contracts.UserRepository;

final class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private User user;

  AuthServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private String username;
  public void setUsername(String tmpUsername) {
    this.username = tmpUsername;
  }

  public boolean authenticate(String password) {
    // Перевіряємо, чи вже існує аутентифікований користувач
    if (user != null) {
      throw new UserAlreadyAuthException("Ви вже авторизувалися як: %s"
          .formatted(user.getUsername()));
    }

    if (username == null) { return false; }
    User foundedUser = userRepository.findByUsername(username)
        .orElseThrow(AuthException::new);

//    if (!BCrypt.checkpw(password, foundedUser.getPassword())) {
//      return false;
//    }
    //HashingUtil.hashPassword(password,foundedUser.getId().toString());
    if(!HashingUtil.checkPassword(password,foundedUser.getId().toString(),foundedUser.getPassword())) {
      return false;
    }

    user = foundedUser;
    return true;
  }

  public boolean isAuthenticated() {
    return user != null;
  }

  public User getUser() {
    return user;
  }

  public void logout() {
    if (user == null) {
      throw new UserAlreadyAuthException("Ви ще не автентифіковані.");
    }
    user = null;
  }
}
