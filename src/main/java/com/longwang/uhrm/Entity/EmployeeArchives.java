package com.longwang.uhrm.Entity;


public class EmployeeArchives {

  private long employeeId;
  private String employeeName;
  private String employeeSex;
  private String employeeBirthday;
  private String employeeAddress;
  private long employeePhoneNumber;
  private String employeeDepartment;
  private String employeeEducation;
  private String employeePost;
  private String employeeTitle;
  private String employeeTechnicalGrade;
  private String employeeIdentity;
  private long SalaryParameters_idSalaryParameters;
  private long Position_idPosition;
  private long Department_idDepartment;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private String password;


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


  public String getEmployeeBirthday() {
    return employeeBirthday;
  }

  public void setEmployeeBirthday(String employeeBirthday) {
    this.employeeBirthday = employeeBirthday;
  }


  public String getEmployeeAddress() {
    return employeeAddress;
  }

  public void setEmployeeAddress(String employeeAddress) {
    this.employeeAddress = employeeAddress;
  }


  public long getEmployeePhoneNumber() {
    return employeePhoneNumber;
  }

  public void setEmployeePhoneNumber(long employeePhoneNumber) {
    this.employeePhoneNumber = employeePhoneNumber;
  }


  public String getEmployeeDepartment() {
    return employeeDepartment;
  }

  public void setEmployeeDepartment(String employeeDepartment) {
    this.employeeDepartment = employeeDepartment;
  }


  public String getEmployeeEducation() {
    return employeeEducation;
  }

  public void setEmployeeEducation(String employeeEducation) {
    this.employeeEducation = employeeEducation;
  }


  public String getEmployeePost() {
    return employeePost;
  }

  public void setEmployeePost(String employeePost) {
    this.employeePost = employeePost;
  }


  public String getEmployeeTitle() {
    return employeeTitle;
  }

  public void setEmployeeTitle(String employeeTitle) {
    this.employeeTitle = employeeTitle;
  }


  public String getEmployeeTechnicalGrade() {
    return employeeTechnicalGrade;
  }

  public void setEmployeeTechnicalGrade(String employeeTechnicalGrade) {
    this.employeeTechnicalGrade = employeeTechnicalGrade;
  }


  public String getEmployeeIdentity() {
    return employeeIdentity;
  }

  public void setEmployeeIdentity(String employeeIdentity) {
    this.employeeIdentity = employeeIdentity;
  }


  @Override
  public String toString() {
    return "EmployeeArchives{" +
            "employeeId=" + employeeId +
            ", employeeName='" + employeeName + '\'' +
            ", employeeSex='" + employeeSex + '\'' +
            ", employeeBirthday='" + employeeBirthday + '\'' +
            ", employeeAddress='" + employeeAddress + '\'' +
            ", employeePhoneNumber=" + employeePhoneNumber +
            ", employeeDepartment='" + employeeDepartment + '\'' +
            ", employeeEducation='" + employeeEducation + '\'' +
            ", employeePost='" + employeePost + '\'' +
            ", employeeTitle='" + employeeTitle + '\'' +
            ", employeeTechnicalGrade='" + employeeTechnicalGrade + '\'' +
            ", employeeIdentity='" + employeeIdentity + '\'' +
            ", SalaryParameters_idSalaryParameters=" + SalaryParameters_idSalaryParameters +
            ", Position_idPosition=" + Position_idPosition +
            ", Department_idDepartment=" + Department_idDepartment +
            ", password='" + password + '\'' +
            '}';
  }

  public long getPosition_idPosition() {
    return Position_idPosition;
  }

  public void setPosition_idPosition(long position_idPosition) {
    Position_idPosition = position_idPosition;
  }

  public long getDepartment_idDepartment() {
    return Department_idDepartment;
  }

  public void setDepartment_idDepartment(long department_idDepartment) {
    Department_idDepartment = department_idDepartment;
  }

  public long getSalaryParameters_idSalaryParameters() {

    return SalaryParameters_idSalaryParameters;
  }

  public void setSalaryParameters_idSalaryParameters(long salaryParameters_idSalaryParameters) {
    SalaryParameters_idSalaryParameters = salaryParameters_idSalaryParameters;
  }
}
