package com.qpp.basic.exception;

/**
 * @author qipengpai
 */
public class MyException extends RuntimeException {

  private String message;

  public MyException(String message){
    super(message);
    this.message=message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
