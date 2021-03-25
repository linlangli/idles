package io.github.grooters.idles.bean;

public class User {

    private int id;

    private String message;

    private int code;

    private int level;

    private long time = 0;

    private String grade = "";

    private String university;

    // 买到的东西的序列号
    private String[] myGoodsOrderNumber;

    // 买到的事务的序列号
    private String[] myWorksOrderNumber;

    // 发布的东西的序列号
    private String[] myGoodsPushNumber;

    // 发布的事务的序列号
    private String[] myWorksPushNumber;

    // 收藏的东西的序列号
    private String[] myGoodsCollectionNumber;

    // 收藏的事务的序列号
    private String[] myWorksCollectionNumber;

    // 关注者的number
    private String[] concernNumber;

    // 关注的number
    private String[] followNumber;

    private String tokenNumber;

    private String name;

    private String location;

    private String resume;

    private String gender;

    private String userNumber;

    private String avatarUrl;

    private String password;

    private String email;

    public User(){}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String[] getConcernNumber() {
        return concernNumber;
    }

    public void setConcernNumber(String[] concernNumber) {
        this.concernNumber = concernNumber;
    }

    public String[] getFollowNumber() {
        return followNumber;
    }

    public void setFollowNumber(String[] followNumber) {
        this.followNumber = followNumber;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String[] getMyGoodsOrderNumber() {
        return myGoodsOrderNumber;
    }

    public void setMyGoodsOrderNumber(String[] myGoodsOrderNumber) {
        this.myGoodsOrderNumber = myGoodsOrderNumber;
    }

    public String[] getMyWorksOrderNumber() {
        return myWorksOrderNumber;
    }

    public void setMyWorksOrderNumber(String[] myWorksOrderNumber) {
        this.myWorksOrderNumber = myWorksOrderNumber;
    }

    public String[] getMyGoodsPushNumber() {
        return myGoodsPushNumber;
    }

    public void setMyGoodsPushNumber(String[] myGoodsPushNumber) {
        this.myGoodsPushNumber = myGoodsPushNumber;
    }

    public String[] getMyWorksPushNumber() {
        return myWorksPushNumber;
    }

    public void setMyWorksPushNumber(String[] myWorksPushNumber) {
        this.myWorksPushNumber = myWorksPushNumber;
    }

    public String[] getMyGoodsCollectionNumber() {
        return myGoodsCollectionNumber;
    }

    public void setMyGoodsCollectionNumber(String[] myGoodsCollectionNumber) {
        this.myGoodsCollectionNumber = myGoodsCollectionNumber;
    }

    public String[] getMyWorksCollectionNumber() {
        return myWorksCollectionNumber;
    }

    public void setMyWorksCollectionNumber(String[] myWorksCollectionNumber) {
        this.myWorksCollectionNumber = myWorksCollectionNumber;
    }

}
