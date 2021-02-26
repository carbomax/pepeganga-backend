package uy.com.pepeganga.business.common.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
@Builder
public class MeliOrderItemDto implements Serializable {

 private String sellerSKU;

 private String description;

 private int quantity;

 private double price;

 private String observations;

}
