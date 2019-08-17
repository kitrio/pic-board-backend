package com.j.board.domain;

public class FilesVO {
    private int boardNum;
    private String fileName;
    private String fileAltName;


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
}