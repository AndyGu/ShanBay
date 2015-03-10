package com.shanbay.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import com.loopj.android.http.PersistentCookieStore;
import com.shanbay.Config;
import com.shanbay.sentry.Sentry;
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
      return getPackageName() + "/" + str + " (Android," + Build.VERSION.RELEASE + "," + Build.MODEL + "," + Build.MANUFACTURER + ")";
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        localNameNotFoundException.printStackTrace();
    }
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
    return this.userAgent;
  }

  protected abstract void onConfigure();

  public void onCreate()
  {
    onConfigure();
    if (Config.SENTRY_ENABLE)
      Sentry.init(this, Config.DSN);
    super.onCreate();
    Misc.disableConnectionReuseIfNecessary();
    this.cookieStore = new PersistentCookieStore(this);
  }
}