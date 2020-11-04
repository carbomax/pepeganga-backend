package meli.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class UpdateTitleAndPriceRequest {
    public static final String SERIALIZED_NAME_ID = "title";
    @SerializedName(SERIALIZED_NAME_ID)
    private String title;

    public static final String SERIALIZED_NAME_PRICE = "price";
    @SerializedName(SERIALIZED_NAME_PRICE)
    private Integer price;

    public UpdateTitleAndPriceRequest title(String title) {

        this.title = title;
        return this;
    }

    /**
     * Get title
     * @return title
     **/
    @ApiModelProperty(example = "Item de test - No Ofertar", required = true, value = "")

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public UpdateTitleAndPriceRequest price(Integer price) {

        this.price = price;
        return this;
    }

    /**
     * Get price
     * @return price
     **/
    @ApiModelProperty(example = "350", required = true, value = "")

    public Integer getPrice() {
        return price;
    }


    public void setPrice(Integer price) {
        this.price = price;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateTitleAndPriceRequest item = (UpdateTitleAndPriceRequest) o;
        return Objects.equals(this.title, item.title) &&
                Objects.equals(this.price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateTitleAndPriceRequest {\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }



}
