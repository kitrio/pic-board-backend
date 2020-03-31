package com.j.board.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

import com.j.board.domain.FilesVO;
import com.j.board.persistence.BoardMapper;
@Service
public class FileServiceImpl implements FileService{

	private final BoardMapper boardMapper;

	public FileServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	@Value("${file_save_path}")
	private String SAVE_PATH;
	
	public String upLoadFile(MultipartFile file) {
		String fileExtention = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		fileExtention.toLowerCase();
		if(isValidImage(fileExtention) == true){
			String uuid = UUID.randomUUID().toString();
			FilesVO filesVO = new FilesVO();
			filesVO.setFileName(file.getOriginalFilename());
			filesVO.setFileAltName(uuid + fileExtention);
			filesVO.setFilePath(SAVE_PATH+ filesVO.getFileAltName());

			File saveFile = new File(SAVE_PATH + filesVO.getFileAltName());
			
			try {
				file.transferTo(saveFile);
				boardMapper.insertFile(filesVO);
				Thumbnails.of(saveFile)					
					.size(400,300)
					.toFile(SAVE_PATH+"thumb_"+saveFile.getName());
			} catch (IOException e) {
				return "fail";
			}
			return filesVO.getFileAltName();
		} else {
			return "invalidfile";
		}
	}

	private static boolean isValidImage(String fileExtension) {
		String imgExtension [] = {".png", ".jpeg", ".jpg", ".gif"};
		for(int i=0;i<imgExtension.length;i++){
			if(imgExtension[i].equals(fileExtension.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}