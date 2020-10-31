package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "meli_order_buyer_billing_info")
public class MeliOrderBuyerBillingInfo implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docType;

    private Long docNumber;

    public MeliOrderBuyerBillingInfo() {
        // do nothing
    }

    public MeliOrderBuyerBillingInfo(String docType, Long docNumber) {
        this.docType = docType;
        this.docNumber = docNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Long getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(Long docNumber) {
        this.docNumber = docNumber;
    }
}
