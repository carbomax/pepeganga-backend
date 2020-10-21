package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliUserAccount {

    private String email;

    private String nickname;

    private String userType;

    private int points;

    private String siteId;

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
}
