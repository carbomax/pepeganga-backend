package uy.pepeganga.meli.service.utils;

public enum FlexResponse {
    No_Content(204,"No Content"),
    Bad_Request(400, "Bad Request"),
    Forbidden(403,"Forbidden"),
    Not_Found(404, "Not Found");

    private final int status;
    private final String error;

    FlexResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
