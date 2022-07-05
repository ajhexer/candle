package utils;

import model.CandleData;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public class ConsumerUtil {

    public static KafkaConsumer<Long, CandleData> CreateConsumer(Properties props) {
        return new KafkaConsumer<Long, CandleData>(props);
    }
}
