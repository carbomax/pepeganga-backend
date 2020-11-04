package uy.pepeganga.meli.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import uy.pepeganga.meli.service.models.orders.DMOrderItem;
import uy.pepeganga.meli.service.models.orders.DMOrderItems;
import uy.pepeganga.meli.service.repository.*;
import uy.pepeganga.meli.service.utils.ApiResources;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            this.updateItemPublished(order.getOrderItems());
        }

        // Payments in order
        if (order.getPayments() != null) {
            List<MeliOrderPayment> payments = new ArrayList<>();
            order.getPayments().forEach(dmOrderPayment -> payments.add(new MeliOrderPayment(dmOrderPayment.getId(), dmOrderPayment.getTransactionAmount(), dmOrderPayment.getCurrencyId(), dmOrderPayment.getStatus())));
            orders.setPayments(payments);
        }

        if(order.getDateCreated() != null){
            Date date;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                date = formatter.parse(order.getDateCreated());
            } catch (ParseException e) {
                logger.info("");
                date = new Date();
            }

            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTime(date);
            String dateOrder = String.format("%d%d%d", Calendar.YEAR, Calendar.MONTH+1, Calendar.DAY_OF_MONTH);
            orders.setBusinessDateCreated(Long.getLong(dateOrder));
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

}
