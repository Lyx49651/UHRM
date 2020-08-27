package com.longwang.uhrm.Entity;


public class Department {

  private long idDepartment;
  private String nameDepartment;
  private String scaleDepartment;
  private String fundsTypeDepartment;
  private long totalStaffDepartment;


  public long getIdDepartment() {
    return idDepartment;
  }

  public void setIdDepartment(long idDepartment) {
    this.idDepartment = idDepartment;
  }


  public String getNameDepartment() {
    return nameDepartment;
  }

  public void setNameDepartment(String nameDepartment) {
    this.nameDepartment = nameDepartment;
  }


  public String getScaleDepartment() {
    return scaleDepartment;
  }

  public void setScaleDepartment(String scaleDepartment) {
    this.scaleDepartment = scaleDepartment;
  }


  public String getFundsTypeDepartment() {
    return fundsTypeDepartment;
  }

  public void setFundsTypeDepartment(String fundsTypeDepartment) {
    this.fundsTypeDepartment = fundsTypeDepartment;
  }


  public long getTotalStaffDepartment() {
    return totalStaffDepartment;
  }

  public void setTotalStaffDepartment(long totalStaffDepartment) {
    this.totalStaffDepartment = totalStaffDepartment;
  }

}
