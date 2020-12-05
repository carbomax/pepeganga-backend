package uy.com.pepeganga.consumingwsstore.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tempfamily")
public class TempFamily implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private Short id;

    @Column(name="description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "tempfamily_id")
    private List<TempSubFamily> subfamilies = null;

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

    public List<TempSubFamily> getSubfamilies() {
        return subfamilies;
    }

    public void setSubfamilies(List<TempSubFamily> subfamilies) {
        this.subfamilies = subfamilies;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempFamily)) return false;
        TempFamily that = (TempFamily) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
