package com.j.board.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.j.board.domain.FilesVO;
import com.j.board.persistence.BoardMapper;
@Service
public class FileServiceImpl implements FileService{
	@Autowired
	BoardMapper boardMapper;

	@Value("${file_save_path}")
	private String SAVE_PATH;
	
	public String upLoadFile(MultipartFile file) {
		String fileExtention = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String uuid = UUID.randomUUID().toString();
		FilesVO filesVO = new FilesVO();
		filesVO.setBoardNum(-1); // Temp file number
        filesVO.setFileName(file.getOriginalFilename());
        filesVO.setFileAltName(uuid + fileExtention);
		filesVO.setFilePath(SAVE_PATH+ filesVO.getFileAltName());

		File saveFile = new File(SAVE_PATH + filesVO.getFileAltName());
		try {
			file.transferTo(saveFile);
			boardMapper.insertFile(filesVO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "fail";
		}
		return filesVO.getFileAltName();
	}
}