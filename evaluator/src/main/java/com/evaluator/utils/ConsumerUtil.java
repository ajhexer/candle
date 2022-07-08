package com.evaluator.utils;

import com.collector.models.CandleData;
import org.apache.kafka.clients.consumer.Consumer;

import java.util.Collections;

public class ConsumerUtil {
    private Consumer<Long, CandleData> consumer;


    public ConsumerUtil(Consumer<Long, CandleData> consumer, String topicName) {
        this.consumer = consumer;
        consumer.subscribe(Collections.singletonList(topicName));
    }

    public Consumer<Long, CandleData> getConsumer() {
        return consumer;
    }
}

