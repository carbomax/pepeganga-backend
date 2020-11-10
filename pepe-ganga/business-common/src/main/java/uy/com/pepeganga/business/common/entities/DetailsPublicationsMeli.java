package uy.com.pepeganga.business.common.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "detailspublicationsmeli")
public class DetailsPublicationsMeli implements Serializable {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = -4061160109561062557L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="mlpublication", nullable = true)
    private Integer idMLPublication;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "details_publication")
    private List<ImagePublicationMeli> images;

    @Column(name="account_meli")
    private Integer accountMeli;

    @Column(name="id_publication_meli")
    private String idPublicationMeli;

    @Column(name = "user_id")
    private Long userId;

    @Column(name="title")
    private String title;

    @Column(name="sku")
    private String sku;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name="permalink")
    private String permalink;

    @Column(name="category_meli")
    private String categoryMeli;

    @Column(name="price_publication")
    private Integer pricePublication;

    @Column(name="price_cost_uyu")
    private double priceCostUYU;

    @Column(name="price_cost_usd")
    private double priceCostUSD;

    @Column(name="price_edit_product")
    private double priceEditProduct;

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

    private Integer saleStatus = 0;

    private Integer deleted = 0;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMlPublication() {
        return idMLPublication;
    }

    public void setMlPublication(Integer idMLPublication) {
        this.idMLPublication = idMLPublication;
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

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPriceCostUYU() {
        return priceCostUYU;
    }

    public void setPriceCostUYU(double priceCostUYU) {
        this.priceCostUYU = priceCostUYU;
    }

    public double getPriceCostUSD() {
        return priceCostUSD;
    }

    public void setPriceCostUSD(double priceCostUSD) {
        this.priceCostUSD = priceCostUSD;
    }

    public double getPriceEditProduct() {
        return priceEditProduct;
    }

    public void setPriceEditProduct(double priceEditProduct) {
        this.priceEditProduct = priceEditProduct;
    }

    public List<ImagePublicationMeli> getImages() {
        return images;
    }

    public void setImages(List<ImagePublicationMeli> images) {
        this.images = images;
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
