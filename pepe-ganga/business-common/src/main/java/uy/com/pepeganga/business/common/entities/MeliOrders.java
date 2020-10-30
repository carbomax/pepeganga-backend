package uy.com.pepeganga.business.common.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "meli_orders")
public class MeliOrders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    private String status;

    private String dateCreated;

    private String dateClosed;

    private double totalAmount;

    private String currencyId;

    private Long shippingId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private MeliOrderSeller seller;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private MeliOrderBuyer buyer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "meli_orders_id")
    private List<MeliOrderPayment> payments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "meli_orders_id")
    private List<MeliOrderItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getShippingId() {
        return shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    public MeliOrderSeller getSeller() {
        return seller;
    }

    public void setSeller(MeliOrderSeller seller) {
        this.seller = seller;
    }

    public MeliOrderBuyer getBuyer() {
        return buyer;
    }

    public void setBuyer(MeliOrderBuyer buyer) {
        this.buyer = buyer;
    }

    public List<MeliOrderPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<MeliOrderPayment> payments) {
        this.payments = payments;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public List<MeliOrderItem> getItems() {
        return items;
    }

    public void setItems(List<MeliOrderItem> items) {
        this.items = items;
    }


}
