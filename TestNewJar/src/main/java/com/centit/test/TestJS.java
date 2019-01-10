package com.centit.test;

import com.alibaba.fastjson.JSON;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.util.Collection;

public class TestJS {
    private String name;

    public void sayHello(){
        System.out.println("Hello " + name + " !\nThis is a message from javaObject!");
    }

    public static void main(String[] args) {
        javaObjFunc();
        callJavaObject();
        System.out.println(jsObjFunc());
        System.out.println(getArray());
        System.out.println(jsCalculate("a=1+2+3+(2*2)"));
        jsFunction();
        jsVariables();
    }

    public static String testJavaFunc(Object name) {
        System.out.println(name.getClass());
        System.out.format("Hi there from Java, %s!\n", name);
        return "greetings from java";
    }

    public static void callJavaObject() {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine scriptEngine = sem.getEngineByName("js"); // "nashorn" 等价与 “js”, "JavaScript"
        String script =
            "function callJavaFunc(javaObj) {\n" +
            "  javaObj.sayHello();\n" +
            "}";
        try {
            scriptEngine.eval(script);
            //scriptEngine.put("dataSet",dataSet);
            //Object obj = scriptEngine.eval("runOpt(dataSet)");
            Invocable invocable = (Invocable) scriptEngine;
            TestJS jobj = new TestJS();
            jobj.setName("codefan");
            Object obj = invocable.invokeFunction("callJavaFunc", jobj);
            Object obj2 = JSON.toJSON(obj);
            System.out.println(obj2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void javaObjFunc() {
        String script =
            "var MyJavaClass = Java.type('com.centit.test.TestJS');\n" +
            "var result = MyJavaClass.testJavaFunc('John Doe');\n" +
            "print(result)";
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine scriptEngine = sem.getEngineByName("js");
        try {
            scriptEngine.eval(script);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 运行JS对象中的函数
     *
     * @return
     */
    public static Object jsObjFunc() {
        String script =
            "var obj={run:function(){return 'run method : return:\"abc'+this.next(name)+'\"';},next:function(str){return ' 我来至 next function '+str+')'}}";
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine scriptEngine = sem.getEngineByName("js");
        try {
            scriptEngine.put("name", "codefan");
            scriptEngine.eval(script);
            Object object = scriptEngine.get("obj");
            Invocable inv2 = (Invocable) scriptEngine;
            return inv2.invokeMethod(object, "run");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<Object> getArray() {
        ScriptEngineManager sem = new ScriptEngineManager();
        String script = "var obj={array:['test',true,1,1.0,2.11111]}";

        ScriptEngine scriptEngine = sem.getEngineByName("js");
        try {
            scriptEngine.eval(script);
            Object object2 = scriptEngine.eval("obj.array");
          /*  Class<?> clazz = object2.getClass();
            Field denseField = clazz.getDeclaredField("size");
            denseField.setAccessible(true);*/
            return ((ScriptObjectMirror) object2).values();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object jsCalculate(String script) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            return engine.eval(script);
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 运行JS基本函数
     */
    public static void jsFunction() {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");
        try {
            String script = "function say(name){ return 'hello,'+name; }";
            se.eval(script);
            Invocable inv2 = (Invocable) se;
            String res = (String) inv2.invokeFunction("say", "test");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JS中变量使用
     */
    public static void jsVariables() {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("javascript");
        File file = new File("TestNewJar/src/main/java/com/centit/test/optLogic.py");
        engine.put("file", file);
        try {
            engine.eval("print('path:'+file.getAbsoluteFile())");
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

