package uy.com.pepeganga.consumingwsstore.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "temp_brand")
public class TempBrand implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private Short id;

    @Column(name="description")
    private String description;

    @Column(name="marcaInUse")
    private String marcaInUse;

    public Short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMarcaInUse() {
        return marcaInUse;
    }

    public void setMarcaInUse(String marcaInUse) {
        this.marcaInUse = marcaInUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempBrand)) return false;
        TempBrand tempBrand = (TempBrand) o;
        return Objects.equals(id, tempBrand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
