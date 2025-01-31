package ua.hiikkolab.noteune.persistence.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.impl.TextScreen;
import ua.hiikkolab.noteune.persistence.repository.contracts.UIRepository;

public class UIRepositoryJson extends StandartJsonRepository<TextScreen> implements UIRepository {
  UIRepositoryJson(Gson gson, String language) {
    super(gson, getViewsPath(language), TypeToken
        .getParameterized(Set.class, TextScreen.class)
        .getType());
  }
  public static Path getViewsPath(String language) {
    Map<String,String> tmp = JsonPaths.loadLanguageFiles();
    try {
      String path = tmp.get(language);
      if (path == null) {
        return JsonPaths.VIEWS.getPath();
      }
      else {
        return Path.of(path);
      }
    } catch (Exception e) {
      return JsonPaths.VIEWS.getPath();
    }
  }
  @Override
  public Optional<TextScreen> findViewByHeader(String headerName) {
    return entities.stream().filter(u -> u.getHeader().equals(headerName)).findFirst();
  }
  @Override
  public Optional<TextScreen> findViewByUUID(UUID uuid) {
    return entities.stream().filter(u -> u.getId().equals(uuid)).findFirst();
  }
}
