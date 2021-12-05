package com.example.messagingstompwebsocket;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import java.io.IOException; 

public class FileHandler {

	private String fileName;

    public FileHandler() {

    }

	public FileHandler(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return fileName;
	}

    public void setName(String fileName) {
        this.fileName = fileName;
    }

    public String getContents() {
        System.out.println(fileName);
        try {
            File file = new File("files/" + fileName);
            return FileUtils.readFileToString(file);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}