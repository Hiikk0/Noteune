package ua.hiikkolab.noteune.persistence.repository.json;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum JsonPaths {
  USERS("users.json"),
  VIEWS("views_en.json"),
  USERSETTINGS("user.settings.json"),
  GLOBALSETTINGS("global.settings.json");
  static final char SEP = File.separatorChar;

  private static final String DATA_DIRECTORY = Path.of("." + SEP + "data").toAbsolutePath().normalize().toString();
  private final String fileName;

  JsonPaths(String fileName) {
    this.fileName = fileName;
  }

  public Path getPath() {
    return Path.of(DATA_DIRECTORY, this.fileName);
  }

  private static final Pattern LANG_PATTERN = Pattern.compile(".*_([a-z]{2})\\.json$");
  public static Map<String, String> loadLanguageFiles() {
    Map<String, String> languageFiles = new HashMap<>();
    File dir = new File(DATA_DIRECTORY);
    if (dir.exists() && dir.isDirectory()) {
      for (File file : Objects.requireNonNull(dir.listFiles())) {
        if (file.isFile() && file.getName().endsWith(".json")) {
          Matcher matcher = LANG_PATTERN.matcher(file.getName());
          if (matcher.matches()) {
            String language = matcher.group(1);
            languageFiles.put(language, file.getPath());
          }
        }
      }
    }
    return languageFiles;
  }
}

