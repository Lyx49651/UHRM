package com.longwang.uhrm.Entity;


public class Document {

  private long idDocument;
  private long typeDocument;
  private String content;
  private long authorId;


  public long getIdDocument() {
    return idDocument;
  }

  public void setIdDocument(long idDocument) {
    this.idDocument = idDocument;
  }


  public long getTypeDocument() {
    return typeDocument;
  }

  public void setTypeDocument(long typeDocument) {
    this.typeDocument = typeDocument;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(long authorId) {
    this.authorId = authorId;
  }

}
