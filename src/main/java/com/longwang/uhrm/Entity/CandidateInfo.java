package com.longwang.uhrm.Entity;


public class CandidateInfo {

  private long idCandidateInfo;
  private String interviewResult;
  private String writtenResult;
  private String departmentPost;
  private String status;

  public long getIdCandidateInfo() {
    return idCandidateInfo;
  }

  public void setIdCandidateInfo(long idCandidateInfo) {
    this.idCandidateInfo = idCandidateInfo;
  }


  public String getInterviewResult() {
    return interviewResult;
  }

  public void setInterviewResult(String interviewResult) {
    this.interviewResult = interviewResult;
  }


  public String getWrittenResult() {
    return writtenResult;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDepartmentPost() {
    return departmentPost;
  }

  public void setDepartmentPost(String departmentPost) {
    this.departmentPost = departmentPost;
  }

  public void setWrittenResult(String writtenResult) {
    this.writtenResult = writtenResult;
  }

}
