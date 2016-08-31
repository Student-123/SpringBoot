package com.quicklogs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Santhosh on 8/27/2016.
 */
public class CommandUtil {
    public static String pwd = "pwd";
    public static String echo = "echo";
    public static String verificationCommand = "Cmd:";
    public static String hoi_regex = "{HOI}";
    public static String file_regex = "\\(\\((.*)\\)\\)";

    public static String param1 = "{1}";
    public static String param2 = "{2}";

    public static List<String> excludeCommandList;
    static{
        excludeCommandList = new ArrayList<String>();
        excludeCommandList.add("be ");
    }

    public static Pattern filePattern = Pattern.compile(file_regex);

    public static Command getPWDCommand(String file){
        Command commandPWD = new Command();
        commandPWD.setCmdString(pwd +" && " + echo +" \""+ verificationCommand + file+"\"\n");
        commandPWD.setExpectedStatus(file);
        return commandPWD;
    }
    public static List<Command> createCommands(String queryString,List<String> hois){
        List<Command> commands = new ArrayList<Command>();
        String[] splitCommands = queryString.split(";");
        for(int i=0; i < splitCommands.length;i++){
            commands.addAll(buildCommands(splitCommands[i],hois));
        }
        return  commands;
    }

    private static List<Command> buildCommands(String queryString,List<String> hois){
        List<Command> commands = new ArrayList<Command>();
        if(!queryString.contains(hoi_regex)){
            commands.add(buildRegularCommand(queryString));
        }else{
            commands.addAll(buildHOIBasedCommands(queryString,hois));
        }
        return  commands;
    }

    public static Command buildRegularCommand(String queryString){
        Command command = new Command();
        command.setCmdString(queryString);
        command.setExpectedStatus(getExpectedStatus(queryString));
        return  command;
    }

    public static List<Command> buildHOIBasedCommands(String queryString,List<String> hois){
        List<Command> commands = new ArrayList<Command>();
        for(String hoi: hois){
            String hoiBasedString = queryString.replace(hoi_regex,hoi);
            Matcher m = filePattern.matcher(hoiBasedString);
            boolean isFileCommand = false;
            String fileName = null;
            String fileQuery = null;
            while(m.find()){
                isFileCommand = true;
                fileName = m.group(1);
                fileQuery = m.replaceFirst(fileName);
            }

            if(isFileCommand){
                commands.add(buildFileCommand(fileQuery,fileName));
            }else{
                commands.add(buildRegularCommand(hoiBasedString));
            }

        }
        return  commands;
    }

    public static Command buildFileCommand(String commandStr,String fileName){
        Command command = new Command();
        command.setCmdString(commandStr);
        command.setExpectedStatus(getExpectedStatus(commandStr));
        command.setFileName(fileName);
        return  command;
    }

    public static String getExpectedStatus(String commandStr){
        boolean skipExpectationChecking = false;
        for(String skipExpect: excludeCommandList){
            if(commandStr.startsWith(skipExpect)){
                skipExpectationChecking = true;
                break;
            }
        }
        if(!skipExpectationChecking){
            return commandStr;
        }else{
            return "Okk";
        }
    }

    public static String paramReplacer(String inputString,String paramm1,String paramm2) {
        if (inputString.contains(param1)) {
            if (null != paramm1) {
                inputString = inputString.replace(param1, paramm1);
            } else {
                System.out.println("Param 1 is expected");
            }
        }

        if (inputString.contains(param2)) {
            if (null != paramm2) {
                inputString = inputString.replace(param2, paramm2);
            } else {
                System.out.println("Param 2 is expected");
            }
        }
        return  inputString;
    }
}
