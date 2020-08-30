package com.longwang.uhrm.Entity;


public class Position {

  private long idPosition;
  private String typePostion;
  private long employeeManagement;
  private long salaryManagement;
  private long documentManagement;
  private long organizationManagement;
  private long attendanceManagement;
  private long recruitmentManagement;
  private long personnelManagement;
  private long systemManagement;
  private long totalStaff;
  private long departmentId;

  @Override
  public String toString() {
    return "Position{" +
            "idPosition=" + idPosition +
            ", typePostion='" + typePostion + '\'' +
            ", employeeManagement=" + employeeManagement +
            ", salaryManagement=" + salaryManagement +
            ", documentManagement=" + documentManagement +
            ", organizationManagement=" + organizationManagement +
            ", attendanceManagement=" + attendanceManagement +
            ", recruitmentManagement=" + recruitmentManagement +
            ", personnelManagement=" + personnelManagement +
            ", systemManagement=" + systemManagement +
            ", totalStaff=" + totalStaff +
            ", departmentId=" + departmentId +
            '}';
  }

  public void setDepartmentId(long departmentId) {
    this.departmentId = departmentId;
  }

  public long getTotalStaff() {
    return totalStaff;
  }

  public void setTotalStaff(long totalStaff) {
    this.totalStaff = totalStaff;
  }

  public long getIdPosition() {
    return idPosition;
  }

  public void setIdPosition(long idPosition) {
    this.idPosition = idPosition;
  }


  public String getTypePostion() {
    return typePostion;
  }

  public void setTypePostion(String typePostion) {
    this.typePostion = typePostion;
  }


  public long getEmployeeManagement() {
    return employeeManagement;
  }

  public void setEmployeeManagement(long employeeManagement) {
    this.employeeManagement = employeeManagement;
  }


  public long getSalaryManagement() {
    return salaryManagement;
  }

  public void setSalaryManagement(long salaryManagement) {
    this.salaryManagement = salaryManagement;
  }


  public long getDocumentManagement() {
    return documentManagement;
  }

  public void setDocumentManagement(long documentManagement) {
    this.documentManagement = documentManagement;
  }


  public long getOrganizationManagement() {
    return organizationManagement;
  }

  public void setOrganizationManagement(long organizationManagement) {
    this.organizationManagement = organizationManagement;
  }


  public long getAttendanceManagement() {
    return attendanceManagement;
  }

  public void setAttendanceManagement(long attendanceManagement) {
    this.attendanceManagement = attendanceManagement;
  }


  public long getRecruitmentManagement() {
    return recruitmentManagement;
  }

  public void setRecruitmentManagement(long recruitmentManagement) {
    this.recruitmentManagement = recruitmentManagement;
  }


  public long getPersonnelManagement() {
    return personnelManagement;
  }

  public void setPersonnelManagement(long personnelManagement) {
    this.personnelManagement = personnelManagement;
  }


  public long getSystemManagement() {
    return systemManagement;
  }

  public void setSystemManagement(long systemManagement) {
    this.systemManagement = systemManagement;
  }

}
