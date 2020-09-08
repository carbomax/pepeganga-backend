package uy.com.pepeganga.consuming_ws_store.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import uy.com.pepeganga.consuming_ws_store.services.TrademarkService;

@Configuration
public class TrademarkConfiguration {

	@Bean(name = "marshallerTrademark")
	  public Jaxb2Marshaller marshaller() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    // this package must match the package in the <generatePackage> specified in
	    // pom.xml
	    marshaller.setContextPath("uy.com.pepeganga.consuming_ws_store.wsdl.marcas");
	    return marshaller;
	  }

	  @Bean	
	  public TrademarkService trademarkClient() {
		  TrademarkService client = new TrademarkService();
		  client.setDefaultUri("http://201.217.140.35/agile");
	    client.setMarshaller(this.marshaller());
	    client.setUnmarshaller(this.marshaller());
	    return client;
	  }
}
