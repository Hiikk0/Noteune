package ua.hiikkolab.noteune.domain.impl;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.apache.commons.lang3.NotImplementedException;
import ua.hiikkolab.noteune.domain.contract.UserService;
import ua.hiikkolab.noteune.domain.dto.UserDTO;
import ua.hiikkolab.noteune.domain.exception.EntityNotFoundException;
import ua.hiikkolab.noteune.persistence.entity.impl.User;
import ua.hiikkolab.noteune.persistence.repository.contracts.UserRepository;

final class UserServiceImpl
    extends BasicService<User>
    implements UserService {

  //TODO: Чому б не використати той екземпляр, що збережений в BasicService???
  private final UserRepository userRepository;

  UserServiceImpl(UserRepository userRepository) {
    super(userRepository);
    this.userRepository = userRepository;
  }

  @Override
  public User getByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("Такого користувача не існує."));
  }

  @Override
  public User getByEmail(String email) {
    return userRepository.findByUsername(email)
        .orElseThrow(() -> new EntityNotFoundException("Такого користувача не існує."));
  }

  @Override
  public Set<User> getAll() {
    return getAll(u -> true);
  }

  @Override
  public Set<User> getAll(Predicate<User> filter) {
    return new TreeSet<>(userRepository.findAll(filter));
  }

  @Override
  public User add(User entity) {
    throw new NotImplementedException(
        "Помилка архітектури, так як ми не використовували DTO та маппінг. "
            + "Прошу використовувати User add(UserAddDto userAddDto) версію.");
  }

  @Override
  public User add(UserDTO userDto) {
    try {
      String avatar = ImageUtil.copyToImages(userDto.avatarPath());
      var user = new User(userDto.getId(),
          userDto.username(),
          userDto.email(),
          //BCrypt.hashpw(userDto.validPassword(), BCrypt.gensalt()),
          HashingUtil.hashPassword(userDto.validPassword(), userDto.getId().toString()),
          Optional.of(avatar));
      userRepository.add(user);
      return user;
    } catch (IOException e) {
//      throw new SignUpException("Помилка при збереженні аватара користувача: %s"
//          .formatted(e.getMessage()));

    }
    var user = new User(userDto.getId(),
        userDto.username(),
        userDto.email(),
        //BCrypt.hashpw(userDto.validPassword(), BCrypt.gensalt()),
        HashingUtil.hashPassword(userDto.validPassword(), userDto.getId().toString()),
        Optional.empty());
    userRepository.add(user);
    return user;
  }
  @Override
  public void updateUsername(User user, String newUsername) {
    update(new UserDTO(user.getId(), newUsername, user.getEmail(), user.getPassword(),
        Optional.ofNullable(user.getAvatar()), 0));
  }
  private void update(UserDTO userDTO){
    String avatar = "";
    try {
      avatar = ImageUtil.copyToImages(userDTO.avatarPath());
    }
    catch (IOException e) {
//      throw new SignUpException("Помилка при збереженні аватара користувача: %s"
//          .formatted(e.getMessage()));
    }
    var user = new User(userDTO.getId(),
        userDTO.username(),
        userDTO.email(),
        //BCrypt.hashpw(userDTO.validPassword(), BCrypt.gensalt()),
        userDTO.validPassword(),
        Optional.of(avatar));
    userRepository.update(user);
  }
  @Override
  public void updatePassword(User user, String newPassword) {
    UserDTO tmpDTO = new UserDTO(user.getId(),user.getUsername(),user.getEmail(),
        newPassword,
        Optional.ofNullable(user.getAvatar()));

    update(new UserDTO(tmpDTO.getId(),tmpDTO.username(),tmpDTO.email(),
        //BCrypt.hashpw(tmpDTO.validPassword(), BCrypt.gensalt()),
        HashingUtil.hashPassword(tmpDTO.validPassword(), tmpDTO.getId().toString()),
        Optional.ofNullable(tmpDTO.avatarPath()),0));
  }
  @Override
  public void updateEmail(User user, String newEmail) {
    update(new UserDTO(user.getId(),user.getUsername(),newEmail,user.getPassword(),
        Optional.ofNullable(user.getAvatar()),0));
  }
  @Override
  public void updateAvatar(User user, String newAvatarPath) {
    update(new UserDTO(user.getId(),user.getUsername(),user.getEmail(),user.getPassword(),
        Optional.ofNullable(newAvatarPath),0));
  }
}
