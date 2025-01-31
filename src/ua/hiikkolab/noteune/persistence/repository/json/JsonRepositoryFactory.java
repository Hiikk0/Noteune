package ua.hiikkolab.noteune.persistence.repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.Entity;
import ua.hiikkolab.noteune.persistence.entity.impl.GlobalSettings;
import ua.hiikkolab.noteune.persistence.exceptions.JsonFileIOException;
import ua.hiikkolab.noteune.persistence.repository.RepositoryFactory;
import ua.hiikkolab.noteune.persistence.repository.contracts.UserSettingsRepository;
import ua.hiikkolab.noteune.persistence.repository.contracts.UIRepository;
import ua.hiikkolab.noteune.persistence.repository.contracts.UserRepository;

public class JsonRepositoryFactory extends RepositoryFactory {
  private final Gson gson;
  private final UserRepositoryJson userRepositoryJson;
  private final UIRepositoryJson uiRepositoryJson;
  private final UserSettingsRepositoryJson userSettingsRepositoryJson;
  private final GlobalSettingsRepositoryJson globalSettingsRepositoryJson;

  private JsonRepositoryFactory() {
    // Адаптер для типу даних LocalDateTime при серіалізації/десеріалізації
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(LocalDateTime.class,
        (JsonSerializer<LocalDateTime>) (localDate, srcType, context) ->
            new JsonPrimitive(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(localDate)));
    gsonBuilder.registerTypeAdapter(LocalDateTime.class,
        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
            LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                    .withLocale(Locale.of("uk", "UA"))));

    // Адаптер для типу даних LocalDate при серіалізації/десеріалізації
    gsonBuilder.registerTypeAdapter(LocalDate.class,
        (JsonSerializer<LocalDate>) (localDate, srcType, context) ->
            new JsonPrimitive(
                DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDate)));
    gsonBuilder.registerTypeAdapter(LocalDate.class,
        (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
            LocalDate.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    .withLocale(Locale.of("uk", "UA"))));

    gson = gsonBuilder.setPrettyPrinting().create();

    userRepositoryJson = new UserRepositoryJson(gson);
    globalSettingsRepositoryJson = new GlobalSettingsRepositoryJson(gson);
    GlobalSettings globalSettings = globalSettingsRepositoryJson.findAll()
        .stream()
        .findFirst()
        .orElseGet(() -> new GlobalSettings(UUID.randomUUID(), "en", -1, -1));
    uiRepositoryJson = new UIRepositoryJson(gson, globalSettings.getLanguage().toLowerCase());
    userSettingsRepositoryJson = new UserSettingsRepositoryJson(gson);
  }
  public static JsonRepositoryFactory getInstance() {
    return Instance.INST;
  }

  @Override
  public UserRepository getUserRepository() {
    return userRepositoryJson;
  }
  @Override
  public UIRepository getUIRepository() {
    return uiRepositoryJson;
  }
  @Override
  public UserSettingsRepository getUserSettingsRepository(){
    return userSettingsRepositoryJson;
  }
  @Override
  public GlobalSettingsRepositoryJson getGlobalSettingsRepository(){
    return globalSettingsRepositoryJson;
  }
  private static class Instance {
    public static final JsonRepositoryFactory INST = new JsonRepositoryFactory();
  }
  public void save(){
    serialize(userRepositoryJson.getPath(), userRepositoryJson.findAll());
    serialize(userSettingsRepositoryJson.getPath(), userSettingsRepositoryJson.findAll());
    serialize(globalSettingsRepositoryJson.getPath(), globalSettingsRepositoryJson.findAll());
    //"serialize(uiRepositoryJson.getPath(), uiRepositoryJson.findAll());"
  }
  private <E extends Entity> void serialize(Path path, Set<E> entities) {
    try (FileWriter writer = new FileWriter(path.toFile())) {
      writer.write("");
      // Перетворюємо колекцію в JSON та записуємо у файл
      gson.toJson(entities, writer);

    } catch (IOException e) {
      throw new JsonFileIOException("Не вдалось зберегти дані у json-файл. Детальніше: %s"
          .formatted(e.getMessage()));
    }
  }
}
