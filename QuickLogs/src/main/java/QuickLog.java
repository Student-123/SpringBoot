import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.quicklogs.Command;
import com.quicklogs.CommandExecutor;
import com.quicklogs.ConfigUtility;
import com.quicklogs.ShellOutputProcessor;
import com.sun.corba.se.spi.orbutil.fsm.Input;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Santhosh on 8/26/2016.
 */
public class QuickLog {

    public static  void main(String[] args){
        try{



            ConfigUtility.loadConfig();
            JSch jSch = new JSch();
            Session session = jSch.getSession("studentnumber123","sdf.org",22);
            session.setPassword("learnertilldeath");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            Channel channel=session.openChannel("shell");

            InputStream inputStream = new PipedInputStream();
            PipedOutputStream pipedOutputStream = new PipedOutputStream((PipedInputStream) inputStream);


            OutputStream outputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream((PipedOutputStream) outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pipedInputStream));


            channel.setInputStream(inputStream);
            channel.setOutputStream(outputStream);

            channel.connect(3000);

            ConcurrentHashMap<String,String> commandStatusMap = new ConcurrentHashMap<String, String>();
            ShellOutputProcessor processShellOutput = new ShellOutputProcessor(bufferedReader,commandStatusMap);
            Thread shellOutputThread = new Thread(processShellOutput);
            shellOutputThread.start();

            Thread.sleep(1000);
            pipedOutputStream.write("\n\n".getBytes());
            Thread.sleep(1000);
            pipedOutputStream.write("\n\n".getBytes());
            Thread.sleep(1000);
            pipedOutputStream.write("pwd\n".getBytes());
            Thread.sleep(2000);
            System.out.println("ok");

            Command myCommand = new Command();
            myCommand.setCmdString("ls -ltr");
            myCommand.setExpectedStatus(myCommand.getCmdString());

            CommandExecutor commandExecutor = new CommandExecutor(pipedOutputStream,commandStatusMap);
            commandExecutor.executeCommand(myCommand);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
