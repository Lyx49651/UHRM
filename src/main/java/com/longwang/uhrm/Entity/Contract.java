package com.longwang.uhrm.Entity;


public class Contract {

  private long idContract;
  private long employeeId;
  private String employeeName;
  private String employeeSex;
  private String contractPeriod;
  private String salary;
  private String position;


  public long getIdContract() {
    return idContract;
  }

  public void setIdContract(long idContract) {
    this.idContract = idContract;
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


  public String getEmployeeSex() {
    return employeeSex;
  }

  public void setEmployeeSex(String employeeSex) {
    this.employeeSex = employeeSex;
  }


  public String getContractPeriod() {
    return contractPeriod;
  }

  public void setContractPeriod(String contractPeriod) {
    this.contractPeriod = contractPeriod;
  }


  public String getSalary() {
    return salary;
  }

  public void setSalary(String salary) {
    this.salary = salary;
  }


  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

}
