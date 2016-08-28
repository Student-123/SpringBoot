package com.quicklogs.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Santhosh on 8/27/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "connectionConfig",propOrder = {
    "host",
     "user",
        "password"
})
public class ConnectionConfig {
    private String host;
    private String user;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
