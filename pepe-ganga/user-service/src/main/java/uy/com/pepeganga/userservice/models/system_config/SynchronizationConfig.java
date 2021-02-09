package uy.com.pepeganga.userservice.models.system_config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SynchronizationConfig {
    private String synchronization_time;
    private Integer stock_risk;

    @JsonProperty("synchronization_time")
    public String getSynchronization_time() {
        return synchronization_time;
    }

    public void setSynchronization_time(String synchronization_time) {
        this.synchronization_time = synchronization_time;
    }

    @JsonProperty("stock_risk")
    public Integer getStock_risk() {
        return stock_risk;
    }

    public void setStock_risk(Integer stock_risk) {
        this.stock_risk = stock_risk;
    }
}
