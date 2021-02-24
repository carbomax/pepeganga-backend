package uy.pepeganga.meli.service.models.meli_account_configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryRequest implements Serializable {
    private String query;

    public QueryRequest(Long accountId) {
        this.query = String.format("{ configuration(user_id: %d){ adoption" +
                "{ service_id status creation_date last_update penalty_status recover_date delivery_window } " +
                "address{ id address_line zip_code city{ id name } } capacity{ availables selected current_count }" +
                " cutoff{ availables{ value unit } selected{ value unit } } training_time{ activation_date offset" +
                "{ value unit } } zones{ id label neighborhoods price{ cents currency_id decimal_separator fraction symbol }" +
                " is_mandatory selected } }}", accountId);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
