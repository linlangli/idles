package io.github.grooters.idles.bean;

public class Works {

    private int id;

    private String message;

    private int code;

    private String worksNumber;

    private String worksName;

    private String[] buyerNumber;

    private String startTime;

    private String endTime;

    private String description;

    private String sellerNumber;

    private String sellerName;

    private String location;

    private float price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getWorksNumber() {
        return worksNumber;
    }

    public void setWorksNumber(String worksNumber) {
        this.worksNumber = worksNumber;
    }

    public String getWorksName() {
        return worksName;
    }

    public void setWorksName(String worksName) {
        this.worksName = worksName;
    }

    public String[] getBuyerNumber() {
        return buyerNumber;
    }

    public void setBuyerNumber(String[] buyerNumber) {
        this.buyerNumber = buyerNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

}
