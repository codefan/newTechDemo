package com.centit.test;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.util.*;

public class TestJypthon {
    public static void main(String[] args) {
        Map<String, List<Map<String, Object>>> dataSet = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("id", 1);
        item.put("name", "admin");
        data.add(item);
        item = new HashMap<>();
        item.put("id", 2);
        item.put("name", "guest");
        data.add(item);
        dataSet.put("cust", data);

        data = new ArrayList<>();
        item = new HashMap<>();
        item.put("uid", 1);
        item.put("city", "nanjing");
        item.put("date", new Date());
        data.add(item);
        item = new HashMap<>();
        item.put("uid", 2);
        item.put("city", "shanghai");
        item.put("date", new Date());
        data.add(item);
        dataSet.put("city", data);

        PythonInterpreter interpreter = new PythonInterpreter();
        //interpreter.exec("def runOpt(a):\n\tprint(a)\n\treturn a");
        interpreter.execfile(
            "/home/codefan/projects/framework/newTechDemo/TestNewJar/src/main/java/com/centit/test/optLogic.py");
        PyFunction func = interpreter.get("runOpt", PyFunction.class);
        PyObject pyobj = func.__call__(Py.java2py(dataSet));
        Map obj = Py.tojava(pyobj,Map.class);
        System.out.println(obj);
    }
}
