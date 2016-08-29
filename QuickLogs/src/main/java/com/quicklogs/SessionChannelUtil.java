package com.quicklogs;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.quicklogs.config.ConnectionConfig;
import com.quicklogs.config.EnvConfig;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Santhosh on 8/28/2016.
 */
public class SessionChannelUtil {
    public static Session createSession(EnvConfig envConfig){
        Session sshSession = null;
        try{
            ConnectionConfig connectionConfig = envConfig.getConnectionConfig();
            JSch jSch = new JSch();
            sshSession = jSch.getSession(connectionConfig.getUser(),connectionConfig.getHost(),22);
            sshSession.setPassword(connectionConfig.getPassword());
            sshSession.setConfig("StrictHostKeyChecking", "no");
            sshSession.connect();
        }catch (Exception e){
            System.out.println("Error: Unable to create ssh session");
            e.printStackTrace();
        }
        return sshSession;
    }

    public static Channel createShellChannel(Session session, InputStream inputStream, OutputStream outputStream){
        Channel channel = null;
        try{
            channel=session.openChannel("shell");
            channel.setInputStream(inputStream);
            channel.setOutputStream(outputStream);
        }catch (Exception e){
            System.out.println("Error: Unable to create shell channel");
            e.printStackTrace();
        }
        return  channel;
    }

    public static Channel createExecChannel(Session session, InputStream inputStream, OutputStream outputStream){
        Channel channel = null;
        try{
            channel=session.openChannel("exec");
            channel.setInputStream(inputStream);
            channel.setOutputStream(outputStream);
        }catch (Exception e){
            System.out.println("Error: Unable to create exec channel");
            e.printStackTrace();
        }
        return  channel;
    }

    public static Channel createSFTPChannel(Session session){
        Channel channel = null;
        try{
            channel=session.openChannel("sftp");
        }catch (Exception e){
            System.out.println("Error: Unable to create sftp channel");
            e.printStackTrace();
        }
        return  channel;
    }
}
