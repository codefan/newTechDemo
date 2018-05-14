package com.centit.demo.kafka;

import com.alibaba.fastjson.JSON;

public class DemoMessage {
    private final String message;
    private final String topic;
    private final int partition;
    private final long offset;

    public DemoMessage(String message,
                       String topic,
                       int partition,
                       long offset) {

        this.message = message;
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
    }

    public String getMessage() {
        return message;
    }

    public String getTopic() {
        return topic;
    }

    public int getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
