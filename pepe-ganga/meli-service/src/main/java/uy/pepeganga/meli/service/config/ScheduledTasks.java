package uy.pepeganga.meli.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.business.common.models.MeliOrderItemDto;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.pepeganga.meli.service.services.ConsumingService;
import uy.pepeganga.meli.service.services.IConsumingService;
import uy.pepeganga.meli.service.services.IOrderService;
import uy.pepeganga.meli.service.services.IStockProcessorService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

     @Autowired
     private IOrderService orderService;

     @Autowired
     private IStockProcessorService stockProcessorService;

     @Autowired
     private IConsumingService consumingService;


    @Scheduled(cron = "${scheduler.cron.notification}")
    @Async
    public void processingOrdersNotification(){
        logger.info("Processing Orders V2 by Notification scheduler....");
        orderService.schedulingOrdersV2();
        logger.info("Finishing processing Orders V2 by Notification scheduler....");
    }

    @Scheduled(cron = "${scheduler.cron.stock}")
    @Async
    public void schedulingStockProcessor() {
        logger.info("Processing Stock processor scheduler....");
        stockProcessorService.schedulingStockProcessor();
        logger.info("Finishing Stock processor scheduler....");
    }

/*
    @Scheduled(fixedRate = 20000, initialDelay = 5000)
    public void testProcessPurchases(){
logger.info("iniciando test");
        List<MeliOrderItemDto> meliOrderItemDtoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            meliOrderItemDtoList.add(MeliOrderItemDto.builder()
                    .itemId(String.valueOf(i))
                    .description("DevHighLevel Test")
                    .price(100*i)
                    .quantity(3*i).build());
        }

        OrderDto orderToSend = OrderDto.builder()
                .ci(66666666)
                .address("Address Tes")
                .coin(1)
                .date(19910506)
                .email("test@test.com")
                .department("Montevideo")
                .rut(3333333333333L)
                .ci(33333333333333L)
                .sellerName("Test name")
                .location("Location Test")
                .orderId(3333)
                .sellerId(333333333)
                .observation("DevHighLevelTest")
                .items(meliOrderItemDtoList)
                .build();
        List<OrderDto> orders = new ArrayList<>();
        orders.add(orderToSend);
        logger.info("Retorno del servicio: \n {} \n", consumingService.processPurchases(orders) );
        logger.info("Finalizando test");
    }*/


}
