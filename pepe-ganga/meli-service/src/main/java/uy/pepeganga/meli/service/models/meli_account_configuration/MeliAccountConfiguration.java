package uy.pepeganga.meli.service.models.meli_account_configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliAccountConfiguration {
    private ConfigurationProperty configuration;

    public ConfigurationProperty getConfiguration() {
        return configuration;
    }
}
