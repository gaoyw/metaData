package com.meta.ffmpeg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 获取视频音频的各项属性帮助类 如果需要修改或者添加属性，只要扩展下面的二维数组和修改下面getVideoInfo()方法
 * 
 * @author tanghui
 *
 */
public class VideoInfoHelps {

	private static final Logger logger = LoggerFactory.getLogger(VideoInfoHelps.class);

	// public static final String ffmpegPath =
	// "D:\\ffmpeg-win64-static\\bin\\ffmpeg.exe"; // ffmpeg.exe的目录
	public static final String ffmpegPath = "/C:/metaWorkspace/metadata/target/classes/tools/ffmpeg.exe"; // ffmpeg.exe的目录

	/**
	 * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 */
	public static String processVideo(String filePath) {
		URL url = VideoInfoHelps.class.getClassLoader().getResource("tools/ffmpeg.exe");
		System.err.println("url: " + url);

		// filePath = "C:\\Users\\gaoy\\Music\\音频测试\\midi测试mid";
		filePath = "C:\\Users\\gaoy\\Videos\\视频测试\\源计划 火.mp4";
		List<String> commend = new java.util.ArrayList<String>();
		commend.add(ffmpegPath);// 可以设置环境变量从而省去这行
		commend.add("-i");
		commend.add(filePath);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader buf = null; // 保存ffmpeg的输出结果流
			String line = null;
			buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuffer sb = new StringBuffer();
			while ((line = buf.readLine()) != null) {
				System.err.println("line: " + line);
				sb.append(line);
				continue;
			}
			p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
			// 从视频信息中解析时长
			String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
			Pattern pattern = Pattern.compile(regexDuration);
			Matcher m = pattern.matcher(sb.toString());
			if (m.find()) {
				int time = getTimelen(m.group(1));
				System.err.println(",视频时长：" + time + ", 开始时间：" + m.group(2) + ",比特率：" + m.group(3) + "kb/s");
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("ffmpeg解析视频文件【" + filePath + "】失败!");
			return null;
		}
	}

	// 格式:"00:00:10.68"
	private static int getTimelen(String timelen) {
		int min = 0;
		String strs[] = timelen.split(":");
		if (strs[0].compareTo("0") > 0) {
			min += Integer.valueOf(strs[0]) * 60 * 60;// 秒
		}
		if (strs[1].compareTo("0") > 0) {
			min += Integer.valueOf(strs[1]) * 60;
		}
		if (strs[2].compareTo("0") > 0) {
			min += Math.round(Float.valueOf(strs[2]));
		}
		return min;
	}
}