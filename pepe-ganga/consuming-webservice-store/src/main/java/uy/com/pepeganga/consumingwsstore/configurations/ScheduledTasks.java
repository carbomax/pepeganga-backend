package uy.com.pepeganga.consumingwsstore.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.consumingwsstore.services.IScheduledSyncService;

@Component
@EnableAsync
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    IScheduledSyncService schedulesService;

    @Scheduled(initialDelay = 5000, fixedRate = 10800000)
    public void syncDataBase(){
        //@Scheduled(cron = "0 * * ? * *")
        logger.info("Initializing updating consuming service....");
        schedulesService.syncDataBase();
        logger.info("Finishing updating consuming service....");
    }

    public void test(){
        //schedulesService.updateStockOfPublicationsMeli();
    }
}
