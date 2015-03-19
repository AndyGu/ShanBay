package com.shanbay.words.model;

public class NoteContent
{
  private String content;
  private long id;

  public String getContent()
  {
    return this.content;
  }

  public long getId()
  {
    return this.id;
  }

  public NoteContent setContent(String paramString)
  {
    this.content = paramString;
    return this;
  }

  public NoteContent setId(long paramLong)
  {
    this.id = paramLong;
    return this;
  }
}