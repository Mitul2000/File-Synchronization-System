package com.example.messagingstompwebsocket;

public class ReceiveMessage {

	private String content;
	private String fileName;

	public ReceiveMessage() {
	}

	public ReceiveMessage(String content, String fileName) {
		this.content = content;
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return this.fileName;
	}
}