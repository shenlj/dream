package com.wholetech.commons.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.List;

import com.wholetech.commons.entity.SysFile;

/**
 * 文件名： FileOperationService.java
 * 作者：
 * 日期： 2011-3-15
 * 功能说明：文件上传下载操作接口
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
public interface FileOperationService {

  /**
   * 单独保存文件信息。
   * 
   * @param fileNames
   *          文件原始名称。
   * @param files
   *          文件。
   * @return 上传成功后，保存的文件ID。
   */
  public String[] saveFiles(String[] fileNames, File[] files);

  /**
   * 保存文件接口，此时业务数据已经保存，直接关联业务信息保存文件信息。
   * 将文件进行物理存储，同时在公共的附件表中登记记录，返回登记的ID。
   * 
   * @param busiID
   *          对应的业务实体编号。
   * @param busiType
   *          业务类型，参见OA中的配置文件。
   * @param fileNames
   *          文件原始名称。
   * @param files
   *          文件。
   * @return 上传成功后，保存的文件ID。
   */
  public String[] saveFiles(String busiID, String busiType, String[] fileNames, File[] files);

  /**
   * 将文件ids拷贝一份指针给指定的业务实体。
   * 
   * @param ids
   *          String[] 文件ids
   * @param busiType
   *          业务类型
   * @param busiID
   *          业务id
   * @return
   */
  public String[] executeCopyFile(String[] ids, String busiType, String busiID);

  /**
   * 为文件关联业务信息。
   * 
   * @param id
   *          文件id；
   * @param busiID
   *          业务id；
   * @param busiType
   *          业务类型；
   */
  public void updateBusi(String id, String busiID, String busiType);

  /**
   * @author
   * @version 1.0
   *          功能描述： 文件下载
   * @param sourceFileName
   *          文件名
   * @param os
   *          文件下载的输出流
   * @return 文件下载成功(true)，文件下载失败(false)
   * @exception JeawException
   */
  public boolean downloadFile(SysFile sysFile, OutputStream os);

  /**
   * 删除文件。
   * 
   * @param fileID
   *          文件数据库记录的id；
   * @param deleteDiskFile
   *          是否删除磁盘上的文件。
   * @return 删除成功与否。
   * @throws Exception
   */
  public boolean deleteFile(String fileID, boolean deleteDiskFile) throws Exception;

  /**
   * 删除一个业务实体关联的所有文件。
   * 
   * @param fileID
   *          文件数据库记录的id；
   * @param deleteDiskFile
   *          是否删除磁盘上的文件。
   * @return 删除成功与否。
   */
  public boolean deleteFile(String busiID, String busiType, boolean deleteDiskFile);

  /**
   * 功能描述：删除多个业务实体相关联的附件
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
  public boolean deleteFile(String[] busiID, boolean deleteDiskFile);

  /**
   * 根据附件id获取文件信息。
   * 
   * @param id
   * @return
   */
  public SysFile getFileById(String id);

  /**
   * 判断文件是否被锁定
   * 
   * @param id
   *          文件id
   * @return true - 文件被锁定； false - 文件没被锁定。
   */
  public boolean isFileLocked(String id);

  /**
   * 锁定或解锁文件
   * 
   * @param id
   *          文件id
   * @param locked
   *          锁定标志，参见Constants.BOOLEAN_TRUE;Constants.BOOLEAN_FALSE
   */
  public void executeLockFile(String id, String locked);

  /**
   * 获取一个业务实体关联的附件信息。
   * 
   * @param entity
   *          业务实体
   * @param busiType
   *          业务类型。
   * @return 附件信息列表
   */
  public List<SysFile> getFileByBusi(String busiID, String busiType);

  /**
   * 更新标示为id的文件为当前file。
   * 
   * @param id
   * @param file
   */
  public void executeReplaceFile(String id, File file);

  /**
   * @author
   * @version 1.0
   *          功能描述： CKEditor上传图片
   * @param file
   *          将要上传的图片文件
   * @param srourcesName
   *          原文件名
   * @return String 图片无理地址
   * @exception JeawException
   */
  public String upLoadImg(File file, String srourcesName);

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
   */
  public boolean downloadFile(String imgURI, OutputStream os);
  public boolean uploadFile(String name,ByteArrayInputStream file) ;
}
