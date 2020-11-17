package meli.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Objects;

public class Shipping {
    public static final String SERIALIZED_NAME_MODE = "mode";
    @SerializedName(SERIALIZED_NAME_MODE)
    String mode;

    public static final String SERIALIZED_NAME_LOCAL_PICK_UP = "local_pick_up";
    @SerializedName(SERIALIZED_NAME_LOCAL_PICK_UP)
    Boolean localPickUp;

    public static final String SERIALIZED_NAME_FREE_SHIPPING = "free_shipping";
    @SerializedName(SERIALIZED_NAME_FREE_SHIPPING)
    Boolean freeShipping;

    public static final String SERIALIZED_NAME_FREE_METHODS = "free_methods";
    @SerializedName(SERIALIZED_NAME_FREE_METHODS)
    List<String> freeMethods;
/*
    public static final String SERIALIZED_NAME_DIMENSIONS = "dimensions";
    @SerializedName(SERIALIZED_NAME_DIMENSIONS)
    Object dimensions;

    public static final String SERIALIZED_NAME_TAGS = "tags";
    @SerializedName(SERIALIZED_NAME_TAGS)
    List<String> tags;

    public static final String SERIALIZED_NAME_LOGISTIC_TYPE = "logistic_type";
    @SerializedName(SERIALIZED_NAME_LOGISTIC_TYPE)
    String logisticType;

    public static final String SERIALIZED_NAME_STORE_PICK_UP = "store_pick_up";
    @SerializedName(SERIALIZED_NAME_STORE_PICK_UP)
    Boolean storePickUp;
*/

    public Shipping(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Get valueId
     * @return valueId
     **/
    @javax.annotation.Nullable
    @ApiModelProperty(value = "")
    public Shipping localPickUp(Boolean localPickUp) {

        this.localPickUp = localPickUp;
        return this;
    }

    public Boolean getLocalPickUp() {
        return localPickUp;
    }

    public void setLocalPickUp(Boolean localPickUp) {
        this.localPickUp = localPickUp;
    }

    /**
     * Get valueId
     * @return valueId
     **/
    @javax.annotation.Nullable
    @ApiModelProperty(value = "")
    public Shipping freeShipping(Boolean freeShipping) {

        this.freeShipping = freeShipping;
        return this;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    /**
     * Get valueId
     * @return valueId
     **/
    @javax.annotation.Nullable
    @ApiModelProperty(value = "")
    public Shipping freeMethods(List<String> freeMethods) {

        this.freeMethods = freeMethods;
        return this;
    }

    public List<String> getFreeMethods() {
        return freeMethods;
    }

    public void setFreeMethods(List<String> freeMethods) {
        this.freeMethods = freeMethods;
    }

    /**
     * Get valueId
     * @return valueId
     **/
  /*  @javax.annotation.Nullable
    @ApiModelProperty(value = "")
    public Shipping dimensions(Object dimensions) {

        this.dimensions = dimensions;
        return this;
    }

    public Object getDimensions() {
        return dimensions;
    }

    public void setDimensions(Object dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Get valueId
     * @return valueId
     **/
   /* @javax.annotation.Nullable
    @ApiModelProperty(value = "")
    public Shipping tags(List<String> tags) {

        this.tags = tags;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Get valueId
     * @return valueId
     **/
  /*  @javax.annotation.Nullable
    @ApiModelProperty(value = "")
    public Shipping logisticType(String logisticType) {

        this.logisticType = logisticType;
        return this;
    }

    public String getLogisticType() {
        return logisticType;
    }

    public void setLogisticType(String logisticType) {
        this.logisticType = logisticType;
    }

    /**
     * Get valueId
     * @return valueId
     **/
 /*   @javax.annotation.Nullable
    @ApiModelProperty(value = "")
    public Shipping storePickUp(Boolean storePickUp) {

        this.storePickUp = storePickUp;
        return this;
    }

    public Boolean getStorePickUp() {
        return storePickUp;
    }

    public void setStorePickUp(Boolean storePickUp) {
        this.storePickUp = storePickUp;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shipping)) return false;
        Shipping shipping = (Shipping) o;
        return Objects.equals(mode, shipping.mode) &&
                Objects.equals(localPickUp, shipping.localPickUp) &&
                Objects.equals(freeShipping, shipping.freeShipping) &&
                Objects.equals(freeMethods, shipping.freeMethods);
               /* Objects.equals(dimensions, shipping.dimensions) &&
                Objects.equals(tags, shipping.tags) &&
                Objects.equals(logisticType, shipping.logisticType) &&
                Objects.equals(storePickUp, shipping.storePickUp);*/
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, localPickUp, freeShipping, freeMethods/*, dimensions, tags, logisticType, storePickUp*/);
    }

    @Override
    public String toString() {
        return "shipping{" +
                "mode='" + mode + '\'' +
                ", localPickUp=" + localPickUp +
                ", freeShipping=" + freeShipping +
                ", freeMethods=" + freeMethods + '}';
                /* ", dimensions=" + dimensions +
                ", tags=" + tags +
                ", logisticType='" + logisticType + '\'' +
                ", storePickUp=" + storePickUp +
                '}';*/
    }
}
