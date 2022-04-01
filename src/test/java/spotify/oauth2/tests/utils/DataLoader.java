package spotify.oauth2.tests.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader dataloader;

    private DataLoader(){
        properties=PropertyUtils.propertyLoader("src/test/resources/data.properties");
    }
    public static DataLoader getInstance(){
        if(dataloader==null){
            dataloader=new DataLoader();
        }
        return dataloader;
    }
    public String getGetPlaylistId(){
        String prop = properties.getProperty("get_playlist_id");
        if (prop!=null) return prop;
        else throw new RuntimeException("property get_playlist_id is not specified in config.property file");
    }

    public String getUpdatePlaylistId(){
        String prop = properties.getProperty("update_playlist_id");
        if (prop!=null) return prop;
        else throw new RuntimeException("property update_playlist_id is not specified in config.property file");
    }

}
