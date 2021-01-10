package uy.com.pepeganga.business.common.utils.methods;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ConfigurationsSystem {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationsSystem.class);
    String directory = "PepeGangaSystemConfig";

    public JSONObject readAllJsonFile() {
        String url = String.valueOf(buildURI(false));

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(url))
        {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            return obj;
        } catch (FileNotFoundException e) {
            logger.error("Not found configuration file in the server, Method: readAllJsonFile(), Error: {} ", e.getMessage());
        } catch (IOException e) {
            logger.error("Error reading configuration file in the server, Method: readAllJsonFile(), Error: {} ", e.getMessage());
        } catch (ParseException e) {
            logger.error("Error parsing configuration in the server, Method: readAllJsonFile(), Error: {} ", e.getMessage());
        }
        return null;
    }

    public JSONObject getPublicationConfig() {
        JSONObject obj = readAllJsonFile();
        JSONObject objResult = new JSONObject(obj);
        if (obj != null) {
            JSONObject objPubli = new JSONObject();
            return (JSONObject) obj.get("publication_config");
        } else {
            return null;
        }
    }

    public JSONObject getSynchronizationConfig() {
        JSONObject obj = readAllJsonFile();
        JSONObject objResult = new JSONObject(obj);
        if (obj != null) {
            JSONObject objPubli = new JSONObject();
            return (JSONObject) obj.get("synchronization_config");
        } else {
            return null;
        }
    }

    // *** ***** Metodos auxiliares *** ***** //

    //Construye ruta y crea el directorio si no existe
    private StringBuilder buildURI(boolean createDir){
        //Build the directory router
        StringBuilder builder = new StringBuilder();
        builder.append(System.getProperty("user.home"));
        builder.append(File.separator);
        builder.append(directory);

        if(createDir) {
            File folder = new File(builder.toString());
            if (!folder.exists())
                folder.mkdir();
        }
        builder.append(File.separator);
        builder.append("config.json");
        return builder;
    }

    //Obtiene la fecha actual
    private String getCurrentDate(){
        Date now = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(now);
    }
}
