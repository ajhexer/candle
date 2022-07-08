import com.config.ConfigHandler;
import com.config.SMA;
import com.evaluator.core.Evaluator;
import com.evaluator.utils.SMARuleFactory;
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
        kafkaProperties.put("key.serializer", properties.getProperty("key.serializer"));
        kafkaProperties.put("value.serializer", properties.getProperty("value.serializer"));

        var evaluator = new Evaluator(properties, topicName);
        var configs = ConfigHandler.getSMAConfigs();
        for (SMA s : configs) {
            var smaFactory = new SMARuleFactory(s.getName(), s.getFieldName1(), s.getFieldName2(), s.getTimeInterval1(), s.getTimeInterval2(), s.getAlarmCondition(), s.getMarket());
            evaluator.AddRule(s.getMarket(), smaFactory);
        }
        new Thread(evaluator).start();
    }
}

