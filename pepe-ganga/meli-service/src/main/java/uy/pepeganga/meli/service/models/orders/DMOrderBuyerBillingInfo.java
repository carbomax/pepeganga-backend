package uy.pepeganga.meli.service.models.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DMOrderBuyerBillingInfo {


    private Long id;

    private String docType;

    private Long docNumber;


    @JsonProperty("doc_type")
    public String getDocType() {
        return docType;
    }

    @JsonProperty("doc_number")
    public Long getDocNumber() {
        return docNumber;
    }


}
