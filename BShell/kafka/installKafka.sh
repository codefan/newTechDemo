# https://blog.csdn.net/javastart/article/details/78498884
# https://blog.csdn.net/yongyuandefengge/article/details/76684793/
# https://www.cnblogs.com/zongzong/p/6566656.html

# 常用命令

netstat -tunlp|egrep "(2181|9092)"

nohup bin/zookeeper-server-start.sh config/zookeeper.properties 1>/dev/null 2>&1 &
nohup bin/kafka-server-start.sh config/server.properties 1>/dev/null 2>&1 &

bin/kafka-topics.sh --create --zookeeper codefanpc:2181 --replication-factor 1 --partitions 1 --topic test


bin/kafka-console-consumer.sh --bootstrap-server PLAINTEXT://jishuceshi6:9092 --topic test --from-beginning


bin/kafka-console-consumer.sh --bootstrap-server SASL_PLAINTEXT://192.168.134.7:9092 --topic test --from-beginning

# 有验证程序
bin/kafka-console-producer.sh --broker-list codefanpc:9092 --topic test --producer.config config/producer.properties

bin/kafka-console-consumer.sh  --bootstrap-server codefanpc:9092  --topic test --from-beginning --consumer.config config/consumer.properties


bin/kafka-console-consumer.sh --bootstrap-server PLAINTEXT://jishuceshi5:9092 --topic test --from-beginning


bin/kafka-console-consumer.sh --bootstrap-server PLAINTEXT://jishuceshi5:9092 --topic test --from-beginning

## 添加 SASL 验证
1 修改 bin/bin/kafka-run-class.sh 的最后5行 就是添加参数  $KAFKA_SASL_OPTS

# Launch mode
if [ "x$DAEMON_MODE" = "xtrue" ]; then
  nohup $JAVA $KAFKA_HEAP_OPTS $KAFKA_JVM_PERFORMANCE_OPTS $KAFKA_GC_LOG_OPTS $KAFKA_SASL_OPTS $KAFKA_JMX_OPTS $KAFKA_LOG4J_OPTS -cp $CLASSPATH $KAFKA_OPTS "$@" > "$CONSOLE_OUTPUT_FILE" 2>&1 < /dev/null &
else
  exec $JAVA $KAFKA_HEAP_OPTS $KAFKA_JVM_PERFORMANCE_OPTS $KAFKA_GC_LOG_OPTS $KAFKA_SASL_OPTS $KAFKA_JMX_OPTS $KAFKA_LOG4J_OPTS -cp $CLASSPATH $KAFKA_OPTS "$@"
fi

2 添加zookeeper 验证

2.1 新建文件 config/zookeeper_jaas.conf

Server {
   org.apache.kafka.common.security.plain.PlainLoginModule required
   username="admin"
   password="centit.1"
   user_admin="centit.1"
   user_alice="centit.1";
};

2.2 修改  bin/zookeeper-server-start.sh 添加

export KAFKA_SASL_OPTS='-Djava.security.auth.login.config=/opt/kafka2/config/zookeeper_jaas.conf'

3 添加 kafka 验证

3.1 新建文件 config/kafka_server_jaas.conf 其中的 Client 使用来连接zookeeper的

KafkaServer {
     org.apache.kafka.common.security.plain.PlainLoginModule required
     username="admin"
     password="centit.1"
     user_admin="centit.1"
     user_alice="centit.1";
};

Client {
     org.apache.kafka.common.security.plain.PlainLoginModule required
     username="admin"
     password="centit.1";
};

2.2 修改  bin/kafka-server-start.sh 添加

export KAFKA_SASL_OPTS="-Djava.security.auth.login.config=/opt/kafka2/config/kafka_server_jaas.conf"

4 添加 kafka 客户端链接

4.1 新建文件  config/kafka_client_jaas.conf

KafkaClient {
     org.apache.kafka.common.security.plain.PlainLoginModule required
     username="admin"
     password="centit.1";
};


4.2 修改 bin/kafka-console-producer.sh 和 bin/kafka-console-consumer.sh 文件 ，添加

export KAFKA_SASL_OPTS="-Djava.security.auth.login.config=/opt/kafka2/config/kafka_client_jaas.conf"

4.3 修改 config/consumer.properties 和 config/producer.properties 文件 ，添加

security.protocol=SASL_PLAINTEXT
sasl.mechanism=PLAIN

5 程序修改

System.setProperty("java.security.auth.login.config", ".../kafka_client_jaas.conf"); // 环境变量添加，需要输入配置文件的路径
props.put("security.protocol", "SASL_PLAINTEXT");
props.put("sasl.mechanism", "PLAIN");
