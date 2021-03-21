package uy.com.pepeganga.business.common.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;

@JsonIgnoreProperties({"stackTrace", "suppressed", "localizedMessage", "cause", })
@JsonPropertyOrder({"message", "error", "status", "causes"})
public class PGException  extends Exception{


    private final String error;

    private final String[] causes;

    private final int status;

    private final String timestamp;


    public PGException(String message, String error, int status, String... causes) {
        super(message);
        this.error = error;
        this.causes = causes;
        this.status = status;
        timestamp = DateTimeUtilsBss.getDateTimeAtCurrentTime().toString();
    }

    public static <T extends PGException> PGException builder(T e){
        return new PGException(e.getMessage(), e.getError(), e.getStatus(), e.getCauses());
    }

    public String getError() {
        return error;
    }

    public String[] getCauses() {
        return causes;
    }

    public int getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
