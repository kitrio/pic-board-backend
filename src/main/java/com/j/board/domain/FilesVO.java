package com.j.board.domain;

public class FilesVO {
    private int boardNum;
    private String fileName;
    private String fileAltName;
    private String filePath;

    public int getBoardNum() {
        return boardNum;
    }
    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileAltName() {
        return fileAltName;
    }
    public void setFileAltName(String fileAltName) {
        this.fileAltName = fileAltName;
    }
    
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}