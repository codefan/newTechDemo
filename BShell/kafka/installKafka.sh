# https://blog.csdn.net/javastart/article/details/78498884
# https://blog.csdn.net/yongyuandefengge/article/details/76684793/


netstat -tunlp|egrep "(2181|9092)"

nohup bin/kafka-server-start.sh config/server.properties 1>/dev/null 2>&1 &


bin/kafka-console-consumer.sh --bootstrap-server PLAINTEXT://jishuceshi6:9092 --topic test --from-beginning


bin/kafka-console-consumer.sh --bootstrap-server SASL_PLAINTEXT://192.168.134.7:9092 --topic test --from-beginning


System.setProperty("java.security.auth.login.config", ".../kafka_client_jaas.conf"); // 环境变量添加，需要输入配置文件的路径
props.put("security.protocol", "SASL_PLAINTEXT");
props.put("sasl.mechanism", "PLAIN");


bin/kafka-console-producer.sh --broker-list jishuceshi6:9092 --topic test

bin/kafka-console-consumer.sh --bootstrap-server PLAINTEXT://jishuceshi5:9092 --topic test --from-beginning
