package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "imagedetailpublications")
public class ImageDetailPublications implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="photos")
    private String photos;

    @Column(name="order_in_list")
    private int order;

    @Column(name="title")
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageDetailPublications)) return false;
        ImageDetailPublications that = (ImageDetailPublications) o;
        return id.equals(that.id) &&
                photos.equals(that.photos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photos);
    }
}
