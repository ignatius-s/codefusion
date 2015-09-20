package com.hack.file.json;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.hack.file.TransformationHandler;

public class DataUpdater {
	private static final Logger LOGGER = Logger.getLogger(DataUpdater.class);

	public Object update(Object payload) throws InterruptedException
	{
		Map<Integer,Map<String,String>> glbList= new HashMap<Integer, Map<String,String>>();
		String jsonOut="";
		String[] inputSplitNewLine =null;
		LOGGER.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+payload.getClass());
		inputSplitNewLine =((String) payload).split("\\n");
        System.out.println("\n After split:\n");
        for(int i=0;i<inputSplitNewLine.length;i++){
            String tmp = inputSplitNewLine[i];
            String[] col=tmp.split(",");
            int key =new Integer(col[0]);
            Map<String,String> map = null;
        	
            if(glbList.containsKey(key))
            {
            	map=glbList.get(key);
            }
            else
            {
            	map=new HashMap<String,String>();           
            }
            
            map.put(col[1], col[2]);
            glbList.put(key, map);
        }
        try {
			jsonOut = new ObjectMapper().writeValueAsString(glbList);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println(jsonOut);
		return jsonOut;
	}

}
