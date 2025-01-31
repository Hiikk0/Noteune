package ua.hiikkolab.noteune.domain.impl;

import ua.hiikkolab.noteune.domain.contract.AuthService;
import ua.hiikkolab.noteune.domain.contract.GlobalSettingsService;
import ua.hiikkolab.noteune.domain.contract.UserSettingsService;
import ua.hiikkolab.noteune.domain.contract.SignUpService;
import ua.hiikkolab.noteune.domain.contract.UIService;
import ua.hiikkolab.noteune.domain.contract.UserService;
import ua.hiikkolab.noteune.domain.exception.DependencyException;
import ua.hiikkolab.noteune.persistence.repository.RepositoryFactory;
import ua.hiikkolab.noteune.persistence.repository.json.JsonRepositoryFactory;

public final class ServiceFactory {

  private static volatile ServiceFactory INSTANCE;
  private final AuthService authService;
  private final UserService userService;
  private final SignUpService signUpService;
  private final RepositoryFactory repositoryFactory;
  private UIService uiService;
  private final UserSettingsService userSettingsService;
  private final GlobalSettingsService globalSettingsService;

  private ServiceFactory(RepositoryFactory repositoryFactory) {
    this.repositoryFactory = repositoryFactory;
    var userRepository = repositoryFactory.getUserRepository();
    //var settingsRepository = repositoryFactory.getUserSettingsRepository();
    authService = new AuthServiceImpl(userRepository);
    userService = new UserServiceImpl(userRepository);
    signUpService = new SignUpServiceImpl(userService);
    userSettingsService = new UserSettingsServiceImpl(authService,
         JsonRepositoryFactory.getInstance().getUserSettingsRepository());
    globalSettingsService = new GlobalSettingsImpl(
        JsonRepositoryFactory.getInstance().getGlobalSettingsRepository());
  }
  public void initializeInterface(GlobalSettingsService globalSettingsService) {
    var uiRepository = repositoryFactory.getUIRepository();
    uiService = new UIServiceImpl(uiRepository, globalSettingsService);
  }
  /**
   * Використовувати, лише якщо впевнені, що існує об'єкт RepositoryFactory.
   *
   * @return екземпляр типу ServiceFactory
   */
  public static ServiceFactory getInstance() {
    if (INSTANCE.repositoryFactory != null) {
      return INSTANCE;
    } else {
      throw new DependencyException(
          "Ви забули створити обєкт RepositoryFactory, перед використанням ServiceFactory.");
    }
  }

  public static ServiceFactory getInstance(RepositoryFactory repositoryFactory) {
    if (INSTANCE == null) {
      synchronized (ServiceFactory.class) {
        if (INSTANCE == null) {
          INSTANCE = new ServiceFactory(repositoryFactory);
        }
      }
    }

    return INSTANCE;
  }

  public AuthService getAuthService() {
    return authService;
  }

  public UserService getUserService() {
    return userService;
  }

  public SignUpService getSignUpService() {
    return signUpService;
  }

  public UIService getUIService() {
    return uiService;
  }

  public UserSettingsService getUserSettingsService() {
    return userSettingsService;
  }

  public GlobalSettingsService getGlobalSettingsService() {
    return globalSettingsService;
  }

  public RepositoryFactory getRepositoryFactory() {
    return repositoryFactory;
  }
}
