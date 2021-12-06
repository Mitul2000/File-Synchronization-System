package com.example.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 

import java.util.*;

@Controller
public class FileController {

	public String fileName = "";

	@MessageMapping("/save")
	public void saveFile(FileCreator fileDetails) throws Exception {
		try {			
			File f = new File("files/" + fileDetails.getName() + ".txt");
			FileWriter writer = new FileWriter("files/" + fileDetails.getName() + ".txt");
			writer.write(fileDetails.getBody());
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	@MessageMapping("/getFiles")
	@SendTo("/topic/files")
	public ArrayList<String> getFiles() {
		File f = new File("files/");
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		return names;
	}

	@MessageMapping("/getFileContent")
	@SendTo("/topic/fileContent")
	public String getFileContent(String message) {
		FileHandler handler = new FileHandler(message.substring(message.indexOf(":")+2, message.length()-2));
		return handler.getContents();
	}

	@MessageMapping("/text")
	@SendTo("/topic/message")
	public String[] update(ReceiveMessage message) throws Exception {
		Thread.sleep(100);
		String[] updateReturn = {message.getContent(), message.getFileName()};
		return updateReturn;
	}

}
