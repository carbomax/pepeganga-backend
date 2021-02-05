package uy.com.pepeganga.business.common.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class OrderDto implements Serializable   {

private long orderId;

private long date;

private long sellerId;

private String sellerName;

private String email;

private String address;

private String location;

private String department;

private long rut;

private long ci;

private int coin;

private String observation;

private List<MeliOrderItemDto> items;

}
