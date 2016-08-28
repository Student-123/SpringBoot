package com.quicklogs;

/**
 * Created by Santhosh on 8/27/2016.
 */
public class Command {
    private String cmdString;
    private String expectedStatus;
    private String fileName;
    private boolean fileCommand;

    public boolean isFileCommand() {
        return fileCommand;
    }

    public void setFileCommand(boolean fileCommand) {
        this.fileCommand = fileCommand;
    }

    public String getCmdString() {
        return cmdString;
    }

    public void setCmdString(String cmdString) {
        this.cmdString = cmdString;
    }

    public String getExpectedStatus() {
        return expectedStatus;
    }

    public void setExpectedStatus(String expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
