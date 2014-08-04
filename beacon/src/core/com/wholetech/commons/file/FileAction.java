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
import com.opensymphony.xwork2.Action;
import com.wholetech.commons.MRSConstants;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.util.DateUtil;
import com.wholetech.commons.util.PropertyXmlMgr;
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
@Results({ @Result(name = "input", value = "/errors/error.jsp") })
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

	public void setFileOperationService(final FileOperationService fileOperationService) {

		this.fileOperationService = fileOperationService;
	}

	public FileOperationService getFileOperationService() {

		return fileOperationService;
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

		busiID = getParameter("busiID");
		busiType = getParameter("busiType");
		String[] fileIDs;
		if (StringUtils.isEmpty(busiID)) {
			fileIDs = fileOperationService.saveFiles(filesFileName, files);
		} else {
			fileIDs = fileOperationService.saveFiles(busiID, busiType, filesFileName, files);
		}
		final StringBuffer sb = new StringBuffer();
		if (fileIDs != null) {
			for (final String fileID2 : fileIDs) {
				sb.append(MRSConstants.DEFAULT_STRING_SPLIT).append(fileID2);
			}
		}
		renderText("success#" + sb.toString().substring(1));
		return Action.NONE;
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

		fileID = getParameter("fileID");
		if (fileOperationService.deleteFile(fileID, true)) {
			renderText("success");
		}

		return Action.NONE;
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

		busiID = getParameter("busiID");
		busiType = getParameter("busiType");
		final List<SysFile> sysFileList = fileOperationService.getFileByBusi(busiID, busiType);

		final JSONArray files = JSONResult.list2Json(sysFileList, "id,fileName,logicFileName,busiId,busiType,remark");
		renderJson(files);
		return Action.NONE;
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

		fileID = getParameter("fileID");
		final SysFile sysFile = fileOperationService.getFileById(fileID);

		getResponse().reset();
		try {
			final String sourceFileName = sysFile.getFileName();
			getResponse().setCharacterEncoding("utf-8");
			getResponse().setHeader("Content-disposition",
					"attachment;filename=" + encodingStr(sysFile.getLogicFileName()));
			final String fileType = sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1).toLowerCase();
			final String contentType = PropertyXmlMgr.getString("MRSCONFIG", "file." + fileType);
			if (contentType != null) {
				getResponse().setContentType(contentType);
			} else {
				getResponse().setContentType("bin");
			}
			final OutputStream os = getResponse().getOutputStream();
			if (os != null) {
				final boolean flag = fileOperationService.downloadFile(sysFile, os);
				if (!flag) {
					logger.error("用户可能取消下载");
				}
			}
			getResponse().flushBuffer();
		} catch (final IOException e) {
			logger.error("用户可能取消下载");
		}

		return Action.NONE;
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
	public String encodingStr(final String str) {

		String returnFileName = "";
		try {
			returnFileName = URLEncoder.encode(str, "UTF-8");
			returnFileName = StringUtils.replace(returnFileName, "+", "%20");
			returnFileName = new String(str.getBytes("GBK"), "ISO8859-1");
			returnFileName = StringUtils.replace(returnFileName, " ", "%20");
		} catch (final UnsupportedEncodingException e) {
			logger.error("Don't support this encoding ...");
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

		final String fileName = DateUtil.getCurrentDateTime() + ".doc";
		filesFileName = new String[1];
		files = new File[1];
		filesFileName[0] = fileName;
		files[0] = contextDoc;
		busiID = getParameter("busiID");
		busiType = getParameter("busiType");
		fileID = getParameter("fileID");

		if (StringUtils.isEmpty(fileID)) {
			if (StringUtils.isNotEmpty(busiID) && StringUtils.isNotEmpty(busiType)) {
				// 业务ID与业务type不为空时
				fileID = fileOperationService.saveFiles(busiID, busiType, filesFileName,
						files)[0];
				renderText("success#" + fileID);
			} else {
				fileID = fileOperationService.saveFiles(filesFileName, files)[0];
				renderText("success#" + fileID);
			}
		} else {
			fileOperationService.executeReplaceFile(fileID, contextDoc);
			renderText("success");
		}
		return Action.NONE;
	}

	@Override
	protected BaseService getBaseService() {

		return null;
	}

	public File getContextDoc() {

		return contextDoc;
	}

	public void setContextDoc(final File contextDoc) {

		this.contextDoc = contextDoc;
	}

	public String getBusiID() {

		return busiID;
	}

	public void setBusiID(final String busiID) {

		this.busiID = busiID;
	}

	public String getFileID() {

		return fileID;
	}

	public void setFileID(final String fileID) {

		this.fileID = fileID;
	}

	public String getBusiType() {

		return busiType;
	}

	public void setBusiType(final String busiType) {

		this.busiType = busiType;
	}

	public File[] getFiles() {

		return files;
	}

	public void setFiles(final File[] files) {

		this.files = files;
	}

	public String[] getFilesFileName() {

		return filesFileName;
	}

	public void setFilesFileName(final String[] filesFileName) {

		this.filesFileName = filesFileName;
	}

}
