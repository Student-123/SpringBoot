package com.quicklogs;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;

import java.io.*;
import java.util.List;

/**
 * Created by Santhosh on 8/28/2016.
 */
public class GetFileTask implements Runnable {
    private ChannelSftp channel;
    private String workingDir;
    private String fileName;
    private String ouputFileName;
    private List<String> fileList;
    public GetFileTask(Channel channel, String workingDir, String fileName, String ouputFileName, List<String> fileList){
        this.channel = (ChannelSftp)channel;
        this.workingDir = workingDir;
        this.fileName = fileName;
        this.ouputFileName = ouputFileName;
        this.fileList = fileList;
    }

    @Override
    public void run() {
        try{
            channel.connect();
            System.out.println("Downloading file :" +fileName);
            channel.cd(workingDir);
            byte[] buffer = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(channel.get(fileName));
            File newFile = new File(ouputFileName);
            OutputStream os = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int readCount;
            while( (readCount = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, readCount);
            }
            bis.close();
            bos.close();
            System.out.println("File downloaded:" +ouputFileName);
            fileList.add(ouputFileName);
            channel.disconnect();
        }catch (Exception e){
            System.out.println("Error: Download failed for "+fileName);
            e.printStackTrace();
        }

    }
}
