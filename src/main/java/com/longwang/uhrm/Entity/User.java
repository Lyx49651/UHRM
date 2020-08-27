package com.longwang.uhrm.Entity;


public class User {

  private long idUser;
  private String name;
  private String sex;
  private String IDCard;
  private String photo;
  private String address;
  private long age;
  private String mailAddress;

  public User() {

  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public User(String name, String sex, String idCard, String photo, String address, long age, String mailAddress, String telephone, long postIdPost, String password) {
    this.name = name;
    this.sex = sex;
    this.IDCard = idCard;
    this.photo = photo;
    this.address = address;
    this.age = age;
    this.mailAddress = mailAddress;
    this.telephone = telephone;
    this.postIdPost = postIdPost;
    this.password = password;
  }

  public User(long idUser, String name, String sex, String idCard, String photo, String address, long age, String mailAddress, String telephone, long postIdPost, String password) {
    this.idUser = idUser;
    this.name = name;
    this.sex = sex;
    this.IDCard = idCard;
    this.photo = photo;
    this.address = address;
    this.age = age;
    this.mailAddress = mailAddress;
    this.telephone = telephone;
    this.postIdPost = postIdPost;
    this.password = password;
  }

  private String telephone;
  private long postIdPost;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private String password;


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
    return IDCard;
  }

  public void setIdCard(String idCard) {
    this.IDCard = idCard;
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




  public long getPostIdPost() {
    return postIdPost;
  }

  public void setPostIdPost(long postIdPost) {
    this.postIdPost = postIdPost;
  }

}
