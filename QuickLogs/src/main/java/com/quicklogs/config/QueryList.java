package com.quicklogs.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Santhosh on 8/27/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryList",propOrder = {
    "query"
})
public class QueryList {
    List<Query> query;
}
