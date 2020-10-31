package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "meli_order_seller")
public class MeliOrderSeller implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;

    private String nickname;

    private String email;

    private String firstsName;

    private String lastName;

    public MeliOrderSeller() {
        // do nothing
    }

    public MeliOrderSeller(Long sellerId, String nickname, String email, String firstsName, String lastName) {
        this.sellerId = sellerId;
        this.nickname = nickname;
        this.email = email;
        this.firstsName = firstsName;
        this.lastName = lastName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstsName() {
        return firstsName;
    }

    public void setFirstsName(String firstsName) {
        this.firstsName = firstsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
