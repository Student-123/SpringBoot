package com.quicklogs;

import com.quicklogs.config.CommonConfig;
import com.quicklogs.config.Configuration;
import com.quicklogs.config.EnvConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Santhosh on 8/27/2016.
 */
public class ConfigUtility {
    public static String commonConfigId = "{commonConfigId}";
    public static String configFile = "configuration.xml";

    public static EnvConfig loadConfigFor(String envName){
        EnvConfig envConfig = null;

        Configuration config = loadConfig();
        HashMap<String, EnvConfig> envConfigMap = getEnvConfigMap(config);
        HashMap<String,CommonConfig> commonConfigMap = getCommonConfigMap(config);

        if(null != envConfigMap.get(envName)){
            return  envConfigMap.get(envName);
        }else{
            return  getFromCommonConfig(envName,envConfigMap,commonConfigMap);
        }


    }

    public static Configuration loadConfig(){
        try{
            File file = new File(Thread.currentThread().getContextClassLoader().getResource(configFile).getFile());
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Configuration config = (Configuration) jaxbUnmarshaller.unmarshal(file);
            return  config;

        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    public static EnvConfig getFromCommonConfig(String envName, HashMap<String, EnvConfig> envConfigMap, HashMap<String,CommonConfig> commonConfigMap){
        EnvConfig envConfig = null;
        String commonName = envName.substring(0,envName.length() -2);
        String commonId = envName.substring(envName.length()-1,1);
        envConfig = envConfigMap.get(commonName+commonConfigId);
        if(null != envConfig){
            envConfig.setQueryList(commonConfigMap.get(commonId).getQueryList());
            envConfig.setGroupedQueryList(commonConfigMap.get(commonId).getGroupedQueryList());
        }
        return envConfig;
    }

    public static HashMap<String,EnvConfig> getEnvConfigMap(Configuration config){
        List<EnvConfig> envConfigs = config.getEnvConfig();
        HashMap<String, EnvConfig> envConfigMap = new HashMap<String, EnvConfig>();
        for(EnvConfig environment:envConfigs){
            envConfigMap.put(environment.getEnvironment(),environment);
        }
        return  envConfigMap;
    }

    public static HashMap<String,CommonConfig> getCommonConfigMap(Configuration config){
        List<CommonConfig> commonConfigs = config.getCommonConfig();
        HashMap<String,CommonConfig> commonConfigMap = new HashMap<String, CommonConfig>();
        for(CommonConfig commonConfig:commonConfigs){
            String commonConfigName = commonConfig.getCommonConfigId();
            String[] commonConfigIds = commonConfigName.split(":");
            for(int i=0;i<commonConfigIds.length;i++){
                commonConfigMap.put(commonConfigIds[i],commonConfig);
            }
        }
        return commonConfigMap;
    }
}
