package meli.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleTerms {
    private String id;

    public static final String SERIALIZED_NAME_VALUE_NAME = "value_name";
    @SerializedName(SERIALIZED_NAME_VALUE_NAME)
    private String valueName;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("value_name")
    public String getValueName() {
        return valueName;
    }

    @JsonProperty("value_name")
    public void setValueName(String valueName) {
        this.valueName = valueName;
    }
}
