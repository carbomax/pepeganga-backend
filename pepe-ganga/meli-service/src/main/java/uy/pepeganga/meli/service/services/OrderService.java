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
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.*;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.business.common.utils.enums.NotificationTopic;
import uy.com.pepeganga.business.common.utils.enums.RoleType;
import uy.pepeganga.meli.service.exceptions.OrderCreateException;
import uy.pepeganga.meli.service.exceptions.TokenException;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.dto.CountPaidAndCancellerSalesDto;
import uy.pepeganga.meli.service.models.dto.ISalesAndAmountBySeller;
import uy.pepeganga.meli.service.models.orders.DMOrder;
import uy.pepeganga.meli.service.models.orders.DMOrderItems;
import uy.pepeganga.meli.service.models.orders.DMOrderShipping;
import uy.pepeganga.meli.service.models.dto.IBetterSkuDto;
import uy.pepeganga.meli.service.models.dto.OrdersByDateCreatedAndCountDto;
import uy.pepeganga.meli.service.repository.*;
import uy.pepeganga.meli.service.utils.ApiResources;
import uy.pepeganga.meli.service.utils.MeliUtils;
import uy.pepeganga.meli.service.utils.OperatorBusinessStatusType;
import uy.pepeganga.meli.service.utils.OrderStatusType;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Value("${meli.api.invoice.url}")
    private String invoiceUrl;

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

    @Autowired
    CheckingStockProcessorRepository checkingStockProcessorRepository;


    @Value("${meli.order.attempts}")
    private String orderAttempts;

    private static int ORDER_ATTEMPTS;

    @Value("${meli.order.attempts}")
    public void setNameStatic(String orderAttempts) {
        OrderService.ORDER_ATTEMPTS = Integer.parseInt(orderAttempts);
    }

    @Override
    public void schedulingOrdersV2() {
        // Get All notifications
        List<Notification> notifications = notificationRepository.findByTopic(NotificationTopic.ORDERS_V2.getTopicName().trim());
        if (notifications != null) {
            logger.info("Notifications received: {}", notifications.size());
            this.processingOrdersNotifications(notifications);
        } else logger.info("There are no notifications to process");
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
    public Page<MeliOrders> getAllOrdersByProfile(Integer profileId, List<String> statusFilter, String nameClient, String nameSeller, Long dateFrom, Long dateTo, int page, int size, List<String> operatorBusinessStatus) {
        Optional<Profile> profile = profileRepository.findById(profileId);

        if (profile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s", profileId));
        } else {
            List<String> accounts = new ArrayList<>();
            List<Role>  rolesProfile = profile.get().getUser().getRoles();
            boolean isOperatorOrAdmin = rolesProfile.stream().anyMatch(role -> role.getName().equals(RoleType.OPERATOR.getBusinessRole()) || role.getName().equals(RoleType.ADMIN.getBusinessRole()) );
            if(!isOperatorOrAdmin){
                accounts = profile.get().getSellerAccounts().stream().filter(sellerAccount -> Objects.nonNull(sellerAccount.getUserIdBss())).map(result -> String.valueOf(result.getUserIdBss())).collect(Collectors.toList());
            }

            List<Integer> operatorBssStatus = new ArrayList<>();
            if (operatorBusinessStatus.isEmpty()) {
                operatorBssStatus.add(OperatorBusinessStatusType.IN_PROCESS.getCode());
                operatorBssStatus.add(OperatorBusinessStatusType.UNDELIVERED.getCode());
                operatorBssStatus.add(OperatorBusinessStatusType.DELIVERED.getCode());
            } else {
                try {
                    operatorBusinessStatus.forEach(s -> operatorBssStatus.add(OperatorBusinessStatusType.ofByStatus(s).getCode()));
                } catch (IllegalArgumentException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            String.format("Operator business status not corrects. Try with : [ %s, %s, %s ]",
                                    OperatorBusinessStatusType.IN_PROCESS.getStatus(),
                                    OperatorBusinessStatusType.UNDELIVERED.getStatus(),
                                    OperatorBusinessStatusType.DELIVERED.getStatus()));
                }


            }
            Page<MeliOrders> orders;
            if(isOperatorOrAdmin){
                orders = ordersRepository.findAllOrders(statusFilter, nameClient.trim(), nameSeller.trim(), dateFrom, dateTo, operatorBssStatus, PageRequest.of(page, size));
            } else {
                orders = ordersRepository.findBySellerId(accounts, statusFilter, nameClient.trim(), nameSeller.trim(), dateFrom, dateTo, operatorBssStatus, PageRequest.of(page, size));
            }
            List<Long> shipments = new ArrayList<>();
            orders.getContent().forEach(order -> {
                if (order.getShippingId() != null && order.getShippingId() > 0) {
                    shipments.add(order.getShippingId());
                }
            });

            List<MeliOrderShipment> shipmentsFounded = shipmentRepository.findAllById(shipments);
            orders.getContent().forEach(order -> shipmentsFounded.forEach(shipment -> {
                if (shipment.getId() != null && shipment.getId() > 0 && shipment.getId().equals(order.getShippingId())) {
                    order.setShipment(shipment);
                }
            }));
            return orders;

        }

    }

    @Override
    public boolean updateCarrier(Long orderId, int carrierId) {
        Optional<MeliOrders> orderToUpdate = ordersRepository.findById(orderId);
        if (orderToUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }
        Optional<Carrier> carrierFound = carrierRepository.findById(carrierId);
        if (carrierFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Carrier with id: %d not found", carrierId));
        }
        orderToUpdate.get().setCarrier(carrierFound.get());
        ordersRepository.save(orderToUpdate.get());
        logger.info("Order : {} updated with carrier: {}", orderId, carrierId);
        return true;
    }

    @Override
    public boolean updateOperatorName(Long orderId, String name) {
        Optional<MeliOrders> orderToUpdate = ordersRepository.findById(orderId);
        if (orderToUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }

        orderToUpdate.get().setOperatorNameBss(name);
        ordersRepository.save(orderToUpdate.get());
        logger.info("Order : {} updated with operator name: {}", orderId, name);
        return true;
    }

    @Override
    public boolean updateTag(Long orderId, Integer tagBss) {
        Optional<MeliOrders> orderToUpdate = ordersRepository.findById(orderId);
        if (tagBss < 0 || tagBss > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Tag: %d not allowed. Only accept values: 0 and 1", tagBss));
        }
        if (orderToUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }

        orderToUpdate.get().setTagBss(tagBss);
        ordersRepository.save(orderToUpdate.get());
        logger.info("Order : {} updated with tag: {}", orderId, tagBss);
        return true;
    }

    @Override
    public boolean updateObservation(Long orderId, String observation) {
        Optional<MeliOrders> orderToUpdate = ordersRepository.findById(orderId);
        if (orderToUpdate.isEmpty()) {
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
        if (orderToUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }

        orderToUpdate.get().setInvoiceNumberBss(invoice);
        ordersRepository.save(orderToUpdate.get());
        logger.info("Order : {} updated with invoice: {}", orderId, invoice);
        return true;
    }

    @Override
    public Map<String, Object> getInvoiceUrl(Long orderId) {
        Optional<MeliOrders> order = ordersRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }

        if (order.get().getShippingId() != null && order.get().getShippingId() > 0) {
            SellerAccount sellerAccount = sellerAccountRepository.findByUserId(order.get().getSeller().getSellerId());
            if (Objects.isNull(sellerAccount)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Not exist a seller to order %s", orderId));
            } else {

                boolean tokenExpiration;
                try {
                    tokenExpiration = isTokenExpiration(sellerAccount);
                } catch (TokenException | ApiException e) {
                    throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, String.format("Refresh token cannot be obtained. Exception: %s ", e.getMessage()));
                }
                String url = this.invoiceUrl.replace("XXX", String.valueOf(order.get().getShippingId()));
                if (tokenExpiration) {
                    url = url.concat(sellerAccountRepository.findByUserId(order.get().getSeller().getSellerId()).getAccessToken());
                } else url = url.concat(sellerAccount.getAccessToken());
                Map<String, Object> map = new HashMap<>();
                map.put("response", url);
                return map;

            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("ShippingId to order: %d not exist", orderId));
        }
    }

    @Override
    public boolean updateOperatorBusinessStatus(Long orderId, Integer status) {
        Optional<MeliOrders> orderToUpdate = ordersRepository.findById(orderId);
        if (orderToUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order with id: %d not found", orderId));
        }

        orderToUpdate.get().setOperatorBusinessStatus(status);
        ordersRepository.save(orderToUpdate.get());
        logger.info("Order : {} updated with operator business status: {}", orderId, status);
        return true;
    }

    private void processingOrdersNotifications(List<Notification> notifications) {
        for (Notification notification : notifications) {
            if (notificationRepository.existsByResource(notification.getResource().trim())) {
                boolean error = false;
                boolean errorCreating = false;
                logger.info("Processing notification: topic: {}, applicationId: {}, userId: {}", notification.getTopic(), notification.getApplicationId(), notification.getUserId());
                try {
                    // check if exist a value for account
                    if (sellerAccountRepository.existsByUserId(notification.getUserId())) {

                        SellerAccount sellerFounded = sellerAccountRepository.findByUserId(notification.getUserId());
                        if (MeliUtils.isExpiredToken(sellerFounded)) {
                            apiService.getTokenByRefreshToken(sellerAccountRepository.findByUserId(notification.getUserId()));
                        }
                        DMOrder order = processingOrderByNotification(notification);
                        if (Objects.nonNull(order)) {
                            DMOrderShipping orderShipping = mapper.convertValue(apiService.getShipmentOfOrder(order.getShipping().getId(),
                                    sellerAccountRepository.findByUserId(notification.getUserId()).getAccessToken()), DMOrderShipping.class);

                            // Processing shipments
                            processingShipment(orderShipping);
                        }
                        error = false;
                    } else {
                        // doing something
                        logger.info("No exist a account with id: {}", notification.getUserId());
                        error = true;
                    }

                } catch (ApiException | TokenException e) {

                    error = true;
                    logger.error(String.format("Error meli api request  message: %s", e.getMessage()), e);

                } catch (OrderCreateException e) {
                    errorCreating = true;

                }  finally {
                    if (notification.getBusinessAttempts() > ORDER_ATTEMPTS) {
                        if (notificationRepository.existsByResource(notification.getResource())) {
                            notificationRepository.deleteAllByResource(notification.getResource());
                            logger.info("Deleting notification for more  id: {}, resource: {}", notification.getId(), notification.getResource());
                        }

                    } else if (error) {
                        notification.setBusinessAttempts(notification.getBusinessAttempts() + 1);
                        notificationRepository.save(notification);
                        logger.info("Order notification set business attempts to: {} by meli request error before", notification.getBusinessAttempts() + 1);
                    }else if (errorCreating) {
                        logger.error("Error creating resource order: {}, userId: {}", notification.getResource(), notification.getUserId());
                    }
                    else {
                        if (notificationRepository.existsByResource(notification.getResource())) {
                            notificationRepository.deleteAllByResource(notification.getResource());
                            logger.info("Deleting notification by success id: {}, resource: {}", notification.getId(), notification.getResource());
                        }

                    }
                }
            }else {
               logger.info("OK - Notification with resource: {} already deleted", notification.getResource());
            }
        }

    }

    private void processingShipment(DMOrderShipping orderShipping) {
        MeliOrderShipment shipment = shipmentRepository.findMeliOrderShipmentById(orderShipping.getId());
        if (Objects.nonNull(shipment)) {
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
            if (costComponents.isEmpty()) {
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
            MeliOrderShipment shipmentToCreate = new MeliOrderShipment();
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

        if (Objects.isNull(order)) {
            throw new OrderCreateException(String.format("Order not obtained by resource: %s", notification.getResource()));
        }
        // Exist publication for this order
        if (detailsPublicationMeliRepository.existsByIdPublicationMeli(order.getOrderItems().get(0).getItem().getId())) {
            if (ordersRepository.existsByOrderId(String.valueOf(order.getId()))) {
                // update order
                updateMeliOrder(order);

            } else {
                // create order
                this.createMeliOrder(order);
            }
            return order;
        } else {
            //Delete notification
            if (notificationRepository.existsById(notification.getId())) {
                notificationRepository.deleteById(notification.getId());
            }
            return null;
        }

    }

    private void updateMeliOrder(DMOrder order) {
        MeliOrders orderToUpdate = ordersRepository.findByOrderId(String.valueOf(order.getId()));
        orderToUpdate.setStatus(order.getStatus());
        orderToUpdate.setCurrencyId(order.getCurrencyId());
        orderToUpdate.setAmountTaxes(order.getTaxes().getAmount());
        orderToUpdate.setCurrencyIdTaxes(order.getTaxes().getCurrencyId());
        orderToUpdate.setPaidAmount(order.getPaidAmount());
        if (!orderToUpdate.getStatus().equals(order.getStatus())) {
            StockProcessor stockProcessorFounded = stockProcessorRepository.findBySku(order.getOrderItems().get(0).getItem().getSellerSku());

            if (Objects.nonNull(stockProcessorFounded)) {
                StockProcessor stockProcessor = new StockProcessor();
                // Add quantity to expected Stock.
                Integer quantity = order.getOrderItems().get(0).getQuantity();
                if (order.getStatus().equals(OrderStatusType.PAID.getStatus())) {
                    stockProcessorFounded.setExpectedStock(stockProcessorFounded.getExpectedStock() + quantity);
                    stockProcessorFounded.setRealStock(stockProcessorFounded.getRealStock() - quantity);
                    stockProcessor = stockProcessorRepository.save(stockProcessorFounded);
                } else if (order.getStatus().equals(OrderStatusType.CANCELLED.getStatus())) {
                    stockProcessorFounded.setExpectedStock(stockProcessorFounded.getExpectedStock() - quantity);
                    stockProcessorFounded.setRealStock(stockProcessorFounded.getRealStock() + quantity);
                    stockProcessor = stockProcessorRepository.save(stockProcessorFounded);
                }

                updateCheckingStockProcessor(stockProcessor);
            }


        }
        ordersRepository.save(transformDateOrderCreated(orderToUpdate));
    }

    private void updateCheckingStockProcessor(StockProcessor stockProcessor) {
        CheckingStockProcessor checkingFounded = checkingStockProcessorRepository.findBySku(stockProcessor.getSku());
        if (Objects.isNull(checkingFounded)) {
            checkingStockProcessorRepository.save(new CheckingStockProcessor(stockProcessor.getSku(), stockProcessor.getExpectedStock(), stockProcessor.getRealStock()));
            logger.info("Updating checking stock processor with sku: {}", stockProcessor.getSku());
        } else {
            checkingFounded.setExpectedStock(stockProcessor.getExpectedStock());
            checkingFounded.setRealStock(stockProcessor.getRealStock());
            checkingStockProcessorRepository.save(checkingFounded);
        }
    }

    private MeliOrders transformDateOrderCreated(MeliOrders orderToUpdate) {
        DateTime currentTime = DateTimeUtilsBss.getDateTimeAtCurrentTime();
        if (orderToUpdate.getDateCreated() == null || orderToUpdate.getBusinessDateCreated() == null || orderToUpdate.getBusinessDateCreated() <= 0 || orderToUpdate.getDateCreated().equals("")) {
            orderToUpdate.setBusinessDateCreated(Long.parseLong(String.format("%d%s%s", currentTime.getYear(), DateTimeUtilsBss.helperZeroBeforeMonthOrDay(currentTime.getMonthOfYear()), DateTimeUtilsBss.helperZeroBeforeMonthOrDay(currentTime.getDayOfMonth()))));
            orderToUpdate.setDateCreated(String.format("%d-%s-%s", currentTime.getYear(), DateTimeUtilsBss.helperZeroBeforeMonthOrDay(currentTime.getMonthOfYear()), DateTimeUtilsBss.helperZeroBeforeMonthOrDay(currentTime.getDayOfMonth())));
        }
        return orderToUpdate;
    }

    private void createMeliOrder(DMOrder order) throws OrderCreateException {
        try {
            if (order.getStatus().equals(OrderStatusType.PAID.getStatus())) {
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
                orderToCreate.setDateCreatedMeli(order.getDateCreated());
                Carrier carrier = new Carrier();
                carrier.setId(0);
                orderToCreate.setCarrier(carrier);
                ordersRepository.save(orderToCreate);

                // Update stock
                order.getOrderItems().forEach(dmOrderItems -> {
                    StockProcessor stockProcessorFounded = stockProcessorRepository.findBySku(dmOrderItems.getItem().getSellerSku());

                    if (Objects.nonNull(stockProcessorFounded)) {
                        StockProcessor processor = new StockProcessor();
                        // Add quantity to expected Stock.
                        stockProcessorFounded.setExpectedStock(stockProcessorFounded.getExpectedStock() + dmOrderItems.getQuantity());
                        stockProcessorFounded.setRealStock(stockProcessorFounded.getRealStock() - dmOrderItems.getQuantity());
                        processor = stockProcessorRepository.save(stockProcessorFounded);
                        // updating checking table to scheduler
                        updateCheckingStockProcessor(processor);
                    }


                });

            } else
                throw new OrderCreateException(String.format("Order with status different to paid cannot be created. Order id: %d", order.getId()));
        } catch (Exception e) {
            throw new OrderCreateException(e.getMessage(), e);
        }
    }

    private void updateItemPublished(List<DMOrderItems> dmOrderItems) {

        dmOrderItems.forEach(dmOrderItems1 -> {
            DetailsPublicationsMeli detailItemPublication = detailsPublicationMeliRepository.findByIdPublicationMeli(dmOrderItems1.getItem().getId());
            if (detailItemPublication != null) {
                logger.info("Updating item publication with idPublicationMeli: {}, account: {}", detailItemPublication.getIdPublicationMeli(), detailItemPublication.getAccountMeli());
                detailItemPublication.setSaleStatus(1);
                detailsPublicationMeliRepository.save(detailItemPublication);
                logger.info("DetailItemPublication updated successfully");
            }

        });
    }

    private boolean isTokenExpiration(SellerAccount sellerAccount) throws TokenException, ApiException {
        // Verify  token expiration
        boolean tokenExpiration = false;
        if (MeliUtils.isExpiredToken(sellerAccount)) {
            apiService.getTokenByRefreshToken(sellerAccountRepository.findByUserId(sellerAccount.getUserId()));
            tokenExpiration = true;
        }
        return tokenExpiration;
    }

    @Override
    public List<OrdersByDateCreatedAndCountDto> getSalesByBusinessDateCreated(Long dateFrom, Long dateTo, Long sellerId){
        return Objects.isNull(sellerId) ? ordersRepository.getSalesByBusinessDateCreated(dateFrom, dateTo) : ordersRepository.getSalesByBusinessDateCreated(dateFrom, dateTo, sellerId);
    }

    @Override
    public CountPaidAndCancellerSalesDto getCountAllSales(Long sellerId) {
        CountPaidAndCancellerSalesDto dto = new CountPaidAndCancellerSalesDto();
        if(Objects.isNull(sellerId)){
            dto.setPaid(ordersRepository.getCountAllSalesPaid());
            dto.setCancelled(ordersRepository.getCountAllSalesCancelled());
        } else {
            dto.setPaid(ordersRepository.getCountAllSalesPaid(sellerId));
            dto.setCancelled(ordersRepository.getCountAllSalesCancelled(sellerId));
        }
        return dto;
    }

    @Override
    public IBetterSkuDto getBetterSku(Long sellerId) {
        if(Objects.isNull(sellerId)){
            return ordersRepository.getBetterSku();
        } else {
            return ordersRepository.getBetterSku(sellerId);
        }

    }

    @Override
    public List<IBetterSkuDto> getBettersSku(Integer size, Long sellerId) {
        return Objects.isNull(sellerId) ? ordersRepository.getBettersSku(size) : ordersRepository.getBettersSku(size, sellerId);
    }

    @Override
    public List<ISalesAndAmountBySeller> getAnalysisDrop(long dateFrom, long dateTo, Long sellerId) {
        return Objects.isNull(sellerId) ? ordersRepository.getSalesAndAmountSellerByDate(dateFrom, dateTo) : ordersRepository.getSalesAndAmountSellerByDate(dateFrom, dateTo, sellerId);
    }

}
