package com.meta.metadata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.meta.ffmpeg.VideoInfoHelps;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FFmpegTests {

	public static final String ffmpegPath = "D:\\ffmpeg-win64-static\\bin\\ffmpeg.exe"; // ffmpeg.exe的目录

	/**
	 * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 * 
	 * @param inputPath
	 * @return
	 */
	@Test
	public void processVideo() {
		VideoInfoHelps.processVideo(null);
	}
}
