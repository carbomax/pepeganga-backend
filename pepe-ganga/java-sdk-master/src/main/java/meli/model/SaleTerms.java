package meli.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaleTerms {
    public static final String SERIALIZED_NAME_ID = "id";
    @SerializedName(SERIALIZED_NAME_ID)
    private String id;

    public static final String SERIALIZED_NAME_VALUE_NAME = "value_name";
    @SerializedName(SERIALIZED_NAME_VALUE_NAME)
    private String valueName;
/*
    public static final String SERIALIZED_NAME_NAME = "name";
    @SerializedName(SERIALIZED_NAME_NAME)
    private String name;

    public static final String SERIALIZED_NAME_VALUE_ID = "value_id";
    @SerializedName(SERIALIZED_NAME_VALUE_ID)
    private String valueId;

    public static final String SERIALIZED_NAME_VALUE_STRUCT = "value_struct";
    @SerializedName(SERIALIZED_NAME_VALUE_STRUCT)
    private AttributesValueStruct valueStruct;

    public static final String SERIALIZED_NAME_VALUES = "values";
    @SerializedName(SERIALIZED_NAME_VALUES)
    private List<AttributesValues> values = null;
*/
    public SaleTerms(String id) {
        this.id = id;
    }

    /**
     * Get id
     * @return id
     **/
    @javax.annotation.Nullable
    @ApiModelProperty(example = "DATA_STORAGE_CAPACITY", value = "")

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public SaleTerms valueName(String valueName) {

        this.valueName = valueName;
        return this;
    }

    /**
     * Get valueName
     * @return valueName
     **/
    @javax.annotation.Nullable
    @ApiModelProperty(example = "8 GB", value = "")

    public String getValueName() {
        return valueName;
    }


    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

   /*
    public SaleTerms name(String name) {

        this.name = name;
        return this;
    }


    @javax.annotation.Nullable
    @ApiModelProperty(example = "Capacidad de almacenamiento de datos", value = "")

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public SaleTerms valueId(String valueId) {

        this.valueId = valueId;
        return this;
    }

    @javax.annotation.Nullable
    @ApiModelProperty(value = "")

    public String getValueId() {
        return valueId;
    }


    public void setValueId(String valueId) {
        this.valueId = valueId;
    }


    public SaleTerms valueStruct(AttributesValueStruct valueStruct) {

        this.valueStruct = valueStruct;
        return this;
    }


    @javax.annotation.Nullable
    @ApiModelProperty(value = "")

    public AttributesValueStruct getValueStruct() {
        return valueStruct;
    }


    public void setValueStruct(AttributesValueStruct valueStruct) {
        this.valueStruct = valueStruct;
    }


    public SaleTerms values(List<AttributesValues> values) {

        this.values = values;
        return this;
    }

    public SaleTerms addValuesItem(AttributesValues valuesItem) {
        if (this.values == null) {
            this.values = new ArrayList<AttributesValues>();
        }
        this.values.add(valuesItem);
        return this;
    }


    @javax.annotation.Nullable
    @ApiModelProperty(value = "")

    public List<AttributesValues> getValues() {
        return values;
    }


    public void setValues(List<AttributesValues> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleTerms)) return false;
        SaleTerms saleTerms = (SaleTerms) o;
        return Objects.equals(id, saleTerms.id) &&
                Objects.equals(name, saleTerms.name) &&
                Objects.equals(valueId, saleTerms.valueId) &&
                Objects.equals(valueName, saleTerms.valueName) &&
                Objects.equals(valueStruct, saleTerms.valueStruct) &&
                Objects.equals(values, saleTerms.values);
    }

     @Override
    public int hashCode() {
        return Objects.hash(id, name, valueId, valueName, valueStruct, values);
    }

     @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Attributes {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    valueId: ").append(toIndentedString(valueId)).append("\n");
        sb.append("    valueName: ").append(toIndentedString(valueName)).append("\n");
        sb.append("    valueStruct: ").append(toIndentedString(valueStruct)).append("\n");
        sb.append("    values: ").append(toIndentedString(values)).append("\n");
        sb.append("}");
        return sb.toString();
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleTerms)) return false;
        SaleTerms saleTerms = (SaleTerms) o;
        return Objects.equals(id, saleTerms.id) &&
               Objects.equals(valueName, saleTerms.valueName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valueName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Attributes {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    valueName: ").append(toIndentedString(valueName)).append("\n");
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