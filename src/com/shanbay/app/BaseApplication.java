package com.shanbay.app;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.loopj.android.http.PersistentCookieStore;
import com.shanbay.util.Misc;

public abstract class BaseApplication extends Application
{
  private PersistentCookieStore cookieStore;
  private String userAgent;

  protected String buildUserAgent()
  {
    String str = "unknown";
    try
    {
      str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
    }
    catch (PackageManager.NameNotFoundException exception)
    {
    	exception.printStackTrace();
    }
    return getPackageName() + "/" + str + " (Android," + Build.VERSION.RELEASE + "," + Build.MODEL + "," + Build.MANUFACTURER + ")";
  }

  public abstract String getApplicationName();

  public PersistentCookieStore getCookieStore()
  {
    return this.cookieStore;
  }

  public String getUserAgent()
  {
    if (this.userAgent == null)
      this.userAgent = buildUserAgent();
    Log.e("getUserAgent()", "userAgent="+userAgent);
    return this.userAgent;
  }

  protected abstract void onConfigure();

  public void onCreate()
  {
    onConfigure();
//    if (Config.SENTRY_ENABLE)
//      Sentry.init(this, Config.DSN);
    super.onCreate();
    Misc.disableConnectionReuseIfNecessary();
    this.cookieStore = new PersistentCookieStore(this);
  }
}