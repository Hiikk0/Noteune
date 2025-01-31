package ua.hiikkolab.noteune.domain.contract;

import ua.hiikkolab.noteune.domain.dto.UserDTO;

public interface SignUpService {
  void signUp(UserDTO userAddDto, String waitForUserInput);
  String getUsername();
  String getPassword();
  void setPassword(String password);
  void setUsername(String username);
  void sendVerificationCode(String email);

  UserDTO getTmpUserDTO();
  void setTmpUserDTO(UserDTO userDTO);
}
