package uy.com.pepeganga.consumingwsstore.Entities;

import java.io.Serializable;
import java.util.Objects;

public class TempSubFamilyPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private Short id;
    private String description;


    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempSubFamilyPK)) return false;
        TempSubFamilyPK that = (TempSubFamilyPK) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
