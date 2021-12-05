package com.example.messagingstompwebsocket;

public class FileCreator {

	private String name;
	private String body;

	public FileCreator() {
	}

	public FileCreator(String name, String body) {
		this.name = name;
		this.body = body;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}
}