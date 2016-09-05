package com.example.loginsdk.bean.response;
import java.util.List;
/**
 * Created by mitnick.cheng on 2016/9/2.
 */

public class WXUserInfo {
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private List<String> privilege ;
    private String unionid;

    public void setOpenid(String openid){
        this.openid = openid;
    }
    public String getOpenid(){
        return this.openid;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setSex(int sex){
        this.sex = sex;
    }
    public int getSex(){
        return this.sex;
    }
    public void setLanguage(String language){
        this.language = language;
    }
    public String getLanguage(){
        return this.language;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setProvince(String province){
        this.province = province;
    }
    public String getProvince(){
        return this.province;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }
    public void setHeadimgurl(String headimgurl){
        this.headimgurl = headimgurl;
    }
    public String getHeadimgurl(){
        return this.headimgurl;
    }
    public void setPrivilege(List<String> privilege){
        this.privilege = privilege;
    }
    public List<String> getPrivilege(){
        return this.privilege;
    }
    public void setUnionid(String unionid){
        this.unionid = unionid;
    }
    public String getUnionid(){
        return this.unionid;
    }

}
