package com.stylefeng.guns.api.user.vo;


public class MoocUserT {

  private long uuid;
  private String userName;
  private String userPwd;
  private String nickName;
  private long userSex;
  private String birthday;
  private String email;
  private String userPhone;
  private String address;
  private String headUrl;
  private String biography;
  private long lifeState;
  private java.sql.Timestamp beginTime;
  private java.sql.Timestamp updateTime;


  public long getUuid() {
    return uuid;
  }

  public void setUuid(long uuid) {
    this.uuid = uuid;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getUserPwd() {
    return userPwd;
  }

  public void setUserPwd(String userPwd) {
    this.userPwd = userPwd;
  }


  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }


  public long getUserSex() {
    return userSex;
  }

  public void setUserSex(long userSex) {
    this.userSex = userSex;
  }


  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public String getHeadUrl() {
    return headUrl;
  }

  public void setHeadUrl(String headUrl) {
    this.headUrl = headUrl;
  }


  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }


  public long getLifeState() {
    return lifeState;
  }

  public void setLifeState(long lifeState) {
    this.lifeState = lifeState;
  }


  public java.sql.Timestamp getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(java.sql.Timestamp beginTime) {
    this.beginTime = beginTime;
  }


  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }

}
