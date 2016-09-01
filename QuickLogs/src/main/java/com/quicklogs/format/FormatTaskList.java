package com.quicklogs.format;

import com.sun.javafx.tk.Toolkit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Santhosh on 9/1/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="formatTaskList", propOrder = {
    "formatTask"
})
public class FormatTaskList {
    List<FormatTask> formatTask;

    public List<FormatTask> getFormatTask() {
        return formatTask;
    }

    public void setFormatTask(List<FormatTask> formatTask) {
        this.formatTask = formatTask;
    }
}
