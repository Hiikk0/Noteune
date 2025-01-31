package ua.hiikkolab.noteune.persistence.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Optional;
import java.util.Set;
import ua.hiikkolab.noteune.persistence.entity.impl.User;
import ua.hiikkolab.noteune.persistence.repository.contracts.UserRepository;

public class UserRepositoryJson extends StandartJsonRepository<User> implements UserRepository {
  UserRepositoryJson(Gson gson) {
    super(gson, JsonPaths.USERS.getPath(), TypeToken
        .getParameterized(Set.class, User.class)
        .getType());
  }
  @Override
  public Optional<User> findByUsername(String username) {
    return entities.stream().filter(u -> u.getUsername().equals(username)).findFirst();
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return entities.stream().filter(u -> u.getEmail().equals(email)).findFirst();
  }
  @Override
  public void update(User user) {
    entities.removeIf(existingUser -> existingUser.getId().equals(user.getId()));
    entities.add(user);
  }
}
