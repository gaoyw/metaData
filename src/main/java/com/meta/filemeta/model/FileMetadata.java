package com.meta.filemeta.model;

/**
 * 文件元数据
 * 
 * @ClassName: FileMetadata
 * @author gaoy
 * @date 2018年12月28日 下午4:19:15
 */
public class FileMetadata {

	private String fileName; // 文件名称

	private String fileSize; // 文件大小

	private String fileFormat; // 文件格式

	private String fileOwner;// 文件所有者

	private String lastAccessTime; // 最后访问时间

	private String creationTime; // 创建时间

	private String lastModifiedTime;// 最后修改时间

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getFileOwner() {
		return fileOwner;
	}

	public void setFileOwner(String fileOwner) {
		this.fileOwner = fileOwner;
	}

	public String getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
}
