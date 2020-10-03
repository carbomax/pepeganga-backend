package uy.com.pepeganga.business.common.utils.enums;

public enum States {
	 PUBLISHED("Publicado", 1),
	    NOPUBLISHED("No Publicado", 2),
	    PAUSED("Pausado", 3);

		private final String value;
	    private final int id;
		
		States(String value, int id) {
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
