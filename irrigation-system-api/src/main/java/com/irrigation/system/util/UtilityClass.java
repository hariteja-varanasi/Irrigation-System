package com.irrigation.system.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class UtilityClass {

    public static String converExceptionToString(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
