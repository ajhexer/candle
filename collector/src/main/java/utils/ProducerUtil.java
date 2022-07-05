package utils;

import model.CandleData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ProducerUtil {
    private Properties props;
    private Long timeout;
    private Producer<Long, CandleData> producer;

    public ProducerUtil(Properties props, Long timeout) {
        this.props = props;
        this.producer = new KafkaProducer<>(props);
        this.timeout = timeout;
    }

    public void Send(ProducerRecord<Long, CandleData> data) throws Exception{
        producer.send(data).get(timeout, TimeUnit.SECONDS);
    }
}
