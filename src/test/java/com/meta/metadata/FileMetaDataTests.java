package com.meta.metadata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.meta.util.FileSizeUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileMetaDataTests {

	@Test
	public void getFile() {
		String path = "C:\\Users\\gaoy\\Music\\音频测试\\midi测试.midi";
		String ss = FileSizeUtil.getAutoFileOrFilesSize(path);
		System.err.println(ss);
	}
}
