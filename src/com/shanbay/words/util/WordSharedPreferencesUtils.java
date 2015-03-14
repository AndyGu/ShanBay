package com.shanbay.words.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class WordSharedPreferencesUtils
{
  private static final String SCHEME = "shanbay_word";

  public static boolean getBoolean(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (paramContext == null)
      return paramBoolean;
    return paramContext.getSharedPreferences("shanbay_word", 0).getBoolean(paramString, paramBoolean);
  }

  public static int getInt(Context paramContext, String paramString, int paramInt)
  {
    if (paramContext == null)
      return paramInt;
    return paramContext.getSharedPreferences("shanbay_word", 0).getInt(paramString, paramInt);
  }

  public static long getLong(Context paramContext, String paramString, long paramLong)
  {
    if (paramContext == null)
      return paramLong;
    return paramContext.getSharedPreferences("shanbay_word", 0).getLong(paramString, paramLong);
  }

  public static String getString(Context paramContext, String paramString1, String paramString2)
  {
    if (paramContext == null)
      return "";
    return paramContext.getSharedPreferences("shanbay_word", 0).getString(paramString1, paramString2);
  }

  public static void remove(Context paramContext, String paramString)
  {
    paramContext.getSharedPreferences("shanbay_word", 0).edit().remove(paramString).commit();
  }

  public static boolean saveBoolean(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (paramContext == null)
      return false;
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("shanbay_word", 0).edit();
    localEditor.putBoolean(paramString, paramBoolean);
    localEditor.commit();
    return true;
  }

  public static boolean saveInt(Context paramContext, String paramString, int paramInt)
  {
    if (paramContext == null)
      return false;
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("shanbay_word", 0).edit();
    localEditor.putInt(paramString, paramInt);
    localEditor.commit();
    return true;
  }

  public static boolean saveLong(Context paramContext, String paramString, long paramLong)
  {
    if (paramContext == null)
      return false;
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("shanbay_word", 0).edit();
    localEditor.putLong(paramString, paramLong);
    localEditor.commit();
    return true;
  }

  public static boolean saveString(Context paramContext, String paramString1, String paramString2)
  {
    if (paramContext == null)
      return false;
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("shanbay_word", 0).edit();
    localEditor.putString(paramString1, paramString2);
    localEditor.commit();
    return true;
  }
}