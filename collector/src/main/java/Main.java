import com.collector.core.Collector;
import com.collector.core.DataExtractor;
import com.collector.utils.ProducerUtil;
import com.config.ConfigHandler;
import com.config.SMA;
import org.apache.tomcat.util.bcel.classfile.JavaClass;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
//        String topicName = "Topic-A";
//        Properties prop = new Properties();
//        prop.put("bootstrap.servers", "localhost:9092");
//        prop.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
//        prop.put("value.serializer", "model.CandleSerializer");
//        Thread thread = new Thread(new Collector(new ProducerUtil(prop, 10L, topicName), new DataExtractor("https://api.kucoin.com/api/v1/market/candles", "symbol", "ETH-BTC", "startAt", "endAt", "type", "1min")));
//        Thread thread2 = new Thread(new Collector(new ProducerUtil(prop, 10L, topicName), new DataExtractor("https://api.kucoin.com/api/v1/market/candles", "symbol", "BTC-USDT", "startAt", "endAt", "type", "1min")));
//        thread.start();
//        thread2.start();
//        var properties = new Properties();
//        try(InputStream input = JavaClass.class.getClassLoader().getResourceAsStream("kafka.properties")){
//            properties.load(input);
//            System.out.println(properties.getProperty("bootstrap.servers"));
//        }catch (Exception e){
//            System.out.println("Error: An error occurred when loading properties files");
//            return;
//        }
//        var topicName = properties.getProperty("topicName");
//        var kafkaProperties = new Properties();
//        kafkaProperties.put("bootstrap.servers", properties.getProperty("bootstrap.servers"));
//        kafkaProperties.put("key.serializer", properties.getProperty("key.serializer"));
//        kafkaProperties.put("value.serializer", properties.getProperty("value.serializer"));
        ConfigHandler.loadConfig();
        var config = ConfigHandler.getSMAConfigs();
        for(SMA s: config){
            System.out.println(s);
        }

    }
}
