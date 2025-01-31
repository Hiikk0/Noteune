package ua.hiikkolab.noteune.persistence.repository.contracts;

import java.util.Optional;
import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.impl.UserSettings;
import ua.hiikkolab.noteune.persistence.repository.Repository;

public interface UserSettingsRepository extends Repository<UserSettings> {
  Optional<UserSettings> findSettings(UUID uuid);
  Optional<UserSettings> findSettings(String username);
}
