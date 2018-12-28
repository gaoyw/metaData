package com.meta.ffmpeg.model;

/**
 * 音频元数据
 * 
 * @ClassName: AudioMetadata
 * @author gaoy
 * @date 2018年12月28日 下午4:13:38
 */
public class AudioMetadata {

	private String audioCodedFormat; // 编码格式

	private String audioSamplingRate; // 采样率

	private String audioVocalTract; // 声道

	private String audioBitrate; // 比特率(码率)

	private String Duration; // 播放时长

	public String getAudioCodedFormat() {
		return audioCodedFormat;
	}

	public void setAudioCodedFormat(String audioCodedFormat) {
		this.audioCodedFormat = audioCodedFormat;
	}

	public String getAudioSamplingRate() {
		return audioSamplingRate;
	}

	public void setAudioSamplingRate(String audioSamplingRate) {
		this.audioSamplingRate = audioSamplingRate;
	}

	public String getAudioVocalTract() {
		return audioVocalTract;
	}

	public void setAudioVocalTract(String audioVocalTract) {
		this.audioVocalTract = audioVocalTract;
	}

	public String getAudioBitrate() {
		return audioBitrate;
	}

	public void setAudioBitrate(String audioBitrate) {
		this.audioBitrate = audioBitrate;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}
}
