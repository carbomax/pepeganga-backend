package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliUserAccount {

    private String email;

    private String nickname;

    private String userType;

    private int points;

    private String siteId;

    private List<String> shippingModes;


    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("nickname")
    public String getNickname() {
        return nickname;
    }

    @JsonProperty("user_type")
    public String getUserType() {
        return userType;
    }

    @JsonProperty("points")
    public int getPoints() {
        return points;
    }

    @JsonProperty("site_id")
    public String getSiteId() {
        return siteId;
    }

    @JsonProperty("shipping_modes")
    public List<String> getShippingModes() {
        return shippingModes;
    }

}
