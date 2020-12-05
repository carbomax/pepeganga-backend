package uy.com.pepeganga.consumingwsstore.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(TempSubFamilyPK.class)
@Table(name = "tempsubfamily")
public class TempSubFamily implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private Short id;

    @Id
    @Column(name="description")
    private String description;


    public Short getId() {
        return this.id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempSubFamily)) return false;
        TempSubFamily that = (TempSubFamily) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
