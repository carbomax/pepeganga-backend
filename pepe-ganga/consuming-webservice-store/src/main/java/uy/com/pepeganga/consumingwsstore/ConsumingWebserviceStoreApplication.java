package uy.com.pepeganga.consumingwsstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import uy.com.pepeganga.business.common.entities.*;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EnableDiscoveryClient
@SpringBootApplication
@EntityScan({"uy.com.pepeganga.business.common.entities"})
public class ConsumingWebserviceStoreApplication {

	private List<Item> list;	
	public List<Item> getList() {
		return list;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ConsumingWebserviceStoreApplication.class, args);
	}
/*Estos son metodos auxiliares para la generacion de Items*/
	
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
	        	this.list = new ArrayList<Item>();	        	
	    		 
	    		List<String> fotos = new ArrayList<String>();
	    		fotos.add("http://201.217.140.35/sisvend/fotos/HC0828.jpg");
	    	    fotos.add("http://201.217.140.35/sisvend/fotos/HC0828-1.jpg");
	    		fotos.add("http://201.217.140.35/sisvend/fotos/HC0767.jpg");	    		 	    		 
	    		List<Image> imageList = new ArrayList<Image>();
	    		
	    		 for (String variable : fotos)
	    			 imageList.add(ConvertModels.createImageInstanceWithoutId(variable)); 
	    		 
	    		 // Item 1
	    		Item item = new Item();
	    		item.setSku("HC0190");
	    		item.setArtDescripCatalogo("Cepillo neumático mediano, Kelmax");
	    		item.setArtMedida("6x21x4cm");
	    		item.setArtEspecificaciones(null);
	    		item.setFamily(ConvertModels.createFamilyInstance((short) 5));
	    		item.setCantidadPorCaja("240");
	    		item.setPrecioPesos(59);
	    		item.setPrecioDolares(1.25);
	    		item.setFuturo("N");
	    		item.setNuevo("N");
	    		item.setOferta("N");
	    		item.setImage(imageList);
	    		item.setCategories(new ArrayList<Category>());
	    		item.setStockActual(2139);
	    		item.setArtCantUnidades((short) 0);
	    		item.setArtPreUniPesos(0);
	    		item.setArtPreUniDolares(0);
	    		item.setBrand(ConvertModels.createBrandInstance((short) 27));
	    		item.setArtMostrarEnWeb("S");
	    		item.setArtVendibleMercadoLibre("");
	    		item.setArtCodPro("");
	    		item.setArtNombreML("");
	    		item.setArtDescripML("");
	    		item.setMedidaEmpaque("");
	    		item.setCapacidad("");
	    		item.setTalle("");
	    		
	    		this.list.add(item);
	    		
	    		 // Item 2
	    		item = new Item();
	    		item.setSku("HC0285");
	    		item.setArtDescripCatalogo("Cadena para chupete, en blister, varios colores");
	    		item.setArtMedida("18x10cm");
	    		item.setArtEspecificaciones("");
	    		item.setFamily(ConvertModels.createFamilyInstance((short) 5));
	    		item.setCantidadPorCaja("240");
	    		item.setPrecioPesos(33);
	    		item.setPrecioDolares(0.7);
	    		item.setFuturo("N");
	    		item.setNuevo("N");
	    		item.setOferta("N");
	    		item.setImage(imageList);
	    		
	    		Category category = new Category();
	    		category.setId((short) 48);
	    		category.setDescription("Bebé");
	    		List<Category> catList = new ArrayList<Category>();
	    		catList.add(category); 
	    		
	    		item.setCategories(catList);
	    		item.setStockActual(1567);
	    		item.setArtCantUnidades((short) 0);
	    		item.setArtPreUniPesos(0);
	    		item.setArtPreUniDolares(0);
	    		item.setBrand(ConvertModels.createBrandInstance((short) 19));
	    		item.setArtMostrarEnWeb("S");
	    		item.setArtVendibleMercadoLibre("");
	    		item.setArtCodPro("");
	    		item.setArtNombreML("CADENA PARA CHUPETE");
	    		item.setArtDescripML("- Cadena para chupete.\r\n" + 
	    				"- En blister.\r\n" + 
	    				"- Colores: amarillo, rosado, celeste.");
	    		item.setMedidaEmpaque("18x10cm");
	    		item.setCapacidad("");
	    		item.setTalle("");
    			
    			this.list.add(item); 
	        };	
	 }

	}
