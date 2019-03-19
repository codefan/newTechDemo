package com.centit.demo.application;

import com.centit.support.algorithm.StringBaseOpt;
import com.centit.support.file.TxtLogFile;

public class SimpleApp {

    public static void main(String[] args) {
        TxtLogFile.writeLog("/D/Projects/RunData/demo_home/logs/testApp.log",
            "Hello from test application " + StringBaseOpt.castObjectToString(args),
            true,true);
    }
}
