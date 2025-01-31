package ua.hiikkolab.noteune.persistence.entity.impl;

import java.util.List;
import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.Entity;

public class TextScreen extends Entity {
  private final String header;
  private final String content;
  private final List<UUID> ways;
  private final List<String> slugways;
  private final int foreground;
  private final int background;
  private final String type;
  public TextScreen(UUID id, String header, String content, List<UUID> ways, List<String> slugways,
      int foreground, int background, String type) {
    super(id);
    this.header = header;
    this.content = content;
    this.ways = ways;
    this.slugways = slugways;
    this.foreground = foreground;
    this.background = background;
    this.type = type;
  }
  public String getHeader() {
    return header;
  }
  public String getContent() {
    return content;
  }
  public List<UUID> getWays() {
    return ways;
  }
  public List<String> getSlugways() {
    return slugways;
  }
  public int getForeground() {
    return foreground;
  }
  public int getBackground() {
    return background;
  }
  public String getType() {
    return type;
  }
  @Override
  public String toString() {
    return "TextScreen{" +
        "header='" + header + '\'' +
        ", content='" + content + '\'' +
        ", ways=" + ways.toString() +
        ", slugways=" + slugways.toString() +
        ", fg=" + foreground +
        ", background=" + background +
        ", type='" + type + '\'' +
        ", id=" + id +
        '}';
  }
}
