package com.longwang.uhrm.Entity;


public class SalaryLog {

  private long idSalaryLog;
  private String receiverId;
  private String time;
  private String amount;
  private long employeeArchivesEmployeeId;


  public long getIdSalaryLog() {
    return idSalaryLog;
  }

  public void setIdSalaryLog(long idSalaryLog) {
    this.idSalaryLog = idSalaryLog;
  }


  public String getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(String receiverId) {
    this.receiverId = receiverId;
  }


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }


  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }


  public long getEmployeeArchivesEmployeeId() {
    return employeeArchivesEmployeeId;
  }

  public void setEmployeeArchivesEmployeeId(long employeeArchivesEmployeeId) {
    this.employeeArchivesEmployeeId = employeeArchivesEmployeeId;
  }

}
