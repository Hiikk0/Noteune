package ua.hiikkolab.noteune.domain.contract;

import ua.hiikkolab.noteune.persistence.entity.impl.User;

public interface AuthService {

  void setUsername(String username);
  boolean authenticate(String password);

  boolean isAuthenticated();

  User getUser();

  void logout();
}
