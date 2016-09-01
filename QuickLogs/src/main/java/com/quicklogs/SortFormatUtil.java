package com.quicklogs;

import com.quicklogs.config.Configuration;
import com.quicklogs.format.FormatTask;
import com.quicklogs.format.FormatTaskList;
import com.quicklogs.format.SortFormatConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;

/**
 * Created by Santhosh on 9/1/2016.
 */

public class SortFormatUtil {
    public static String sortFormatConfig_file = "sortFormatConfig.xml";
    public static SortFormatConfig sortFormatConfig;

    public static SortFormatConfig getSortFormatConfig(){
        if(sortFormatConfig == null){
            sortFormatConfig = loadSortFormatConfig();
        }
        return  sortFormatConfig;
    }
    public static String getSortPattern(){
        return sortFormatConfig.getSortPattern();
    }

    public static HashMap<String,String> getFormatTaskMap(){
        HashMap<String,String> formatTaskMap = new HashMap<String, String>();
        FormatTaskList formatTaskList = getSortFormatConfig().getFormatTaskList();
        for(FormatTask formatTask:formatTaskList.getFormatTask()){
            formatTaskMap.put(formatTask.getIfThis(),formatTask.getDoThis());
        }
        return  formatTaskMap;
    }

    private static SortFormatConfig loadSortFormatConfig(){
        SortFormatConfig sortFormatConfig = null;
        try{
            File file = new File(Thread.currentThread().getContextClassLoader().getResource(sortFormatConfig_file).getFile());
            JAXBContext jaxbContext = JAXBContext.newInstance(SortFormatConfig.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            sortFormatConfig = (SortFormatConfig) jaxbUnmarshaller.unmarshal(file);
        }catch (Exception e){
            System.out.println("Error: Not able to sortFormatConfig");
            e.printStackTrace();
        }
        return  sortFormatConfig;
    }

}
