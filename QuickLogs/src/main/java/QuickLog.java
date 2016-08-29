import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.quicklogs.*;
import com.sun.corba.se.spi.orbutil.fsm.Input;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Santhosh on 8/26/2016.
 */
public class QuickLog {
    public static String inputFile = "InputHOIs.txt";
    public static  void main(String[] args){
        try{
            String envName = null;
            String queryName = null;
            List<String> hois = null;
            if(args.length > 2){
                envName = args[0];
                queryName = args[1];
                hois = Arrays.asList(args[2].split(","));
            }else if(args.length == 2){
                envName = args[0];
                queryName = args[1];
                System.out.println("HOIs will be read from " + inputFile);
            }else{
                System.out.println("Please ensure you provided  :environment:    :query:");
                System.exit(1);
            }
            if(hois.isEmpty()){
                hois = FileUtil.getHOIsFromFile(inputFile);
            }



            InputStream inputStream = new PipedInputStream();
            PipedOutputStream pipedOutputStream = new PipedOutputStream((PipedInputStream) inputStream);


            OutputStream outputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream((PipedOutputStream) outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pipedInputStream));



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

            //CommandExecutor commandExecutor = new CommandExecutor(pipedOutputStream,commandStatusMap);
            //commandExecutor.executeCommand(myCommand);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
