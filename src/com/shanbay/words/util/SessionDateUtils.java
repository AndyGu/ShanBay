package com.shanbay.words.util;

import android.content.Context;
import android.util.Log;

import com.shanbay.account.UserCache;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;

public class SessionDateUtils
{
  private static final String SESSION_DATE = "session_date";

  public static String get(Context paramContext)
  {
    String str = "";
    if (paramContext != null)
      str = WordSharedPreferencesUtils.getString(paramContext, UserCache.userId(paramContext) + "session_date", "");
    Log.e("SessionDateUtils.get()", "str="+str);
    return str;
  }

  public static boolean isExpired(Context paramContext, String paramString)
  {
    return !StringUtils.equals(get(paramContext), paramString);
  }

  public static boolean isValid(Context paramContext)
  {
    String str = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
	Log.e("SimpleDateFormat", "str="+str);
    return (UserCache.isUserLogin(paramContext)) && (!isExpired(paramContext, str));
  }

  public static void remove(Context paramContext)
  {
    if (paramContext != null)
      WordSharedPreferencesUtils.remove(paramContext, UserCache.userId(paramContext) + "session_date");
  }

  public static boolean save(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (StringUtils.isBlank(paramString)))
      return false;
    return WordSharedPreferencesUtils.saveString(paramContext, UserCache.userId(paramContext) + "session_date", paramString);
  }
}