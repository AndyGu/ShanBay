package com.shanbay.words.constant;

public class ReviewType
{
  public static final int REVIEW_TYPE_FRESH = 1;
  public static final int REVIEW_TYPE_TEST_NEW = 2;
  public static final int REVIEW_TYPE_TEST_ALL = 3;

  public static boolean isFresh(int paramInt)
  {
    return paramInt == REVIEW_TYPE_FRESH;
  }

  public static boolean isTest(int paramInt)
  {
    return (paramInt == REVIEW_TYPE_TEST_NEW) || (paramInt == REVIEW_TYPE_TEST_ALL);
  }

  public static boolean isTestAll(int paramInt)
  {
    return paramInt == REVIEW_TYPE_TEST_ALL;
  }

  public static boolean isTestNew(int paramInt)
  {
    return paramInt == REVIEW_TYPE_TEST_NEW;
  }
}