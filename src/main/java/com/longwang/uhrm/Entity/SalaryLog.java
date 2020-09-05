package com.longwang.uhrm.Entity;


public class SalaryLog {

  private long idSalaryLog;
  private String time;
  private String amount;

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public String getEmployeeDepartment() {
    return employeeDepartment;
  }

  public void setEmployeeDepartment(String employeeDepartment) {
    this.employeeDepartment = employeeDepartment;
  }

  private String employeeName;
  private String employeeDepartment;

  public long getEmployeeArchives_employeeId() {
    return EmployeeArchives_employeeId;
  }

  public void setEmployeeArchives_employeeId(long employeeArchives_employeeId) {
    EmployeeArchives_employeeId = employeeArchives_employeeId;
  }

  private long EmployeeArchives_employeeId;


  public long getIdSalaryLog() {
    return idSalaryLog;
  }

  public void setIdSalaryLog(long idSalaryLog) {
    this.idSalaryLog = idSalaryLog;
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



}
