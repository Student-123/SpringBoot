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
    public static  void main(String[] args){
        try{
            String[] arg = {"omf403","tr","A36D27X4"};//, "20160828"};
            String envName = null;
            String queryName = null;
            List<String> hois = new ArrayList<String>();
            boolean paramStarted = false;
            int paramCount = 1;
            String param1 = null;
            String param2 = null;

            for(int i=0;i<arg.length;i++){
                switch (i){
                    case 0: envName = arg[i];
                        break;
                    case 1: queryName = arg[i];
                        break;
                    case 2: if(arg[i].equals("-p")){
                                paramStarted = true;
                                System.out.println("HOIs will be read from " + inputFile);
                            }else{
                                hois = Arrays.asList(arg[i].split(","));
                            }
                        break;
                    case 3: if(arg[i].equals("-p")){
                                paramStarted = true;
                            }else if(paramStarted){
                                param1 = arg[i];
                                paramCount++;
                            }
                        break;
                    case 4: if(paramCount == 1){
                                param1 = arg[i];
                                paramCount++;
                            }else if(paramCount == 2){
                                param2 = arg[i];
                                paramCount++;
                            }
                        break;
                    case 5:
                        param2 = arg[i];
                        paramCount++;
                        break;
                }
            }

            if(hois.isEmpty()){
                hois = FileUtil.getHOIsFromFile(inputFile);
            }
            if(hois.isEmpty()){
                System.out.println("Error: No hois provided");
                System.exit(1);
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
                    fileExtractService.execute(new ExtractFileTask(fileName,true));
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
