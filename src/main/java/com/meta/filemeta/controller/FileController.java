package com.meta.filemeta.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * 文件上传测试
 * 
 * @ClassName: FileController
 * @author gaoy
 * @date 2018年12月11日 下午3:45:52
 */
@RestController
@RequestMapping("uploadFile")
public class FileController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

	@PostMapping("jpegFile")
	public String uploadFile(@RequestParam("file") MultipartFile file) throws ImageProcessingException, IOException {
		if (file.isEmpty()) {
			return "上传失败，请选择文件";
		}
		String fileName = file.getOriginalFilename();
		String filePath = "E:/upload/";
		File dest = new File(filePath + fileName);
		try {
			file.transferTo(dest);
			Metadata metadata = ImageMetadataReader.readMetadata(dest);
			for (Directory directory : metadata.getDirectories()) {
				for (Tag tag : directory.getTags()) {
					System.err.println("TagName：" + tag.getTagName() + "，Description：" + tag.getDescription());
					System.err.println();
				}
			}
			LOGGER.info("上传成功");
			return "上传成功";
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
		}
		return "上传失败！";
	}
}
