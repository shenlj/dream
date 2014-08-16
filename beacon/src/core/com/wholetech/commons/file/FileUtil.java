package com.wholetech.commons.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wholetech.commons.util.PropertyXmlMgr;

/**
 * 文件操作.
 */
public class FileUtil {

  private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
  private static String rootPath = PropertyXmlMgr.getString("MRSCONFIG", "file.content.root.path");

  public static FileServer connectServer() throws Exception {

    try {
      FileServer fileServer = new FileServer(rootPath);
      return fileServer;
    } catch (Exception e) {
      logger.error("connect.server.error", e);
      throw new Exception("connect.server.error");
    }
  }

  public static void closeServer(FileServer fileServer) {

    try {
      fileServer = null;
    } catch (Exception e) {
      logger.error("close.server.error", e);
    }
  }

  public static InputStream downFile(String sourceFileName, FileServer fileServer) throws Exception {

    try {
      return fileServer.downFile(sourceFileName);
    } catch (Exception e) {
      logger.error("down.file.error.info", sourceFileName, e);
      throw new Exception("down.file.error");
    }
  }

  public static InputStream downFile(String path, String sourceFileName, FileServer fileServer) throws Exception {

    try {
      fileServer.changeDirectory(path);
      return fileServer.downFile(sourceFileName);
    } catch (Exception e) {
      logger.error("down.file.error.info", sourceFileName, e);
      throw new Exception("down.file.error");
    }
  }

  public static boolean uploadFile(InputStream iStream, String newName, FileServer fileServer) {

    try {
      return fileServer.uploadFile(iStream, newName);
    } catch (Exception e) {
      logger.error("upload.file.error.info", newName, e);
      return false;
    }
  }

  public static void main(String[] args) throws Exception {

    FileServer fileServer = connectServer();
    InputStream in = new FileInputStream(new File("D:/新建 文本文档 (2).txt"));
    uploadFile(in, "dfdf/gggg.txt", fileServer);
    System.out.print(in);
    // InputStream in = downFile("df/dfdf.txt", fileServer);

    System.out.print(in);
  }
}
