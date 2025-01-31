package ua.hiikkolab.noteune.persistence.repository.contracts;

import java.util.Optional;
import ua.hiikkolab.noteune.persistence.entity.impl.User;
import ua.hiikkolab.noteune.persistence.repository.Repository;

public interface UserRepository extends Repository<User> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);
  void update(User user);
}
