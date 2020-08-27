package com.longwang.uhrm.Entity;


public class InformationChange {

  private String idInformationChange;
  private long emploteeId;
  private String employeeName;
  private java.sql.Timestamp changeTime;
  private String changeType;
  private String changeOriginal;
  private String changeNow;


  public String getIdInformationChange() {
    return idInformationChange;
  }

  public void setIdInformationChange(String idInformationChange) {
    this.idInformationChange = idInformationChange;
  }


  public long getEmploteeId() {
    return emploteeId;
  }

  public void setEmploteeId(long emploteeId) {
    this.emploteeId = emploteeId;
  }


  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }


  public java.sql.Timestamp getChangeTime() {
    return changeTime;
  }

  public void setChangeTime(java.sql.Timestamp changeTime) {
    this.changeTime = changeTime;
  }


  public String getChangeType() {
    return changeType;
  }

  public void setChangeType(String changeType) {
    this.changeType = changeType;
  }


  public String getChangeOriginal() {
    return changeOriginal;
  }

  public void setChangeOriginal(String changeOriginal) {
    this.changeOriginal = changeOriginal;
  }


  public String getChangeNow() {
    return changeNow;
  }

  public void setChangeNow(String changeNow) {
    this.changeNow = changeNow;
  }

}
