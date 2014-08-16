package com.wholetech.commons.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 文件服务.
 */
public class FileServer {

  private String currentPath = "";
  private String rootPath = "/";
  public static final String FILE_SPLIT = "/";

  public FileServer(String rootPath) throws IOException {

    setRootDiretory(rootPath);
  }

  public void setRootDiretory(String rootPath) throws IOException {

    this.rootPath = rootPath;
    this.currentPath = rootPath;
  }

  public void changeDirectory(String path) throws IOException {

    if (isAbsolute(path)) {
      this.currentPath = this.rootPath + FILE_SPLIT + path;
    } else {
      this.currentPath = this.currentPath + FILE_SPLIT + path;
    }
  }

  public boolean makeDirectory(String path) throws IOException {

    return getFileByPath(path).mkdirs();
  }

  public boolean removeDirectory(String path) throws IOException {

    File[] fileList = getFileByPath(path).listFiles();
    for (File file : fileList) {
      if (file.isFile()) {
        file.delete();
      } else if (file.isDirectory()) {
        removeDirectory(path + FILE_SPLIT + file.getName());
      }
    }
    return getFileByPath(path).delete();
  }

  public boolean existDirectory(String path) throws IOException {

    return getFileByPath(path).exists();
  }

  public List<String> getFileList(String path) throws IOException {

    return Arrays.asList(getFileByPath(path).list());
  }

  public boolean deleteFile(String pathName) throws IOException {

    return getFileByPath(pathName).delete();
  }

  public boolean uploadFile(String fileName, String newName) throws IOException {

    InputStream iStream = null;
    try {
      iStream = new FileInputStream(fileName);
      return uploadFile(iStream, newName);
    } finally {
      if (iStream != null) {
        iStream.close();
      }
    }
  }

  public boolean uploadFile(String fileName) throws IOException {

    return uploadFile(fileName, fileName);
  }

  public boolean uploadFile(InputStream iStream, String newName) throws IOException {

    OutputStream os = null;
    try {
      String remotePath = null;
      if (newName.contains(FILE_SPLIT)) {
        remotePath = newName.substring(0, newName.lastIndexOf(FILE_SPLIT));
        // 创建服务器远程目录结构，创建失败直接返回
        makeDirectory(remotePath);
      }
      File file = getFileByPath(newName);
      os = new BufferedOutputStream(new FileOutputStream(file));
      org.apache.commons.io.IOUtils.copyLarge(iStream, os);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } finally {
      org.apache.commons.io.IOUtils.closeQuietly(os);
    }
  }

  public boolean download(String remoteFileName, String localFileName) throws IOException {

    File outfile = new File(localFileName);
    OutputStream oStream = null;
    InputStream is = null;
    try {
      oStream = new BufferedOutputStream(new FileOutputStream(outfile));
      is = new BufferedInputStream(new FileInputStream(getFileByPath(remoteFileName)));
      org.apache.commons.io.IOUtils.copyLarge(is, oStream);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } finally {
      org.apache.commons.io.IOUtils.closeQuietly(is);
      org.apache.commons.io.IOUtils.closeQuietly(oStream);
    }
  }

  public InputStream downFile(String sourceFileName) throws IOException {

    return new BufferedInputStream(new FileInputStream(getFileByPath(sourceFileName)));
  }

  private boolean isAbsolute(String path) {

    return path.startsWith(FILE_SPLIT);
  }

  public  File getFileByPath(String path) {

    if (isAbsolute(path)) {
      return new File(this.rootPath + FILE_SPLIT + path);
    } else {
      return new File(this.currentPath + FILE_SPLIT + path);
    }
  }

  public static void main(String[] arg) throws Exception {

    FileServer fileServer = new FileServer("E:");
    // fileServer.makeDirectory("/ddgg/dfdf");
    // // fileServer.removeDirectory("ddgg");
    // fileServer.uploadFile("d:/新建 文本文档.txt", "/dfdaf/df.txt");
    fileServer.download("/traj1.txt", "E:/xxx.txt");
  }
}
