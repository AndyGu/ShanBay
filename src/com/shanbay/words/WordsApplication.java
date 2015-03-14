package com.shanbay.words;

import android.util.Log;
import com.shanbay.Config;
import com.shanbay.app.BaseApplication;

public class WordsApplication extends BaseApplication
{

  public String getApplicationName()
  {
    return "bdc";
  }

  protected void onConfigure()
  {
    Config.USER_AGENT = getUserAgent();
    Log.e("onConfigure", "Config.USER_AGENT="+Config.USER_AGENT);
  }

  public void onCreate()
  {
    super.onCreate();
    Log.e("WordsApplication.onCreate", "getCookieStore()="+getCookieStore().toString());
    WordsClient.getInstance().setCookieStore(getCookieStore());
    WordsSyncClient.getInstance().setCookieStore(getCookieStore());
    Env.init(this);
  }
}