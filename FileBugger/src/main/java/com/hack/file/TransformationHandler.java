/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hack.file;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.MappingIterator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.config.FileToStringTransformerParser;
import org.springframework.integration.support.MessageBuilder;

/**
 * This Spring Integration transformation handler takes the input file, converts
 * the file into a string, converts the file contents into an upper-case string
 * and then sets a few Spring Integration message headers.
 *
 * @author Ignatius
 * @since 1.0
 */
public class TransformationHandler {

	/**
	 * Actual Spring Integration transformation handler.
	 *
	 * @param inputMessage Spring Integration input message
	 * @return New Spring Integration message with updated headers
	 */
	private static final Logger LOGGER = Logger.getLogger(TransformationHandler.class);

	@Transformer
	public Message<String> handleFile(final Message<File> inputMessage) {

		final File inputFile = inputMessage.getPayload();
		final String filename = inputFile.getName();
		final String fileExtension;// = FilenameUtils.getExtension(filename);

		final String inputAsString;

		try {
			if(FilenameUtils.getExtension(filename).equals("csv"))
			{
				fileExtension="json";
				inputAsString = FileUtils.readFileToString(inputFile);
			}else{
			fileExtension = FilenameUtils.getExtension(filename);
			inputAsString = readObjectsFromCsv(FileUtils.readFileToString(inputFile));
			}
			
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		LOGGER.info(">>>>>>>>"+fileExtension);
		final Message<String> message = MessageBuilder.withPayload(inputAsString.toUpperCase(Locale.ENGLISH))
					.setHeader(FileHeaders.FILENAME,      filename)
					.setHeader(FileHeaders.ORIGINAL_FILE, inputFile)
					.setHeader("file_size", inputFile.length())
					.setHeader("file_extension", fileExtension)
					.build();

		return message;
	}
	
	
	private String readObjectsFromCsv(String fileContent) throws IOException {
		
        String mapAsJson = new ObjectMapper().writeValueAsString(fileContent);
        return mapAsJson;
    }

    public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, data);
    }
}
