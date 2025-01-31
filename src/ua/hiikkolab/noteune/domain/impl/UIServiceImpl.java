package ua.hiikkolab.noteune.domain.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import ua.hiikkolab.noteune.appui.impl.Renderer;
import ua.hiikkolab.noteune.appui.impl.Responder;
import ua.hiikkolab.noteune.domain.contract.GlobalSettingsService;
import ua.hiikkolab.noteune.domain.contract.UIService;
import ua.hiikkolab.noteune.domain.contract.UserSettingsService;
import ua.hiikkolab.noteune.domain.dto.UserDTO;
import ua.hiikkolab.noteune.domain.exception.InvalidMenuSelectionException;
import ua.hiikkolab.noteune.domain.exception.SignUpException;
import ua.hiikkolab.noteune.domain.exception.ViewNotFound;
import ua.hiikkolab.noteune.persistence.entity.impl.TextScreen;
import ua.hiikkolab.noteune.persistence.repository.contracts.UIRepository;
import ua.hiikkolab.noteune.persistence.repository.json.JsonPaths;
import ua.hiikkolab.noteune.persistence.repository.json.JsonRepositoryFactory;

public class UIServiceImpl
    extends BasicService<TextScreen>
    implements UIService {

  private final UIRepository uiRepository;
  private TextScreen currentTextScreen;
  private TextScreen previuosTextScreen;
  private final Renderer renderer = Renderer.instance();
  private UserSettingsService userSettings;
  private final GlobalSettingsService globalSettings;

  UIServiceImpl(UIRepository uiRepository, GlobalSettingsService globalSettings) {
    super(uiRepository);
    this.uiRepository = uiRepository;
    this.globalSettings = globalSettings;
    TextScreen startLogonScreen = getView("LogonMenu");
    changeView(startLogonScreen);
  }

  @Override
  public void changeView(TextScreen textScreen) {
    previuosTextScreen = currentTextScreen;
    currentTextScreen = textScreen;

    int foreground = textScreen.getForeground();
    int background = textScreen.getBackground();

    if (userSettings != null
        && userSettings.getForeground() >= 0
        && userSettings.getForeground() <= 255) {
        foreground = userSettings.getForeground();
    }
    if (userSettings != null
      && userSettings.getBackground() >= 0
      && userSettings.getBackground() <= 255)
    {
      background = userSettings.getBackground();
    }
    if (globalSettings != null
        && globalSettings.getDefaultForeground() >= 0
        && globalSettings.getDefaultForeground() <= 255){
      foreground = globalSettings.getDefaultForeground();
    }
    if (globalSettings != null
        && globalSettings.getDefaultBackground() >= 0
        && globalSettings.getDefaultBackground() <= 255){
      background = globalSettings.getDefaultBackground();
    }
    if (textScreen.getHeader().equals("GlobalSettingsChangeLanguage")) {
      JsonPaths.loadLanguageFiles().keySet().stream().forEach(key ->
          renderer.render("", key, currentTextScreen.getForeground(), currentTextScreen.getBackground()));
    }
    renderer.render(textScreen.getHeader(), textScreen.getContent(), foreground, background);
    analyseInput(Responder.instance().getInput());
  }
  @Override
  public void analyseInput(String input) {
    switch (currentTextScreen.getType().toLowerCase()) {
      case "auth": analyseAuthInput(input);
        break;
      case "reg":
        analyseRegInput(input);
        break;
      case "menu":
        analyseMenuInput(input);
        break;
      case "set":
        analyseSetInput(input);
        break;
      case "err":
        analyseErrInput(input);
        break;
      case "mainmenu":
        analyseMainMenuInput(input);
        break;
      default: throw new ViewNotFound("Unknown view type: " + currentTextScreen.getType());
    }
  }

  private void analyseAuthInput(String input) {
      if (currentTextScreen.getHeader().equals("AuthGetUsername")) {
        ServiceFactory.getInstance().getAuthService().setUsername(input);
        changeView(getView(currentTextScreen.getWays().getFirst()));
      } else if (currentTextScreen.getHeader().equals("AuthGetPassword")) {
        try {
          if (ServiceFactory.getInstance().getAuthService().authenticate(input)){
            userSettings = ServiceFactory.getInstance().getUserSettingsService();
            changeView(getView(currentTextScreen.getWays().getFirst()));
          }
          else {
            changeView(getView(currentTextScreen.getWays().getLast()));
          }
        }
        catch (Exception e) {
          changeView(getView(currentTextScreen.getWays().getLast()));
        }
      }
    }

  private void analyseRegInput(String input) {
    switch (currentTextScreen.getHeader()) {
      case "RegistrationGetUsername":
        ServiceFactory.getInstance().getSignUpService().setUsername(input);
        changeView(getView(currentTextScreen.getWays().getFirst()));
        break;
      case "RegistrationGetPassword":
        ServiceFactory.getInstance().getSignUpService().setPassword(input);
        changeView(getView(currentTextScreen.getWays().getFirst()));
        break;
      case "RegistrationRepeatPassword":
        if(!Objects.equals(ServiceFactory.getInstance().getSignUpService().getPassword(), input)){
          changeView(getView(currentTextScreen.getWays().getLast()));
        }
        changeView(getView(currentTextScreen.getWays().getFirst()));
        break;
      case "RegistrationGetEmail":
        UserDTO tmpUser = new UserDTO(UUID.randomUUID(),
            ServiceFactory.getInstance().getSignUpService().getUsername(),
            input,
            ServiceFactory.getInstance().getSignUpService().getPassword(),
            Optional.of(""));
        ServiceFactory.getInstance().getSignUpService().setTmpUserDTO(tmpUser);
        // Закоментувати для тестування
        ServiceFactory.getInstance().getSignUpService().sendVerificationCode(tmpUser.email());
        // Розкоментувати 2 строки нижче для тестування
//        ServiceFactory.getInstance().getSignUpService().
//            signUp(ServiceFactory.getInstance().getSignUpService().getTmpUserDTO(),input);
        changeView(getView(currentTextScreen.getWays().getFirst()));
        break;
      case "RegistrationGetVerificationCode":
        try {
          ServiceFactory.getInstance().getSignUpService().
            signUp(ServiceFactory.getInstance().getSignUpService().getTmpUserDTO(),input);
          changeView(getView(currentTextScreen.getWays().getFirst()));
        }
        catch (SignUpException e){
          changeView(getView(currentTextScreen.getWays().getLast()));
        }

        break;
      default:
        throw new InvalidMenuSelectionException("");
    }
  }

  private void analyseSetInput(String input) {
    switch (currentTextScreen.getHeader()) {
      case "SettingsChangeUsername":
        var user = ServiceFactory.getInstance().getAuthService().getUser();
        ServiceFactory.getInstance().getUserService().updateUsername(user, input);
        changeView(getView(currentTextScreen.getWays().getFirst()));
        break;
      case "SettingsChangePassword":
        ServiceFactory.getInstance().getUserService().updatePassword(
            ServiceFactory.getInstance().getAuthService().getUser(), input);
        changeView(getView(currentTextScreen.getWays().getFirst()));
        break;
      case "SettingsChangeEmail":
        ServiceFactory.getInstance().getUserService().updateEmail(
            ServiceFactory.getInstance().getAuthService().getUser(), input);
        changeView(getView(currentTextScreen.getWays().getFirst()));
        break;
      case "SettingsChangeBackgroundColor":
        settingsChangeBackgroundColor(input);
        break;
      case "SettingsChangeForegroundColor":
        settingsChangeForegroundColor(input);
        break;
      case "GlobalSettingsChangeForegroundColor":
        globalSettingsChangeForegroundColor(input);
        break;
      case "GlobalSettingsChangeBackgroundColor":
        globalSettingsChangeBackgroundColor(input);
        break;
      case "GlobalSettingsChangeLanguage":
        ServiceFactory.getInstance().getGlobalSettingsService().setDefaultLanguage(input.toLowerCase());
        changeView(getView(currentTextScreen.getWays().getFirst()));
        break;
      default:
        changeView(getView("InvalidMenuSelection"));
    }
  }

  private void settingsChangeBackgroundColor(String input){
    if(parseInput(input) >= 0 && parseInput(input) <= 255 ) {
      ServiceFactory.getInstance().getUserSettingsService().setBackground(parseInput(input));
    }
    else {
      changeView(getView(currentTextScreen.getWays().getLast()));
    }
    changeView(getView(currentTextScreen.getWays().getFirst()));
  }

  private void settingsChangeForegroundColor(String input){
    if(parseInput(input) >= 0 && parseInput(input) <= 255 ) {
      ServiceFactory.getInstance().getUserSettingsService().setForeground(parseInput(input));
    }
    else {
      changeView(getView(currentTextScreen.getWays().getLast()));
    }
    changeView(getView(currentTextScreen.getWays().getFirst()));
  }

  private void globalSettingsChangeForegroundColor (String input){
    if(parseInput(input) >= 0 && parseInput(input) <= 255 ) {
      ServiceFactory.getInstance().getGlobalSettingsService()
          .setDefaultForeground(parseInput(input));
    }
    else {
      changeView(getView(currentTextScreen.getWays().getLast()));
    }
    changeView(getView(currentTextScreen.getWays().getFirst()));
  }
  private void globalSettingsChangeBackgroundColor (String input){
    //if(parseInput(input) >= 40 && parseInput(input) <= 47 ) {
      ServiceFactory.getInstance().getGlobalSettingsService()
          .setDefaultBackground(parseInput(input));
    //}
    //else {
      changeView(getView(currentTextScreen.getWays().getLast()));
    //}
    changeView(getView(currentTextScreen.getWays().getFirst()));
  }

  private void analyseErrInput(String input) {
    if (currentTextScreen.getWays().size() == 1){
      changeView(previuosTextScreen);
    }
    else if (currentTextScreen.getWays().size() == 2){
      switch (input.toLowerCase()){
        case "continue", "1":
          changeView(getView(currentTextScreen.getWays().get(1)));
          break;
        case "start again", "again", "2":
          changeView(previuosTextScreen);
          break;
        default: changeView(getView(currentTextScreen.getWays().getFirst())); break;
      }
    }
    switch (input.toLowerCase()){
      case "continue", "2":
      changeView(getView(currentTextScreen.getWays().get(1)));
      break;
    case "start again", "again", "3":
      changeView(previuosTextScreen);
      break;
      default: changeView(getView(currentTextScreen.getWays().getFirst())); break;
    }
  }
  private Integer parseInput(String input) {
    int i = -1;
    try{
      i = Integer.parseInt(input);
    }
    catch(NumberFormatException e){
      changeView(getView("InvalidMenuSelection"));
    }
    return i;
  }
  private void analyseMainMenuInput(String input) {
    switch (input.toLowerCase()){
      case "exit", "0":
        JsonRepositoryFactory.getInstance().save();
        System.exit(0);
        break;
      case "log out", "1":
        JsonRepositoryFactory.getInstance().save();
        ServiceFactory.getInstance().getAuthService().logout();
        changeView(getView(currentTextScreen.getWays().getFirst()));
        break;
        default: analyseMenuInput(input); break;
    }
  }
  private void analyseMenuInput(String input) {
    for (int i = 0; i < currentTextScreen.getWays().size()-1; i++) {
      TextScreen tmp = getView(currentTextScreen.getWays().get(i));
      if (tmp.getHeader().equalsIgnoreCase(input)) {
        changeView(tmp);
        break;
      }
    }

    int index = -1;
    try {
      index = Integer.parseInt(input) - 1;
    }
    catch (NumberFormatException e) {
      /*Nothing*/
    }
    if (index < 0 || currentTextScreen.getWays().size()-1 < index ){
      // Можливо, тут має бути щось по типу
      // "throw new InvalidMenuSelectionException("Не існує такого пункта меню.");"
      changeView(getView(currentTextScreen.getWays().getLast()));
    }
    else {
      changeView(getView(currentTextScreen.getWays().get(index)));
    }
  }

  @Override
  public TextScreen getView(UUID id) {
    return uiRepository.findViewByUUID(id).orElse(null);
  }
  @Override
  public TextScreen getView(String header){
    return uiRepository.findViewByHeader(header).orElse(null);
  }
}
