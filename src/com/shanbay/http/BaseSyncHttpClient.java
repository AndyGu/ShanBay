package com.shanbay.http;

import android.content.Context;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.shanbay.Config;
import com.shanbay.util.LogUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.params.HttpParams;

public abstract class BaseSyncHttpClient
{
  private static String HOST = "http://www.shanbay.com/";
  private static final String SHANBAY_API_VERSION_HEADER_KEY = "SBAY-API-VER";
  private static final String SHANBAY_API_VERSION_HEADER_VAL = "1.0";
  private final String TAG = LogUtils.makeLogTag(getClass());
  private SyncHttpClient client = new SyncHttpClient()
  {
    public String onRequestFailed(Throwable paramAnonymousThrowable, String paramAnonymousString)
    {
      return null;
    }
  };

  protected BaseSyncHttpClient()
  {
    this.client.setTimeout(Config.TIME_OUT);
    this.client.getHttpClient().getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
    getSyncHttpClient().setUserAgent(Config.USER_AGENT);
  }

  private String getAbsoluteUrl(String paramString)
  {
    return getAbsoluteUrl(paramString, getHost());
  }

  public static String getAbsoluteUrl(String paramString1, String paramString2)
  {
    if ((paramString1.startsWith("http://")) || (paramString1.startsWith("https://")))
      return paramString1;
    if (paramString1.startsWith("/"))
      return paramString2 + paramString1.substring(1);
    return paramString2 + paramString1;
  }

  private String getHost()
  {
    return HOST;
  }

  public static void setDomain(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0))
      return;
    HOST = "http://" + paramString + "/";
  }

  public void cancelRequest(Context paramContext, boolean paramBoolean)
  {
    getSyncHttpClient().cancelRequests(paramContext, paramBoolean);
  }

  protected void d(String paramString)
  {
    LogUtils.LOGD(this.TAG, paramString);
  }

  protected void d(String paramString, Throwable paramThrowable)
  {
    LogUtils.LOGD(this.TAG, paramString, paramThrowable);
  }

  public RequestHandle delete(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = getAbsoluteUrl(paramString);
    d("delete:" + str);
    getSyncHttpClient().addHeader("SBAY-API-VER", "1.0");
    return getSyncHttpClient().delete(paramContext, str, paramAsyncHttpResponseHandler);
  }

  public RequestHandle get(Context paramContext, String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = getAbsoluteUrl(paramString);
    d("get:" + str);
    d("params:" + paramRequestParams);
    getSyncHttpClient().addHeader("SBAY-API-VER", "1.0");
    return getSyncHttpClient().get(paramContext, str, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public SyncHttpClient getSyncHttpClient()
  {
    return this.client;
  }

  public RequestHandle post(Context paramContext, String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = getAbsoluteUrl(paramString);
    d("post:" + str);
    d("params:" + paramRequestParams);
    getSyncHttpClient().addHeader("SBAY-API-VER", "1.0");
    return getSyncHttpClient().post(str, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public RequestHandle put(Context paramContext, String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = getAbsoluteUrl(paramString);
    d("put:" + str);
    d("params:" + paramRequestParams);
    getSyncHttpClient().addHeader("SBAY-API-VER", "1.0");
    return getSyncHttpClient().put(str, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public void setCookieStore(CookieStore paramCookieStore)
  {
    getSyncHttpClient().setCookieStore(paramCookieStore);
  }
}