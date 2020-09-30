package uy.com.pepeganga.business.common.utils;

 public class UtilsClass {

	public enum States {
	    Publicado,
	    NoPublicado,
	    Pausado;			    
	  }
	
	public enum MarketplaceType {
	    MERCADOLIBRE("Mercado Libre", 1),
	    AMAZON("Amazon", 2);
	    

	    private final String value;
	    private final int id;

	    MarketplaceType(String value, int id) {
	        this.value = value;
	        this.id = id;
	    }

	    public String getValue() {
	        return value;
	    }

	    public int getId() {
	        return id;
	    }
	}
}
