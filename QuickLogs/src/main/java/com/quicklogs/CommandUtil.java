package com.quicklogs;

/**
 * Created by Santhosh on 8/27/2016.
 */
public class CommandUtil {
    public static String pwd = "pwd";
    public static String echo = "echo";
    public static String verificationCommand = "Cmd:";

    public static Command getPWDCommand(String file){
        Command commandPWD = new Command();
        commandPWD.setCmdString(pwd +"&&" + echo +" "+ verificationCommand + file);
        commandPWD.setExpectedStatus(file);
        return commandPWD;
    }
}
