package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;

@Entity
@Table(name = "meli_order_payment")
public class MeliOrderPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long paymentId;

    private double transactionAmount;

    private String  currencyId;

    private String status;

    public MeliOrderPayment() {
        // do nothing
    }

    public MeliOrderPayment(Long paymentId, double transactionAmount, String currencyId, String status) {
        this.paymentId = paymentId;
        this.transactionAmount = transactionAmount;
        this.currencyId = currencyId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
