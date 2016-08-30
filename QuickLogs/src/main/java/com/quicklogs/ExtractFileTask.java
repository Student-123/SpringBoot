package com.quicklogs;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;

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
                Collections.sort(fileLines);
            }
            for(String outputLine: fileLines){
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
