package uy.com.pepeganga.consumingwsstore.services;

import org.joda.time.LocalTime;

import java.util.List;
import java.util.Map;

public interface IConfigurationsSystemService {

    Map<String, Object> saveScheduleDatabaseUpdate(String formatDays, List<LocalTime> hours, boolean running);
}
