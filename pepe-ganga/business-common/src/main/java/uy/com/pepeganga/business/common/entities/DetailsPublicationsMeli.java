package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "detailspublicationsmeli")
public class DetailsPublicationsMeli implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mlpublication", nullable = false)
    private MercadoLibrePublications mlPublication;

    @Column(name="account_meli")
    private Integer accountMeli;

    @Column(name="id_publication_meli")
    private String idPublicationMeli;

    @Column(name="title")
    private String title;

    @Column(name="permalink")
    private String permalink;

    @Column(name="category_meli")
    private String categoryMeli;

    @Column(name="price_publication")
    private Integer pricePublication;

    @Column(name="warranty_type")
    private String warrantyType;

    @Column(name="warranty_time")
    private String warrantyTime;

    @Column(name="margin")
    private Short margin;

    @Column(name="last_upgrade")
    private String lastUpgrade;

    @Column(name="status")
    private String status;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public MercadoLibrePublications getMlPublication() {
        return mlPublication;
    }

    public void setMlPublication(MercadoLibrePublications mlPublication) {
        this.mlPublication = mlPublication;
    }

    public Integer getAccountMeli() {
        return accountMeli;
    }

    public void setAccountMeli(Integer accountMeli) {
        this.accountMeli = accountMeli;
    }

    public String getIdPublicationMeli() {
        return idPublicationMeli;
    }

    public void setIdPublicationMeli(String idPublicationMeli) {
        this.idPublicationMeli = idPublicationMeli;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getCategoryMeli() {
        return categoryMeli;
    }

    public void setCategoryMeli(String categoryMeli) {
        this.categoryMeli = categoryMeli;
    }

    public Integer getPricePublication() {
        return pricePublication;
    }

    public void setPricePublication(Integer pricePublication) {
        this.pricePublication = pricePublication;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public String getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(String warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    public Short getMargin() {
        return margin;
    }

    public void setMargin(Short margin) {
        this.margin = margin;
    }

    public String getLastUpgrade() {
        return lastUpgrade;
    }

    public void setLastUpgrade(String lastUpgrade) {
        this.lastUpgrade = lastUpgrade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailsPublicationsMeli)) return false;
        DetailsPublicationsMeli that = (DetailsPublicationsMeli) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
