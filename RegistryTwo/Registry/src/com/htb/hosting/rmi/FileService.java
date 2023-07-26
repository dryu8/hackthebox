package com.htb.hosting.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileService extends Remote {
  List<AbstractFile> list(String paramString1, String paramString2) throws RemoteException;
  
  boolean uploadFile(String paramString1, String paramString2, byte[] paramArrayOfbyte) throws IOException;
  
  boolean delete(String paramString) throws RemoteException;
  
  boolean createDirectory(String paramString1, String paramString2) throws RemoteException;
  
  byte[] view(String paramString1, String paramString2) throws IOException;
  
  AbstractFile getFile(String paramString) throws RemoteException;
  
  AbstractFile getFile(String paramString1, String paramString2) throws RemoteException;
  
  void deleteDomain(String paramString) throws RemoteException;
  
  boolean newDomain(String paramString) throws RemoteException;
  
  byte[] view(String paramString) throws RemoteException;
}
