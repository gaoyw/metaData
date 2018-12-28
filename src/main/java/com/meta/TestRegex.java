package com.meta;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.meta.ffmpeg.VideoInfoHelps;

public class TestRegex {

	public static void main(String[] args) {

		String fileName = "ddd.bb.rm测试.rm";
		String nameStr = fileName;
		int n = nameStr.lastIndexOf(".");
		String name = nameStr.substring(0, n);
		System.err.println(name);

		String sb = "";
		String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
		String regexAudio = "Audio: (.*?), (\\d*) Hz, (.*?), (.*?), (\\d*) kb\\/s";
		String regexVideo = "Video: (.*?), (.*?), (\\d.*?)[,\\s]";
		Pattern pattern = Pattern.compile(regexDuration);
		Matcher m = pattern.matcher(sb.toString());
		if (m.find()) {
			int time = VideoInfoHelps.getTimelen(m.group(1));
			System.out.println("提取出播放时间  === " + time);
			System.out.println("提取出播放时间  === " + m.group(1));
			System.out.println("开始时间        ===== " + m.group(2));
			System.out.println("bitrate 码率 单位 kb== " + m.group(3));
		}
		Pattern patternAudio = Pattern.compile(regexAudio);
		Matcher mAudio = patternAudio.matcher(sb.toString());
		if (mAudio.find()) {
			System.err.println("audio: " + mAudio.toString());
			System.out.println("音频编码 === " + mAudio.group(1));
			System.out.println("音频采样频率 === " + mAudio.group(2));
			System.out.println("音频声道  === " + mAudio.group(3));
			System.out.println("音频码率 === " + mAudio.group(5));

		}
		Pattern patternVideo = Pattern.compile(regexVideo);
		Matcher mVideo = patternVideo.matcher(sb.toString());
		if (mVideo.find()) {
			System.err.println("video: " + mVideo.toString());
			System.err.println("编码格式  === " + mVideo.group(1));
			System.err.println("视频格式   === " + mVideo.group(2));
			System.err.println("分辨率  === " + mVideo.group(3));
		}
	}
}
