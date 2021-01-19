package uy.com.pepeganga.consumingwsstore.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.consumingwsstore.services.IScheduledSyncService;


@Component
public class ConsumingInitAsync extends Thread{

    @Autowired
    IScheduledSyncService scheduleService;

    private static final Logger logger = LoggerFactory.getLogger(ConsumingInitAsync.class);
    public void run() {
        try{
            scheduleService.syncStockRisk();
        }catch (Exception e){
            logger.warn(String.format("Error executing syncStockRisk() method, Msg: %s, Error: ", e.getMessage()), e);
        }
    }


}
