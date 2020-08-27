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

}
