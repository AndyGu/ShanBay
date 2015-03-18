package com.shanbay.words.model;

import com.shanbay.model.Model;

public class Vocabulary extends Model
{
  public String audio;
  public int conentId;
  public String content;
  public String contentType;
  public String definition;
  public EnDefinition enDefinition;
  public long id;
  public String pron;
  public String ukAudio;
  public String usAudio;

  public class EnDefinition extends Model
  {
    public String defn;
    public String pos;

    public EnDefinition()
    {
    }
  }
}