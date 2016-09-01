package com.quicklogs.format;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Santhosh on 9/1/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "formatTask",propOrder = {
    "ifThis",
        "doThis"
})
public class FormatTask {

    private String ifThis;
    private String doThis;

    public String getIfThis() {
        return ifThis;
    }

    public void setIfThis(String ifThis) {
        this.ifThis = ifThis;
    }

    public String getDoThis() {
        return doThis;
    }

    public void setDoThis(String doThis) {
        this.doThis = doThis;
    }
}
