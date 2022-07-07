import com.collector.core.Collector;
import com.collector.models.DataExtractor;
import com.collector.utils.ProducerUtil;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        String topicName = "Topic-A";
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        prop.put("value.serializer", "model.CandleSerializer");
        Thread thread = new Thread(new Collector(new ProducerUtil(prop, 10L), new DataExtractor("https://api.kucoin.com/api/v1/market/candles", "symbol", "ETH-BTC", "startAt", "endAt", "type", "1min"), "Topic-A"));
        Thread thread2 = new Thread(new Collector(new ProducerUtil(prop, 10L), new DataExtractor("https://api.kucoin.com/api/v1/market/candles", "symbol", "BTC-USDT", "startAt", "endAt", "type", "1min"), "Topic-A"));
        thread.start();
        thread2.start();
    }
}
