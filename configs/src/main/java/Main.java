import com.config.ConfigHandler;
import com.config.SMA;

public class Main {
    public static void main(String[] args) {
        ConfigHandler.loadConfig();
        var config = ConfigHandler.getSMAConfigs();
        for(SMA s: config){
            System.out.println(s);
        }
    }
}
