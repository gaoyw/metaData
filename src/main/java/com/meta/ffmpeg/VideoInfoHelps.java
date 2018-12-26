package com.meta.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取视频音频的各项属性帮助类 如果需要修改或者添加属性，只要扩展下面的二维数组和修改下面getVideoInfo()方法
 * 
 * @author tanghui
 *
 */
@RestController
@RequestMapping(value = "ffmpeg")
public class VideoInfoHelps {

	private static final Logger logger = LoggerFactory.getLogger(VideoInfoHelps.class);

	// public static final String ffmpegPath =
	// "/C:/metaWorkspace/metadata/target/classes/tools/ffmpeg.exe"; //
	// ffmpeg.exe的目录
	// public static final String filePath = "C:\\Users\\gaoy\\Videos\\视频测试\\源计划
	// 火.mp4";
	// public static final String ffmpegUrl =
	// VideoInfoHelps.class.getClassLoader().getResource("tools/win/ffmpeg.exe")
	// .getPath();
	// public static final String videoUrl =
	// VideoInfoHelps.class.getClassLoader()
	// .getResource("test-documents/video/testMP4.mp4").getPath();
	// public static final String audioUrl =
	// VideoInfoHelps.class.getClassLoader()
	// .getResource("test-documents/audio/西单女孩 - 原点.mp3").getPath();

	// public static final String ffmpegUrl =
	// "D:/ffmpeg-win64-static/bin/ffmpeg.exe";
	// public static final String videoUrl =
	// "C:/Users/gaoy/Videos/视频测试/源计划火.mp4";
	// public static final String audioUrl = "C:/Users/gaoy/Music/音频测试/西单女孩 -
	// 原点.mp3";

	public static final String ffmpegUrl = "/home/ffmpeg-linux/ffmpeg";
	public static final String videoUrl = "/home/fileTest/源计划火.mp4";
	public static final String audioUrl = "/home/fileTest/西单女孩 - 原点.mp3";

	// https://blog.csdn.net/ajklaclk/article/details/80753302
	/**
	 * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@GetMapping("getFile/{type}")
	public static String processVideo(@PathVariable("type") String type) throws IOException, InterruptedException {
		String ffpegUrlStr = URLDecoder.decode(ffmpegUrl, "UTF-8");
		System.err.println("ffpegUrlStr: " + ffpegUrlStr);
		String fileUrl = null;
		// String fileUrlSub = null;
		if (type.equals("audio")) {
			String audioUrlStr = URLDecoder.decode(audioUrl, "UTF-8");
			System.err.println("audioUrlStr: " + audioUrlStr);
			fileUrl = audioUrlStr;
		} else if (type.equals("video")) {
			String videoUrlStr = URLDecoder.decode(videoUrl, "UTF-8");
			System.err.println("videoUrlStr: " + videoUrlStr);
			fileUrl = videoUrlStr;
		}

		// fileUrlSub = fileUrl.substring(1, fileUrl.length());
		// System.err.println("fileUrlSub: " + fileUrlSub);

		Path testPath = Paths.get(fileUrl);
		BasicFileAttributeView basicView = Files.getFileAttributeView(testPath, BasicFileAttributeView.class);
		BasicFileAttributes basicFileAttributes = basicView.readAttributes();
		// System.err.println("创建时间：" + new
		// Date(basicFileAttributes.creationTime().toMillis()));
		// System.err.println("最后访问时间：" + new
		// Date(basicFileAttributes.lastAccessTime().toMillis()));
		// System.err.println("最后修改时间：" + new
		// Date(basicFileAttributes.lastModifiedTime().toMillis()));
		System.err.println("文件大小：" + basicFileAttributes.size());
		System.err.println("文件大小：" + getPrintSize(basicFileAttributes.size()));
		FileOwnerAttributeView ownerView = Files.getFileAttributeView(testPath, FileOwnerAttributeView.class);
		System.err.println("文件所有者：" + ownerView.getOwner());
		String ctime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(new Date(basicFileAttributes.lastAccessTime().toMillis()));// 最后访问时间
		System.err.println("最后访问时间: " + ctime);
		String cetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(new Date(basicFileAttributes.creationTime().toMillis()));// 创建时间
		System.err.println("创建时间: " + cetime);
		String etime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(new Date(basicFileAttributes.lastModifiedTime().toMillis()));// 最后修改时间
		System.err.println("最后修改时间: " + etime);

		List<String> commend = new ArrayList<String>();
		commend.add(ffpegUrlStr);// 可以设置环境变量从而省去这行
		commend.add("-i");
		commend.add(fileUrl);
		// try {
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
		// } catch (Exception e) {
		// e.getStackTrace();
		// logger.error("ffmpeg解析视频文件【" + fileUrl + "】失败!");
		// return null;
		// }
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

	public static String getPrintSize(long size) {
		// 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
		if (size < 1024) {
			return String.valueOf(size) + " B";
		} else {
			size = size / 1024;
		}
		// 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		// 因为还没有到达要使用另一个单位的时候
		// 接下去以此类推
		if (size < 1024) {
			return String.valueOf(size) + " KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			// 因为如果以MB为单位的话，要保留最后1位小数，
			// 因此，把此数乘以100之后再取余
			size = size * 100;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + " MB";
		} else {
			// 否则如果要以GB为单位的，先除于1024再作同样的处理
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + " GB";
		}
	}
}