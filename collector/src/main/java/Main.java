import com.collector.core.Collector;
import com.collector.core.DataExtractor;
import com.collector.utils.ProducerUtil;
import com.config.ConfigHandler;
import com.config.SMA;
import org.apache.tomcat.util.bcel.classfile.JavaClass;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        var properties = new Properties();
        try(InputStream input = JavaClass.class.getClassLoader().getResourceAsStream("kafka.properties")){
            properties.load(input);
        }catch (Exception e){
            System.out.println("Error: An error occurred when loading properties files");
            return;
        }
        var topicName = properties.getProperty("topicName");
        var kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", properties.getProperty("bootstrap.servers"));
        kafkaProperties.put("key.serializer", properties.getProperty("key.serializer"));
        kafkaProperties.put("value.serializer", properties.getProperty("value.serializer"));

        var apiProperties = new Properties();
        try(InputStream input = JavaClass.class.getClassLoader().getResourceAsStream("api.properties")){
            apiProperties.load(input);
        }catch (Exception e){
            System.out.println("Error: An error occurred while loading api configs");
        }
        var apiAddress = apiProperties.getProperty("apiAddress");
        var symbolQuery = apiProperties.getProperty("symbolQuery");
        var startQuery = apiProperties.getProperty("startQuery");
        var endQuery = apiProperties.getProperty("endQuery");
        var intervalQuery = apiProperties.getProperty("intervalQuery");
        var intervalValue = apiProperties.getProperty("intervalValue");
        var timeout = Long.parseLong(apiProperties.getProperty("timeout"));

        var markets = new HashSet<String>();

        ConfigHandler.loadConfig();
        var config = ConfigHandler.getSMAConfigs();
        for(SMA s: config){
            markets.add(s.getMarket());
        }

        for(String market: markets){
            new Thread(new Collector(new ProducerUtil(kafkaProperties, timeout, topicName), new DataExtractor(apiAddress, symbolQuery, market, startQuery, endQuery, intervalQuery, intervalValue))).start();
        }
    }
}
