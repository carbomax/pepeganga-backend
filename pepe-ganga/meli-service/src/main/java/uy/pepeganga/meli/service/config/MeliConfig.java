package uy.pepeganga.meli.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiClient;
import meli_marketplace_lib.OAuth20Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeliConfig {


    @Bean
    OAuth20Api auth20Api(){
        ApiClient defaultClient = meli.Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.mercadolibre.com");
        return new OAuth20Api(defaultClient);
    }


    @Bean
    ObjectMapper objectMapper (){
        return new ObjectMapper();
    }


}
