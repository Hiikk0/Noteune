package ua.hiikkolab.noteune.persistence.entity.impl;

import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.Entity;

public class GlobalSettings extends Entity {

  private final String language;
  private final int background;
  private final int foreground;

  public GlobalSettings(UUID id, String language, int background, int foreground) {
    super(id);
    this.language = language;
    this.background = background;
    this.foreground = foreground;
  }
  public String getLanguage() {
    return language;
  }
  public int getBackground() {
    return background;
  }
  public int getForeground() {
    return foreground;
  }
  @Override
  public String toString() {
    return "GlobalSettings{" +
        ", language='" + language + '\'' +
        ", background=" + background +
        ", foreground=" + foreground +
        ", id=" + id;
  }
}
