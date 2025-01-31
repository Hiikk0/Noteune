package ua.hiikkolab.noteune.domain.impl;

import java.util.Optional;
import java.util.UUID;
import ua.hiikkolab.noteune.domain.contract.AuthService;
import ua.hiikkolab.noteune.domain.contract.UserSettingsService;
import ua.hiikkolab.noteune.domain.dto.UserSettingsDTO;
import ua.hiikkolab.noteune.persistence.entity.impl.UserSettings;
import ua.hiikkolab.noteune.persistence.repository.contracts.UserSettingsRepository;
import ua.hiikkolab.noteune.persistence.repository.json.JsonRepositoryFactory;

public class UserSettingsServiceImpl extends BasicService<UserSettings> implements UserSettingsService {

  private final AuthService authService;
  UserSettingsServiceImpl(AuthService authService, UserSettingsRepository userSettingsRepository) {
    super(userSettingsRepository);
    this.authService = authService;
  }
  @Override
  public void setBackground(int background) {
    UserSettings settings = getUserSettings();
    UserSettingsDTO userSettingsDTO = new UserSettingsDTO(settings.getId(),settings.getUserName(),
        background,settings.getForeground()) ;
    JsonRepositoryFactory.getInstance().getUserSettingsRepository().remove(settings);
    JsonRepositoryFactory.getInstance().getUserSettingsRepository().add(new UserSettings(
        userSettingsDTO.getId(), userSettingsDTO.getUsername(), userSettingsDTO.getBackground(),
        userSettingsDTO.getForeground()
    ));
  }
  private UserSettings getUserSettings() {
    Optional<UserSettings> userSettings = JsonRepositoryFactory.getInstance().getUserSettingsRepository().
        findSettings(authService.getUser().getUsername());
    return userSettings.orElse(new UserSettings(UUID.randomUUID(),authService.getUser().getUsername(),-1,-1));
  }
  @Override
  public Integer getForeground() {
    UserSettings settings = getUserSettings();
    return settings.getForeground();
  }
  @Override
  public Integer getBackground() {
    UserSettings settings = getUserSettings();
    return settings.getBackground();
  }
  @Override
  public void setForeground(int foreground) {
    UserSettings settings = getUserSettings();
    UserSettingsDTO userSettingsDTO = new UserSettingsDTO(settings.getId(),settings.getUserName(),
        settings.getBackground(),foreground) ;
    JsonRepositoryFactory.getInstance().getUserSettingsRepository().remove(settings);
    JsonRepositoryFactory.getInstance().getUserSettingsRepository().add(new UserSettings(
        userSettingsDTO.getId(), userSettingsDTO.getUsername(), userSettingsDTO.getBackground(),
        userSettingsDTO.getForeground()
    ));
  }
}
