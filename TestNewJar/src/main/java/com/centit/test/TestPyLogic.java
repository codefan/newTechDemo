package com.centit.test;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.util.*;

public class TestPyLogic {
    public static void main(String[] args) {
        Map<String, List<Map<String, Object>>> dataSet =TestDataSet.createDate();
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
