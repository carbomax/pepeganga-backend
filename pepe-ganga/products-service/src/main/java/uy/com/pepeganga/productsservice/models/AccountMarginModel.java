package uy.com.pepeganga.productsservice.models;

public class AccountMarginModel {

    String accountName;
    Integer idAccount;

    String nameMargin;
    Short idMargin;
    Integer typeMargin;
    Double valueMargin;

    public AccountMarginModel(String accountName, Integer idAccount, String nameMargin, Short idMargin, Integer typeMargin, Double valueMargin) {
        this.accountName = accountName;
        this.idAccount = idAccount;
        this.nameMargin = nameMargin;
        this.idMargin = idMargin;
        this.typeMargin = typeMargin;
        this.valueMargin = valueMargin;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getNameMargin() {
        return nameMargin;
    }

    public void setNameMargin(String nameMargin) {
        this.nameMargin = nameMargin;
    }

    public Short getIdMargin() {
        return idMargin;
    }

    public void setIdMargin(Short idMargin) {
        this.idMargin = idMargin;
    }

    public Integer getTypeMargin() {
        return typeMargin;
    }

    public void setTypeMargin(Integer typeMargin) {
        this.typeMargin = typeMargin;
    }

    public Double getValueMargin() {
        return valueMargin;
    }

    public void setValueMargin(Double valueMargin) {
        this.valueMargin = valueMargin;
    }
}
