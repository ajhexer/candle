package com.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.tomcat.util.bcel.classfile.JavaClass;

import java.io.InputStream;
import java.util.ArrayList;

public class ConfigHandler {
    private static Config config;

    /**
     * Load configs from file
     */
    public static void loadConfig(){
        if(config!=null){
            return;
        }
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try(InputStream input = JavaClass.class.getClassLoader().getResourceAsStream("config.yml")){
          config = mapper.readValue(input, Config.class);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Rules related to SMA indicator
     */
    public static ArrayList<SMA> getSMAConfigs(){
        if(config==null){
            return null;
        }
        return config.getSmas();
    }




}
