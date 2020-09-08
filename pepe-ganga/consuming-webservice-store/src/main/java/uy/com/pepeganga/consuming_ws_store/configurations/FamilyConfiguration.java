package uy.com.pepeganga.consuming_ws_store.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import uy.com.pepeganga.consuming_ws_store.services.FamilyService;

@Configuration
public class FamilyConfiguration {
	
	@Bean(name = "marshallerFamily")
	  public Jaxb2Marshaller marshaller() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    // this package must match the package in the <generatePackage> specified in
	    // pom.xml
	    marshaller.setContextPath("uy.com.pepeganga.consuming_ws_store.wsdl.families");
	    return marshaller;
	  }

	  @Bean	
	  public FamilyService familyClient() {
		  FamilyService client = new FamilyService();
		  client.setDefaultUri("http://201.217.140.35/agile");
	    client.setMarshaller(this.marshaller());
	    client.setUnmarshaller(this.marshaller());
	    return client;
	  }
}
