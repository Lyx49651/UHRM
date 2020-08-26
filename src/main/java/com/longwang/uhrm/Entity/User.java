package com.longwang.uhrm.Entity;


public class User {

  private long idUser;
  private String name;
  private String sex;
  private String idCard;
  private String photo;
  private String address;
  private long age;
  private String mailAddress;
  private long telephone;
  private long postIdPost;


  public long getIdUser() {
    return idUser;
  }

  public void setIdUser(long idUser) {
    this.idUser = idUser;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }


  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }


  public String getMailAddress() {
    return mailAddress;
  }

  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }


  public long getTelephone() {
    return telephone;
  }

  public void setTelephone(long telephone) {
    this.telephone = telephone;
  }


  public long getPostIdPost() {
    return postIdPost;
  }

  public void setPostIdPost(long postIdPost) {
    this.postIdPost = postIdPost;
  }

}
