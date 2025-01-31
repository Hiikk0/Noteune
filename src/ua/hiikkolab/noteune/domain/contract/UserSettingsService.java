package ua.hiikkolab.noteune.domain.contract;

import ua.hiikkolab.noteune.domain.Service;
import ua.hiikkolab.noteune.persistence.entity.impl.UserSettings;

public interface UserSettingsService extends Service<UserSettings> {
  void setBackground(int background);

  Integer getForeground();

  Integer getBackground();

  void setForeground(int foreground);
}
