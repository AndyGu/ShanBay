package com.shanbay.words.model;

public class ProgressBarData
{
  private int failure;
  private int init;
  private int recognition;
  private int success;
  private int sum;

  public ProgressBarData(ReviewStat paramReviewStat)
  {
    sum = paramReviewStat.getTotal();
    init = paramReviewStat.getInit();
    success = paramReviewStat.getSuccess();
    failure = paramReviewStat.getFailure();
    recognition = paramReviewStat.getRecognition();
  }

  public int getFailure()
  {
    return failure;
  }

  public int getInit()
  {
    return init;
  }

  public int getRecognition()
  {
    return recognition;
  }

  public int getSuccess()
  {
    return success;
  }

  public int getSum()
  {
    return sum;
  }
}