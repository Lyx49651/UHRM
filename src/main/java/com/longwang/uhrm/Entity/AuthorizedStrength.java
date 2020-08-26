package com.longwang.uhrm.Entity;


public class AuthorizedStrength {

  private long idAuthorizedStrength;
  private String typeAuthorizedStrength;
  private java.sql.Timestamp timeAuthorizedStrength;
  private long numberAuthorizedStrength;
  private long positionIdPosition;


  public long getIdAuthorizedStrength() {
    return idAuthorizedStrength;
  }

  public void setIdAuthorizedStrength(long idAuthorizedStrength) {
    this.idAuthorizedStrength = idAuthorizedStrength;
  }


  public String getTypeAuthorizedStrength() {
    return typeAuthorizedStrength;
  }

  public void setTypeAuthorizedStrength(String typeAuthorizedStrength) {
    this.typeAuthorizedStrength = typeAuthorizedStrength;
  }


  public java.sql.Timestamp getTimeAuthorizedStrength() {
    return timeAuthorizedStrength;
  }

  public void setTimeAuthorizedStrength(java.sql.Timestamp timeAuthorizedStrength) {
    this.timeAuthorizedStrength = timeAuthorizedStrength;
  }


  public long getNumberAuthorizedStrength() {
    return numberAuthorizedStrength;
  }

  public void setNumberAuthorizedStrength(long numberAuthorizedStrength) {
    this.numberAuthorizedStrength = numberAuthorizedStrength;
  }


  public long getPositionIdPosition() {
    return positionIdPosition;
  }

  public void setPositionIdPosition(long positionIdPosition) {
    this.positionIdPosition = positionIdPosition;
  }

}
