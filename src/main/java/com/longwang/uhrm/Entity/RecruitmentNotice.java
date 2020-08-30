package com.longwang.uhrm.Entity;


public class RecruitmentNotice {

  private long id;
  private String title;
  private String content;
  private java.sql.Timestamp time;
  private  String stringTime;//存储String类型的日期

  public String getStringTime() {
    return stringTime;
  }

  public void setStringTime(String stringTime) {
    this.stringTime = stringTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }

}
