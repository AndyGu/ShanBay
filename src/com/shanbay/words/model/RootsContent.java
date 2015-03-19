package com.shanbay.words.model;

public class RootsContent
{
  private String content;
  private String meaningCn;
  private int state;

  public String getContent()
  {
    return this.content;
  }

  public String getMeaningCn()
  {
    return this.meaningCn;
  }

  public int getState()
  {
    return this.state;
  }

  public RootsContent setContent(String paramString)
  {
    this.content = paramString;
    return this;
  }

  public RootsContent setMeaningCn(String paramString)
  {
    this.meaningCn = paramString;
    return this;
  }

  public RootsContent setState(int paramInt)
  {
    this.state = paramInt;
    return this;
  }
}