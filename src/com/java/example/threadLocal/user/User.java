package com.java.example.threadLocal.user;

public class User {
  
  private String id;

  public User(String id) {
    super();
    this.id = id;
  }

  public String getId() {
    return id;
  }
  
  

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("User [id=");
    builder.append(id);
    builder.append("]");
    return builder.toString();
  }

}
