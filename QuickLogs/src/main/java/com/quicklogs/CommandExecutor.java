package com.quicklogs;

import com.jcraft.jsch.Session;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * Created by Santhosh on 8/27/2016.
 */
public class CommandExecutor {

    OutputStream shell;
    ConcurrentHashMap<String,String> commandStatusMap;
    ExecutorService executorService;
    Session session;

    public CommandExecutor(OutputStream shell, ConcurrentHashMap<String,String> commandStatusMap, ExecutorService executorService,Session session){
        this.shell = shell;
        this.commandStatusMap = commandStatusMap;
        this.executorService = executorService;
        this.session = session;
    }

    public boolean executeCommands(List<Command> commands,List<String> fileList){
        for(Command command: commands) {
            if (!executeCommand(command,fileList)) {
                return false;
            }
        }
        return true;
    }

    public boolean executeCommand(Command command,List<String> fileList){
        try{
            System.out.println("Executing " + command.getCmdString());

            if(!command.getCmdString().contains(CommandUtil.echo)){
                shell.write(commandExpectedStatusAppended(command).getBytes());
            }else{
                shell.write(command.getCmdString().getBytes());
            }

            boolean success =  isCommandSuccess(command);
            if(success && command.isFileCommand()){
                prepareFileTransfer(command,fileList);
            }
            //System.out.print("Executed");
            return success;
        }catch(Exception e){
            System.out.println("Error while running : " + command.getCmdString());
            e.printStackTrace();
            return  false;
        }
    }

    private boolean isCommandSuccess(Command command){
        if(command.getExpectedStatus().equals("Okk")){
            return  true;
        }
        while(commandStatusMap.get(command.getExpectedStatus()) == null);
        if(commandStatusMap.containsKey(command.getExpectedStatus())){
            return true;
        }
        return false;
    }

    private void prepareFileTransfer(Command command, List<String> fileList){
        Command commandPWD = CommandUtil.getPWDCommand(command.getFileName());
        if(executeCommand(commandPWD,fileList) && null != commandStatusMap.get(command.getFileName())){
            System.out.println("Preparing transfer of " + command.getFileName() + " in asynchronous mode");
            //Start file transfer
            executorService.execute(new GetFileTask(SessionChannelUtil.createSFTPChannel(session),commandStatusMap.get(command.getFileName()),command.getFileName(),command.getFileName(),fileList));
        }else{
            System.out.println("Error: could not transfer " + command.getFileName());
        }
    }
    //if ; not working then change it to && - will figure out something for grep comments
    private static String commandExpectedStatusAppended(Command command){
        return command.getCmdString() +" ; " + CommandUtil.echo + " \"Cmd:" +command.getExpectedStatus()+"\"\n";
    }
}
