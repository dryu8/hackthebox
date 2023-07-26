package com.htb.hosting.rmi.quarantine;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuarantineService extends Remote {
  QuarantineConfiguration getConfiguration() throws RemoteException;
}
