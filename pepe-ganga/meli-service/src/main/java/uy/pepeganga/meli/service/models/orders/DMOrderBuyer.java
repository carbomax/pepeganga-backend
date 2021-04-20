package uy.pepeganga.meli.service.models.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DMOrderBuyer {

    private Long id;

    private String nickname;

    private String email;

    private String firstName;

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
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }


}
