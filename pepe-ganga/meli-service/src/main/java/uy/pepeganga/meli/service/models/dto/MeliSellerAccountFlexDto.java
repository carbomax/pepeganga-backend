package uy.pepeganga.meli.service.models.dto;

import java.io.Serializable;

public class MeliSellerAccountFlexDto implements Serializable {

    private int id;

    private String profileName;

    private String accountName;

    private boolean enabledFlex;

    public MeliSellerAccountFlexDto() {
    }

    public MeliSellerAccountFlexDto(int id, String profileName, String accountName, boolean enabledFlex) {
        this.id = id;
        this.profileName = profileName;
        this.accountName = accountName;
        this.enabledFlex = enabledFlex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public boolean isEnabledFlex() {
        return enabledFlex;
    }

    public void setEnabledFlex(boolean enabledFlex) {
        this.enabledFlex = enabledFlex;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
