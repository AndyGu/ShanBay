package com.shanbay.words;

import android.content.Context;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanbay.account.UserCache;
import com.shanbay.http.APIClient;
import com.shanbay.util.Misc;
//import com.shanbay.words.dao.SyncDataDao;
import com.shanbay.words.model.ReviewSyncData;
import com.shanbay.words.util.AudioUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class WordsClient extends APIClient
{
  static final String API_APPLET_ROOTS = "api/v1/roots/applet/";
  static final String API_BUY_APPLET_TEMPLATE = "/api/v1/market/%s/buy/";
  static final String API_CHECKIN = "api/v1/checkin/";
  static final String API_CHECKIN_LIST = "api/v1/checkin/user/";
  static final String API_CHECKIN_MAKEUP = "/api/v1/checkin/makeup/";
  static final String API_CHECKIN_SESSION_DATE = "api/v1/checkin/date/";
  static final String API_COINS_USER_ACCOUNT = "/api/v1/coins/useraccount/";
  static final String API_DEFINITIONS = "api/v1/bdc/vocabulary/definitions/";
  static final String API_EXAMPLE = "api/v1/bdc/example/";
  static final String API_GET_APPLET_PRICE = "/api/v1/market/applet/price/";
  static final String API_INIT_CONTENT_VERSION = "/api/v1/bdc/contentversion/?type=";
  static final String API_NOTE = "api/v1/bdc/note/";
  static final String API_NOTE_COLLECT = "api/v1/bdc/note/collect/";
  static final String API_RECOMMEND = "api/v1/mobile/promotion/";
  static final String API_REVIEW = "api/v1/bdc/review/";
  static final String API_REVIEW_INDEX = "/api/v1/bdc/review/index/";
  static final String API_STATS = "api/v1/bdc/stats/today/";
  static final String API_SYNC_REVIEW = "/api/v1/bdc/review/sync/";
  static final String API_TODAY_REVIEW = "/api/v1/bdc/review/sync/";
  static final String API_USER_APPLET = "api/v1/market/userapplet/";
  static final String API_USER_SETTING = "api/v1/bdc/setting/";
  static final String API_VACABULARY_TEST = "/api/v1/vocabtest/wechat/";
  static final String API_VOCABULARY = "api/v1/bdc/vocabulary/";
  static final String API_WORDBOOK_CATEGORIES = "api/v1/wordbook/categories/";
  static final String API_WORDBOOK_CATEGORY = "api/v1/wordbook/category/{id}/";
  static final String API_WORDBOOK_USER = "api/v1/wordbook/userwordbook/";
  static final String API_WORDS_CHECKIN_AWARD = "/api/v1/badger/award/checkin/";
  static final String API_WORDS_EXPERIENCE = "/api/v1/bdc/experience/?roots={root}&collins={collin}&category={category}";
  static final String API_WORDS_EXPERIENCE_CATEGORY = "/api/v1/bdc/experience/category/";
  static final String API_WORDS_FAQ = "/api/v1/help/faq/";
  static final String API_WORDS_QUOTE = "/api/v1/quote/";
  private static WordsClient singleton;

  public static String getAbsoluteUrl(String paramString)
  {
    if ((paramString.startsWith("http://")) || (paramString.startsWith("https://")))
      return paramString;
    if (paramString.startsWith("/"))
      return HOST + paramString.substring(1);
    return HOST + paramString;
  }

  public static WordsClient getInstance()
  {
    if (singleton == null)
      singleton = new WordsClient();
    return singleton;
  }

  public void activateWordbook(Context paramContext, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("wordbook_id", Long.toString(paramInt));
    localRequestParams.put("action", "activate");
    put(paramContext, "api/v1/wordbook/userwordbook/update/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void buyApplet(Context paramContext, String paramString, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = String.format("/api/v1/market/%s/buy/", new Object[] { paramString });
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("price_id", Long.toString(paramLong));
    post(paramContext, str, localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void cacheSound(String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    if (StringUtils.isNotBlank(paramString))
      cacheSound(paramString, Env.getAudioCacheFile(AudioUtil.getFilenameByUrl(paramString)), paramAsyncHttpResponseHandler);
  }

  public void checkin(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/checkin/", null, paramAsyncHttpResponseHandler);
  }

  public void checkin(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("user_note", paramString);
    post(paramContext, "api/v1/checkin/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void checkinDiary(Context paramContext, long paramLong, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("checkin_id", Long.toString(paramLong));
    localRequestParams.put("user_note", paramString);
    put(paramContext, "api/v1/checkin/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void checkinList(Context paramContext, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    long l = UserCache.userId(paramContext);
    get(paramContext, "api/v1/checkin/user/" + l + "/?page=" + paramInt, null, paramAsyncHttpResponseHandler);
  }

  public void checkinMakeup(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/checkin/makeup/", null, paramAsyncHttpResponseHandler);
  }

  public void checkinMakeup(Context paramContext, String paramString1, String paramString2, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("date", paramString1);
    localRequestParams.put("note", paramString2);
    post(paramContext, "/api/v1/checkin/makeup/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void collectNote(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("note_id", Long.toString(paramLong));
    post(paramContext, "api/v1/bdc/note/collect/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void collinsDefinition(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/vocabulary/definitions/" + paramLong, null, paramAsyncHttpResponseHandler);
  }

  public void deleteNote(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    delete(paramContext, "api/v1/bdc/note/" + Long.toString(paramLong), paramAsyncHttpResponseHandler);
  }

  public void exampleInWords(Context paramContext, ArrayList<Long> paramArrayList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/example/?ids=" + Misc.idsToQueryString(paramArrayList), null, paramAsyncHttpResponseHandler);
  }

  public void experienceCategory(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/bdc/experience/category/", null, paramAsyncHttpResponseHandler);
  }

  public void experienceData(Context paramContext, int paramInt1, int paramInt2, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/bdc/experience/?roots={root}&collins={collin}&category={category}".replace("{root}", Integer.toString(paramInt1)).replace("{collin}", Integer.toString(paramInt2)).replace("{category}", Long.toString(paramLong)), null, paramAsyncHttpResponseHandler);
  }

  public void getAppletPrices(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("iap", "0");
    localRequestParams.put("code", paramString);
    get(paramContext, "/api/v1/market/applet/price/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void getCheckinAward(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/badger/award/checkin/", null, paramAsyncHttpResponseHandler);
  }

  public void getCoinsAccount(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/coins/useraccount/", null, paramAsyncHttpResponseHandler);
  }

  public void getFAQ(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/help/faq/", null, paramAsyncHttpResponseHandler);
  }

  public void getQuote(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/quote/", null, paramAsyncHttpResponseHandler);
  }

  public void getRoots(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/roots/applet/?ids=" + paramLong, null, paramAsyncHttpResponseHandler);
  }

  public void getVocabularyTestList(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/vocabtest/wechat/", null, paramAsyncHttpResponseHandler);
  }

  public void initContentVersion(Context paramContext, String paramString, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/bdc/contentversion/?type=" + paramString + "&version=" + String.valueOf(paramLong), null, paramAsyncHttpResponseHandler);
  }

  public void note(Context paramContext, Collection<Long> paramCollection, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    if (paramCollection.size() == 0);
    for (String str = "0"; ; str = Misc.idsToQueryString((List)paramCollection))
    {
      get(paramContext, "api/v1/bdc/note/?ids=" + str, null, paramAsyncHttpResponseHandler);
      return;
    }
  }

  public void noteByVocabulary(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/note/?vocabulary_id=" + paramLong, null, paramAsyncHttpResponseHandler);
  }

  public void postVocabularyTestResult(Context paramContext, List<Long> paramList1, List<Long> paramList2, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("correct_words", Misc.idsToQueryString(paramList1));
    localRequestParams.put("words", Misc.idsToQueryString(paramList2));
    post(paramContext, "/api/v1/vocabtest/wechat/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void putPlayMode(Context paramContext, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("auto_play_mode", String.valueOf(paramInt));
    put(paramContext, "api/v1/bdc/setting/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void putUserSetting(Context paramContext, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("target_level", String.valueOf(paramInt));
    put(paramContext, "api/v1/bdc/setting/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void reviewIndex(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/bdc/review/index/?update_type=" + paramString, null, paramAsyncHttpResponseHandler);
  }

  public void sessionDate(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/checkin/date/", null, paramAsyncHttpResponseHandler);
  }

  public void stats(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/stats/today/", null, paramAsyncHttpResponseHandler);
  }

  public void subscribeWordbook(Context paramContext, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("wordbook_id", Long.toString(paramInt));
    localRequestParams.put("action", "subscribe");
    put(paramContext, "api/v1/wordbook/userwordbook/update/", localRequestParams, paramAsyncHttpResponseHandler);
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

  public void testDownloadMedia(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, paramString, null, paramAsyncHttpResponseHandler);
  }

  public void updateCollinsDefinition(Context paramContext, long paramLong1, long paramLong2, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = "api/v1/bdc/learning/" + paramLong1;
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("sense", String.valueOf(paramLong2));
    put(paramContext, str, localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void updateReviewIndex(Context paramContext, String paramString, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("update_type", paramString);
    localRequestParams.put("index", String.valueOf(paramInt));
    put(paramContext, "/api/v1/bdc/review/index/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void userApplet(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    long l = UserCache.userId(paramContext);
    get(paramContext, "api/v1/market/userapplet/" + l + "/", null, paramAsyncHttpResponseHandler);
  }

  public void userSetting(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/setting/" + paramLong, null, paramAsyncHttpResponseHandler);
  }

  public void userWordbook(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    long l = UserCache.userId(paramContext);
    get(paramContext, "api/v1/wordbook/userwordbook/" + l + "/", null, paramAsyncHttpResponseHandler);
  }

  public void vocabularyInWords(Context paramContext, ArrayList<Long> paramArrayList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/vocabulary/?ids=" + Misc.idsToQueryString(paramArrayList), null, paramAsyncHttpResponseHandler);
  }

  public void wordbookByCategory(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/wordbook/category/{id}/".replace("{id}", paramLong + ""), null, paramAsyncHttpResponseHandler);
  }

  public void wordbookCategories(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/wordbook/categories/", null, paramAsyncHttpResponseHandler);
  }
}