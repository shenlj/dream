package com.wholetech.commons.file;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrs.sysmgr.entity.SysFile;
import com.wholetech.commons.MRSConstants;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.util.DateUtil;
import com.wholetech.commons.util.PropertyXmlMgr;
import com.wholetech.commons.util.StringUtil;
import com.wholetech.commons.web.BaseAction;
import com.wholetech.commons.web.JSONResult;

/**
 * 文件名：FileAction.java
 * 作者：
 * 日期：2011-3-29
 * 功能说明：文件控制层action
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
@ParentPackage("default")
@Namespace("/file")
@Results( { @Result(name = "input", value = "/errors/error.jsp") })
public class FileAction extends BaseAction<SysFile> {

	/**
	 * 说明：
	 */
	private static final long serialVersionUID = -3897367336628218815L;
	private static Logger logger = LoggerFactory.getLogger(FileAction.class);
	private FileOperationService fileOperationService;
	private String[] filesFileName;
	private File[] files;
	private String busiID;
	private String busiType;
	private String fileID;
	// 要上传的正文文件
	private File contextDoc;

	public void setFileOperationService(FileOperationService fileOperationService) {

		this.fileOperationService = fileOperationService;
	}

	public FileOperationService getFileOperationService() {

		return this.fileOperationService;
	}

	/**
	 * 功能描述：上传附件
	 * 
	 * @author
	 * @version 1.0
	 * @see com.itc.oa.docs.web.action
	 * @return
	 *         String
	 * @throws UnsupportedEncodingException
	 */
	public String uploadFile() throws UnsupportedEncodingException {

		this.busiID = getParameter("busiID");
		this.busiType = getParameter("busiType");
		String[] fileIDs;
		if (StringUtils.isEmpty(this.busiID)) {
			fileIDs = this.fileOperationService.saveFiles(this.filesFileName, this.files);
		} else {
			fileIDs = this.fileOperationService.saveFiles(this.busiID, this.busiType, this.filesFileName, this.files);
		}
		StringBuffer sb = new StringBuffer();
		if (fileIDs != null) {
			for (String fileID2 : fileIDs) {
				sb.append(MRSConstants.DEFAULT_STRING_SPLIT).append(fileID2);
			}
		}
		renderText("success#" + sb.toString().substring(1));
		return NONE;
	}

	/**
	 * 功能描述：删除文件
	 * 
	 * @author
	 * @version 1.0
	 * @see
	 * @return
	 *         String
	 * @throws Exception
	 */
	public String deleteFile() throws Exception {

		this.fileID = getParameter("fileID");
		if (this.fileOperationService.deleteFile(this.fileID, true)) {
			renderText("success");
		}

		return NONE;
	}

	/**
	 * 功能描述：获取附件列表
	 * 
	 * @author
	 * @version 1.0
	 * @see
	 * @return
	 *         String
	 */
	public String getAttachmentsList() {

		this.busiID = getParameter("busiID");
		this.busiType = getParameter("busiType");
		List<SysFile> sysFileList = this.fileOperationService.getFileByBusi(this.busiID, this.busiType);

		JSONArray files = JSONResult.list2Json(sysFileList, "id,fileName,logicFileName,busiId,busiType,remark");
		renderJson(files);
		return NONE;
	}

	/**
	 * 功能描述：通过文件ID下载文件
	 * 
	 * @author
	 * @version 1.0
	 * @see
	 * @return
	 *         String
	 */
	public String downLoadFileByID() {

		this.fileID = getParameter("fileID");
		SysFile sysFile = this.fileOperationService.getFileById(this.fileID);

		getResponse().reset();
		try {
			String sourceFileName = sysFile.getFileName();
			getResponse().setCharacterEncoding("utf-8");
			getResponse().setHeader("Content-disposition",
					"attachment;filename=" + encodingStr(sysFile.getLogicFileName()));
			String fileType = sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1).toLowerCase();
			String contentType = PropertyXmlMgr.getString("MRSCONFIG", "file." + fileType);
			if (contentType != null) {
				getResponse().setContentType(contentType);
			} else {
				getResponse().setContentType("bin");
			}
			OutputStream os = getResponse().getOutputStream();
			if (os != null) {
				boolean flag = this.fileOperationService.downloadFile(sysFile, os);
				if (!flag) {
					this.logger.error("用户可能取消下载");
				}
			}
			getResponse().flushBuffer();
		} catch (IOException e) {
			this.logger.error("用户可能取消下载");
		}

		return NONE;
	}

	/**
	 * 功能描述：获取附件列表
	 * 
	 * @author
	 * @version 1.0
	 * @see
	 * @return
	 *         String
	 */
	public String encodingStr(String str) {

		String returnFileName = "";
		try {
			returnFileName = URLEncoder.encode(str, "UTF-8");
			returnFileName = StringUtil.replace(returnFileName, "+", "%20");
			returnFileName = new String(str.getBytes("GBK"), "ISO8859-1");
			returnFileName = StringUtil.replace(returnFileName, " ", "%20");
		} catch (UnsupportedEncodingException e) {
			this.logger.error("Don't support this encoding ...");
		}
		return returnFileName;
	}

	/**
	 * 功能描述：上传报告编写
	 * 
	 * @author
	 * @version 1.0
	 * @see
	 * @return
	 *         String
	 * @throws UnsupportedEncodingException
	 */
	public String uploadChapterDoc() throws UnsupportedEncodingException {

		String fileName = DateUtil.getCurrentDateTime() + ".doc";
		this.filesFileName = new String[1];
		this.files = new File[1];
		this.filesFileName[0] = fileName;
		this.files[0] = this.contextDoc;
		this.busiID = getParameter("busiID");
		this.busiType = getParameter("busiType");
		this.fileID = getParameter("fileID");

		if (StringUtils.isEmpty(this.fileID)) {
			if (StringUtils.isNotEmpty(this.busiID) && StringUtils.isNotEmpty(this.busiType)) {
				// 业务ID与业务type不为空时
				this.fileID = this.fileOperationService.saveFiles(this.busiID, this.busiType, this.filesFileName,
						this.files)[0];
				renderText("success#" + this.fileID);
			} else {
				this.fileID = this.fileOperationService.saveFiles(this.filesFileName, this.files)[0];
				renderText("success#" + this.fileID);
			}
		} else {
			this.fileOperationService.executeReplaceFile(this.fileID, this.contextDoc);
			renderText("success");
		}
		return NONE;
	}

	@Override
	protected BaseService getBaseService() {

		return null;
	}

	public File getContextDoc() {

		return this.contextDoc;
	}

	public void setContextDoc(File contextDoc) {

		this.contextDoc = contextDoc;
	}

	public String getBusiID() {

		return this.busiID;
	}

	public void setBusiID(String busiID) {

		this.busiID = busiID;
	}

	public String getFileID() {

		return this.fileID;
	}

	public void setFileID(String fileID) {

		this.fileID = fileID;
	}

	public String getBusiType() {

		return this.busiType;
	}

	public void setBusiType(String busiType) {

		this.busiType = busiType;
	}

	public File[] getFiles() {

		return this.files;
	}

	public void setFiles(File[] files) {

		this.files = files;
	}

	public String[] getFilesFileName() {

		return this.filesFileName;
	}

	public void setFilesFileName(String[] filesFileName) {

		this.filesFileName = filesFileName;
	}

}
