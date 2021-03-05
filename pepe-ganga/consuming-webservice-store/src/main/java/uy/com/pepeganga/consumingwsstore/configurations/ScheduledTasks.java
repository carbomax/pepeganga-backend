package uy.com.pepeganga.consumingwsstore.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.consumingwsstore.services.IMeliServiceClient;
import uy.com.pepeganga.consumingwsstore.services.IScheduledSyncService;

@Component
@EnableAsync
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    IScheduledSyncService schedulesService;

    @Autowired
    IMeliServiceClient meliService;

    @Scheduled(initialDelay = 5000, fixedRate = 10800000)
    @Async
    public void syncDataBase(){
        //@Scheduled(cron = "0 * * ? * *")
        logger.info("Initializing updating consuming service....");
        schedulesService.syncDataBase();
        logger.info("Finishing updating consuming service....");
    }

    @Scheduled(initialDelay = 5000, fixedRate = 180000)
    @Async
    public void processPurchases(){
        //@Scheduled(cron = "0 * * ? * *")
        logger.info("Initializing process of Purchase Order Service....");
        meliService.executePurchaseOrder();
        logger.info("Finishing process of Purchase Order Service....");
    }
}
