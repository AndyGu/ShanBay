package com.shanbay.words.model;

import java.util.ArrayList;
import java.util.List;

public class RootsData
{
  private List<RootsContent> rootsList = new ArrayList();

  public List<RootsContent> getRootsList()
  {
    return this.rootsList;
  }

  public void setRootsList(List<RootsContent> paramList)
  {
    this.rootsList = paramList;
  }
}