package com.quicklogs.format;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Santhosh on 9/1/2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sortFormatConfig",propOrder = {
    "sortPattern",
        "formatTaskList"
})
public class SortFormatConfig {

    private String sortPattern;
    private FormatTaskList formatTaskList;

    public String getSortPattern() {
        return sortPattern;
    }

    public void setSortPattern(String sortPattern) {
        this.sortPattern = sortPattern;
    }

    public FormatTaskList getFormatTaskList() {
        return formatTaskList;
    }

    public void setFormatTaskList(FormatTaskList formatTaskList) {
        this.formatTaskList = formatTaskList;
    }
}
