package uy.com.pepeganga.business.common.utils.enums;

public enum ActionResult {
	DONE("DONE", (short)1),
	PARTIAL("PARTIAL", (short)2),
	BAD("BAD", (short)3);
	
	private final String value;
    private final short id;
	
    ActionResult(String value, short id) {
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
