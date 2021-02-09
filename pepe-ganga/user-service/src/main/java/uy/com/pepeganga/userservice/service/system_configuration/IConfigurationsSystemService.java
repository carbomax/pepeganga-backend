package uy.com.pepeganga.userservice.service.system_configuration;

import org.json.simple.JSONObject;
import org.joda.time.LocalTime;
import uy.com.pepeganga.userservice.models.system_config.SystemConfig;
import java.util.List;
import java.util.Map;

public interface IConfigurationsSystemService {

    Map<String, Object> saveScheduleDatabaseUpdate(String formatDays, List<LocalTime> hours, boolean running);

    boolean createJsonFile();

    JSONObject updateAttributesFromJsonFile(SystemConfig model);

    JSONObject readAllJsonFile();

}
