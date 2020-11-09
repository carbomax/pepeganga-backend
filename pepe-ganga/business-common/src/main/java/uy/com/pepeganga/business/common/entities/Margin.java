package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "margin")
public class Margin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    private String name;

    private int type;

    private double value;

    @Column(name = "marketplace_id")
    private Short marketplaceId;

    @Transient
    private Marketplace marketplace;

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Short getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(Short marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Margin margin = (Margin) o;

        return id.equals(margin.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
