package com.shanbay.words.model;

import com.google.renamedgson.JsonElement;
import com.google.renamedgson.reflect.TypeToken;
import com.shanbay.model.Model;
import java.util.ArrayList;
import java.util.Map;

public class Roots extends Model
{
  public String defin;
  public String dueUrl;
  public long id;
  public boolean isDue;
  public ArrayList<InnerRoots> roots;
  public String targetUrl;
  public String word;

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static Map<Long, Map<String, Roots>> createMap(JsonElement paramJsonElement)
  {
    return (Map)gson().fromJson(paramJsonElement, new TypeToken(){}.getType());
  }

  public class InnerRoots extends Model
  {
    public String content;
    public String meaningCn;
    public String meaningEn;
    public ArrayList<Roots.Representative> representatives;

    public InnerRoots()
    {
    }
  }

  public class Representative extends Model
  {
    public String definition;
    public boolean hasLearned;
    public String note;
    public int vocabularyId;
    public String word;

    public Representative()
    {
    }
  }
}