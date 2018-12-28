package com.meta.ffmpeg;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.meta.ffmpeg.model.AudioMetadata;
import com.meta.ffmpeg.model.VideoMetadata;
import com.meta.filemeta.model.FileMetadata;
import com.meta.filemeta.model.MetadataFile;

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

	// public static final String ffmpegUrl = "/home/ffmpeg-linux/ffmpeg";
	// public static final String filePath = "/home/fileTest/";
	public static final String ffmpegUrl = "D:/ffmpeg-win64-static/bin/ffmpeg.exe";
	public static final String filePath = "E:/upload/";

	/**
	 * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@PostMapping("getFile")
	public static String processVideo(@RequestParam("file") MultipartFile file) throws IOException {
		FileMetadata fileMetadata = new FileMetadata();
		AudioMetadata audioMetadata = new AudioMetadata();
		VideoMetadata videoMetadata = new VideoMetadata();
		MetadataFile metadataFile = new MetadataFile();
		if (file.isEmpty()) {
			return "上传失败，请选择文件";
		}
		String fileName = file.getOriginalFilename();
		File dest = new File(filePath + fileName);
		file.transferTo(dest);
		Path testPath = Paths.get(filePath + fileName);
		String ffpegUrlStr = null;
		String fileUrl = null;
		try {
			ffpegUrlStr = URLDecoder.decode(ffmpegUrl, "UTF-8");
			fileUrl = URLDecoder.decode(testPath.toString(), "UTF-8");
			System.err.println("fileUrl: " + fileUrl);
			System.err.println("ffpegUrlStr: " + ffpegUrlStr);
		} catch (UnsupportedEncodingException e3) {
			e3.printStackTrace();
			System.err.println("URLDecoder转换失败");
		}
		BasicFileAttributeView basicView = Files.getFileAttributeView(testPath, BasicFileAttributeView.class);
		BasicFileAttributes basicFileAttributes;
		try {
			String nameStr = fileName;
			int n = nameStr.lastIndexOf(".");
			String name = nameStr.substring(0, n);
			System.err.println("namess: " + name);
			String formatStr = fileName;
			int f = formatStr.lastIndexOf(".");
			String format = formatStr.substring((f + 1), formatStr.length());
			fileMetadata.setFileName(name);
			fileMetadata.setFileFormat(format + " 格式");
			basicFileAttributes = basicView.readAttributes();
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
			fileMetadata.setFileSize(getPrintSize(basicFileAttributes.size()));
			fileMetadata.setFileOwner(ownerView.getOwner().toString() + " 文件所有者");
			fileMetadata.setLastAccessTime(ctime + " 最后访问时间");
			fileMetadata.setCreationTime(cetime + " 创建时间");
			fileMetadata.setLastModifiedTime(etime + " 最后修改时间");
			metadataFile.setFileMetadata(fileMetadata);
		} catch (IOException e2) {
			e2.printStackTrace();
			System.err.println("获取文件属性失败");
		}
		List<String> commend = new ArrayList<String>();
		commend.add(ffpegUrlStr);// 可以设置环境变量从而省去这行
		commend.add("-i");
		commend.add(fileUrl);
		ProcessBuilder builder = new ProcessBuilder();
		Process process = null;
		builder.command(commend);
		builder.redirectErrorStream(true);
		try {
			process = builder.start();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("解析失败");
		}

		// 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
		InputStream inputStream = process.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		String line = "";
		StringBuffer sb = new StringBuffer(); // 线程安全的
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
				System.err.println("line: " + line);
			}
			if (br != null) {
				br.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("BufferedReader，读取失败");
		}

		String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
		String regexAudio = "Audio: (.*?), (\\d*) Hz, (.*?), (.*?), (\\d*) kb\\/s";
		String regexVideo = "Video: (.*?), (.*?), (\\d.*?)[,\\s]";
		Pattern pattern = Pattern.compile(regexDuration);
		Matcher m = pattern.matcher(sb.toString());
		if (m.find()) {
			// int time = getTimelen(m.group(1));
			// System.out.println("提取出播放时间 === " + time);
			// System.out.println("提取出播放时间 === " + m.group(1));
			// System.out.println("开始时间 ===== " + m.group(2));
			// System.out.println("bitrate 码率 单位 kb== " + m.group(3));
			String duration = m.group(1).substring(0, m.group(1).indexOf("."));
			videoMetadata.setDuration(duration + " 时长");
			audioMetadata.setDuration(duration + " 时长");
		}
		Pattern patternAudio = Pattern.compile(regexAudio);
		Matcher mAudio = patternAudio.matcher(sb.toString());
		if (mAudio.find()) {
			// System.err.println("audio: " + mAudio.toString());
			// System.err.println("音频编码 === " + mAudio.group(1));
			// System.err.println("音频采样频率 === " + mAudio.group(2));
			// System.err.println("音频声道 === " + mAudio.group(3));
			// System.err.println("音频比特率 === " + mAudio.group(5));
			audioMetadata.setAudioCodedFormat(mAudio.group(1) + " 音频编码");
			audioMetadata.setAudioSamplingRate(mAudio.group(2) + " Hz 音频采样率");
			audioMetadata.setAudioVocalTract(mAudio.group(3) + " 音频声道");
			audioMetadata.setAudioBitrate(mAudio.group(5) + " kbps 音频比特率");
			metadataFile.setAudioMetadata(audioMetadata);

		}
		Pattern patternVideo = Pattern.compile(regexVideo);
		Matcher mVideo = patternVideo.matcher(sb.toString());
		if (mVideo.find()) {
			// System.err.println("video: " + mVideo.toString());
			// System.err.println("编码格式 === " + mVideo.group(1));
			// System.err.println("视频格式 === " + mVideo.group(2));
			// System.err.println("分辨率 === " + mVideo.group(3));
			videoMetadata.setVideoCodedFormat(mVideo.group(1) + " 编码格式");
			videoMetadata.setVideoFormat(mVideo.group(2) + " 视频格式");
			videoMetadata.setVideoResolution(mVideo.group(3) + " 分辨率");
			metadataFile.setVideoMetadata(videoMetadata);
		}
		System.err.println(JSON.toJSONString(metadataFile, true));
		return JSON.toJSONString(metadataFile);
	}

	// 格式:"00:00:10.68"
	public static int getTimelen(String timelen) {
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