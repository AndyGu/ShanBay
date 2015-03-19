package com.shanbay.words.util;

import android.content.Context;
import com.shanbay.account.UserCache;
import com.shanbay.model.Model;
import com.shanbay.words.model.Applet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.helper.StringUtil;

public class AppletUtil
{
  private static final String APPLET_NAME = "com.shanbay.words.applet";
  public static final String APPLET_NAME_COLLINS = "collins";
  public static final String APPLET_NAME_ROOTS = "roots";
  public static final int APPLET_STATUS_DISABLE = 3;
  public static final int APPLET_STATUS_NORMAL = 2;
  public static final int APPLET_STATUS_SUSPEND = 4;
  public static final int APPLET_STATUS_TRIAL = 1;
  public static final int APPLET_STATUS_UNUSE = 0;

  public static boolean getAppletIsDisable(Context paramContext, String paramString)
  {
    long l = UserCache.userId(paramContext);
    return WordSharedPreferencesUtils.getBoolean(paramContext, paramString + l, false);
  }

  public static List<Applet> getAppletList(Context paramContext)
  {
    long l = UserCache.userId(paramContext);
    String str = WordSharedPreferencesUtils.getString(paramContext, "com.shanbay.words.applet" + l, "");
    if (StringUtil.isBlank(str))
      return new ArrayList();
    return Model.fromJsonToList(str, Applet.class);
  }

  public static int getAppletStatus(Context paramContext, String paramString)
  {
    Iterator localIterator = getAppletList(paramContext).iterator();
    while (localIterator.hasNext())
    {
      Applet localApplet = (Applet)localIterator.next();
      if (paramString.equals(localApplet.codeName))
        return localApplet.state;
    }
    return 0;
  }

//  public static boolean isStateChange(int paramInt1, int paramInt2)
//  {
//    int i = 1;
//    switch (paramInt1)
//    {
//    default:
//    case 0:
//    case 3:
//    case 4:
//    case 1:
//    case 2:
//    }
//    do
//    {
//      i = 0;
//      do
//      {
//        do
//          return i;
//        while (paramInt2 == i);
//        if (paramInt2 != 2)
//          break;
//        return i;
//      }
//      while ((paramInt2 == 0) || (paramInt2 == 4));
//    }
//    while (paramInt2 != 3);
//    return i;
//  }

  public static void saveApplet(Context paramContext, List<Applet> paramList)
  {
    if (paramContext == null)
      return;
    long l = UserCache.userId(paramContext);
    WordSharedPreferencesUtils.saveString(paramContext, "com.shanbay.words.applet" + l, Model.toJson(paramList));
  }

  public static void saveAppletDisable(Context paramContext, String paramString)
  {
    if (paramContext == null)
      return;
    long l = UserCache.userId(paramContext);
    WordSharedPreferencesUtils.saveBoolean(paramContext, paramString + l, true);
  }
}