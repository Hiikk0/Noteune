package ua.hiikkolab.noteune.domain.dto;

import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.Entity;

public final class UserSettingsDTO extends Entity {
  private final String username;
  private final int background;
  private final int foreground;
  public UserSettingsDTO(UUID uuid, String username, int background, int foreground) {
    super(uuid);
    this.username = username;
    this.background = background;
    this.foreground = foreground;
  }
  public String getUsername() {
    return username;
  }
  public int getBackground() {
    return background;
  }
  public int getForeground() {
    return foreground;
  }
}
