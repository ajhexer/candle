package core;

import model.CandleData;
import models.Alarm;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

public class Consumer implements Runnable {
    private final KafkaConsumer<Long, CandleData> consumer;
    private final ArrayList<Rule> rules = new ArrayList<Rule>();
    private final HashMap<String, ArrayList<CandleData>> candleData = new HashMap<String, ArrayList<CandleData>>();
    private String topicName;

    public Consumer(Properties props, String topicName) {
        consumer = new KafkaConsumer<Long, CandleData>(props);
        this.topicName = topicName;
    }

    public void run() {
//        consumer.subscribe(Collections.singletonList(topicName));
//        while (true) {
//            ConsumerRecords<Long, CandleData> records = consumer.poll(100);
//            for (ConsumerRecord<Long, CandleData> record : records) {
//                CandleData candle = record.value();
//                candleData.put(candle.get, candle);
//            }
//            for (Rule rule : rules) {
//                Alarm alarm = rule.evaluate(candleData);
//                if (alarm != null) {
//                    System.out.println(alarm);
//                }
//            }
//        }
    }
}

