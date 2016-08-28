package com.quicklogs.config;

import javax.management.Query;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Santhosh on 8/27/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "envConfig",propOrder = {
    "environment",
        "connectionConfig",
        "queryList",
        "groupedQueryList"
})
public class EnvConfig {
    private String environment;
    private ConnectionConfig connectionConfig;
    private QueryList queryList;
    private QueryList groupedQueryList;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public ConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }

    public void setConnectionConfig(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    public QueryList getQueryList() {
        return queryList;
    }

    public void setQueryList(QueryList queryList) {
        this.queryList = queryList;
    }

    public QueryList getGroupedQueryList() {
        return groupedQueryList;
    }

    public void setGroupedQueryList(QueryList groupedQueryList) {
        this.groupedQueryList = groupedQueryList;
    }
}
