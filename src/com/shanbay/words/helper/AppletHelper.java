package com.shanbay.words.helper;

import android.content.Context;
import com.shanbay.words.util.AppletUtil;

public class AppletHelper
{
  private static int appletStatus = -1;
  private static AppletHelper singleton = new AppletHelper();

  public static final AppletHelper getInstance()
  {
    return singleton;
  }

  public int getAppletStatus(Context paramContext, String paramString)
  {
    if (appletStatus == -1)
      appletStatus = AppletUtil.getAppletStatus(paramContext, paramString);
    return appletStatus;
  }

  public void reset()
  {
    appletStatus = -1;
  }
}