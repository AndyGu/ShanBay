package com.shanbay.http;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.shanbay.Config;
import com.shanbay.util.LogUtils;
import org.apache.http.client.CookieStore;

public abstract class HttpClient
{
  public static final String SHANBAY_API_VERSION_HEADER_KEY = "SBAY-API-VER";
  public static final String SHANBAY_API_VERSION_HEADER_VAL = "1.0";
  private final String TAG = LogUtils.makeLogTag(getClass());
  private AsyncHttpClient client = new AsyncHttpClient();

  protected HttpClient()
  {
    this.client.setTimeout(Config.TIME_OUT);
//    定义环形重定向(重定向到相同路径)是否被允许
    this.client.getHttpClient().getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
    getAsyncHttpClient().setUserAgent(Config.USER_AGENT);
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

  public void cancelRequest(Context paramContext, boolean paramBoolean)
  {
    getAsyncHttpClient().cancelRequests(paramContext, paramBoolean);
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
    getAsyncHttpClient().addHeader(SHANBAY_API_VERSION_HEADER_KEY, SHANBAY_API_VERSION_HEADER_VAL);
    return getAsyncHttpClient().delete(paramContext, str, paramAsyncHttpResponseHandler);
  }

  public RequestHandle get(Context mContext, String paramStr, RequestParams params, AsyncHttpResponseHandler ahrh)
  {
    String url = getAbsoluteUrl(paramStr);
    Log.e("HttpClient.get()", "url=" + url+" params=" + params);
    getAsyncHttpClient().addHeader(SHANBAY_API_VERSION_HEADER_KEY, SHANBAY_API_VERSION_HEADER_VAL);
    return getAsyncHttpClient().get(mContext, url, params, ahrh);
  }

  public AsyncHttpClient getAsyncHttpClient()
  {
    return this.client;
  }

  public abstract String getHost();

  public RequestHandle post(Context paramContext, String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = getAbsoluteUrl(paramString);
    d("post:" + str);
    d("params:" + paramRequestParams);
    getAsyncHttpClient().addHeader(SHANBAY_API_VERSION_HEADER_KEY, SHANBAY_API_VERSION_HEADER_VAL);
    return getAsyncHttpClient().post(str, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public RequestHandle put(Context paramContext, String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = getAbsoluteUrl(paramString);
    d("put:" + str);
    d("params:" + paramRequestParams);
    getAsyncHttpClient().addHeader(SHANBAY_API_VERSION_HEADER_KEY, SHANBAY_API_VERSION_HEADER_VAL);
    return getAsyncHttpClient().put(str, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public void setCookieStore(CookieStore cookieStore)
  {
    getAsyncHttpClient().setCookieStore(cookieStore);
  }
}