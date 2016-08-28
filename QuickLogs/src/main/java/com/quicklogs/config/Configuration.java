package com.quicklogs.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Santhosh on 8/27/2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configuration",propOrder = {
    "envConfig",
        "commonConfig"
})
public class Configuration {
    private List<EnvConfig> envConfig;
    private List<CommonConfig> commonConfig;

    public List<EnvConfig> getEnvConfig() {
        return envConfig;
    }

    public void setEnvConfig(List<EnvConfig> envConfig) {
        this.envConfig = envConfig;
    }

    public List<CommonConfig> getCommonConfig() {
        return commonConfig;
    }

    public void setCommonConfig(List<CommonConfig> commonConfig) {
        this.commonConfig = commonConfig;
    }
}
