package com.wholetech.commons.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrs.sysmgr.entity.SysFile;
import com.wholetech.commons.Constants;
import com.wholetech.commons.dao.CommonDao;
import com.wholetech.commons.dao.UUIDHexGenerator;
import com.wholetech.commons.util.DateUtil;
import com.wholetech.commons.util.PropertyXmlMgr;
import com.wholetech.commons.util.SessionInfoHolder;
import com.wholetech.commons.util.StringUtil;

/**
 * 文件名： FileOperationServiceImp.java
 * 作者：
 * 日期： 2011-3-15
 * 功能说明：文件上传下载操作实现类
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
public class FileOperationServiceImp implements FileOperationService {

  private static Logger logger = LoggerFactory.getLogger(FileOperationServiceImp.class);
  // CKEditor上传图片的相对上传路径的地址
  private static String imgPatch = PropertyXmlMgr.getString("MRSCONFIG", "upload.img.ckeditor");
  private CommonDao commonDao;
  private SessionInfoHolder sessionInfoHolder;

  public void setCommonDao(CommonDao commonDao) {

    this.commonDao = commonDao;
  }

  /**
   * @author
   * @version 1.0
   *          功能描述： 文件下载
   * @param id
   *          文件记录id
   * @param os
   *          文件下载的输出流
   * @return 文件 下载成功(true)，文件下载失败(false)
   * @exception JeawException
   * @see com.jeaw.file.FileOperationService#downloadFile(java.lang.String, java.io.OutputStream)
   */
  public boolean downloadFile(SysFile sysFile, OutputStream os) {

    InputStream is = null;
    FileServer fileServer = null;
    try {
      fileServer = FileUtil.connectServer();
      is = FileUtil.downFile(sysFile.getFileName(), fileServer);
      IOUtils.copy(is, os);
      return true;
    } catch (Exception e) {
      logger.error("下载文件过程中出错");
      return false;
    } finally {
      IOUtils.closeQuietly(is);
      FileUtil.closeServer(fileServer);
    }
  }

  /**
   * @author
   * @version 1.0
   *          功能描述： 上传文件
   * @param name
   *          上传文件名
   * @param srourcesName
   *          将要上传文件的原文件名
   * @param file
   *          将要被上传的文件
   * @return 文件上传成功(true)，文件上传失败(false)
   * @exception JeawException
   * @see com.jeaw.file.FileOperationService#uploadFile(java.lang.String, java.io.File)
   */
  private boolean uploadFile(String name, File file) {

    if (StringUtils.isNotEmpty(name) && file != null) {
      return saveFile(name, file);
    }
    return false;
  }

  private boolean saveFile(String name, File file) {

    InputStream fileIO = null;
    FileServer fileServer = null;
    try {
      // 获取图片的后缀名
      fileIO = new BufferedInputStream(new FileInputStream(file));
      fileServer = FileUtil.connectServer();
      return FileUtil.uploadFile(fileIO, name, fileServer);

    } catch (FileNotFoundException e) {
      return false; // TODO 定义框架异常类型，然后抛出
    } catch (Exception e) {
      return false; // TODO 定义框架异常类型，然后抛出
    } finally {
      IOUtils.closeQuietly(fileIO);
      FileUtil.closeServer(fileServer);
    }
  }
  public boolean uploadFile(String name,ByteArrayInputStream file) {

	    if (StringUtils.isNotEmpty(name) && file != null) {
	      return saveFile(name, file);
	    }
	    return false;
	  }
  private boolean saveFile(String name, ByteArrayInputStream file) {

    InputStream fileIO = null;
    FileServer fileServer = null;
    try {
      // 获取图片的后缀名
      fileIO = new BufferedInputStream(file);
      fileServer = FileUtil.connectServer();
      return FileUtil.uploadFile(fileIO, name, fileServer);

    } catch (FileNotFoundException e) {
      return false; // TODO 定义框架异常类型，然后抛出
    } catch (Exception e) {
      return false; // TODO 定义框架异常类型，然后抛出
    } finally {
      IOUtils.closeQuietly(fileIO);
      FileUtil.closeServer(fileServer);
    }
  }

  public String[] saveFiles(String[] fileNames, File[] files) {

    return saveFiles("", "", fileNames, files);
  }

  public void executeReplaceFile(String id, File file) {

    SysFile sysFile = getFileById(id);
    saveFile(sysFile.getFileName(), file);
  }

  public String[] saveFiles(String busiID, String busiType, String[] fileNames, File[] files) {

    if (fileNames == null || fileNames.length == 0) {
      return null;
    }
    String[] fileIDs = new String[fileNames.length];

    for (int i = 0; i < files.length; i++) {

      // 物理保存上传文件
      String extension = StringUtil.getFilenameExt(fileNames[i]);
      String physicalName = UUIDHexGenerator.getInstance().generate() + "." + extension;
      String updFileName = null;
      if (StringUtils.isNotEmpty(busiType)) {
        updFileName = FileServer.FILE_SPLIT + generateSubDir(busiType) + FileServer.FILE_SPLIT + physicalName;
      } else {
        updFileName = FileServer.FILE_SPLIT + physicalName;
      }

      if (!uploadFile(updFileName, files[i])) {
        // TODO 定义框架异常类型，然后抛出
      }

      fileIDs[i] = insertFile(updFileName, fileNames[i], busiID, busiType);
    }

    return fileIDs;
  }

  /**
   * 插入文件
   * 
   * @param updFileName
   *          存储路径和名称
   * @param fileName
   *          文件的真实名称
   * @param busiID
   *          业务id
   * @param busiType
   *          业务类型
   * @return
   */
  private String insertFile(String updFileName, String fileName, String busiID, String busiType) {

    String id = UUIDHexGenerator.getInstance().generate();
    this.commonDao.executeSql("sql_insert_file", id, updFileName, fileName, busiID, busiType);
    return id;
  }

  /**
   * 为一种业务类型生成一种
   * 
   * @param busiType
   * @return
   */
  private String generateSubDir(String busiType) {

    return busiType + FileServer.FILE_SPLIT + DateUtil.format(new java.util.Date(), DateUtil.FMT_DATE_YYYYMM);
  }

  public boolean deleteFile(String fileID, boolean deleteDiskFile) throws Exception {

    if (deleteDiskFile) {
      SysFile sysFile = getFileById(fileID);
      FileServer fileServer = FileUtil.connectServer();
      fileServer.deleteFile(sysFile.getFileName().substring(1));
      FileUtil.closeServer(fileServer);
    }

    this.commonDao.executeHql("hql_delete_file_by_id", fileID);
    return true;
  }

  public boolean deleteFile(String busiID, String busiType, boolean deleteDiskFile) {

    this.commonDao.executeHql("hql_delete_file_by_busi", busiID, busiType);
    return true;
  }

  /**
   * 功能描述：删除业务实体相关联的附件
   * 
   * @author 英孚泰克
   * @version 1.0
   * @see com.jeaw.system.file
   * @param busiID
   *          业务ID组
   * @param deleteDiskFile
   *          是否删除磁盘上的文件。
   * @return
   *         boolean
   */
  public boolean deleteFile(String[] busiID, boolean deleteDiskFile) {

    if (busiID != null && busiID.length > 0) {
      StringBuffer temp = new StringBuffer("");
      StringBuffer delhql = new StringBuffer("delete from SysFile where busiId in (");
      StringBuffer selhql = new StringBuffer(
          "select id, fileName, logicfileName, busiId, busiType, locked, remark,operatorcode, operatorName, operateTime,operateType from SysFile where busiID IN (");
      for (String sobj : busiID) {
        temp.append(",'").append(sobj).append("'");
      }
      delhql.append(temp.substring(1)).append(")");
      selhql.append(temp.substring(1)).append(")");
      List list = this.commonDao.findListByHql(selhql.toString());
      this.commonDao.executeSql(delhql.toString());

    }
    return true;
  }

  public List<SysFile> getFileByBusi(String busiID, String busiType) {

    List<SysFile> rslt = new ArrayList<SysFile>();
    List list = this.commonDao.findListByHql("hql_find_file_by_busi", busiID, busiType);
    if (list != null && list.size() > 0) {
      for (Object obj : list) {
        rslt.add(new SysFile((Object[]) obj));
      }
    }
    return rslt;
  }

  public SysFile getFileById(String id) {

    List list = this.commonDao.findListByHql("hql_find_file_by_id", id);
    if (list != null && list.size() > 0) {
      return new SysFile((Object[]) list.get(0));
    } else {
      return null;
    }
  }

  public void updateBusi(String id, String busiID, String busiType) {

    SysFile file = getFileById(id);
    file.setBusiId(busiID);
    file.setBusiType(busiType);
    updateFile(file);
  }

  private void updateFile(SysFile file) {

    HttpServletRequest request = ServletActionContext.getRequest();
    Date sysDate = this.commonDao.getSystemDate();
    if (request != null) {
      this.commonDao.executeSql("sql_update_busi", file.getFileName(), file.getLogicFileName(), file.getBusiId(),
          file.getBusiType(), file.getLocked(), this.sessionInfoHolder.getUserId(request),
          this.sessionInfoHolder.getLoginName(request), sysDate, file.getId());
    } else {
      this.commonDao.executeSql("sql_update_busi", file.getFileName(), file.getLogicFileName(), file.getBusiId(),
          file.getBusiType(), file.getLocked(), null, null, sysDate, file.getId());
    }
  }

  /**
   * @author
   * @version 1.0
   *          功能描述： CKEditor上传图片
   * @param file
   *          将要上传的图片文件
   * @return String 图片无理地址
   * @exception JeawException
   * @see com.jeaw.system.file.FileOperationService#upLoadImg(java.io.File)
   */
  public String upLoadImg(File file, String srourcesName) {

    String strDate = DateUtil.format(new java.util.Date(), DateUtil.FMT_DATE_YYYYMM);
    UUID uuid = UUID.randomUUID();
    String imgType = StringUtil.getFilenameExt(srourcesName);
    // 组织图片上传地址
    String imgURI = imgPatch + strDate + FileServer.FILE_SPLIT + uuid.toString() + "." + imgType;
    if (uploadFile(imgURI, file)) {
      return imgURI;
    }
    return null;
  }

  /**
   * @author
   * @version 1.0
   *          功能描述： CKEditro载入图片
   * @param imgURI
   *          获取文件的URI
   * @param os
   *          文件下载的输出流
   * @return 文件 下载成功(true)，文件下载失败(false)
   * @exception JeawException
   * @see com.jeaw.file.FileOperationService#downloadFile(java.lang.String, java.io.OutputStream)
   */
  public boolean downloadFile(String imgURI, OutputStream os) {

    InputStream is = null;
    FileServer fileServer = null;
    try {
      fileServer = FileUtil.connectServer();
      is = FileUtil.downFile(imgURI, fileServer);
      IOUtils.copy(is, os);
      return true;
    } catch (Exception e) {
      logger.error("下载文件过程中出错");
      return false;
    } finally {
      IOUtils.closeQuietly(is);
      FileUtil.closeServer(fileServer);
    }
  }

  @Override
  public void executeLockFile(String id, String locked) {

    SysFile file = getFileById(id);
    file.setLocked(locked);
    updateFile(file);
  }

  @Override
  public boolean isFileLocked(String id) {

    SysFile file = getFileById(id);
    return Constants.BOOLEAN_TRUE.equals(file.getLocked());
  }

  @Override
  public String[] executeCopyFile(String[] ids, String busiType, String busiID) {

    if (ids != null && ids.length > 0) {
      String[] rsltId = new String[ids.length];
      for (int i = 0; i < ids.length; i++) {
        SysFile file = getFileById(ids[i]);
        rsltId[i] = insertFile(file.getFileName(), file.getLogicFileName(), null, busiType);
      }
      return rsltId;
    }
    return null;
  }

  public SessionInfoHolder getSessionInfoHolder() {

    return this.sessionInfoHolder;
  }

  public void setSessionInfoHolder(SessionInfoHolder sessionInfoHolder) {

    this.sessionInfoHolder = sessionInfoHolder;
  }

}
