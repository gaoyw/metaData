package com.meta.metadata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FFmpegTests {

	// String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*)
	// kb\\/s";
	String regexDuration = "Duration: (.*?), bitrate: (\\d*) kb\\/s";
	String regexAudio = "Audio: (.*?), (\\d*) Hz, (.*?), (.*?), (\\d*) kb\\/s";
	String regexVideo = "Video: (.*?), (.*?), (\\d.*?)[,\\s]";
	String regexAudio2 = "Audio: (.*?), (\\d*) Hz, (.*?), (.*?)";

	@Test
	public void processVideo() {
		String sb = "Audio: mp3, 44100 Hz, stereo, fltp, 320 kb/s";
		// String sb = "Audio: amr_nb (samr / 0x726D6173), 8000 Hz, mono, flt";

		Pattern pattern = Pattern.compile(regexAudio);
		Matcher m = pattern.matcher(sb.toString());
		if (m.find()) {
			System.err.println("m: " + m);
			System.err.println(m.group(1));
			System.err.println(m.group(2));
			System.err.println(m.group(3));
			System.err.println(m.group(4));
			System.err.println(m.group(5));
		}
		Pattern pattern2 = Pattern.compile(regexAudio2);
		Matcher m2 = pattern2.matcher(sb.toString());
		if (m2.find()) {
			System.err.println("m2: " + m2);
			System.err.println(m2.group(1));
			System.err.println(m2.group(2));
			System.err.println(m2.group(3));
		}
	}
}
