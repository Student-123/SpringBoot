package com.quicklogs;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

import static com.quicklogs.SortFormatUtil.getFormatTaskMap;

/**
 * Created by Santhosh on 8/30/2016.
 */
public class ExtractFileTask implements Runnable {
    String fileName;
    boolean isNeedSort;

    public ExtractFileTask(String fileName, boolean isNeedSort){
        this.fileName = fileName;
        this.isNeedSort = isNeedSort;
    }

    @Override
    public void run() {
        try{
            if(!fileName.contains(".gz"))
            {
                System.out.println("Error: Not a zip file " + fileName);
                return;
            }
            FileInputStream fin = new FileInputStream(fileName);
            GZIPInputStream gzipInputStream = new GZIPInputStream(fin);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream));

            FileOutputStream fout = new FileOutputStream(new File(fileName.replace(".gz","")));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fout));

            List<String> fileLines = new ArrayList<String>();
            String line;
            while ((line=bufferedReader.readLine()) != null){
                fileLines.add(line);
            }
            if(isNeedSort){
                System.out.println("Sorting file "+ fileName + "based on timestamp");
                Collections.sort(fileLines,new LogSortComparator(SortFormatUtil.getSortPattern()));
            }
            for(String outputLine: fileLines){
                HashMap<String,String> sortFormatMap = SortFormatUtil.getFormatTaskMap();
                for(Map.Entry entry:sortFormatMap.entrySet()){
                    outputLine = outputLine.replace((String)entry.getKey(),(String)entry.getValue());
                }
                writer.write(outputLine);
                writer.newLine();
            }
            writer.close();
            bufferedReader.close();
            new File(fileName).delete();
            System.out.println("File extracted : " + fileName);

        }catch(Exception e){
            System.out.println("Error: Decompression failed for " + fileName);
            e.printStackTrace();
        }

    }
}
