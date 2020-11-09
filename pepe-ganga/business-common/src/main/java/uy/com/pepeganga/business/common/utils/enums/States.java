package uy.com.pepeganga.business.common.utils.enums;

public enum States {
	    PUBLISHED("Publicado", (short) 1),
	    NOPUBLISHED("No Publicado", (short) 2);

		private final String value;
	    private final short id;
		
		States(String value, short id) {
			this.value = value;
	        this.id = id;
		}	
		
		 public String getValue() {
		        return value;
		    }

		 public short getId() {
		        return id;
		    }
}
