package com.shanbay.words.model;

import com.shanbay.model.Model;
import java.util.List;

public class ExpReview extends Model
{
  public String audio;
  public String content;
  public long contentId;
  public String definition;
  public Vocabulary.EnDefinition enDefinition;
  public boolean hasAudio;
  public long id;
  public boolean isNew;
  public String pron;
  public int retention;
  public int reviewStatus;
  public int reviewTimes;
  public List<Roots.InnerRoots> roots;
  public long senseId;
  public List<Example> sysExamples;
  public String ukAudio;
  public String usAudio;
}