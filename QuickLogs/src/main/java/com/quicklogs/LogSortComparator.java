package com.quicklogs;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Santhosh on 8/30/2016.
 */
public class LogSortComparator implements Comparator<String> {

    //private static String log_timestamp_regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}";
    private Pattern timeStampPattern;

    public LogSortComparator(String regEx){
        timeStampPattern = Pattern.compile(regEx);
    }

    @Override
    public int compare(String o1, String o2) {
        String s1 = "";
        String s2 = "";
        Matcher m1 = timeStampPattern.matcher(s1);
        s1 = m1.group();
        Matcher m2 = timeStampPattern.matcher(s2);
        s2 = m2.group();
        return s1.compareTo(s2);
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

}
