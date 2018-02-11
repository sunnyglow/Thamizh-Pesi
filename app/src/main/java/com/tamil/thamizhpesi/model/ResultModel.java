package com.tamil.thamizhpesi.model;

public class ResultModel 
{
	String fileName = null;
	int startTime = 0;
	int endTime = 0;
	boolean completeSound = false;
	String word = null;
	String audioPath = null;
	
	public String getAudioPath() {
		return audioPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public boolean isCompleteSound() {
		return completeSound;
	}
	public void setCompleteSound(boolean completeSound) {
		this.completeSound = completeSound;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
}
