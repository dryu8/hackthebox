package com.htb.hosting.rmi.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;

public class FileUtil {
  public static String printPermissions(Path absolutePath) {
    try {
      Set<PosixFilePermission> filePerm = Files.getPosixFilePermissions(absolutePath, new java.nio.file.LinkOption[0]);
      return PosixFilePermissions.toString(filePerm);
    } catch (IOException e) {
      return "?";
    } 
  }
  
  public static String relative(File base, File file) {
    String res = (new File(base.getAbsolutePath())).toURI().relativize((new File(file.getAbsolutePath())).toURI()).getPath();
    if (res == null || res.length() == 0)
      res = "/"; 
    return res;
  }
  
  public static boolean containsAny(Path file, List<Path> baseFolders) {
    Path normalizedPath = file.normalize();
    for (Path base : baseFolders) {
      if (normalizedPath.startsWith(base))
        return true; 
    } 
    return false;
  }
  
  public static void deleteFolder(File file) {
    File[] contents = file.listFiles();
    if (contents != null)
      for (File f : contents) {
        if (!Files.isSymbolicLink(f.toPath()))
          deleteFolder(f); 
      }  
    file.delete();
  }
}
