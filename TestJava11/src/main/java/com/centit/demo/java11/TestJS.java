package com.centit.demo.java11;

import com.alibaba.fastjson.JSON;

import javax.script.*;

public class TestJS {
    private String name;

    public void sayHello(){
        System.out.println("Hello " + name + " !\nThis is a message from javaObject!");
    }

    public static void main(String[] args) {
        //javaObjFunc();
        callJavaObject();
        //System.out.println(jsObjFunc());
        //System.out.println(getArray());
        //System.out.println(jsCalculate("a=1+2+3+(2*2)"));
        //jsFunction();
    }

    public static String testJavaFunc(Object name) {
        System.out.println(name.getClass());
        System.out.format("Hi there from Java, %s!\n", name);
        return "greetings from java";
    }

    public static void callJavaObject() {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine scriptEngine = sem.getEngineByName("graal.js"); // "nashorn" 等价与 “js”, "JavaScript"
        String script =
            "function callJavaFunc(javaObj) {\n" +
                " print(javaObj.name);\n" +
                " javaObj.sayHello();\n" + // "  print(javaObj);\n" + //
            "}";
        var bindings = scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("polyglot.js.allowAllAccess",true);
        //bindings.put("polyglot.js.allowHostAccess", true);
        //bindings.put("polyglot.js.allowHostClassLookup", (Predicate<String>) s -> true);
        //System.out.println(JSON.toJSONString(bindings));
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
        var script =
            "var MyJavaClass = Java.type('com.centit.test.TestJS');\n" +
            "var obj = new MyJavaClass();\n" +
            "obj.name = '杨淮生';\n" +
            "obj.sayHello();\n" +
            "var result = MyJavaClass.testJavaFunc('John Doe');\n" +
            "print(result)";
        var sem = new ScriptEngineManager();
        var scriptEngine = sem.getEngineByName("graal.js");
        //var bindings = scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE);
        //可以考虑这个，开启一切可开启的..
        //bindings.put("polyglot.js.allowAllAccess",true);
        //bindings.put("polyglot.js.allowHostAccess", true);
        //bindings.put("polyglot.js.allowHostClassLookup", (Predicate<String>) s -> true);
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
        ScriptEngine scriptEngine = sem.getEngineByName("graal.js");
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

    public static Object getArray() {
        ScriptEngineManager sem = new ScriptEngineManager();
        String script = "var obj={array:['test',true,1,1.0,2.11111]}";

        ScriptEngine scriptEngine = sem.getEngineByName("js");
        try {
            scriptEngine.eval(script);
            Object object2 = scriptEngine.eval("obj.array");
          /*  Class<?> clazz = object2.getClass();
            Field denseField = clazz.getDeclaredField("size");
            denseField.setAccessible(true);*/
            return object2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object jsCalculate(String script) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("graal.js");
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
        ScriptEngine se = sem.getEngineByName("graal.js");
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



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

