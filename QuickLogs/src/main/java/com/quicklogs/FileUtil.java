package com.quicklogs;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santhosh on 8/28/2016.
 */
public class FileUtil {
    public static List<String> getHOIsFromFile(String fileName){
        List<String> hoiList = new ArrayList<String>();
        try{
            File inputFile = new File(fileName);
            InputStream inputStream = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                if(!line.trim().isEmpty()){
                    hoiList.add(line.trim());
                }
            }
        }catch (Exception e){
            System.out.println("Error: Unable to load hois from file");
            e.printStackTrace();
        }

        return  hoiList;
    }
}
