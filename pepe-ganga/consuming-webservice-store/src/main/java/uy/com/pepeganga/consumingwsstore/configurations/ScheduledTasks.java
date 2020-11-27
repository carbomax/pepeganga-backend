package uy.com.pepeganga.consumingwsstore.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.consumingwsstore.services.IScheduledSyncService;

@Component
@Async
public class ScheduledTasks {

    @Autowired
    IScheduledSyncService schedulesService;

    @Scheduled(initialDelay = 5000, fixedRate = 10800000)
    public void syncDataBase(){
        //@Scheduled(cron = "0 */ * ? * *")
        schedulesService.syncDataBase();
    }
}
