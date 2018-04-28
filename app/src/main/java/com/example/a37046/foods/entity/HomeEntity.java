package com.example.a37046.foods.entity;

public class HomeEntity {
    private int shop_id;

    private String shopname;

    private String address;

    private String phonenum;

    private String intro;

    private String pic;

    private String comment;

    private int level;


    public HomeEntity() {
    }

    public HomeEntity(String shopname) {
        this.shopname = shopname;
    }

    public void setShop_id(int shop_id){
        this.shop_id = shop_id;
    }
    public int getShop_id(){
        return this.shop_id;
    }
    public void setShopname(String shopname){
        this.shopname = shopname;
    }
    public String getShopname(){
        return this.shopname;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setPhonenum(String phonenum){
        this.phonenum = phonenum;
    }
    public String getPhonenum(){
        return this.phonenum;
    }
    public void setIntro(String intro){
        this.intro = intro;
    }
    public String getIntro(){
        return this.intro;
    }
    public void setPic(String pic){
        this.pic = pic;
    }
    public String getPic(){
        return this.pic;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    public String getComment(){
        return this.comment;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public int getLevel(){
        return this.level;
    }
}
