package uy.com.pepeganga.business.common.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PurchaseNotification implements Serializable {

    private long orderId;

    // 0 - Not processed, 1 - Processed
    private int status;

}
