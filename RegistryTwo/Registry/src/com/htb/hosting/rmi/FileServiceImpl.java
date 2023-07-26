package com.htb.hosting.rmi;

import com.htb.hosting.rmi.utils.CryptUtil;
import com.htb.hosting.rmi.utils.FileUtil;
import com.htb.hosting.rmi.utils.StringUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FileServiceImpl implements FileService {
  private static final Logger logger = Logger.getLogger(FileServiceImpl.class.getSimpleName());
  
  public static final String WWW_STATIC_S_WEBHOSTING_HTB = "www.static-%s.webhosting.htb";
  
  private static File getDocumentRoot(String vhostId) {
    return getFromDocumentRoot(vhostId, null);
  }
  
  private static File getFromDocumentRoot(String vhostId, String unsafePath) {
    if (!vhostId.matches("[0-9a-fA-F]+"))
      throw new IllegalArgumentException("Specified vhost not matching regex '[0-9a-fA-F]+': " + vhostId); 
    File documentRoot = new File(FileServiceConstants.SITES_DIRECTORY, String.format("www.static-%s.webhosting.htb", new Object[] { vhostId }));
    if (!documentRoot.isDirectory()) {
      logger.info("Directory not found, request with document root: " + vhostId + " and path: " + unsafePath);
      return null;
    } 
    if (unsafePath == null)
      return documentRoot; 
    return new File(documentRoot, unsafePath);
  }
  
  private void log(String msg, Object... args) {
    logger.info(String.format(msg, args));
  }
  
  public List<AbstractFile> list(String vhostId, String path) {
    log("list vhostId: %s, path: %s", new Object[] { vhostId, path });
    List<AbstractFile> result = new ArrayList<>();
    File documentRoot = getDocumentRoot(vhostId);
    File file = getFromDocumentRoot(vhostId, path);
    if (file != null) {
      File[] files = file.listFiles();
      File parent = file.getParentFile();
      boolean shouldShowParent = (parent != null && !parent.toPath().equals(FileServiceConstants.SITES_DIRECTORY.toPath()) && !file.toPath().equals(documentRoot.toPath()));
      if (file.isFile()) {
        if (shouldShowParent)
          result.add(of(vhostId, parent, "..")); 
        result.add(of(vhostId, file));
      } else {
        if (shouldShowParent)
          result.add(of(vhostId, parent, "..")); 
        if (files != null)
          for (File fd : files)
            result.add(of(vhostId, fd));  
      } 
    } 
    return result;
  }
  
  public boolean uploadFile(String vhostId, String path, byte[] content) throws IOException {
    log("upload vhostId: %s, path: %s", new Object[] { vhostId, path });
    File file = getFromDocumentRoot(vhostId, path);
    if (file == null)
      return false; 
    FileOutputStream fos = new FileOutputStream(file);
    try {
      fos.write(content);
      fos.close();
    } catch (Throwable throwable) {
      try {
        fos.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    return false;
  }
  
  public AbstractFile getFile(String fRefId) throws RemoteException {
    File file = null;
    try {
      file = new File(CryptUtil.getInstance().decrypt(fRefId));
    } catch (Exception e) {
      logger.throwing(getClass().getSimpleName(), "getFile", e);
    } 
    if (file == null)
      return null; 
    String vhost = getVhostForFile(file);
    log("getting file %s from vhostId", new Object[] { file.getAbsolutePath(), vhost });
    return of(vhost, file);
  }
  
  public static String getVhostForFile(File file) {
    if (file == null)
      return null; 
    Path path = file.toPath().normalize().toAbsolutePath();
    try {
      String name = path.getName(1).toFile().getName();
      return StringUtil.substr(name, "www.static-", ".webhosting.htb");
    } catch (Exception e) {
      return null;
    } 
  }
  
  public boolean delete(String fRefId) {
    File file = null;
    try {
      file = new File(CryptUtil.getInstance().decrypt(fRefId));
    } catch (Exception e) {
      logger.throwing(getClass().getSimpleName(), "delete", e);
    } 
    if (file == null)
      return false; 
    log("delete %s", new Object[] { file.getAbsolutePath() });
    file.delete();
    return !file.exists();
  }
  
  public boolean createDirectory(String vhostId, String path) {
    File file = getFromDocumentRoot(vhostId, path);
    log("create directory for vhostId: %s, path: %s", new Object[] { vhostId, path });
    if (file == null)
      return false; 
    file.mkdirs();
    return file.exists();
  }
  
  public byte[] view(String fRefId) throws RemoteException {
    File file = null;
    try {
      file = new File(CryptUtil.getInstance().decrypt(fRefId));
    } catch (Exception e) {
      logger.throwing(getClass().getSimpleName(), "view", e);
    } 
    if (file == null)
      return null; 
    log("view %s", new Object[] { file.getAbsolutePath() });
    try {
      return Files.readAllBytes(file.toPath());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  @Deprecated
  public byte[] view(String vhostId, String path) throws IOException {
    File file = getFromDocumentRoot(vhostId, path);
    log("view file from vhostId: %s, path: %s", new Object[] { vhostId, path });
    if (file == null)
      return new byte[0]; 
    return Files.readAllBytes(file.toPath());
  }
  
  public void deleteDomain(String vhostId) throws RemoteException {
    File file = getDocumentRoot(vhostId);
    log("delete vhostId: %s", new Object[] { vhostId });
    if (file.isDirectory())
      FileUtil.deleteFolder(file); 
  }
  
  public boolean newDomain(String vhostId) throws RemoteException {
    log("new domain vhostId: %s", new Object[] { vhostId });
    File folder = new File(FileServiceConstants.SITES_DIRECTORY, String.format("www.static-%s.webhosting.htb", new Object[] { vhostId }));
    return folder.mkdir();
  }
  
  public AbstractFile getFile(String vhost, String file) throws RemoteException {
    File fromDocumentRoot = getFromDocumentRoot(vhost, file);
    if (fromDocumentRoot == null)
      return null; 
    return of(vhost, fromDocumentRoot);
  }
  
  public static AbstractFile of(String vhostId, File file) {
    return of(vhostId, file, file.getName());
  }
  
  public static AbstractFile of(String vhostId, File file, String displayName) {
    File documentRoot = getDocumentRoot(vhostId);
    file = file.toPath().normalize().toFile();
    String effectiveVhostId = getVhostForFile(file);
    File parentFile = file.getParentFile();
    String vhostParent = getVhostForFile(parentFile);
    AbstractFile parent = null;
    if (vhostParent != null && vhostParent.equals(vhostId)) {
      String str = FileUtil.relative(documentRoot, parentFile);
      parent = new AbstractFile(CryptUtil.getInstance().encrypt(parentFile.getAbsolutePath()), effectiveVhostId, parentFile.getName(), parentFile, parentFile.getAbsolutePath(), str, parentFile.isFile(), parentFile.isDirectory(), parentFile.length(), FileUtil.printPermissions(parentFile.toPath()), parentFile.lastModified(), null);
    } 
    String relativePath = FileUtil.relative(documentRoot, file);
    return new AbstractFile(CryptUtil.getInstance().encrypt(file.getAbsolutePath()), effectiveVhostId, displayName, file, file
        .getAbsolutePath(), relativePath, file.isFile(), file.isDirectory(), file
        .length(), FileUtil.printPermissions(file.toPath()), file.lastModified(), parent);
  }
}
