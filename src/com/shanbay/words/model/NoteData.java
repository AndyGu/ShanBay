package com.shanbay.words.model;

import java.util.ArrayList;
import java.util.List;

public class NoteData
{
  private List<NoteContent> noteContentList = new ArrayList();

  public List<NoteContent> getNoteContentList()
  {
    return this.noteContentList;
  }

  public void setNoteContentList(List<NoteContent> paramList)
  {
    this.noteContentList = paramList;
  }
}