package com.shanbay.words;

import com.shanbay.app.BaseApplication;

public class WordsApplication extends BaseApplication
{

  public String getApplicationName()
  {
    return "bdc";
  }

  protected void onConfigure()
  {
    com.shanbay.Config.USER_AGENT = getUserAgent();
  }

  public void onCreate()
  {
    super.onCreate();
    WordsClient.getInstance().setCookieStore(getCookieStore());
    WordsSyncClient.getInstance().setCookieStore(getCookieStore());
    Env.init(this);
  }
}