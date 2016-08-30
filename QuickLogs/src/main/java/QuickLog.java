import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import com.quicklogs.*;
import com.quicklogs.config.EnvConfig;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Santhosh on 8/26/2016.
 */
public class QuickLog {
    public static String inputFile = "InputHOIs.txt";
    public static  void main(String[] arg){
        try{
            String[] args = {"omf403","tr","A36D27X4"};//, "20160828"};
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

            EnvConfig envConfig = ConfigUtility.loadConfigFor(envName);
            String actualQuery = ConfigUtility.queryFromConfig(envConfig,queryName);
            actualQuery = CommandUtil.paramReplacer(actualQuery,null,null);
            List<Command> commands = CommandUtil.createCommands(actualQuery,hois);


            InputStream inputStream = new PipedInputStream();
            PipedOutputStream pipedOutputStream = new PipedOutputStream((PipedInputStream) inputStream);

            OutputStream outputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream((PipedOutputStream) outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pipedInputStream));

            Session session = SessionChannelUtil.createSession(envConfig);
            Channel channel = SessionChannelUtil.createShellChannel(session,inputStream,outputStream);
            channel.connect();

            ConcurrentHashMap<String,String> commandStatusMap = new ConcurrentHashMap<String, String>();
            ShellOutputProcessor processShellOutput = new ShellOutputProcessor(bufferedReader,commandStatusMap);
            Thread shellOutputThread = new Thread(processShellOutput);
            shellOutputThread.start();


            ExecutorService fileExecutorService = Executors.newFixedThreadPool(5);
            CommandExecutor commandExecutor = new CommandExecutor(pipedOutputStream,commandStatusMap,fileExecutorService,session);
            List<String> fileList = new ArrayList<String>();
            Collections.synchronizedList(fileList);
            commandExecutor.executeCommands(commands,fileList);
            fileExecutorService.shutdown();
            while (!fileExecutorService.isTerminated());


            System.out.println("Total files downloaded :" + fileList);
            System.out.print("Would you like to extract the file y/n :");
            String choice = new BufferedReader(new InputStreamReader(System.in)).readLine();
            if(choice.equals("y")){
                ExecutorService fileExtractService = Executors.newFixedThreadPool(5);
                for(String fileName: fileList){
                    fileExtractService.execute(new ExtractFileTask(fileName,false));
                }
                fileExtractService.shutdown();
                while (!fileExtractService.isTerminated());
            }


            channel.disconnect();
            session.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
