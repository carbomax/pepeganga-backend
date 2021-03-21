package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;

@Entity
@Table(name = "meli_category_me2")
public class MeliCategoryME2 {

    @Id
    private String Id;
    private String name = "";
    private String path_from_root = "";

    public MeliCategoryME2() {
    }

    public MeliCategoryME2(String id, String name, String path_from_root) {
        Id = id;
        this.name = name;
        this.path_from_root = path_from_root;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath_from_root() {
        return path_from_root;
    }

    public void setPath_from_root(String path_from_root) {
        this.path_from_root = path_from_root;
    }
}
