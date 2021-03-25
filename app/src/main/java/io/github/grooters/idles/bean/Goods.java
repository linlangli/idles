package io.github.grooters.idles.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Goods implements Parcelable {

    private int id;

    private int code;

    private String message;

    private String goodsNumber;

    private String sellerName;

    private String goodsName;

    private String sellerNumber;

    // 商品购买的时间
    private String time;

    private float price;

    // 卖家最近来查看该商品的时间
    private String comeLatelyTime;

    private String description;

    private String titleImage;

    private String[] introImage;

    private String introVideo;

    private long fansNumber = 0;

    private String location;

    public Goods(){

    }

    private Goods(Parcel in) {
        id = in.readInt();
        code = in.readInt();
        message = in.readString();
        goodsNumber = in.readString();
        goodsName = in.readString();
        sellerNumber = in.readString();
        time = in.readString();
        price = in.readFloat();
        comeLatelyTime = in.readString();
        description = in.readString();
        titleImage = in.readString();
        introImage = in.createStringArray();
        introVideo = in.readString();
        fansNumber = in.readLong();
        location = in.readString();
        sellerName = in.readString();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitleImage() { return titleImage; }

    public void setTitleImage(String titleImage) { this.titleImage = titleImage; }

    public String getIntroVideo() { return introVideo; }

    public void setIntroVideo(String introVideo) { this.introVideo = introVideo; }

    public long getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(long fansNumber) {
        this.fansNumber = fansNumber;
    }

    public String[] getIntroImage() { return introImage; }

    public void setIntroImage(String[] introImage) { this.introImage = introImage; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComeLatelyTime() {
        return comeLatelyTime;
    }

    public void setComeLatelyTime(String comeLatelyTime) {
        this.comeLatelyTime = comeLatelyTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(goodsNumber);
        dest.writeString(goodsName);
        dest.writeString(sellerNumber);
        dest.writeString(time);
        dest.writeFloat(price);
        dest.writeString(comeLatelyTime);
        dest.writeString(description);
        dest.writeString(titleImage);
        dest.writeStringArray(introImage);
        dest.writeString(introVideo);
        dest.writeLong(fansNumber);
        dest.writeString(location);
        dest.writeString(sellerName);
    }
}
