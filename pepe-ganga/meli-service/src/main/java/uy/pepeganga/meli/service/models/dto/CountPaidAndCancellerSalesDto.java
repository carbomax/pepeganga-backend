package uy.pepeganga.meli.service.models.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CountPaidAndCancellerSalesDto implements Serializable {

    private Long paid;

    private Long cancelled;

}
