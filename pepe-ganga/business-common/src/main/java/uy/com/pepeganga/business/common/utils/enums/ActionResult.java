package uy.com.pepeganga.business.common.utils.enums;

public enum ActionResult {
	DONE("DONE", (short)1),
	PARTIAL("PARTIAL", (short)2),
	BAD("BAD", (short)3),
	NOT_FOUND("not_found", (short)4),
	TOKEN_ERROR("token_error", (short)5),
	MELI_ERROR("meli_error", (short)6),
	MELI_CONECCION_ERROR("meli_coneccion_error", (short)7),
	ERROR("error", (short)8),
	DATABASE_ERROR("database_error", (short)9);

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
