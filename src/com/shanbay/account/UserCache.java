package com.shanbay.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.shanbay.model.Model;
import com.shanbay.model.User;

public class UserCache
{
  private static final String PREFS_NAME = "com.shanbay.common";
  private static final String USER_KEY = "user_info";

  public static void clear(Context paramContext)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.shanbay.common", 0).edit();
    localEditor.remove("user_info");
    localEditor.commit();
  }

  public static boolean isUserLogin(Context paramContext)
  {
    String str = paramContext.getSharedPreferences("com.shanbay.common", 0).getString("user_info", null);
    Log.e("isUserLogin", "isUserLogin="+str);
    boolean bool = false;
    if (str != null)
      bool = true;
    return bool;
  }

  public static void update(Context paramContext, User paramUser)
  {
    if (paramUser != null)
    {
      String str = Model.toJson(paramUser);
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.shanbay.common", 0).edit();
      localEditor.putString("user_info", str);
      localEditor.commit();
    }
  }

  public static void updateAvatar(Context paramContext, String paramString)
  {
    User localUser = user(paramContext);
    if (localUser != null)
    {
      localUser.avatar = paramString;
      update(paramContext, localUser);
    }
  }

  public static User user(Context paramContext)
  {
    User localUser = new User();
    if (isUserLogin(paramContext))
      localUser = (User)Model.fromJson(paramContext.getSharedPreferences("com.shanbay.common", 0).getString("user_info", null), User.class);
    return localUser;
  }

  public static long userId(Context paramContext)
  {
    long l = -1L;
    if (user(paramContext) != null)
      l = user(paramContext).userId;
    return l;
  }
}