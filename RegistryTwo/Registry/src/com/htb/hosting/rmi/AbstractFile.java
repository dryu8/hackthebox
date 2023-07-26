package com.htb.hosting.rmi;

import java.io.File;
import java.io.Serializable;

public class AbstractFile implements Serializable {
  private static final long serialVersionUID = 2267537178761464006L;
  
  private final String fileRef;
  
  private final String vhostId;
  
  private final String displayName;
  
  private final File file;
  
  private final String absolutePath;
  
  private final String relativePath;
  
  private final boolean isFile;
  
  private final boolean isDirectory;
  
  private final long displaySize;
  
  private final String displayPermission;
  
  private final long displayModified;
  
  private final AbstractFile parentFile;
  
  public AbstractFile(String fileRef, String vhostId, String displayName, File file, String absolutePath, String relativePath, boolean isFile, boolean isDirectory, long displaySize, String displayPermission, long displayModified, AbstractFile parentFile) {
    this.fileRef = fileRef;
    this.vhostId = vhostId;
    this.displayName = displayName;
    this.file = file;
    this.absolutePath = absolutePath;
    this.relativePath = relativePath;
    this.isFile = isFile;
    this.isDirectory = isDirectory;
    this.displaySize = displaySize;
    this.displayPermission = displayPermission;
    this.displayModified = displayModified;
    this.parentFile = parentFile;
  }
  
  public String getFileRef() {
    return this.fileRef;
  }
  
  public String getVhostId() {
    return this.vhostId;
  }
  
  public String getDisplayName() {
    return this.displayName;
  }
  
  public File getFile() {
    return this.file;
  }
  
  public String getAbsolutePath() {
    return this.absolutePath;
  }
  
  public String getRelativePath() {
    return this.relativePath;
  }
  
  public boolean isDirectory() {
    return this.isDirectory;
  }
  
  public long getDisplaySize() {
    return this.displaySize;
  }
  
  public String getDisplayPermission() {
    return this.displayPermission;
  }
  
  public long getDisplayModified() {
    return this.displayModified;
  }
  
  public AbstractFile getParentFile() {
    return this.parentFile;
  }
  
  public boolean isFile() {
    return this.isFile;
  }
  
  public String getName() {
    return this.file.getName();
  }
  
  public boolean canExecute() {
    return getFile().canExecute();
  }
}
