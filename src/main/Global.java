package main;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import main.tools.Json;

import java.util.HashMap;

public class Global {
    public static final String dir = "config/mods/worse";
    public static final String config_dir = dir + "/config";
    public static final String save_dir = config_dir + "/saves";
    public static final String config_file = config_dir + "/config.json";
    public static Config config = loadConfig();

    public static String clean(String string, String  begin, String  end){
        int fromBegin = 0,fromEnd = 0;
        while (string.contains(begin)){
            int first=string.indexOf(begin,fromBegin),last=string.indexOf(end,fromEnd);
            if(first==-1 || last==-1) break;
            if(first>last){
                fromBegin=first+1;
                fromEnd=last+1;
            }
            string=string.substring(0,first)+string.substring(last+1);
        }
        return string;
    }

    public static String cleanEmotes(String string){
        return clean(string,"<",">");
    }

    public static String cleanColors(String string){
        return clean(string,"[","]");
    }

    public static String cleanName(String name){
        name = cleanColors(name);
        name = cleanEmotes(name);
        return name.replace(" ","_");
    }

    public static Config loadConfig() {
        Config config = Json.loadJackson(config_file, Config.class);
        if (config == null) return new Config();
        return config;
    }

    public static class Config {
        public String symbol = "[green]<Survival>[]";
        public String alertPrefix = "!!";
        public String dbName = "mindustryServer";
        public String playerCollection = "PlayerData";
        public String dbAddress = "mongodb://127.0.0.1:27017";
        public String salt = "TWS";
        public HashMap<String, String > rules;
        public HashMap<String, String > guide;
        public HashMap<String, String > welcomeMessage;
        public int consideredPassive = 10;

        public int vpnTimeout;
        public String vpnApi;

        public Config() {}

        @JsonCreator
        public Config(
                @JsonProperty("consideredPassive") int consideredPassive,
                @JsonProperty("symbol") String symbol,
                @JsonProperty("playerCollection") String playerCollection,
                @JsonProperty("dbAddress") String dbAddress,
                @JsonProperty("alertPrefix") String alertPrefix,
                @JsonProperty("dbName") String dbName,
                @JsonProperty("rules") HashMap<String, String > rules,
                @JsonProperty("guide") HashMap<String, String > guide,
                @JsonProperty("welcomeMessage") HashMap<String, String > welcomeMessage,
                @JsonProperty("vpnApi") String vpnApi,
                @JsonProperty("vpnTimeout") int vpnTimeout
        ){
            if(symbol != null) this.symbol = symbol;
            if(dbAddress != null) this.dbAddress = dbAddress;
            if(playerCollection != null) this.playerCollection = playerCollection;
            if(alertPrefix != null) this.alertPrefix = alertPrefix;
            if(dbName != null) this.dbName = dbName;

            this.consideredPassive = consideredPassive;
            this.rules = rules;
            this.guide = guide;
            this.welcomeMessage = welcomeMessage;
            this.vpnApi = vpnApi;
            this.vpnTimeout = vpnTimeout;
        }
    }
}
