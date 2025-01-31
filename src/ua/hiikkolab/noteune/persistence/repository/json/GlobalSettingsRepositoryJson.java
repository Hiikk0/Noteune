package ua.hiikkolab.noteune.persistence.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.impl.GlobalSettings;
import ua.hiikkolab.noteune.persistence.repository.contracts.GlobalSettingsRepository;

public class GlobalSettingsRepositoryJson extends StandartJsonRepository<GlobalSettings> implements
    GlobalSettingsRepository {
  public GlobalSettingsRepositoryJson(Gson gson) {
    super(gson, JsonPaths.GLOBALSETTINGS.getPath(), TypeToken
        .getParameterized(Set.class, GlobalSettings.class)
        .getType());
  }
  @Override
  public Optional<GlobalSettings> findSettings(UUID uuid) {
    return entities.stream().filter(u -> u.getId().equals(uuid)).findFirst();
  }
}
