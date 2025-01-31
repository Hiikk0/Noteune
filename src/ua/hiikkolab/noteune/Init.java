package ua.hiikkolab.noteune;

import ua.hiikkolab.noteune.domain.impl.ServiceFactory;
import ua.hiikkolab.noteune.persistence.repository.RepositoryFactory;

public class Init {
  public static void ini() {
    RepositoryFactory jsonRepositoryFactory = RepositoryFactory
        .getRepositoryFactory(RepositoryFactory.JSON);

    jsonRepositoryFactory.getUIRepository();
    ServiceFactory serviceFactory = ServiceFactory.getInstance(jsonRepositoryFactory);

    serviceFactory.initializeInterface(ServiceFactory.getInstance().getGlobalSettingsService());
    jsonRepositoryFactory.save();
  }
}
