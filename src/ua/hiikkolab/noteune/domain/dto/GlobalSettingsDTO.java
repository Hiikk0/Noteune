package ua.hiikkolab.noteune.domain.dto;

import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.Entity;

public final class GlobalSettingsDTO extends Entity {

  private final String language;
  private final int background;
  private final int foreground;
  public GlobalSettingsDTO(UUID id, String language, int background, int foreground) {
    super(id);
    this.language = language;
    this.background = background;
    this.foreground = foreground;
  }

  public int getBackground() {
    return background;
  }
  public int getForeground() {
    return foreground;
  }
  public String getLanguage() {
    return language;
  }
}
