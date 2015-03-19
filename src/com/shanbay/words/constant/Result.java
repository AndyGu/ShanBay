package com.shanbay.words.constant;

public enum Result {
	SUCCESS, FAILURE, PASS;
	
  public static boolean isPass(Result result)
  {
    return result == PASS;
  }

  public static boolean isSuccess(Result result)
  {
    return result == SUCCESS;
  }

  public static boolean isSuccessOrPass(Result result)
  {
    return (result == SUCCESS) || (result == PASS);
  }
}