package com.longwang.uhrm.Entity;


public class CollectTable {

  private long id;
  private long Department_idDepartment;
  private String departmentName;
  private String recutimentNumber;
  private long idPost;
  private String namePost;
  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getNamePost() {
    return namePost;
  }

  public void setNamePost(String namePost) {
    this.namePost = namePost;
  }

  public String getDepartmentName() {

    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  private String memberNumber;
  private String authorizedStrengthNumber;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getDepartment_idDepartment() {
    return Department_idDepartment;
  }

  public void setDepartment_idDepartment(long department_idDepartment) {
    Department_idDepartment = department_idDepartment;
  }

  public String getRecutimentNumber() {
    return recutimentNumber;
  }

  public void setRecutimentNumber(String recutimentNumber) {
    this.recutimentNumber = recutimentNumber;
  }


  public long getIdPost() {
    return idPost;
  }

  public void setIdPost(long idPost) {
    this.idPost = idPost;
  }


  public String getMemberNumber() {
    return memberNumber;
  }

  public void setMemberNumber(String memberNumber) {
    this.memberNumber = memberNumber;
  }


  public String getAuthorizedStrengthNumber() {
    return authorizedStrengthNumber;
  }

  public void setAuthorizedStrengthNumber(String authorizedStrengthNumber) {
    this.authorizedStrengthNumber = authorizedStrengthNumber;
  }

}
