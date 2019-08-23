package com.j.board.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String upLoadFile(MultipartFile file);
}