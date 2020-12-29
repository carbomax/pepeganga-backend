package uy.pepeganga.meli.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.pepeganga.meli.service.services.IOrderService;
import uy.pepeganga.meli.service.services.IStockProcessorService;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

     @Autowired
     private IOrderService orderService;

     @Autowired
     private IStockProcessorService stockProcessorService;

    @Scheduled(cron = "${scheduler.cron.notification}")
    @Async
    public void processingOrdersNotification(){
        logger.info("Processing Orders V2 by Notification scheduler....");
        orderService.schedulingOrdersV2();
        logger.info("Finishing processing Orders V2 by Notification scheduler....");
    }

    @Scheduled(cron = "${scheduler.cron.stock}")
    @Async
    public void schedulingStockProcessor(){
        logger.info("Processing Stock processor scheduler....");
        stockProcessorService.schedulingStockProcessor();
        logger.info("Finishing Stock processor scheduler....");
    }
}
