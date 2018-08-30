package com.centit.demo;

import com.centit.demo.alikafka.KafkaProducerDemo;
import com.centit.support.algorithm.BooleanBaseOpt;

import java.util.Properties;

public class JavaKafkaConfigurer {

    private static Properties properties;

    public static void configureSasl() {
        //如果用-D或者其它方式设置过，这里不再设置
        Properties props = getKafkaProperties();
        if(BooleanBaseOpt.castObjectToBoolean(props.getProperty("java.security.auth.enable"),false)) {
            if (null == System.getProperty("java.security.auth.login.config")) {
                //请注意将XXX修改为自己的路径
                //这个路径必须是一个文件系统可读的路径，不能被打包到jar中
                System.setProperty("java.security.auth.login.config", props.getProperty("java.security.auth.login.config"));
            }
        }
    }

    public synchronized static Properties getKafkaProperties() {
        if (null != properties) {
            return properties;
        }
        //获取配置文件kafka.properties的内容
        Properties kafkaProperties = new Properties();
        try {
            kafkaProperties.load(KafkaProducerDemo.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            //没加载到文件，程序要考虑退出
            e.printStackTrace();
        }
        properties = kafkaProperties;
        return kafkaProperties;
    }


}
