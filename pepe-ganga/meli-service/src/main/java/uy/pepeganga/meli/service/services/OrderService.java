package uy.pepeganga.meli.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.*;
import uy.com.pepeganga.business.common.utils.enums.NotificationTopic;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.orders.DMOrder;
import uy.pepeganga.meli.service.repository.*;
import uy.pepeganga.meli.service.utils.ApiResources;

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

    @Override
    @Transactional
    public void schedulingOrdersV2() {

        List<Notification> notifications = notificationRepository.findByTopic(NotificationTopic.ORDERS_V2.getTopicName().trim());
        if (notifications != null) {
            this.processingOrdersNotifications(notifications);
        } else logger.info("There are no notifications to process");
    }


    private void processingOrdersNotifications(List<Notification> notifications) {
        for (Notification notification : notifications) {
            logger.info("Processing notification: topic: {}, applicationId: {}, userId: {}", notification.getTopic(), notification.getApplicationId(), notification.getUserId());
            try {
                // check if exist a value for account
                if (sellerAccountRepository.existsByUserId(notification.getUserId())) {

                    DMOrder order = mapper.convertValue(apiService.getOrderByNotificationResource(notification.getResource(),
                            sellerAccountRepository.findByUserId(notification.getUserId()).getAccessToken()), DMOrder.class);
                    if (ordersRepository.existsByOrderId(String.valueOf(order.getId()))) {
                        // update order
                        MeliOrders orderToUpdate = ordersRepository.findByOrderId(String.valueOf(order.getId()));
                        orderToUpdate.setStatus(order.getStatus());
                        orderToUpdate.setPayments(order.getPayments().stream().map(dmOrderPayment -> new MeliOrderPayment(dmOrderPayment.getId(), dmOrderPayment.getTransactionAmount(),
                                dmOrderPayment.getCurrencyId(), dmOrderPayment.getStatus())
                        ).collect(Collectors.toList()));
                        ordersRepository.save(orderToUpdate);
                        notificationRepository.deleteById(notification.getId());
                    } else {
                        // create order
                        this.createMeliOrder(order);
                        notificationRepository.deleteById(notification.getId());
                    }
                } else {
                    // doing something
                    logger.info("No exist a account with id: {}", notification.getUserId());
                    notificationRepository.deleteById(notification.getId());
                }

            } catch (ApiException e) {
                logger.error("Error meli api request: code {}, body: {}", e.getCode(), e.getResponseBody());
                if (notification.getBusinessAttempts() > 10) {
                    notificationRepository.deleteById(notification.getId());
                }
            }
        }
    }

    private void createMeliOrder(DMOrder order) {
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
        }

        // Payments in order
        if (order.getPayments() != null) {
            List<MeliOrderPayment> payments = new ArrayList<>();
            order.getPayments().forEach(dmOrderPayment -> payments.add(new MeliOrderPayment(dmOrderPayment.getId(), dmOrderPayment.getTransactionAmount(), dmOrderPayment.getCurrencyId(), dmOrderPayment.getStatus())));
            orders.setPayments(payments);
        }

        // private order values
        orders.setCurrencyId(order.getCurrencyId());
        orders.setDateClosed(order.getDateClosed());
        orders.setDateCreated(order.getDateCreated());
        orders.setOrderId(String.valueOf(order.getId()));
        orders.setShippingId(order.getShipping().getId());
        orders.setStatus(order.getStatus());
        orders.setTotalAmount(order.getTotalAmount());
        ordersRepository.save(orders);
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
    public List<MeliOrders> getAllOrdersByProfile(Integer profileId, int page, int size) {
        Optional<Profile> profile = profileRepository.findById(profileId);

        if (profile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s", profile.get().getId()));
        } else {
            List<String> accounts = new ArrayList<>();
            profile.get().getSellerAccounts().forEach(sellerAccount -> {
                if(sellerAccount.getUserId() != null ){
                    accounts.add(String.valueOf(sellerAccount.getUserId()));
                }
            });

            return ordersRepository.findBySellerId(accounts, PageRequest.of(page, size));

        }

    }
}
