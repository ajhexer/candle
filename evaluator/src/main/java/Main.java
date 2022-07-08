import com.collector.models.CandleData;
import com.config.Config;
import com.config.ConfigHandler;
import com.config.SMA;
import com.evaluator.core.Evaluator;
import com.evaluator.utils.ConsumerUtil;
import com.evaluator.utils.SMARuleFactory;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.tomcat.util.bcel.classfile.JavaClass;

import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {


        var properties = new Properties();
        try (InputStream input = JavaClass.class.getClassLoader().getResourceAsStream("kafka.properties")) {
            properties.load(input);
        } catch (Exception e) {
            System.out.println("Error: An error occurred when loading properties files");
            return;
        }
        var topicName = properties.getProperty("topicName");
        var kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", properties.getProperty("bootstrap.servers"));
        kafkaProperties.put("key.deserializer", properties.getProperty("key.deserializer"));
        kafkaProperties.put("value.deserializer", properties.getProperty("value.deserializer"));
        kafkaProperties.put("group.id", "GroupA");

        var evaluator = new Evaluator(new ConsumerUtil(new KafkaConsumer<Long, CandleData>(kafkaProperties), topicName));
        ConfigHandler.loadConfig();
        var configs = ConfigHandler.getSMAConfigs();
        for (SMA s : configs) {
            var smaFactory = new SMARuleFactory(s.getName(), s.getFieldName1(), s.getFieldName2(), s.getTimeInterval1(), s.getTimeInterval2(), s.getAlarmCondition(), s.getMarket());
            evaluator.AddRule(s.getMarket(), smaFactory);

        }
        new Thread(evaluator).start();

    }
}

