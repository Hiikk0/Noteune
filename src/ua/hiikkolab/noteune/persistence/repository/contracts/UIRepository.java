package ua.hiikkolab.noteune.persistence.repository.contracts;

import java.util.Optional;
import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.impl.TextScreen;
import ua.hiikkolab.noteune.persistence.repository.Repository;

public interface UIRepository extends Repository<TextScreen> {
  Optional<TextScreen> findViewByHeader(String header);
  Optional<TextScreen> findViewByUUID(UUID uuid);
}
