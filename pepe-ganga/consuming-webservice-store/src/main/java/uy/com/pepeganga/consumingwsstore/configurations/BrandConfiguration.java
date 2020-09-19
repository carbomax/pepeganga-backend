package uy.com.pepeganga.consumingwsstore.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import uy.com.pepeganga.consumingwsstore.services.BrandRequestService;

@Configuration
public class BrandConfiguration {

	@Bean(name = "marshallerBrand")
	  public Jaxb2Marshaller marshaller() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    // this package must match the package in the <generatePackage> specified in
	    // pom.xml
	    marshaller.setContextPath("uy.com.pepeganga.consumingwsstore.wsdl.marcas");
	    return marshaller;
	  }

	  @Bean	
	  public BrandRequestService brandClient() {
		  BrandRequestService client = new BrandRequestService();
		  client.setDefaultUri("http://201.217.140.35/agile");
	    client.setMarshaller(this.marshaller());
	    client.setUnmarshaller(this.marshaller());
	    return client;
	  }
}
