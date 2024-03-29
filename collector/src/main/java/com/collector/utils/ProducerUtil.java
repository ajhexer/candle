package com.collector.utils;

import com.collector.models.CandleData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ProducerUtil {
    private Long timeout;
    private Producer<Long, CandleData> producer;
    private String topicName;

    /**
     * @param props Properties for the Kafka producer
     * @param timeout The timeout for the producer
     * @param topicName The topic name for the producer
     */
    public ProducerUtil(Properties props, Long timeout, String topicName) {
        this.producer = new KafkaProducer<>(props);
        this.timeout = timeout;
        this.topicName = topicName;
    }

    /**
     * @param key The key of the record
     * @param value The value of the record
     * @throws Exception If there is an error while sending the record
     */
    public void Send(Long key, CandleData value) throws Exception{
        producer.send(new ProducerRecord<>(topicName, key, value)).get(timeout, TimeUnit.SECONDS);
    }
}
