package uy.com.pepeganga.userservice.service.system_configuration;

import org.json.simple.JSONObject;
import org.joda.time.LocalTime;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.userservice.client.RestTemplateConfiguration;
import uy.com.pepeganga.userservice.models.system_config.SystemConfig;
import uy.com.pepeganga.userservice.repository.IConfigurationSystemRepository;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@EnableAsync
@Service
public class ConfigurationsSystemService implements IConfigurationsSystemService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationsSystemService.class);
    String directory = "PepeGangaSystemConfig";

    @Autowired
    IConfigurationSystemRepository configSysRepo;

    @Autowired
    RestTemplateConfiguration restTemplate;

    public Map<String, Object> saveScheduleDatabaseUpdate(String formatDays, List<LocalTime> hours, boolean running) {
/*
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("scheduleDatabaseUpdate.json").getFile());
        JsonNode mySchema = JsonLoader.fromFile(file);

        JsonFactory factoria= new JsonFactory();
        JsonGenerator generator= factoria.createGenerator(file.getPath());
    }
   */
        return new HashMap<>();
    }

    @Override
    public boolean createJsonFile() {
        File folder = new File(String.valueOf(buildURI(false)));
        if (!folder.exists()) {
            String currentDay = getCurrentDate();

            //main object
            JSONObject obj = new JSONObject();
            obj.put("creation_date", currentDay);

            //object for post configuration
            JSONObject publicationConfig = new JSONObject();
            publicationConfig.put("publication_type", "gold_premium"); //listingTypeId
            publicationConfig.put("flex", "not");

            obj.put("publication_config", publicationConfig);

            //object for synchronization configuration
            JSONObject synchrConfig = new JSONObject();
            synchrConfig.put("synchronization_time", "0 * * ? * *");
            synchrConfig.put("stock_risk", 5);

            obj.put("synchronization_config", synchrConfig);

            try {
                StringBuilder builder = buildURI(true);
                FileWriter file = new FileWriter(String.valueOf(builder));
                file.write(String.valueOf(obj));
                file.flush();
                file.close();
                return true;
            } catch (IOException e) {
                logger.error("Error creating configuration systems {} ", e.getMessage());
                return false;
            }
        }else {
            return true;
        }
    }

    @Override
    public JSONObject updateAttributesFromJsonFile(SystemConfig model) {
        try {
            if(model != null) {
                boolean syncStock = false;
                JSONObject obj = readAllJsonFile();
                JSONObject objResult = new JSONObject(obj);
                if (obj != null) {
                    if(model.getPublication_config() != null) {
                        if(!model.getPublication_config().getFlex().isBlank() && !model.getPublication_config().getPublication_type().isBlank()) {
                            JSONObject objPubli = new JSONObject();
                            objPubli = (JSONObject) obj.get("publication_config");
                            objPubli.put("flex", model.getPublication_config().getFlex());
                            objPubli.put("publication_type", model.getPublication_config().getPublication_type());
                            objResult.put("publication_config", objPubli);
                        }
                    }
                    if(model.getSynchronization_config() != null) {
                        if(model.getSynchronization_config().getStock_risk() != null && !model.getSynchronization_config().getSynchronization_time().isBlank()) {
                            JSONObject objSynch = new JSONObject();
                            objSynch = (JSONObject) obj.get("synchronization_config");
                            objSynch.put("synchronization_time", model.getSynchronization_config().getSynchronization_time());
                            objSynch.put("stock_risk", model.getSynchronization_config().getStock_risk());
                            objResult.put("synchronization_config", objSynch);
                            syncStock = true;
                        }
                    }
                    FileWriter file = new FileWriter(String.valueOf(buildURI(true)));
                    file.write(String.valueOf(objResult));
                    file.flush();
                    file.close();

                    //To update stock in consuming
                    if(syncStock == true && configSysRepo.getLastData().getEndDate() != null) {
                        updateStockClient();
                    }
                    return objResult;
                }
            }
        }catch (FileNotFoundException e) {
            logger.error("Not found configuration file in the server, Method: updateAttributesFromJsonFile(), Error: {} ", e.getMessage());
        }catch (IOException e) {
            logger.error("Error updating configuration file in the server, Method: updateAttributesFromJsonFile(), Error: {} ", e.getMessage());
        }
        return null;
    }

    @Override
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

    private static final String UPDATE_RISK = "http://consuming-store-service/api/sync/update_stock";

    private String updateStockClient() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            ResponseEntity<String> result = restTemplate.getRestTemplate().exchange(UPDATE_RISK, HttpMethod.GET, entity, String.class);
            return result.toString();
        }catch (Exception e){
            logger.warn(String.format("Timeout error waiting for response from consuming service to update stock of publications with synchronization {} Error: "), e.getMessage());
        return "Error";
        }
    }




}

