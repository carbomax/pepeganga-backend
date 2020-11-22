package uy.pepeganga.meli.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.*;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.business.common.utils.enums.NotificationTopic;
import uy.pepeganga.meli.service.exceptions.OrderCreateException;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.orders.DMOrder;
import uy.pepeganga.meli.service.models.orders.DMOrderItems;
import uy.pepeganga.meli.service.models.orders.DMOrderShipping;
import uy.pepeganga.meli.service.repository.*;
import uy.pepeganga.meli.service.utils.ApiResources;
import uy.pepeganga.meli.service.utils.MeliUtils;
import uy.pepeganga.meli.service.utils.OrderStatusType;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    ObjectMapper mapper;

    @Autowired
    IApiService apiService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    SellerAccountRepository sellerAccountRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    MeliOrderSellerBillingInfoRepository billingInfoRepository;

    @Autowired
    MeliOrderSellerRepository orderSellerRepository;

    @Autowired
    MeliOrderBuyerRepository orderBuyerRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    DetailsPublicationMeliRepository detailsPublicationMeliRepository;

    @Autowired
    CarrierRepository carrierRepository;

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    MeliOrderShipmentCostComponentsRepository costComponentsRepository;

    @Autowired
    StockProcessorRepository stockProcessorRepository;


    @Value("${meli.order.attempts}")
    private String orderAttempts;

    private static int ORDER_ATTEMPTS;

    @Value("${meli.order.attempts}")
    public void setNameStatic(String orderAttempts){
        OrderService.ORDER_ATTEMPTS = Integer.parseInt(orderAttempts);
    }

    @Override
    @Transactional
    public void schedulingOrdersV2() {

        List<Notification> notifications = notificationRepository.findByTopic(NotificationTopic.ORDERS_V2.getTopicName().trim());
        if (notifications != null) {
            logger.info("Notifications received: {}", notifications.size());
            this.processingOrdersNotifications(notifications);
        } else logger.info("There are no notifications to process");
    }


    private void processingOrdersNotifications(List<Notification> notifications) {
        for (Notification notification : notifications) {
            boolean error = false;
            logger.info("Processing notification: topic: {}, applicationId: {}, userId: {}", notification.getTopic(), notification.getApplicationId(), notification.getUserId());
            try {
                // check if exist a value for account
                if (sellerAccountRepository.existsByUserId(notification.getUserId())) {


                    if(!MeliUtils.validateTokenExpiration(sellerAccountRepository.findByUserId(notification.getUserId()).getExpirationDate())){
                        apiService.getTokenByRefreshToken(sellerAccountRepository.findByUserId(notification.getUserId()));
                    }
                    DMOrder order = processingOrderByNotification(notification);

                    DMOrderShipping orderShipping = mapper.convertValue(apiService.getShipmentOfOrder(order.getShipping().getId(),
                            sellerAccountRepository.findByUserId(notification.getUserId()).getAccessToken()), DMOrderShipping.class);

                    // Processing shipments
                    processingShipment(orderShipping);
                    error = false;
                } else {
                    // doing something
                    logger.info("No exist a account with id: {}", notification.getUserId());
                    error = true;
                }

            } catch (Exception e) {
                error = true;
                logger.error(String.format("Error meli api request  message: %s", e.getMessage()), e);

            } finally {
                if (notification.getBusinessAttempts() > ORDER_ATTEMPTS) {
                    notificationRepository.deleteAllByResource(notification.getResource());
                    logger.info("Deleting notification for more  id: {}, resource: {}", notification.getId(), notification.getResource());
                }  else if(error){
                    notification.setBusinessAttempts(notification.getBusinessAttempts() + 1);
                    notificationRepository.save(notification);
                    logger.info("Order notification set business attempts to: {} by meli request error before", notification.getBusinessAttempts() + 1);
                } else {
                    notificationRepository.deleteAllByResource(notification.getResource());
                    logger.info("Deleting notification by success id: {}, resource: {}", notification.getId(), notification.getResource());
                }
            }
        }
    }

    private void processingShipment(DMOrderShipping orderShipping) {
        MeliOrderShipment shipment = shipmentRepository.findMeliOrderShipmentById(orderShipping.getId());
        if(Objects.nonNull(shipment)){
            logger.info("Updating shipment id: {}", shipment.getId());
            shipment.setBaseCost(orderShipping.getBaseCost());
            shipment.setOrderCost(orderShipping.getOrderCost());
            shipment.setCreateBy(orderShipping.getCreateBy());
            shipment.setDateCreated(orderShipping.getDateCreated());
            shipment.setMode(orderShipping.getMode());
            shipment.setLastUpdated(orderShipping.getLastUpdated());
            shipment.setSiteId(orderShipping.getSiteId());
            shipment.setStatus(orderShipping.getStatus());
            shipment.setOrderId(orderShipping.getOrderId());
            Optional<MeliOrderShipmentCostComponents> costComponents = costComponentsRepository.findById(shipment.getCostComponents().getId());
            if(costComponents.isEmpty()){
                MeliOrderShipmentCostComponents components = new MeliOrderShipmentCostComponents();
                components.setCompensation(orderShipping.getCostComponents().getCompensation());
                components.setEspecialDiscount(orderShipping.getCostComponents().getEspecialDiscount());
                components.setGapDiscount(orderShipping.getCostComponents().getGapDiscount());
                components.setLoyalDiscount(orderShipping.getCostComponents().getLoyalDiscount());
                components.setRatio(orderShipping.getCostComponents().getRatio());
                shipment.setCostComponents(costComponentsRepository.save(components));
            } else {
                costComponents.get().setCompensation(orderShipping.getCostComponents().getCompensation());
                costComponents.get().setEspecialDiscount(orderShipping.getCostComponents().getEspecialDiscount());
                costComponents.get().setGapDiscount(orderShipping.getCostComponents().getGapDiscount());
                costComponents.get().setLoyalDiscount(orderShipping.getCostComponents().getLoyalDiscount());
                costComponents.get().setRatio(orderShipping.getCostComponents().getRatio());
                costComponentsRepository.save(costComponents.get());
            }

            shipmentRepository.save(shipment);
        } else {
            MeliOrderShipment shipmentToCreate =  new MeliOrderShipment();
            shipmentToCreate.setId(orderShipping.getId());
            shipmentToCreate.setBaseCost(orderShipping.getBaseCost());
            shipmentToCreate.setOrderCost(orderShipping.getOrderCost());
            shipmentToCreate.setCreateBy(orderShipping.getCreateBy());
            shipmentToCreate.setDateCreated(orderShipping.getDateCreated());
            shipmentToCreate.setMode(orderShipping.getMode());
            shipmentToCreate.setLastUpdated(orderShipping.getLastUpdated());
            shipmentToCreate.setSiteId(orderShipping.getSiteId());
            shipmentToCreate.setStatus(orderShipping.getStatus());
            shipmentToCreate.setOrderId(orderShipping.getOrderId());
            MeliOrderShipmentCostComponents componentsToCreate = new MeliOrderShipmentCostComponents();
            componentsToCreate.setCompensation(orderShipping.getCostComponents().getCompensation());
            componentsToCreate.setEspecialDiscount(orderShipping.getCostComponents().getEspecialDiscount());
            componentsToCreate.setGapDiscount(orderShipping.getCostComponents().getGapDiscount());
            componentsToCreate.setLoyalDiscount(orderShipping.getCostComponents().getLoyalDiscount());
            componentsToCreate.setRatio(orderShipping.getCostComponents().getRatio());
            shipmentToCreate.setCostComponents(costComponentsRepository.save(componentsToCreate));
            shipmentRepository.save(shipmentToCreate);
        }
    }

    private DMOrder processingOrderByNotification(Notification notification) throws ApiException, OrderCreateException {
        DMOrder order = mapper.convertValue(apiService.getOrderByNotificationResource(notification.getResource(),
                sellerAccountRepository.findByUserId(notification.getUserId()).getAccessToken()), DMOrder.class);

        if (ordersRepository.existsByOrderId(String.valueOf(order.getId()))) {
            // update order
            MeliOrders orderToUpdate = ordersRepository.findByOrderId(String.valueOf(order.getId()));
            orderToUpdate.setStatus(order.getStatus());
            orderToUpdate.setCurrencyId(order.getCurrencyId());
            orderToUpdate.setAmountTaxes(order.getTaxes().getAmount());
            orderToUpdate.setCurrencyIdTaxes(order.getTaxes().getCurrencyId());
            orderToUpdate.setPaidAmount(order.getPaidAmount());
            orderToUpdate.setPayments(order.getPayments().stream().map(dmOrderPayment -> new MeliOrderPayment(dmOrderPayment.getId(), dmOrderPayment.getTransactionAmount(),
                    dmOrderPayment.getCurrencyId(), dmOrderPayment.getStatus())
            ).collect(Collectors.toList()));
            ordersRepository.save(transformDateOrderCreated(orderToUpdate));

        } else {
            // create order
            this.createMeliOrder(order);
        }
        return order;
    }

    private MeliOrders transformDateOrderCreated(MeliOrders orderToUpdate) {
        DateTime currentTime = DateTimeUtilsBss.getDateTimeAtCurrentTime();
        if( orderToUpdate.getDateCreated() == null || orderToUpdate.getBusinessDateCreated() == null || orderToUpdate.getBusinessDateCreated() <= 0 || orderToUpdate.getDateCreated().equals("")){
            orderToUpdate.setBusinessDateCreated(Long.parseLong(String.format("%d%d%d",currentTime.getYear(), currentTime.getMonthOfYear(), currentTime.getDayOfMonth())));
            orderToUpdate.setDateCreated(String.format("%d-%d-%d",currentTime.getYear(), currentTime.getMonthOfYear(), currentTime.getDayOfMonth() ));
        }
        return orderToUpdate;
    }

    private void createMeliOrder(DMOrder order) throws OrderCreateException {
      try {
          if(order.getStatus().equals(OrderStatusType.PAID.getStatus())){
              MeliOrders orders = new MeliOrders();

              // Buyer
              if (order.getBuyer() != null) {
                  MeliOrderBuyer buyer = new MeliOrderBuyer(order.getBuyer().getId(), order.getBuyer().getNickname(), order.getBuyer().getEmail(), order.getBuyer().getFirstName(),
                          order.getBuyer().getLastName(), billingInfoRepository.save(new MeliOrderBuyerBillingInfo(order.getBuyer().getBillingInfo().getDocType(), order.getBuyer().getBillingInfo().getDocNumber())));
                  orders.setBuyer(orderBuyerRepository.save(buyer));
              }

              if (order.getSeller() != null) {
                  MeliOrderSeller seller = new MeliOrderSeller(order.getSeller().getId(), order.getSeller().getNickname(), order.getSeller().getEmail(), order.getSeller().getFirtsName()
                          , order.getSeller().getLastName());
                  orders.setSeller(orderSellerRepository.save(seller));
              }


              // Items in order
              if (order.getOrderItems() != null) {
                  List<MeliOrderItem> orderItems = new ArrayList<>();
                  order.getOrderItems().forEach(dmOrderItems -> orderItems.add(new MeliOrderItem(dmOrderItems.getItem().getId(), dmOrderItems.getItem().getTitle(),
                          dmOrderItems.getItem().getCategoryId(), dmOrderItems.getItem().getSellerSku(), dmOrderItems.getQuantity(), dmOrderItems.getUnitPrice(), dmOrderItems.getCurrencyId())));
                  orders.setItems(orderItems);
                  this.updateItemPublished(order.getOrderItems());
              }

              // Payments in order
              if (order.getPayments() != null) {
                  List<MeliOrderPayment> payments = new ArrayList<>();
                  order.getPayments().forEach(dmOrderPayment -> payments.add(new MeliOrderPayment(dmOrderPayment.getId(), dmOrderPayment.getTransactionAmount(), dmOrderPayment.getCurrencyId(), dmOrderPayment.getStatus())));
                  orders.setPayments(payments);
              }

              MeliOrders orderToCreate = transformDateOrderCreated(orders);
              // private order values
              orderToCreate.setCurrencyId(order.getCurrencyId());
              orderToCreate.setDateClosed(order.getDateClosed());
              orderToCreate.setOrderId(String.valueOf(order.getId()));
              orderToCreate.setShippingId(order.getShipping().getId());
              orderToCreate.setStatus(order.getStatus());
              orderToCreate.setTotalAmount(order.getTotalAmount());
              orderToCreate.setPaidAmount(order.getPaidAmount());
              orderToCreate.setAmountTaxes(order.getTaxes().getAmount());
              orderToCreate.setCurrencyIdTaxes(order.getTaxes().getCurrencyId());
              ordersRepository.save(orderToCreate);

              // Update stock
              order.getOrderItems().forEach( dmOrderItems -> {
                  StockProcessor stockProcessorFounded = stockProcessorRepository.findBySku(dmOrderItems.getItem().getSellerSku());
                  if(Objects.isNull(stockProcessorFounded)){
                      // Create new item to Stock Processor table
                      StockProcessor stockProcessor = new StockProcessor();
                      stockProcessor.setExpectedStock(1);
                      stockProcessor.setSku(dmOrderItems.getItem().getSellerSku());
                      stockProcessorRepository.save(stockProcessor);
                  } else {
                      // Add 1 to expected Stock.
                      stockProcessorFounded.setExpectedStock(stockProcessorFounded.getExpectedStock() + 1);
                      stockProcessorRepository.save(stockProcessorFounded);
                  }
              } );

          } else throw new OrderCreateException(String.format("Order with status different to paid cannot be created. Order id: %d", order.getId()));
      } catch (Exception e){
          throw new OrderCreateException(e.getMessage(), e);
      }
    }

    private void updateItemPublished(List<DMOrderItems> dmOrderItems){

        dmOrderItems.forEach( dmOrderItems1 -> {
            DetailsPublicationsMeli detailItemPublication = detailsPublicationMeliRepository.findByIdPublicationMeli(dmOrderItems1.getItem().getId());
            if(detailItemPublication != null){
                logger.info("Updating item publication with idPublicationMeli: {}, account: {}", detailItemPublication.getIdPublicationMeli(), detailItemPublication.getAccountMeli());
                detailItemPublication.setSaleStatus(1);
                detailsPublicationMeliRepository.save(detailItemPublication);
                logger.info("DetailItemPublication updated successfully");
            }

        });
    }

    @Override
    public Map<String, Object> getOrdersByAccount(Integer accountId) {
        Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(accountId);
        Map<String, Object> response = new HashMap<>();
        if (accountFounded.isEmpty()) {
            response.put(ApiResources.ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
        } else {
            try {
                response.put("response", apiService.getOrdersBySeller(accountFounded.get().getUserId(), accountFounded.get().getAccessToken()));
            } catch (ApiException e) {
                logger.error("Error getting orders by account: {}, error: {}", accountId, e.getResponseBody());
                response.put(ApiResources.MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
            }
        }
        return response;
    }

    @Override
    public Object delete() {
        ordersRepository.deleteAll();
        return "Eliminados";
    }

    @Override
    public Page<MeliOrders> getAllOrdersByProfile(Integer profileId, List<String> statusFilter, String nameClient, Long dateFrom, Long dateTo, int page, int size) {
        Optional<Profile> profile = profileRepository.findById(profileId);

        if (profile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s", profileId));
        } else {
            List<String> accounts = new ArrayList<>();
            profile.get().getSellerAccounts().forEach(sellerAccount -> {
                if(sellerAccount.getUserId() != null ){
                    accounts.add(String.valueOf(sellerAccount.getUserId()));
                }
            });

            return ordersRepository.findBySellerId(accounts, statusFilter, nameClient, dateFrom, dateTo, PageRequest.of(page, size));

        }

    }

    @Override
    public boolean updateCarrier(Long orderId, int carrierId) {
        Optional<MeliOrders> orderToUpdate = ordersRepository.findById(orderId);
        if(orderToUpdate.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }
        Optional<Carrier> carrierFound = carrierRepository.findById(carrierId);
        if(carrierFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Carrier with id: %d not found", carrierId));
        }
        orderToUpdate.get().setCarrier(carrierFound.get());
        ordersRepository.save(orderToUpdate.get());
        logger.info("Order : {} updated with carrier: {}", orderId, carrierId);
        return true;
    }

    @Override
    public boolean updateDescription(Long orderId, String description) {
        Optional<MeliOrders> orderToUpdate = ordersRepository.findById(orderId);
        if(orderToUpdate.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }

        orderToUpdate.get().setDescriptionBss(description);
        ordersRepository.save(orderToUpdate.get());
        logger.info("Order : {} updated with description: {}", orderId, description);
        return true;
    }

    @Override
    public boolean updateObservation(Long orderId, String observation) {
        Optional<MeliOrders> orderToUpdate = ordersRepository.findById(orderId);
        if(orderToUpdate.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }

        orderToUpdate.get().setObservationBss(observation);
        ordersRepository.save(orderToUpdate.get());
        logger.info("Order : {} updated with observation: {}", orderId, observation);
        return true;
    }

    @Override
    public boolean updateInvoice(Long orderId, Long invoice) {
        Optional<MeliOrders> orderToUpdate = ordersRepository.findById(orderId);
        if(orderToUpdate.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }

        orderToUpdate.get().setInvoiceNumberBss(invoice);
        ordersRepository.save(orderToUpdate.get());
        logger.info("Order : {} updated with invoice: {}", orderId, invoice);
        return true;
    }




}
