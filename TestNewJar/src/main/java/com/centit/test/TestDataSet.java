package com.centit.test;

import java.util.*;

public class TestDataSet {
    public static Map<String, List<Map<String, Object>>> createDate(){
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
        return dataSet;
    }
}
