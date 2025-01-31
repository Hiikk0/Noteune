package ua.hiikkolab.noteune.domain.impl;

import java.util.UUID;
import ua.hiikkolab.noteune.domain.contract.GlobalSettingsService;
import ua.hiikkolab.noteune.domain.dto.GlobalSettingsDTO;
import ua.hiikkolab.noteune.persistence.entity.impl.GlobalSettings;
import ua.hiikkolab.noteune.persistence.repository.Repository;
import ua.hiikkolab.noteune.persistence.repository.json.JsonRepositoryFactory;

public class GlobalSettingsImpl extends BasicService<GlobalSettings>
    implements GlobalSettingsService{

  public GlobalSettingsImpl(
      Repository<GlobalSettings> repository) {
    super(repository);
  }
  @Override
  public void setDefaultBackground(int background){
    GlobalSettings settings = getGlobalSettings();
    GlobalSettingsDTO settingsDTO =
        new GlobalSettingsDTO(
            settings.getId(),
            settings.getLanguage(),
            background,
            settings.getForeground());
    JsonRepositoryFactory.getInstance().getGlobalSettingsRepository().remove(settings);
    JsonRepositoryFactory.getInstance().getGlobalSettingsRepository().add(
        new GlobalSettings(
            settingsDTO.getId(),
            settingsDTO.getLanguage(),
            settingsDTO.getBackground(),
            settingsDTO.getForeground()));
  }
  @Override
  public void setDefaultForeground(int foreground){
    GlobalSettings settings = getGlobalSettings();
    GlobalSettingsDTO settingsDTO =
        new GlobalSettingsDTO(
            settings.getId(),
            settings.getLanguage(),
            settings.getBackground(),
            foreground);
    JsonRepositoryFactory.getInstance().getGlobalSettingsRepository().remove(settings);
    JsonRepositoryFactory.getInstance().getGlobalSettingsRepository().add(
        new GlobalSettings(
            settingsDTO.getId(),
            settingsDTO.getLanguage(),
            settingsDTO.getBackground(),
            settingsDTO.getForeground()));
  }
  @Override
  public void setDefaultLanguage(String language){
    GlobalSettings settings = getGlobalSettings();
    GlobalSettingsDTO settingsDTO =
        new GlobalSettingsDTO(
            settings.getId(),
            language,
            settings.getBackground(),
            settings.getForeground());
    JsonRepositoryFactory.getInstance().getGlobalSettingsRepository().remove(settings);
    JsonRepositoryFactory.getInstance().getGlobalSettingsRepository().add(
        new GlobalSettings(
            settingsDTO.getId(),
            settingsDTO.getLanguage(),
            settingsDTO.getBackground(),
            settingsDTO.getForeground()));
  }
  private GlobalSettings getGlobalSettings() {
    return JsonRepositoryFactory.getInstance()
        .getGlobalSettingsRepository()
        .findAll()
        .stream()
        .findFirst()
        .orElseGet(() -> new GlobalSettings(UUID.randomUUID(), "en", -1, -1));
  }
  @Override
  public int getDefaultBackground(){
    return getGlobalSettings().getBackground();
  }
  @Override
  public int getDefaultForeground(){
    return getGlobalSettings().getForeground();
  }
}
