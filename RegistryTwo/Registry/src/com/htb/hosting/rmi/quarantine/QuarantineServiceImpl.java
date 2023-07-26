package com.htb.hosting.rmi.quarantine;

import com.htb.hosting.rmi.FileServiceConstants;
import java.io.File;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class QuarantineServiceImpl implements QuarantineService {
  private static final Logger logger = Logger.getLogger(QuarantineServiceImpl.class.getSimpleName());
  
  private static final QuarantineConfiguration DEFAULT_CONFIG = new QuarantineConfiguration(new File("/tmp/quarantine"), FileServiceConstants.SITES_DIRECTORY, "10.10.16.5", 3310, 1000);
  
  public QuarantineConfiguration getConfiguration() throws RemoteException {
    logger.info("client fetching configuration");
    return DEFAULT_CONFIG;
  }
}
