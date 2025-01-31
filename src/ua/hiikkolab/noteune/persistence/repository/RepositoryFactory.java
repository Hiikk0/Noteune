package ua.hiikkolab.noteune.persistence.repository;
import org.apache.commons.lang3.NotImplementedException;
import ua.hiikkolab.noteune.persistence.repository.contracts.UserSettingsRepository;
import ua.hiikkolab.noteune.persistence.repository.contracts.UIRepository;
import ua.hiikkolab.noteune.persistence.repository.contracts.UserRepository;
import ua.hiikkolab.noteune.persistence.repository.json.GlobalSettingsRepositoryJson;
import ua.hiikkolab.noteune.persistence.repository.json.JsonRepositoryFactory;
public abstract class RepositoryFactory {

  public static final int JSON = 1;
  public static final int XML = 2;
  public static final int SQLITE = 3;

  public static RepositoryFactory getRepositoryFactory(int whichFactory) {
    return switch (whichFactory) {
      case JSON -> JsonRepositoryFactory.getInstance();
      case XML -> throw new NotImplementedException("Робота з XML файлами не реалізована.");
      case SQLITE -> throw new NotImplementedException(
          "Робота з СУБД не реалізована.");
      default -> throw new IllegalArgumentException(
          "Помилка при виборі фабрики репозиторіїв.");
    };
  }
  public abstract UserRepository getUserRepository();

  public abstract UIRepository getUIRepository();

  public abstract UserSettingsRepository getUserSettingsRepository();

  public abstract GlobalSettingsRepositoryJson getGlobalSettingsRepository();

  public abstract void save();
}
