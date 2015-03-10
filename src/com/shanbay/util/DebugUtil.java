package com.shanbay.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.apache.commons.lang.StringUtils;

public class DebugUtil
{
  private static final String DEBUG_KEY = "debug";
  private static final String DEFAULT_DOMAIN = "www.shanbay.com";
  private static final String DOMAIN_KEY = "domain";
  private static final String SCHEME = "shanbay_common";

  public static String getDomain(Context context)
  {
    if (context != null){
        return context.getSharedPreferences(SCHEME, 0).getString(DOMAIN_KEY, DEFAULT_DOMAIN);
    }
    return DEFAULT_DOMAIN;
  }

  public static boolean isOff(Context paramContext)
  {
    return (paramContext == null) || (!paramContext.getSharedPreferences("shanbay_common", 0).getBoolean("debug", false));
  }

  public static boolean isOn(Context context)
  {
    if (context != null){
        return context.getSharedPreferences(SCHEME, 0).getBoolean(DEBUG_KEY, false) == true;
    }
	return false;
  }

  public static void setDebugStatus(Context paramContext, boolean paramBoolean)
  {
    if (paramContext != null)
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("shanbay_common", 0).edit();
      localEditor.putBoolean("debug", paramBoolean);
      localEditor.commit();
    }
  }

  public static void setDomain(Context paramContext, String paramString)
  {
    if ((paramContext != null) && (StringUtils.isNotBlank(paramString)))
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("shanbay_common", 0).edit();
      localEditor.putString("domain", paramString);
      localEditor.commit();
    }
  }
}