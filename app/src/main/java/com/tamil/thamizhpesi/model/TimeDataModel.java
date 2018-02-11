package com.tamil.thamizhpesi.model;

import java.util.ArrayList;
import java.util.List;

public class TimeDataModel 
{
	List<String> letterList = new ArrayList<String>();
	List<Integer> startTime = new ArrayList<Integer>();
	List<Integer> endTime = new ArrayList<Integer>();
	String fileName = null;
	String sentence = null;
	String audioPath = null;
	
	public String getAudioPath() {
		return audioPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public List<String> getLetterList() {
		return letterList;
	}
	public void setLetterList(List<String> letterList) {
		this.letterList = letterList;
	}
	public List<Integer> getStartTime() {
		return startTime;
	}
	public void setStartTime(List<Integer> startTime) {
		this.startTime = startTime;
	}
	public List<Integer> getEndTime() {
		return endTime;
	}
	public void setEndTime(List<Integer> endTime) {
		this.endTime = endTime;
	}
}
