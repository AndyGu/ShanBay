package com.shanbay.model;

import com.google.renamedgson.FieldNamingPolicy;
import com.google.renamedgson.Gson;
import com.google.renamedgson.GsonBuilder;
import com.google.renamedgson.JsonArray;
import com.google.renamedgson.JsonElement;
import com.google.renamedgson.JsonParser;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Model
{
  public static <T> T create(JsonElement paramJsonElement, Class<T> paramClass)
  {
    return gson().fromJson(paramJsonElement, paramClass);
  }

  public static <T> List<T> createList(JsonElement paramJsonElement, Class<T> paramClass)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramJsonElement.getAsJsonArray().iterator();
    while (localIterator.hasNext())
      localArrayList.add(create((JsonElement)localIterator.next(), paramClass));
    return localArrayList;
  }

  public static <T> T fromJson(String paramString, Class<T> paramClass)
  {
    return gson().fromJson(paramString, paramClass);
  }

  public static <T> List<T> fromJsonToList(String paramString, Class<T> paramClass)
  {
    return createList(new JsonParser().parse(paramString), paramClass);
  }

  public static <K, V> Map<K, V> fromJsonToMap(String paramString, Type paramType)
  {
    return (Map)gson().fromJson(paramString, paramType);
  }

  protected static Gson gson()
  {
    return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
  }

  public static String toJson(Object paramObject)
  {
    return gson().toJson(paramObject);
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = System.getProperty("line.separator");
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append(" Object {");
    localStringBuilder.append(str);
    Field[] arrayOfField = getClass().getDeclaredFields();
    int i = arrayOfField.length;
    int j = 0;
      while (j < i)
      {
        Field localField = arrayOfField[j];
        localStringBuilder.append("  ");
        try
        {
          localStringBuilder.append(localField.getName());
          localStringBuilder.append(": ");
          localStringBuilder.append(localField.get(this));
          localStringBuilder.append(str);
          j++;
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
            System.out.println(localIllegalAccessException);
        }
      }
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}