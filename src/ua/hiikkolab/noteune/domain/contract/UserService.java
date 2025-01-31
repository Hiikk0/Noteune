package ua.hiikkolab.noteune.domain.contract;

import ua.hiikkolab.noteune.domain.Service;
import ua.hiikkolab.noteune.persistence.entity.impl.User;
import ua.hiikkolab.noteune.domain.dto.UserDTO;

public interface UserService extends Service<User> {

  User getByUsername(String username);

  User getByEmail(String email);

  User add(UserDTO userDto);

  void updateUsername(User user, String newUsername);

  void updatePassword(User user, String newPassword);

  void updateEmail(User user, String newEmail);

  void updateAvatar(User user, String newAvatarPath);
}
