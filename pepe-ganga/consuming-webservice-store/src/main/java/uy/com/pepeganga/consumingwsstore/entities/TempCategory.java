package uy.com.pepeganga.consumingwsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tempcategory")
public class TempCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private Short id;

    @Column(name="description")
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempCategory)) return false;
        TempCategory that = (TempCategory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
