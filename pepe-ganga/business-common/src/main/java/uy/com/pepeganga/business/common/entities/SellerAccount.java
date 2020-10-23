package uy.com.pepeganga.business.common.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import uy.com.pepeganga.business.common.utils.enums.MarginType;
import uy.com.pepeganga.business.common.utils.enums.MarketplaceType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "selleraccount")
public class SellerAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_description")
    private String businessDescription;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "expires_in")
    private Integer expiresIn;

    private String scope;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "refresh_token")
    private String refreshToken;

    private String nickname;

    private String email;

    private String permalink;

    private Integer status = 0;

    private String userType;

    private Integer points = 0;

    private String siteId;

    private Short marketplaceId = MarketplaceType.MERCADOLIBRE.getId();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    @JsonBackReference
    private Profile profile;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Short getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(Short marketplaceId) {
        this.marketplaceId = marketplaceId;
    }
}
