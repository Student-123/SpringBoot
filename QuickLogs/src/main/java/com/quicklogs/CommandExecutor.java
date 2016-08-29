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

    public boolean executeCommands(List<Command> commands){
        for(Command command: commands) {
            if (!executeCommand(command)) {
                return false;
            }
        }
        return true;
    }

    public boolean executeCommand(Command command){
        try{
            System.out.println("Shell > " + command.getCmdString());
            shell.write(commandExpectedStatusAppended(command).getBytes());
            boolean success =  isCommandSuccess(command);
            if(success && command.isFileCommand()){
                prepareFileTransfer(command);
            }
            System.out.print("Executed");
            return success;
        }catch(Exception e){
            System.out.println("Error while running : " + command.getCmdString());
            e.printStackTrace();
            return  false;
        }
    }

    private boolean isCommandSuccess(Command command){
        while(commandStatusMap.get(command.getExpectedStatus()) == null);
        if(commandStatusMap.containsKey(command.getExpectedStatus())){
            return true;
        }
        return false;
    }

    private void prepareFileTransfer(Command command){
        Command commandPWD = CommandUtil.getPWDCommand(command.getFileName());
        if(executeCommand(commandPWD) && null != commandStatusMap.get(command.getFileName())){
            System.out.println("Preparing transfer of " + command.getFileName() + " in asynchronous mode");
            //Start file transfer
            executorService.execute(new GetFileTask(SessionChannelUtil.createSFTPChannel(session),command.getFileName(),commandStatusMap.get(command.getFileName()),command.getFileName()));
        }else{
            System.out.println("Error: could not transfer " + command.getFileName());
        }
    }

    private static String commandExpectedStatusAppended(Command command){
        return command.getCmdString() +" && " + CommandUtil.echo + " \"" +command.getExpectedStatus()+"\"\n";
    }
}
