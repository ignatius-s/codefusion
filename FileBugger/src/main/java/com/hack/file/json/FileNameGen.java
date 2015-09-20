package com.hack.file.json;

import java.util.Date;

import org.springframework.integration.Message;
import org.springframework.integration.file.DefaultFileNameGenerator;

public class FileNameGen extends DefaultFileNameGenerator {

	@Override
	public String generateFileName(Message<?> message) {
		System.out.println("generateFileName(" + message.getPayload() + ")");
		String filename = super.generateFileName(message);
		System.out.println("Generate filename: " + filename);
		
			filename = "data"+new Date().getSeconds()+".json";
		
		System.out.println("Generate filename: " + filename);
		
		return filename;
	}
}