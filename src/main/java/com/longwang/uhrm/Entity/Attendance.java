package com.longwang.uhrm.Entity;


public class Attendance {

  private String attendanceTime;
  private long employeeId;
  private String attendanceAssistantName;
  private long attendanceAssistantId;
  private String employeeName;
  private String attendanceResults;


  public String getAttendanceTime() {
    return attendanceTime;
  }

  public void setAttendanceTime(String attendanceTime) {
    this.attendanceTime = attendanceTime;
  }


  public long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(long employeeId) {
    this.employeeId = employeeId;
  }


  public String getAttendanceAssistantName() {
    return attendanceAssistantName;
  }

  public void setAttendanceAssistantName(String attendanceAssistantName) {
    this.attendanceAssistantName = attendanceAssistantName;
  }


  public long getAttendanceAssistantId() {
    return attendanceAssistantId;
  }

  public void setAttendanceAssistantId(long attendanceAssistantId) {
    this.attendanceAssistantId = attendanceAssistantId;
  }


  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }


  public String getAttendanceResults() {
    return attendanceResults;
  }

  public void setAttendanceResults(String attendanceResults) {
    this.attendanceResults = attendanceResults;
  }

  public Attendance(String attendanceTime, long employeeId, String attendanceAssistantName, long attendanceAssistantId, String employeeName, String attendanceResults) {
    this.attendanceTime = attendanceTime;
    this.employeeId = employeeId;
    this.attendanceAssistantName = attendanceAssistantName;
    this.attendanceAssistantId = attendanceAssistantId;
    this.employeeName = employeeName;
    this.attendanceResults = attendanceResults;
  }

  public Attendance(String attendanceTime, long employeeId, String employeeName, String attendanceResults) {
    this.attendanceTime = attendanceTime;
    this.employeeId = employeeId;
    this.employeeName = employeeName;
    this.attendanceResults = attendanceResults;
  }

  @Override
  public String toString() {
    return "Attendance{" +
            "attendanceTime='" + attendanceTime + '\'' +
            ", employeeId=" + employeeId +
            ", attendanceAssistantName='" + attendanceAssistantName + '\'' +
            ", attendanceAssistantId=" + attendanceAssistantId +
            ", employeeName='" + employeeName + '\'' +
            ", attendanceResults='" + attendanceResults + '\'' +
            '}';
  }
}
