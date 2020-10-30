package uy.pepeganga.meli.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import meli.model.AttributesValueStruct;
import meli.model.AttributesValues;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeModelResponse {

    private String id;
    private String name;
    private String valueId;
    private String valueName;
    private String attributeGroupId;
    private String attributeGroupName;

    @JsonProperty("id")
    public String getId() {
        return id;
    }
    @JsonProperty("name")
    public String getName() {
        return name;
    }
    @JsonProperty("value_id")
    public String getValueId() {
        return valueId;
    }
    @JsonProperty("value_name")
    public String getValueName() {
        return valueName;
    }
    @JsonProperty("attribute_group_id")
    public String getAttributeGroupId() {
        return attributeGroupId;
    }
    @JsonProperty("attribute_group_name")
    public String getAttributeGroupName() {
        return attributeGroupName;
    }
}


