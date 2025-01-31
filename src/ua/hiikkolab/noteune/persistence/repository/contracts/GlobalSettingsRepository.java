package ua.hiikkolab.noteune.persistence.repository.contracts;

import java.util.Optional;
import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.impl.GlobalSettings;
import ua.hiikkolab.noteune.persistence.repository.Repository;

public interface GlobalSettingsRepository extends Repository<GlobalSettings> {
  Optional<GlobalSettings> findSettings(UUID uuid);
}
