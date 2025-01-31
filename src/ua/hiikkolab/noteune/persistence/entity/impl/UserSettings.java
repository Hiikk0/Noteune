package ua.hiikkolab.noteune.persistence.entity.impl;

import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.Entity;

public class UserSettings extends Entity {
  private final String username;
  private final int background;
  private final int foreground;
  public UserSettings(UUID id, String username, int background, int foreground) {
    super(id);
    this.username = username;
    this.background = background;
    this.foreground = foreground;
  }
  public String getUserName() {
    return username;
  }
  public int getBackground() {
    return background;
  }
  public int getForeground() {
    return foreground;
  }
  @Override
  public String toString() {
    return "UserPreferences{" +
        ", username='" + username + '\'' +
        ", background=" + background +
        ", foreground=" + foreground +
        ", id=" + id;
  }
}
