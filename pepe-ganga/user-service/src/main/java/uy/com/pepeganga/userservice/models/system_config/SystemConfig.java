package uy.com.pepeganga.userservice.models.system_config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemConfig {
    private String creation_date;
    private PublicationConfig publication_config;
    private SynchronizationConfig synchronization_config;

    @JsonProperty("creation_date")
    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    @JsonProperty("publication_config")
    public PublicationConfig getPublication_config() {
        return publication_config;
    }

    public void setPublication_config(PublicationConfig publication_config) {
        this.publication_config = publication_config;
    }

    @JsonProperty("synchronization_config")
    public SynchronizationConfig getSynchronization_config() {
        return synchronization_config;
    }

    public void setSynchronization_config(SynchronizationConfig synchronization_config) {
        this.synchronization_config = synchronization_config;
    }
}
