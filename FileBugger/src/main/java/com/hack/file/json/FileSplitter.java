package com.hack.file.json;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.transformer.MessageTransformationException;
import org.springframework.util.Assert;

public class FileSplitter extends AbstractMessageSplitter {
    private static final Logger log = Logger.getLogger(FileSplitter.class);

    @Override
    public Object splitMessage(Message<?> message) {
        if (log.isDebugEnabled()) {
            log.debug(message.toString());
        }
        try {

            Object payload = message.getPayload();
            Assert.isInstanceOf(File.class, payload, "Expected java.io.File in the message payload");
            return org.apache.commons.io.FileUtils.lineIterator((File) payload);
        } catch (IOException e) {
            String msg = "Unable to transform file: " + e.getMessage();
            log.error(msg);
            throw new MessageTransformationException(msg, e);
        }
    }

	

}