package uy.com.pepeganga.consumingwsstore.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import uy.com.pepeganga.consumingwsstore.services.PurchaseOrdersService;

@Configuration
public class PurchaseOrdersConfiguration {
    @Bean(name = "marshallerPurchaseOrders")
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("uy.com.pepeganga.consumingwsstore.wsdl.purchaseOrders");
        return marshaller;
    }

    @Bean
    public PurchaseOrdersService purchaseOrdersClient() {
        PurchaseOrdersService client = new PurchaseOrdersService();
        client.setDefaultUri("http://201.217.140.35/WSPPGGFE");
        client.setMarshaller(this.marshaller());
        client.setUnmarshaller(this.marshaller());
        return client;
    }
}
