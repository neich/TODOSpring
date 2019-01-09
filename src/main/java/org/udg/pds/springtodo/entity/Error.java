package org.udg.pds.springtodo.entity;

/**
 * Created by imartin on 22/02/17.
 */
public class Error {
  public String type;
  public String message;

  public Error(String type, String message) {
    this.type = type;
    this.message = message;
  }
}
