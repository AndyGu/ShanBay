package com.shanbay.words;

import com.shanbay.app.BaseApplication;

public class WordsApplication extends BaseApplication
{
  private static final String DSN = "http://34fe84f85b7e42be98aafeb9a11c095c:a72b82faa1f8471eb4043756377d7960@sentry.shanbay.com/34";

  public String getApplicationName()
  {
    return "bdc";
  }

  protected void onConfigure()
  {
    com.shanbay.Config.USER_AGENT = getUserAgent();
    com.shanbay.Config.DSN = "http://34fe84f85b7e42be98aafeb9a11c095c:a72b82faa1f8471eb4043756377d7960@sentry.shanbay.com/34";
  }

  public void onCreate()
  {
    super.onCreate();
    WordsClient.getInstance().setCookieStore(getCookieStore());
    WordsSyncClient.getInstance().setCookieStore(getCookieStore());
    Env.init(this);
  }
}