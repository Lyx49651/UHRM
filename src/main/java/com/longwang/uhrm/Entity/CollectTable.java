package com.longwang.uhrm.Entity;


public class CollectTable {

  private long id;
  private long departmentIdDepartment;
  private String departmentName;
  private String recutimentNumber;
  private long idPost;
  private String namePost;

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


  public long getDepartmentIdDepartment() {
    return departmentIdDepartment;
  }

  public void setDepartmentIdDepartment(long departmentIdDepartment) {
    this.departmentIdDepartment = departmentIdDepartment;
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
