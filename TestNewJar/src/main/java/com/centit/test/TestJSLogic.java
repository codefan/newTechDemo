package com.centit.test;

import com.alibaba.fastjson.JSON;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class TestJSLogic {
   public static void main(String[] args) {
       ScriptEngineManager sem = new ScriptEngineManager();
       ScriptEngine scriptEngine = sem.getEngineByName("js");
       Map<String, List<Map<String, Object>>> dataSet =TestDataSet.createDate();
       try {
           scriptEngine.put("dataSet",dataSet);
           File file = new File("/home/codefan/projects/framework/newTechDemo/TestNewJar/src/main/java/com/centit/test/optLogic.js");
           FileReader reader = new FileReader(file);
           scriptEngine.eval(reader);
           Object obj = scriptEngine.eval("runOpt(dataSet)");
           Object obj2 = JSON.toJSON(obj);
           System.out.println(obj2);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}

