package uy.pepeganga.meli.service.models.meli_account_configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdoptionProperty {
    private String creation_date;
    private String delivery_window;
    private String last_update;
    private String penalty_status;
    private String recover_date;
    private Integer service_id;
    private String status;

    public String getCreation_date() {
        return creation_date;
    }

    public String getDelivery_window() {
        return delivery_window;
    }

    public String getLast_update() {
        return last_update;
    }

    public String getPenalty_status() {
        return penalty_status;
    }

    public String getRecover_date() {
        return recover_date;
    }

    public Integer getService_id() {
        return service_id;
    }

    public String getStatus() {
        return status;
    }
}
