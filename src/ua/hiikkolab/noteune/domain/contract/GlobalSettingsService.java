package ua.hiikkolab.noteune.domain.contract;

import ua.hiikkolab.noteune.domain.Service;
import ua.hiikkolab.noteune.persistence.entity.impl.GlobalSettings;

public interface GlobalSettingsService extends Service<GlobalSettings> {

  void setDefaultBackground(int background);

  void setDefaultForeground(int foreground);

  void setDefaultLanguage(String language);

  int getDefaultBackground();

  int getDefaultForeground();
}
