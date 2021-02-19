package uy.pepeganga.meli.service.models.meli_account_configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationProperty {

    private AdoptionProperty adoption;

    public AdoptionProperty getAdoption() {
        return adoption;
    }
}
