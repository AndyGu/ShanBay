package com.shanbay.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.shanbay.Config;
import java.util.Iterator;
import java.util.Set;

public class LogUtils
{
  private static final int LOG_PREFIX_LENGTH = 0;
  private static final int MAX_LOG_TAG_LENGTH = 23;

  public static void LOGD(String paramString1, String paramString2)
  {
    if (Config.DEBUG)
      Log.d(paramString1, paramString2);
  }

  public static void LOGD(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (Config.DEBUG)
      Log.d(paramString1, paramString2, paramThrowable);
  }

  public static void LOGE(String paramString1, String paramString2)
  {
    Log.e(paramString1, paramString2);
  }

  public static void LOGE(String paramString1, String paramString2, Throwable paramThrowable)
  {
    Log.e(paramString1, paramString2, paramThrowable);
  }

  public static void LOGI(String paramString1, String paramString2)
  {
    Log.i(paramString1, paramString2);
  }

  public static void LOGI(String paramString1, String paramString2, Throwable paramThrowable)
  {
    Log.i(paramString1, paramString2, paramThrowable);
  }

  public static void LOGV(String paramString1, String paramString2)
  {
    if (Config.DEBUG)
      Log.v(paramString1, paramString2);
  }

  public static void LOGV(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (Config.DEBUG)
      Log.v(paramString1, paramString2, paramThrowable);
  }

  public static void LOGW(String paramString1, String paramString2)
  {
    Log.w(paramString1, paramString2);
  }

  public static void LOGW(String paramString1, String paramString2, Throwable paramThrowable)
  {
    Log.w(paramString1, paramString2, paramThrowable);
  }

  public static String dumpIntent(Intent paramIntent)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramIntent == null)
    	return null;
    
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle != null)
      {
        localStringBuilder.append("Dumping Intent start");
        localStringBuilder.append("\n");
        localStringBuilder.append("Data:" + paramIntent.getData());
        localStringBuilder.append("\n");

        Iterator localIterator = localBundle.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          localStringBuilder.append("[" + str + "=" + localBundle.get(str) + "]");
          localStringBuilder.append("\n");
        }
        localStringBuilder.append("Dumping Intent end");
      }
      return localStringBuilder.toString();
  }

  public static String getMethodName()
  {
    return java.lang.Thread.currentThread().getStackTrace()[3].getMethodName();
  }

  public static String makeLogTag(Class paramClass)
  {
    return makeLogTag(paramClass.getSimpleName());
  }

  public static String makeLogTag(String paramString)
  {
    String str = Config.LOG_PREFIX;
    if (paramString.length() > 23 - LOG_PREFIX_LENGTH)
      return str + paramString.substring(0, -1 + (23 - LOG_PREFIX_LENGTH));
    return str + paramString;
  }
}