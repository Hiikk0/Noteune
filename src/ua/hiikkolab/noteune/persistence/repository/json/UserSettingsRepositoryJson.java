package ua.hiikkolab.noteune.persistence.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.impl.UserSettings;
import ua.hiikkolab.noteune.persistence.repository.contracts.UserSettingsRepository;

public class UserSettingsRepositoryJson extends StandartJsonRepository<UserSettings> implements
    UserSettingsRepository {
  public UserSettingsRepositoryJson(Gson gson) {
      super(gson, JsonPaths.USERSETTINGS.getPath(), TypeToken
          .getParameterized(Set.class, UserSettings.class)
          .getType());
  }

  @Override
  public Optional<UserSettings> findSettings(UUID uuid) {
    return entities.stream().filter(u -> u.getId().equals(uuid)).findFirst();
  }

  @Override
  public Optional<UserSettings> findSettings(String username) {
    try {
      return entities.stream()
          .filter(u -> u.getUserName().equals(username)).findFirst();
    }
    catch (Exception e) {return Optional.empty();}
  }
}
