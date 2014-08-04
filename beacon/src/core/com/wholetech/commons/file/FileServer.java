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

	public FileServer(final String rootPath) throws IOException {

		setRootDiretory(rootPath);
	}

	public void setRootDiretory(final String rootPath) throws IOException {

		this.rootPath = rootPath;
		currentPath = rootPath;
	}

	public void changeDirectory(final String path) throws IOException {

		if (isAbsolute(path)) {
			currentPath = rootPath + FileServer.FILE_SPLIT + path;
		} else {
			currentPath = currentPath + FileServer.FILE_SPLIT + path;
		}
	}

	public boolean makeDirectory(final String path) throws IOException {

		return getFileByPath(path).mkdirs();
	}

	public boolean removeDirectory(final String path) throws IOException {

		final File[] fileList = getFileByPath(path).listFiles();
		for (final File file : fileList) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				removeDirectory(path + FileServer.FILE_SPLIT + file.getName());
			}
		}
		return getFileByPath(path).delete();
	}

	public boolean existDirectory(final String path) throws IOException {

		return getFileByPath(path).exists();
	}

	public List<String> getFileList(final String path) throws IOException {

		return Arrays.asList(getFileByPath(path).list());
	}

	public boolean deleteFile(final String pathName) throws IOException {

		return getFileByPath(pathName).delete();
	}

	public boolean uploadFile(final String fileName, final String newName) throws IOException {

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

	public boolean uploadFile(final String fileName) throws IOException {

		return uploadFile(fileName, fileName);
	}

	public boolean uploadFile(final InputStream iStream, final String newName) throws IOException {

		OutputStream os = null;
		try {
			String remotePath = null;
			if (newName.contains(FileServer.FILE_SPLIT)) {
				remotePath = newName.substring(0, newName.lastIndexOf(FileServer.FILE_SPLIT));
				// 创建服务器远程目录结构，创建失败直接返回
				makeDirectory(remotePath);
			}
			final File file = getFileByPath(newName);
			os = new BufferedOutputStream(new FileOutputStream(file));
			org.apache.commons.io.IOUtils.copyLarge(iStream, os);
			return true;
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			org.apache.commons.io.IOUtils.closeQuietly(os);
		}
	}

	public boolean download(final String remoteFileName, final String localFileName) throws IOException {

		final File outfile = new File(localFileName);
		OutputStream oStream = null;
		InputStream is = null;
		try {
			oStream = new BufferedOutputStream(new FileOutputStream(outfile));
			is = new BufferedInputStream(new FileInputStream(getFileByPath(remoteFileName)));
			org.apache.commons.io.IOUtils.copyLarge(is, oStream);
			return true;
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			org.apache.commons.io.IOUtils.closeQuietly(is);
			org.apache.commons.io.IOUtils.closeQuietly(oStream);
		}
	}

	public InputStream downFile(final String sourceFileName) throws IOException {

		return new BufferedInputStream(new FileInputStream(getFileByPath(sourceFileName)));
	}

	private boolean isAbsolute(final String path) {

		return path.startsWith(FileServer.FILE_SPLIT);
	}

	private File getFileByPath(final String path) {

		if (isAbsolute(path)) {
			return new File(rootPath + FileServer.FILE_SPLIT + path);
		} else {
			return new File(currentPath + FileServer.FILE_SPLIT + path);
		}
	}

	public static void main(final String[] arg) throws Exception {

		final FileServer fileServer = new FileServer("E:");
		// fileServer.makeDirectory("/ddgg/dfdf");
		// // fileServer.removeDirectory("ddgg");
		// fileServer.uploadFile("d:/新建 文本文档.txt", "/dfdaf/df.txt");
		fileServer.download("/traj1.txt", "E:/xxx.txt");
	}
}
