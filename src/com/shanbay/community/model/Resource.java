package com.shanbay.community.model;

import java.util.ArrayList;
import java.util.List;

public class Resource
{
  private List<String> fileList = new ArrayList<String>();
  private String version;

  public List<String> getFileList()
  {
    return this.fileList;
  }

  public String getVersion()
  {
    return this.version;
  }

  public void setFileList(List<String> paramList)
  {
    if (paramList != null)
    {
      this.fileList.clear();
      this.fileList.addAll(paramList);
    }
  }

  public void setVersion(String paramString)
  {
    this.version = paramString;
  }
}