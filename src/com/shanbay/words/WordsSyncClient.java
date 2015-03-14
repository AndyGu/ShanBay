package com.shanbay.words;

import android.content.Context;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanbay.account.UserCache;
import com.shanbay.http.BaseSyncHttpClient;
import com.shanbay.util.Misc;
import com.shanbay.words.model.ReviewSyncData;
import java.io.File;
import java.util.List;

public class WordsSyncClient extends BaseSyncHttpClient
{
  private static final String API_CHECKIN = "api/v1/checkin/";
  private static final String API_EXAMPLE = "api/v1/bdc/example/";
  private static final String API_NOTE = "api/v1/bdc/note/";
  private static final String API_ROOTS = "api/v1/roots/applet/";
  private static final String API_STATS = "api/v1/bdc/stats/today/";
  private static final String API_SYNC_REVIEW = "/api/v1/bdc/review/sync/";
  private static final String API_USER_APPLET = "api/v1/market/userapplet/";
  private static final String API_VOCABULARY = "api/v1/bdc/vocabulary/";
  private static WordsSyncClient singleton;

  public static WordsSyncClient getInstance()
  {
    try
    {
      if (singleton == null)
        singleton = new WordsSyncClient();
      WordsSyncClient localWordsSyncClient = singleton;
      return localWordsSyncClient;
    }
    finally
    {
    }
  }

  public void applet(Context paramContext, List<Long> paramList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/roots/applet/?ids=" + Misc.idsToQueryString(paramList), null, paramAsyncHttpResponseHandler);
  }

  public void cacheSound(Context paramContext, String paramString1, String paramString2, String paramString3, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    File localFile = Env.getAudioCacheFile(paramString2);
    if (!localFile.exists())
    {
//      get(paramContext, paramString1, null, new WordSoundCacheHandler(localFile, paramString3, paramAsyncHttpResponseHandler));
      return;
    }
//    paramAsyncHttpResponseHandler.onSuccess(0, paramString2);
    d("cache file success: " + paramString1);
  }

  public void checkin(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/checkin/", null, paramAsyncHttpResponseHandler);
  }

  public void deleteExample(Context paramContext, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    delete(paramContext, "api/v1/bdc/example/" + Integer.toString(paramInt), paramAsyncHttpResponseHandler);
  }

  public void example(Context paramContext, List<Long> paramList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/example/?ids=" + Misc.idsToQueryString(paramList), null, paramAsyncHttpResponseHandler);
  }

  public void note(Context paramContext, List<Long> paramList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    if (paramList.size() == 0);
    for (String str = "0"; ; str = Misc.idsToQueryString(paramList))
    {
      get(paramContext, "api/v1/bdc/note/?ids=" + str, null, paramAsyncHttpResponseHandler);
      return;
    }
  }

  public void review(Context paramContext, List<Long> paramList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = Misc.idsToQueryString(paramList);
    get(paramContext, "/api/v1/bdc/review/sync/?ids=" + str, null, paramAsyncHttpResponseHandler);
  }

  public void stats(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/stats/today/", null, paramAsyncHttpResponseHandler);
  }

  public void syncData(Context paramContext, List<ReviewSyncData> paramList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
//    RequestParams localRequestParams = new RequestParams();
//    localRequestParams.put("ids", SyncDataDao.buildIdStr(paramList));
//    localRequestParams.put("review_statuses", SyncDataDao.buildStatusStr(paramList));
//    localRequestParams.put("retentions", SyncDataDao.buildRetentionStr(paramList));
//    localRequestParams.put("seconds", SyncDataDao.buildDeltaSecondsStr(paramList));
//    put(paramContext, "/api/v1/bdc/review/sync/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void sysExamples(Context paramContext, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/example/sys/?vocabulary_id=" + paramInt, null, paramAsyncHttpResponseHandler);
  }

  public void todayReview(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/bdc/review/sync/", null, paramAsyncHttpResponseHandler);
  }

  public void userApplet(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    long l = UserCache.userId(paramContext);
    get(paramContext, "api/v1/market/userapplet/" + l + "/", null, paramAsyncHttpResponseHandler);
  }

  public void vocabulary(Context paramContext, List<Long> paramList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/vocabulary/?ids=" + Misc.idsToQueryString(paramList), null, paramAsyncHttpResponseHandler);
  }
}