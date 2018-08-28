# https://blog.csdn.net/javastart/article/details/78498884
# https://blog.csdn.net/yongyuandefengge/article/details/76684793/


netstat -tunlp|egrep "(2181|9092)"

bin/kafka-server-start.sh config/server.properties 1>/dev/null 2>&1 &


System.setProperty("java.security.auth.login.config", ".../kafka_client_jaas.conf"); // 环境变量添加，需要输入配置文件的路径
props.put("security.protocol", "SASL_PLAINTEXT");
props.put("sasl.mechanism", "PLAIN");
