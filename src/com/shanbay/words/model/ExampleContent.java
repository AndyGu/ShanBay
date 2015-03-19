package com.shanbay.words.model;

import org.apache.commons.lang.StringUtils;

public class ExampleContent
{
  private String annotation;
  private long id;
  private String translation;

  public String getAnnotation()
  {
    return this.annotation;
  }

  public long getId()
  {
    return this.id;
  }

  public String getTranslation()
  {
    return this.translation;
  }

  public ExampleContent setAnnotation(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.annotation = paramString1;
    if (StringUtils.isBlank(paramString1))
      new StringBuilder().append(paramString2).append("<vocab>").append(paramString4).append("</vocab>").append(paramString3).toString();
    return this;
  }

  public ExampleContent setId(long paramLong)
  {
    this.id = paramLong;
    return this;
  }

  public ExampleContent setTranslation(String paramString)
  {
    this.translation = paramString;
    return this;
  }
}