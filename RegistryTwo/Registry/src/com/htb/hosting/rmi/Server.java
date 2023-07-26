package com.htb.hosting.rmi;

import com.htb.hosting.rmi.quarantine.QuarantineService;
import com.htb.hosting.rmi.quarantine.QuarantineServiceImpl;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
  public static void main(String[] args) throws Exception {
    int port = 9002;
    System.setProperty("java.rmi.server.hostname", "registry.webhosting.htb");
    Registry registry = LocateRegistry.createRegistry(9002);
    System.out.printf("[+] Bound to %d\n", new Object[] { Integer.valueOf(9002) });
    FileService fileService = new FileServiceImpl();
    FileService fileServiceStub = (FileService)UnicastRemoteObject.exportObject(fileService, 0);
    registry.bind("FileService", fileServiceStub);
    QuarantineServiceImpl quarantineServiceImpl = new QuarantineServiceImpl();
    QuarantineService quarantineServiceStub = (QuarantineService)UnicastRemoteObject.exportObject((Remote)quarantineServiceImpl, 0);
    registry.bind("QuarantineService", (Remote)quarantineServiceStub);
  }
}
