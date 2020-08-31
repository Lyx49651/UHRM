package com.longwang.uhrm.Entity;


public class InformationChange {

  private long idInformationChange;
  private long employeeId;
  private String employeeName;
  private java.sql.Timestamp changeTime;
  private String changeType;
  private String changeOriginal;
  private String changeNow;


  public long getIdInformationChange() {
    return idInformationChange;
  }

  public void setIdInformationChange(long idInformationChange) {
    this.idInformationChange = idInformationChange;
  }

  public long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(long employeeId) {
    this.employeeId = employeeId;
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



  @Override
  public String toString() {
    return "InformationChange{" +
            "idInformationChange='" + idInformationChange + '\'' +
            ", employeeId=" + employeeId +
            ", employeeName='" + employeeName + '\'' +
            ", changeTime=" + changeTime +
            ", changeType='" + changeType + '\'' +
            ", changeOriginal='" + changeOriginal + '\'' +
            ", changeNow='" + changeNow + '\'' +
            '}';
  }
}
