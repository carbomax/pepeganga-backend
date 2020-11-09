package uy.pepeganga.meli.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiClient;
import meli_marketplace_lib.OAuth20Api;
import meli_marketplace_lib.RestClientApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeliConfig {

    @Value("${meli.api.url}")
    private String basePathUrl;

    @Bean
    OAuth20Api auth20Api() {
        return new OAuth20Api(defaultClient());
    }

    @Bean(name = "defaultClientUy")
    ApiClient defaultClient() {
        ApiClient defaultClient = meli.Configuration.getDefaultApiClient();
        defaultClient.setBasePath(basePathUrl);
        return defaultClient;
    }

    @Bean(name = "restClientApiUy")
    RestClientApi restClientApi() {
        return new RestClientApi(defaultClient());
    }

    @Bean
    ObjectMapper objectMapper() { return new ObjectMapper(); }
}
