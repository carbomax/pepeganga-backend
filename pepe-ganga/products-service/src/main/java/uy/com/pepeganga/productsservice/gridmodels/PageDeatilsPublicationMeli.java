package uy.com.pepeganga.productsservice.gridmodels;

import java.io.Serializable;
import java.util.List;

public class PageDeatilsPublicationMeli implements Serializable {

    private List<DMDetailsPublicationsMeli> content;

    private long totalElements;

    private int numberOfElements;

    public PageDeatilsPublicationMeli(List<DMDetailsPublicationsMeli> content, int numberOfElements, long totalElements) {
        this.content = content;
        this.numberOfElements = numberOfElements;
        this.totalElements = totalElements;
    }

    public List<DMDetailsPublicationsMeli> getContent() {
        return content;
    }

    public void setContent(List<DMDetailsPublicationsMeli> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
