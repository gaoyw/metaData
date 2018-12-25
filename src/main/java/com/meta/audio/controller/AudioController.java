package com.meta.audio.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.mp4.Mp4FileReader;
import org.jaudiotagger.tag.TagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

/**
 * 音配文件读取元数据
 * 
 * @ClassName: AudioController
 * @author gaoy
 * @date 2018年12月12日 下午3:34:38
 */
@RestController
@RequestMapping("audio")
public class AudioController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AudioController.class);

	private static final SimpleDateFormat timeInFormat = new SimpleDateFormat("ss", Locale.UK);
	private static final SimpleDateFormat timeOutFormat = new SimpleDateFormat("mm:ss", Locale.UK);
	private static final SimpleDateFormat timeOutOverAnHourFormat = new SimpleDateFormat("kk:mm:ss", Locale.UK);
	private static final int NO_SECONDS_IN_HOUR = 3600;

	@PostMapping("audioMeta")
	public String uploadFile(@RequestParam("file") MultipartFile file)
			throws CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		if (file.isEmpty()) {
			return "上传失败，请选择文件";
		}
		String fileName = file.getOriginalFilename();
		String filePath = "E:/upload/";
		File dest = new File(filePath + fileName);
		// MP3File mp3File = null;
		try {
			file.transferTo(dest);
			Path testPath = Paths.get(filePath + fileName);
			BasicFileAttributeView basicView = Files.getFileAttributeView(testPath, BasicFileAttributeView.class);
			BasicFileAttributes basicFileAttributes = basicView.readAttributes();
			System.err.println("创建时间：" + new Date(basicFileAttributes.creationTime().toMillis()));
			System.err.println("最后访问时间：" + new Date(basicFileAttributes.lastAccessTime().toMillis()));
			System.err.println("最后修改时间：" + new Date(basicFileAttributes.lastModifiedTime().toMillis()));
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
			System.out.println("=====================================================================");
			FlacFileReader fileReader = new FlacFileReader();
			AudioFile s = fileReader.read(dest);
			AudioHeader fau = s.getAudioHeader();
			System.err.println("f比特率: " + fau.getBitRate()); // 获得比特率
			System.err.println("f采样率: " + fau.getSampleRate()); // 采样率
			System.err.println("f音轨长度: " + fau.getTrackLength()); // 音轨长度
			System.err.println("f格式: " + fau.getFormat()); // 格式，例 MPEG-1
			System.err.println("w音轨长度String: " + getTrackString(fau.getTrackLength()));
			System.err.println("文件名称: " + s.getFile().getName()); // 文件名称
			System.err.println("文件大小: " + s.getFile().length() + "字节");//
			System.err.println("文件大小: " + getPrintSize(s.getFile().length()));

			System.out.println("=====================================================================");
			// WavFileReader wavFileReader = new WavFileReader();
			// AudioFile ss = wavFileReader.read(dest);
			// AudioHeader wau = ss.getAudioHeader();
			// System.err.println("w比特率: " + wau.getBitRate()); // 获得比特率
			// System.err.println("w采样率: " + wau.getSampleRate()); // 采样率
			// System.err.println("w格式: " + wau.getFormat()); // 格式，例 MPEG-1
			// System.err.println("w音轨长度: " + wau.getTrackLength()); // 音轨长度
			// System.err.println("w音轨长度String: " +
			// getTrackString(wau.getTrackLength()));
			System.out.println("=====================================================================");
			// mp3File = new MP3File(dest);
			// MP3AudioHeader header = mp3File.getMP3AudioHeader();
			// System.out.println("=====================================================================");
			// System.err.println();
			// System.err.println("时长String: " +
			// header.getTrackLengthAsString()); // 获得时长
			// System.err.println("时长int: " + header.getTrackLength()); // 获得时长
			// System.err.println("比特率: " + header.getBitRate()); // 获得比特率
			// System.err.println("音轨长度: " + header.getTrackLength()); // 音轨长度
			// System.err.println("格式: " + header.getFormat()); // 格式，例 MPEG-1
			// System.err.println("声道: " + header.getChannels()); // 声道
			// System.err.println("采样率: " + header.getSampleRate()); // 采样率
			// System.err.println("MPEG: " + header.getMpegLayer()); // MPEG
			// System.err.println("MP3起始字节: " + header.getMp3StartByte());
			// MP3起始字节
			// System.err.println("精确的音轨长度: " + header.getPreciseTrackLength());
			// 精确的音轨长度
			System.out.println("=====================================================================");
			LOGGER.info("上传成功");
			return "上传成功";
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
		}
		return "上传失败！";
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

	public static String getTrackString(int trackLenagth) {
		final Date timeIn;
		try {
			final long lengthInSecs = trackLenagth;
			synchronized (timeInFormat) {
				timeIn = timeInFormat.parse(String.valueOf(lengthInSecs));
			}

			if (lengthInSecs < NO_SECONDS_IN_HOUR) {
				synchronized (timeOutFormat) {
					return timeOutFormat.format(timeIn);
				}
			} else {
				synchronized (timeOutOverAnHourFormat) {
					return timeOutOverAnHourFormat.format(timeIn);
				}
			}
		} catch (ParseException pe) {
			LOGGER.error("Unable to parse:" + trackLenagth + " failed with ParseException:" + pe.getMessage());
			return "";
		}
	}
}
