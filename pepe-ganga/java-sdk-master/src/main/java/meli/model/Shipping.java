package meli.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipping {
    public static final String SERIALIZED_NAME_MODE = "mode";
    @SerializedName(SERIALIZED_NAME_MODE)
    private String mode;

    public static final String SERIALIZED_NAME_LOCAL_PICK_UP = "local_pick_up";
    @SerializedName(SERIALIZED_NAME_LOCAL_PICK_UP)
    private Boolean localPickUp;

    public static final String SERIALIZED_NAME_FREE_SHIPPING = "free_shipping";
    @SerializedName(SERIALIZED_NAME_FREE_SHIPPING)
    private Boolean freeShipping;

    public static final String SERIALIZED_NAME_FREE_METHODS = "free_methods";
    @SerializedName(SERIALIZED_NAME_FREE_METHODS)
    private List<String> freeMethods;

    public static final String SERIALIZED_NAME_TAGS = "tags";
    @SerializedName(SERIALIZED_NAME_TAGS)
    private List<String> tags;

    @JsonProperty("mode")
    public String getMode() {
        return mode;
    }

    @JsonProperty("mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @JsonProperty("localPickUp")
    public Boolean getLocalPickUp() {
        return localPickUp;
    }

    @JsonProperty("localPickUp")
    public void setLocalPickUp(Boolean localPickUp) {
        this.localPickUp = localPickUp;
    }

    @JsonProperty("freeShipping")
    public Boolean getFreeShipping() {
        return freeShipping;
    }

    @JsonProperty("freeShipping")
    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    @JsonProperty("freeMethods")
    public List<String> getFreeMethods() {
        return freeMethods;
    }

    @JsonProperty("freeMethods")
    public void setFreeMethods(List<String> freeMethods) {
        this.freeMethods = freeMethods;
    }

    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /*
    public static final String SERIALIZED_NAME_DIMENSIONS = "dimensions";
    @SerializedName(SERIALIZED_NAME_DIMENSIONS)
    private Object dimensions;

    public static final String SERIALIZED_NAME_LOGISTIC_TYPE = "logistic_type";
    @SerializedName(SERIALIZED_NAME_LOGISTIC_TYPE)
    private String logisticType;

    public static final String SERIALIZED_NAME_STORE_PICK_UP = "store_pick_up";
    @SerializedName(SERIALIZED_NAME_STORE_PICK_UP)
    private Boolean storePickUp;
*/



}
