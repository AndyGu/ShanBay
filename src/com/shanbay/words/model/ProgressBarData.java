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
    this.sum = paramReviewStat.getTotal();
    this.init = paramReviewStat.getInit();
    this.success = paramReviewStat.getSuccess();
    this.failure = paramReviewStat.getFailure();
    this.recognition = paramReviewStat.getRecognition();
  }

  public int getFailure()
  {
    return this.failure;
  }

  public int getInit()
  {
    return this.init;
  }

  public int getRecognition()
  {
    return this.recognition;
  }

  public int getSuccess()
  {
    return this.success;
  }

  public int getSum()
  {
    return this.sum;
  }
}