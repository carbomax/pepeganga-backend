package uy.com.pepeganga.business.common.models;

import java.io.Serializable;

public class AuthAddInformationClaim implements Serializable {

    private String firstName;

    private String lastName;

    private Integer profileId;

    public AuthAddInformationClaim() {
    }

    public AuthAddInformationClaim(String firstName, String lastName, Integer profileId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileId = profileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }
}
