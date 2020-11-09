package uy.pepeganga.meli.service.models.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DMOrderSeller {

    private Long id;

    private String nickname;

    private String email;

    private String firtsName;

    private String lastName;


    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("nickname")
    public String getNickname() {
        return nickname;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("first_name")
    public String getFirtsName() {
        return firtsName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }


}
