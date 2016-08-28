package com.quicklogs;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Santhosh on 8/27/2016.
 */
public class ShellOutputProcessor implements Runnable{

    BufferedReader shellOuput;
    ConcurrentHashMap<String,String> commandStatusMap;

    public ShellOutputProcessor(BufferedReader reader, ConcurrentHashMap<String,String> commandStatusMap){
        shellOuput = reader;
        this.commandStatusMap = commandStatusMap;
    }

    @Override
    public void run() {
        String line = null;
            try{
                while((line = shellOuput.readLine()) != null) {
                    System.out.println(line);
                    if (line.startsWith(CommandUtil.verificationCommand)) {
                        String[] commandSplit = line.split(":",2);
                        if(null == commandStatusMap.get(commandSplit[1])){
                            commandStatusMap.put(commandSplit[1],"");
                        }
                    } else if (line.contains(CommandUtil.pwd) && line.contains(CommandUtil.verificationCommand)) {
                        String fileName = line.split(":",2)[1];
                        String workingDir = shellOuput.readLine();
                        commandStatusMap.put(fileName,workingDir);
                    }
                }
            }catch (Exception e){
                System.out.println("Error while processing shell output");
            }
        }
}
