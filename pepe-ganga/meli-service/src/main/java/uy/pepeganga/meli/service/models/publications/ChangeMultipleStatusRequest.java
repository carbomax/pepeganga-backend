package uy.pepeganga.meli.service.models.publications;

import java.util.List;

public class ChangeMultipleStatusRequest {
    private Integer accountId;
    private List<String> publicationsIds;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public List<String> getPublicationsIds() {
        return publicationsIds;
    }

    public void setPublicationsIds(List<String> publicationsIds) {
        this.publicationsIds = publicationsIds;
    }
}
