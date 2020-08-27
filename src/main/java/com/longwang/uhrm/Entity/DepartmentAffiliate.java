package com.longwang.uhrm.Entity;


public class DepartmentAffiliate {

  private long departmentIdParent;
  private long departmentIdChild;


  public long getDepartmentIdParent() {
    return departmentIdParent;
  }

  public void setDepartmentIdParent(long departmentIdParent) {
    this.departmentIdParent = departmentIdParent;
  }


  public long getDepartmentIdChild() {
    return departmentIdChild;
  }

  public void setDepartmentIdChild(long departmentIdChild) {
    this.departmentIdChild = departmentIdChild;
  }

}
