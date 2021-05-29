package uy.com.pepeganga.business.common.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicationsMeliDto {
    private String idMLPublication;
    private String title;
    private String permalink;
    private Integer pricePublication;
    private String lastUpgrade;
    private String status;
    private String sku;
    private String flex;
    private String accountBusinessName;
    private Long currentStock;
    private Integer saleStatus;
}
