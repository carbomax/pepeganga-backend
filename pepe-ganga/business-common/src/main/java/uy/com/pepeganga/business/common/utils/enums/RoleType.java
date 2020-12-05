package uy.com.pepeganga.business.common.utils.enums;

public enum RoleType {

    ADMIN("ROLE_ADMIN"),
    SELLER("ROLE_SELLER"),
    INVITED("ROLE_INVITED"),
    OPERATOR("ROLE_OPERATOR");

    private String businessRole;


    RoleType(String businessRole) {
        this.businessRole = businessRole;
    }

    public String getBusinessRole() {
        return businessRole;
    }

    public void setBusinessRole(String businessRole) {
        this.businessRole = businessRole;
    }
}
