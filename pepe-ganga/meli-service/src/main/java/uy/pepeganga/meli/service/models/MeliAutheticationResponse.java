package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliAutheticationResponse implements Serializable {

    private String accesToken;

    private String tokenType;

    private int expiresIn;

    private String scope;

    private Long userId;

    private String refreshToken;

    @JsonProperty("access_token")
    public String getAccesToken() {
        return accesToken;
    }

    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    @JsonProperty("expires_in")
    public int getExpiresIn() {
        return expiresIn;
    }

    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    @JsonProperty("refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }
}
