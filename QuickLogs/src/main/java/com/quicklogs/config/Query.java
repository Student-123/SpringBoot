package com.quicklogs.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Santhosh on 8/27/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "query",propOrder = {
    "queryName",
        "querySequence"
})
public class Query {
    private String queryName;
    private String querySequence;

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getQuerySequence() {
        return querySequence;
    }

    public void setQuerySequence(String querySequence) {
        this.querySequence = querySequence;
    }
}
