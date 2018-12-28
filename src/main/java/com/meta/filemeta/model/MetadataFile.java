package com.meta.filemeta.model;

import com.meta.ffmpeg.model.AudioMetadata;
import com.meta.ffmpeg.model.VideoMetadata;

/**
 * 元数据
 * 
 * @ClassName: MetadataFile
 * @author gaoy
 * @date 2018年12月28日 下午5:02:54
 */
public class MetadataFile {

	private FileMetadata fileMetadata;

	private AudioMetadata audioMetadata;

	private VideoMetadata videoMetadata;

	public FileMetadata getFileMetadata() {
		return fileMetadata;
	}

	public void setFileMetadata(FileMetadata fileMetadata) {
		this.fileMetadata = fileMetadata;
	}

	public AudioMetadata getAudioMetadata() {
		return audioMetadata;
	}

	public void setAudioMetadata(AudioMetadata audioMetadata) {
		this.audioMetadata = audioMetadata;
	}

	public VideoMetadata getVideoMetadata() {
		return videoMetadata;
	}

	public void setVideoMetadata(VideoMetadata videoMetadata) {
		this.videoMetadata = videoMetadata;
	}
}
