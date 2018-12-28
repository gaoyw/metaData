package com.meta.ffmpeg.model;

/**
 * 视频元数据
 * 
 * @ClassName: VideoMetadata
 * @author gaoy
 * @date 2018年12月28日 下午4:06:14
 */
public class VideoMetadata {

	private String videoCodedFormat; // 编码格式

	private String videoFormat; // 视频格式

	private String videoResolution; // 分辨率

	private String Duration; // 播放时长;

	public String getVideoCodedFormat() {
		return videoCodedFormat;
	}

	public void setVideoCodedFormat(String videoCodedFormat) {
		this.videoCodedFormat = videoCodedFormat;
	}

	public String getVideoFormat() {
		return videoFormat;
	}

	public void setVideoFormat(String videoFormat) {
		this.videoFormat = videoFormat;
	}

	public String getVideoResolution() {
		return videoResolution;
	}

	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}
}
