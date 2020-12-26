package com.j.board.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

import com.j.board.domain.FilesVO;
import com.j.board.persistence.BoardMapper;

@Service
public class FileServiceImpl implements FileService{

	private final BoardMapper boardMapper;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private File saveFile;
	
	public FileServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	@Value("${file_save_path}")
	private String SAVE_PATH;

	@Override
	public String upLoadFile(MultipartFile file) {
		String fileExtention = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		if(isValidImage(fileExtention)){
			String uuid = UUID.randomUUID().toString();
			FilesVO filesVO = new FilesVO();
			filesVO.setFileName(file.getOriginalFilename());
			filesVO.setFileAltName(uuid + fileExtention);
			filesVO.setFilePath(SAVE_PATH+ filesVO.getFileAltName());

			saveFile = new File(SAVE_PATH + filesVO.getFileAltName());
			try {
				file.transferTo(saveFile);
				boardMapper.insertFile(filesVO);
				Thumbnails.of(saveFile)
					.size(400,300)
					.toFile(SAVE_PATH+"thumb_"+saveFile.getName());
			} catch (IOException e) {
				logger.info(e.getMessage(),"fail");
				return "fail";
			}
			return filesVO.getFileAltName();
		} else {
			logger.info("invalid file","fail");
			return "invalidfile";
		}
	}

	private static boolean isValidImage(String fileExtension) {
		String[] imgExtension = {".png", ".jpeg", ".jpg", ".gif"};
		for(int i=0; i<imgExtension.length; i++){
			if(imgExtension[i].equals(fileExtension.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}