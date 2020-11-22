package uy.com.pepeganga.consumingwsstore.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.LocalTime;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationsSystemService implements IConfigurationsSystemService {

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
}
