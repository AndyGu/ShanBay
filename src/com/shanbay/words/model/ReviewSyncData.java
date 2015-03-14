package com.shanbay.words.model;

import java.io.Serializable;

public class ReviewSyncData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private long deltaSeconds;
  private long id;
  private int rentention;
  private int reviewStatus;

  public long getDeltaSeconds()
  {
    return this.deltaSeconds;
  }

  public long getId()
  {
    return this.id;
  }

  public int getRentention()
  {
    return this.rentention;
  }

  public int getReviewStatus()
  {
    return this.reviewStatus;
  }

  public void setDeltaSeconds(long paramLong)
  {
    if (paramLong < 0L)
      paramLong = 0L;
    if (paramLong > 120L)
      paramLong = 120L;
    this.deltaSeconds = paramLong;
  }

  public void setId(long paramLong)
  {
    this.id = paramLong;
  }

  public void setRentention(int paramInt)
  {
    this.rentention = paramInt;
  }

  public void setReviewStatus(int paramInt)
  {
    this.reviewStatus = paramInt;
  }
}