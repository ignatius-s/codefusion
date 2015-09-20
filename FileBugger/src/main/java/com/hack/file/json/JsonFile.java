package com.hack.file.json;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.support.MessageBuilder;

import com.hack.file.TransformationHandler;

public class JsonFile {
	private static final Logger LOGGER = Logger.getLogger(TransformationHandler.class);

	@Transformer
	public Message<String> handleFile(final String inputMessage) {

		final String inputFile = inputMessage;
		final String filename = "data-out";
		 String fileExtension="";// = FilenameUtils.getExtension(filename);

		if(FilenameUtils.getExtension(filename).equals("csv"))
		{
			fileExtension="json";
		}
		final Message<String> message = MessageBuilder.withPayload(inputMessage)
					.setHeader(FileHeaders.FILENAME,      filename)
					.setHeader(FileHeaders.ORIGINAL_FILE, filename)
					.setHeader("file_size", inputFile.length())
					.setHeader("file_extension", fileExtension)
					.build();

		return message;
	}
	
	

}
