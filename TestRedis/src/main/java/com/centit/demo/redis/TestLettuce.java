package com.centit.demo.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLettuce {
    //redis://code7810@192.168.134.5:6380?sentinelMasterId=root
    private static String[] redisUrls  = {
        "redis://192.168.134.2:6379",
        "redis://192.168.134.3:6379",
        "redis://192.168.134.4:6379",
        "redis://192.168.134.5:6379",
        "redis://192.168.134.6:6379",
        "redis://192.168.134.7:6379"};

    public static void testConnectRedis(){
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisStringCommands<String,String> sync = connection.sync();
        String value = sync.get("key");
        System.out.println(value);
    }

    public static void testRedisClusterLua(){
        List<RedisURI> redisURIS = new ArrayList<>();
        for(String url : redisUrls){
            redisURIS.add(RedisURI.create(url));
        }

        ClusterClientOptions options =
            ClusterClientOptions.builder().autoReconnect(true).maxRedirects(10).build();

        RedisClusterClient client = RedisClusterClient.create(redisURIS);
        client.setOptions(options);

        StatefulRedisClusterConnection<String, String> connection = client.connect();
        RedisAdvancedClusterCommands<String, String> sync = connection.sync();
        //System.out.println(sync.lrange("blklist",0,-1));

        String strScript = "local a = {}\n" + "for i = 1, #KEYS do\n" + "table.insert(a, redis.call('lrange', KEYS[i], 0, -1))\n" +
            "end\n" + "return a";

        // https://blog.csdn.net/sinat_29843547/article/details/76508055
        String [] keys = {"{stock}600000", "{stock}600001","{stock}600002","{stock}600003","{stock}600004","{stock}600005",
            "{stock}000001","{stock}000002","{stock}000003","{stock}000004","{stock}000005","{stock}000006"};

        /*Object object = sync.eval(strScript, ScriptOutputType.MULTI, keys );
        System.out.println(object);*/


        Map<Long, List<String>> map = new HashMap<>(10);
        for(String k : keys) {
            Long slot = sync.clusterKeyslot(k);
            if(map.containsKey(slot)) {
                map.get(slot).add(k);
            }else{
                List<String> v = new ArrayList<>();
                v.add(k);
                map.put(slot,v);
            }
        }

        for(Map.Entry<Long, List<String>> ent : map.entrySet()) {
            Object object = sync.eval(strScript, ScriptOutputType.MULTI, ent.getValue().toArray(new String[]{}));
            System.out.println(object);
        }
        //connection.async().eval()
    }

    public static void main(String[] args) {
        /*RedisURI uri = new RedisURI();
        uri.setHost("192.168.134.5");
        uri.setPort(6380);
        uri.setPassword("code7810");
        uri.setSentinelMasterId("root");

        System.out.println(uri.toString());
        System.out.println(uri.toURI().toString());*/
        testRedisClusterLua();
    }

}
