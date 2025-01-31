package ua.hiikkolab.noteune.domain.contract;

import java.util.UUID;
import ua.hiikkolab.noteune.persistence.entity.impl.TextScreen;

public interface UIService {
  TextScreen getView(UUID id);
  TextScreen getView(String header);
  void changeView(TextScreen textScreen);
  void analyseInput(String input);


}
