package com.quicklogs.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Santhosh on 8/27/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commonConfig",propOrder = {
    "commonConfigId",
        "queryList",
        "groupedQueryList"
})
public class CommonConfig {
    private String commonConfigId;
    private QueryList queryList;
    private QueryList groupedQueryList;

    public String getCommonConfigId() {
        return commonConfigId;
    }

    public void setCommonConfigId(String commonConfigId) {
        this.commonConfigId = commonConfigId;
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
