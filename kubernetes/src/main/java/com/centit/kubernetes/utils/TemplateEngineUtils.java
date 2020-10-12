package com.centit.kubernetes.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.text.GStringTemplateEngine;

/**
 * 模板引擎工具类
 */
public class TemplateEngineUtils {

    static Logger logger = LoggerFactory.getLogger(TemplateEngineUtils.class);

    /**
     * 数据绑定方法，通过替换${content}方式实现数据绑定
     * 
     * @param filePath 模板文件路径
     * @param params   需要绑定的数据
     * @return 绑定后文件字符串内容
     * @throws CompilationFailedException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static String binding(String filePath, Map<String, Object> params)
            throws CompilationFailedException, ClassNotFoundException, IOException {

        GStringTemplateEngine engine = new GStringTemplateEngine();
        String content = null;
        FileReader fileReader = new FileReader(filePath); 
        StringWriter stringWriter = new StringWriter();

        engine.createTemplate(fileReader).make(params).writeTo(stringWriter);
        content = stringWriter.toString();
        
        fileReader.close();
        stringWriter.close();

        return content;
    }
}