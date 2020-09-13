package uy.com.pepeganga.consuming_ws_store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import uy.com.pepeganga.consuming_ws_store.models.ItemModelResponse;

@EnableDiscoveryClient
@SpringBootApplication
public class ConsumingWebserviceStoreApplication {

	private List<ItemModelResponse> list;
	
	public List<ItemModelResponse> getList() {
		return list;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumingWebserviceStoreApplication.class, args);
	}

	public String generateRandomString(int number)
	{
		char[] chars = "abcdefghijklmnopqrstuvwxyz1598".toCharArray(); 
		StringBuilder sb = new StringBuilder(number); 
		Random random = new Random(); 
		for (int i = 0; i < 20; i++) { 
		    char c = chars[random.nextInt(chars.length)]; 
		    sb.append(c); 
		}
		return sb.toString();
	}
	
	 @Bean
	    public CommandLineRunner demoData() {
		
	 
	        return args -> { 
	        	this.list = new ArrayList<ItemModelResponse>(); 
	        	 Map<String, String> mapa = new HashMap<String, String>();
	    		 mapa.put("Juguete", "juguerte");
	    		 mapa.put("carro", "carro");
	    		 
	    		 List<String> fotos = new ArrayList<String>();
	    		 fotos.add("http://201.217.140.35/sisvend/fotos/HC0828.jpg");
	    		 fotos.add("http://201.217.140.35/sisvend/fotos/HC0828-1.jpg");
	    		 fotos.add("http://201.217.140.35/sisvend/fotos/HC0767.jpg");		 
	    		 
	    		 
	    		 ItemModelResponse item = new ItemModelResponse();
	    		 for (int i = 0; i < 15; i++) {
	    			item.setArtCantUnidades((short) Math.random());
	    			item.setArtCodPro(generateRandomString(5));
	    			item.setSku(generateRandomString(5));
	    			item.setArtDescripML(generateRandomString(10));
	    			item.setArtFotosList(fotos);
	    			item.setPrecioPesos(Math.random());
	    			item.setCategoriasMap(mapa);
	    			this.list.add(item);
	    		}
	        };	
	 }
}
