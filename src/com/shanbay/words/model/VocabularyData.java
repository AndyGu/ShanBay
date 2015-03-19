package com.shanbay.words.model;

public class VocabularyData
{
  private String audioName;
  private String cnDefinition;
  private String content;
  private String enDefn;
  private String enPos;
  private boolean hasAudio;
  private String pron;
  private long vocabularyId;

  public String getAudioName()
  {
    return this.audioName;
  }

  public String getCnDefinition()
  {
    return this.cnDefinition;
  }

  public String getContent()
  {
    return this.content;
  }

  public String getEnDefn()
  {
    return this.enDefn;
  }

  public String getEnPos()
  {
    return this.enPos;
  }

  public String getPron()
  {
    return this.pron;
  }

  public long getVocabularyId()
  {
    return this.vocabularyId;
  }

  public boolean hasAudio()
  {
    return this.hasAudio;
  }

  public VocabularyData setAudioName(String paramString)
  {
    this.audioName = paramString;
    return this;
  }

  public VocabularyData setCnDefinition(String paramString)
  {
    this.cnDefinition = paramString;
    return this;
  }

  public VocabularyData setContent(String paramString)
  {
    this.content = paramString;
    return this;
  }

  public VocabularyData setEnDefn(String paramString)
  {
    this.enDefn = paramString;
    return this;
  }

  public VocabularyData setEnPos(String paramString)
  {
    this.enPos = paramString;
    return this;
  }

  public VocabularyData setHasAudio(boolean paramBoolean)
  {
    this.hasAudio = paramBoolean;
    return this;
  }

  public VocabularyData setPron(String paramString)
  {
    this.pron = paramString;
    return this;
  }

  public VocabularyData setVocabularyId(long paramLong)
  {
    this.vocabularyId = paramLong;
    return this;
  }
}