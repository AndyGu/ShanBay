package com.shanbay.http;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanbay.model.User;
import com.shanbay.util.Misc;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class APIClient extends HttpClient
{
  protected static final String API_ACCONT = "api/v1/account/";
  static final String API_BDC_STATS = "api/v1/bdc/stats/";
  protected static final String API_BOOK_COMMENT = "/api/v1/book/comment/{app_name}/";
  protected static final String API_BROADCAST = "api/v1/broadcast/";
  protected static final String API_CHARGE_ALIPAY = "api/v1/coins/charge/alipay/";
  protected static final String API_CHECKIN = "api/v1/checkin/";
  protected static final String API_CHECKIN_COMMENT = "/api/v1/checkin/{checkin_id}/comment/?page={page}";
  protected static final String API_CHECKIN_DETAIL = "/api/v1/checkin/{user_id}/{checkin_id}/";
  protected static final String API_CHECKIN_FAVOR = "/api/v1/checkin/{checkin_id}/favor/?page={page}&ipp={ipp}";
  protected static final String API_CHECKIN_LIST = "api/v1/checkin/user/";
  protected static final String API_CHECKIN_MAKEUP = "/api/v1/checkin/makeup/";
  protected static final String API_CHECKIN_MONTH_CALENDAR_LATEST = "/api/v1/checkin/calendar/latest/";
  protected static final String API_CHECKIN_POST_COMMENT = "/api/v1/checkin/{checkin_id}/comment/";
  protected static final String API_CHECKIN_POST_FAVOR = "/api/v1/checkin/{checkin_id}/favor/";
  protected static final String API_CHECKIN_SHARE = "/api/v1/checkin/share/";
  protected static final String API_COMMUNITY_AWARD = "/api/v1/badger/award/{user_id}";
  protected static final String API_COMMUNITY_GROUP_MINE_INFO = "/api/v1/team/member/";
  protected static final String API_COMMUNITY_GROUP_NOTIFICATION = "/api/v1/team/notification/";
  protected static final String API_COMMUNITY_POINT = "/api/v1/points/{user_id}";
  protected static final String API_COMMUNITY_TEAM = "/api/v1/team/{team_id}/";
  protected static final String API_COMMUNITY_TEAM_MEMBER = "/api/v1/team/member/?u={user_id}";
  protected static final String API_DATE = "api/v1/checkin/date/";
  protected static final String API_DISPLAY_ANDROID = "/api/v1/display/devices/android/";
  protected static final String API_EDIT_BOOK_COMMENT = "api/v1/book/comment/{comment_id}/";
  protected static final String API_EXAMPLE = "api/v1/bdc/example/";
  protected static final String API_FEEDBACK = "api/v1/feedback/";
  protected static final String API_FEEDBACK_REPLY = "api/v1/feedback/reply/";
  protected static final String API_GET_BADGE = "/api/v1/badger/award/";
  protected static final String API_HELP_CATEGORY = "/api/v1/help/category/";
  protected static final String API_HELP_DETAIL = "/api/v1/help/article/";
  protected static final String API_INIT = "api/v1/bdc/init/";
  protected static final String API_LATEST_BROADCAST = "api/v1/broadcast/latest/";
  protected static final String API_LATEST_FEEDBACK = "api/v1/feedback/";
  protected static final String API_LEARNING = "api/v1/bdc/learning/";
  protected static final String API_LIST_BOOK_COMMENTS = "/api/v1/book/comment/{app_name}/{book_id}/{comment_type}";
  protected static final String API_LOGIN = "api/v1/account/login/";
  protected static final String API_LOGOUT = "api/v1/account/logout/";
  protected static final String API_NOTIFICATION = "api/v1/notification/";
  protected static final String API_QUOTA = "api/v1/quota/applet/";
  protected static final String API_QUOTE = "/api/v1/quote/";
  protected static final String API_READ_SHARE = "api/v1/read/article/share/";
  protected static final String API_RECEIVE_BADGE = "/api/v1/badger/award/badge/{badge_id}";
  protected static final String API_SEARCH = "api/v1/bdc/search/";
  protected static final String API_SHARE = "api/v1/common/share/";
  protected static final String API_SHARE_IMAGE = "/api/v1/mobile/product/image/android";
  protected static final String API_SHARE_LISTEN_ARTICLE = "api/v1/listen/article/share/{service}/{article_id}";
  protected static final String API_SHORT_MESSAGE = "/api/v1/message/";
  static final String API_STATS = "api/v1/read/stats/";
  protected static final String API_USER = "api/v1/common/user/";
  protected static final String API_USER_ACCOUNT = "api/v1/coins/useraccount/";
  protected static final String API_USER_CHECKIN_CALENDAR = "/api/v1/checkin/user/{user_id}/?v=2&year={year}&month={month}";
  protected static final String API_USER_CHECKIN_DAYS = "/api/v1/checkin/user/{user_id}";
  protected static final String API_VOCABULARY = "api/v1/bdc/vocabulary/";
  public static String DOMAIN = "www.shanbay.com";
  public static String HOST = "http://www.shanbay.com/";
  private static APIClient singleton;

  public static APIClient getInstance()
  {
    if (singleton == null)
      singleton = new APIClient();
    return singleton;
  }

  public static void setDomain(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0))
      return;
    DOMAIN = paramString;
    HOST = "http://" + paramString + "/";
  }

  public void add(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("id", Long.toString(paramLong));
    post(paramContext, "api/v1/bdc/learning/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void award(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/badger/award/{user_id}".replace("{user_id}", Long.toString(paramLong)), null, paramAsyncHttpResponseHandler);
  }

  public void bookComment(Context paramContext, String paramString1, long paramLong, String paramString2, String paramString3, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = "/api/v1/book/comment/{app_name}/".replace("{app_name}", paramString1);
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("book_id", Long.toString(paramLong));
    localRequestParams.put("content", paramString3);
    localRequestParams.put("title", paramString2);
    post(paramContext, str, localRequestParams, paramAsyncHttpResponseHandler);
  }

  @Deprecated
  public void broadcast(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/broadcast/", null, paramAsyncHttpResponseHandler);
  }

  public void cacheSound(String paramString, File paramFile, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
//	  需补充
//    if (!paramFile.exists())
//    {
//      getAsyncHttpClient().get(paramString, new SoundCacheResponseHandler(paramFile, paramAsyncHttpResponseHandler));
//      d("cache file fail: " + paramString);
//      return;
//    }
//    paramAsyncHttpResponseHandler.onSuccess(0, paramFile.getAbsolutePath());
//    d("cache file success: " + paramString);
  }

  public void chargeAlipay(Context paramContext, float paramFloat, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    StringBuilder localStringBuilder = new StringBuilder().append("api/v1/coins/charge/alipay/?value=");
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Float.valueOf(paramFloat);
    get(paramContext, String.format("%.2f", arrayOfObject), null, paramAsyncHttpResponseHandler);
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

  public void checkinComment(Context paramContext, int paramInt, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/checkin/{checkin_id}/comment/?page={page}".replace("{page}", paramInt + "").replace("{checkin_id}", paramLong + ""), null, paramAsyncHttpResponseHandler);
  }

  public void checkinCommentList(Context paramContext, long paramLong, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/checkin/user/" + paramLong + "/?comment&page=" + paramInt, null, paramAsyncHttpResponseHandler);
  }

  public void checkinDetail(Context paramContext, long paramLong1, long paramLong2, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/checkin/{user_id}/{checkin_id}/".replace("{user_id}", paramLong1 + "").replace("{checkin_id}", paramLong2 + ""), null, paramAsyncHttpResponseHandler);
  }

  public void checkinDiary(Context paramContext, long paramLong, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("checkin_id", Long.toString(paramLong));
    localRequestParams.put("user_note", paramString);
    put(paramContext, "api/v1/checkin/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void checkinFavor(Context paramContext, int paramInt1, int paramInt2, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/checkin/{checkin_id}/favor/?page={page}&ipp={ipp}".replace("{page}", paramInt1 + "").replace("{ipp}", paramInt2 + "").replace("{checkin_id}", paramLong + ""), null, paramAsyncHttpResponseHandler);
  }

  public void checkinFavor(Context paramContext, long paramLong1, long paramLong2, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = "/api/v1/checkin/{checkin_id}/favor/".replace("{checkin_id}", paramLong1 + "");
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("checkin_user_id", Long.toString(paramLong2));
    post(paramContext, str, localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void checkinFavorList(Context paramContext, long paramLong, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/checkin/user/" + paramLong + "/?favor&page=" + paramInt, null, paramAsyncHttpResponseHandler);
  }

  public void checkinList(Context paramContext, long paramLong, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/checkin/user/" + paramLong + "/?page=" + paramInt, null, paramAsyncHttpResponseHandler);
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

  public void checkinMonthCalendarLatest(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/checkin/calendar/latest/", null, paramAsyncHttpResponseHandler);
  }

  public void createAccount(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("username", paramString1);
    localRequestParams.put("email", paramString2);
    localRequestParams.put("password1", paramString3);
    localRequestParams.put("password2", paramString4);
    localRequestParams.put("agree", "1");
    post(paramContext, "api/v1/account/create/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void date(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/checkin/date/", null, paramAsyncHttpResponseHandler);
  }

  public void deleteExample(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    delete(paramContext, "api/v1/bdc/example/" + Long.toString(paramLong), paramAsyncHttpResponseHandler);
  }

  public void deleteShortMessage(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    delete(paramContext, "/api/v1/message/" + paramLong + "/", paramAsyncHttpResponseHandler);
  }

  public void displayAndroid(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/display/devices/android/", null, paramAsyncHttpResponseHandler);
  }

  public void editBookComment(Context paramContext, long paramLong, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = "api/v1/book/comment/{comment_id}/".replace("{comment_id}", paramLong + "");
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("content", paramString);
    put(paramContext, str, localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void example(Context paramContext, ArrayList<Long> paramArrayList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/example/?ids=" + Misc.idsToQueryString(paramArrayList), null, paramAsyncHttpResponseHandler);
  }

  public void feedback(Context paramContext, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/feedback/?id=" + paramInt, null, paramAsyncHttpResponseHandler);
  }

  public void feedbackReplied(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/feedback/replied/", null, paramAsyncHttpResponseHandler);
  }

  public void feedbackReply(Context paramContext, int paramInt, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("feedback_id", Integer.toString(paramInt));
    localRequestParams.put("content", paramString);
    post(paramContext, "api/v1/feedback/reply/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void forgot(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = "api/v1/bdc/learning/" + paramLong;
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("retention", "1");
    put(paramContext, str, localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void getBadge(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/badger/award/", null, paramAsyncHttpResponseHandler);
  }

  public String getHost()
  {
    return HOST;
  }

  public void getNewBadge(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/badger/award/?deferred", null, paramAsyncHttpResponseHandler);
  }

  public void groupNotification(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/team/notification/", null, paramAsyncHttpResponseHandler);
  }

  public void helpCategory(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/help/category/", null, paramAsyncHttpResponseHandler);
  }

  public void helpDetail(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/help/article/" + paramString, null, paramAsyncHttpResponseHandler);
  }

  public void ignoreNotification(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    put(paramContext, "api/v1/notification/" + paramLong + "/", null, paramAsyncHttpResponseHandler);
  }

  public void init(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/init/", null, paramAsyncHttpResponseHandler);
  }

  public void latestBroadcast(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/broadcast/latest/", null, paramAsyncHttpResponseHandler);
  }

  public void latestFeedback(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/feedback/", null, paramAsyncHttpResponseHandler);
  }

  public void listBookComments(Context paramContext, String paramString, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/book/comment/{app_name}/{book_id}/{comment_type}".replace("{app_name}", paramString).replace("{book_id}", paramLong + "").replace("{comment_type}", "all"), null, paramAsyncHttpResponseHandler);
  }

  public void listMyBookComment(Context paramContext, String paramString, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/book/comment/{app_name}/{book_id}/{comment_type}".replace("{app_name}", paramString).replace("{book_id}", paramLong + "").replace("{comment_type}", "mine"), null, paramAsyncHttpResponseHandler);
  }

  public void login(Context paramContext, String paramString1, String paramString2, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("username", paramString1);
    localRequestParams.put("password", paramString2);
    put(paramContext, "api/v1/account/login/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void logout(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    put(paramContext, "api/v1/account/logout/", new RequestParams(), paramAsyncHttpResponseHandler);
  }

  public void myGroupInfo(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/team/member/", null, paramAsyncHttpResponseHandler);
  }

  public void notification(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/notification/", null, paramAsyncHttpResponseHandler);
  }

  public void pass(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = "api/v1/bdc/learning/" + paramLong;
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("pass", "1");
    put(paramContext, str, localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void points(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/points/{user_id}".replace("{user_id}", Long.toString(paramLong)), null, paramAsyncHttpResponseHandler);
  }

  public void postCheckinComment(Context paramContext, long paramLong1, String paramString, long paramLong2, long paramLong3, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = "/api/v1/checkin/{checkin_id}/comment/".replace("{checkin_id}", paramLong2 + "");
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("checkin_user_id", paramLong1 + "");
    localRequestParams.put("comment", paramString);
    if (paramLong3 != -1L)
      localRequestParams.put("user", paramLong3 + "");
    post(paramContext, str, localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void postFeedback(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("content", paramString);
    post(paramContext, "api/v1/feedback/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void putQuota(Context paramContext, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("quota", Integer.toString(paramInt));
    put(paramContext, "api/v1/quota/applet/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void quotas(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/quota/applet/", null, paramAsyncHttpResponseHandler);
  }

  public void quote(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/quote/", null, paramAsyncHttpResponseHandler);
  }

  public void readShortMessage(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/message/" + paramLong + "/", null, paramAsyncHttpResponseHandler);
  }

  public void receiveNewBadge(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    post(paramContext, "/api/v1/badger/award/badge/{badge_id}".replace("{badge_id}", Long.toString(paramLong)), null, paramAsyncHttpResponseHandler);
  }

  public void replyShortMessage(Context paramContext, long paramLong, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = "/api/v1/message/" + paramLong + "/reply/";
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("body", paramString);
    post(paramContext, str, localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void search(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    Object localObject = "api/v1/bdc/search/";
    try
    {
      String str = (String)localObject + "?word=" + URLEncoder.encode(paramString, "UTF-8");
      localObject = str;
      get(paramContext, (String)localObject, null, paramAsyncHttpResponseHandler);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
  }

  public void sendShortMessage(Context paramContext, String paramString1, String paramString2, String paramString3, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("recipient", paramString1);
    localRequestParams.put("subject", paramString2);
    localRequestParams.put("body", paramString3);
    post(paramContext, "/api/v1/message/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void shareArticle(Context paramContext, String paramString, int paramInt, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/read/article/share/" + paramString + "/" + paramInt + "/", null, paramAsyncHttpResponseHandler);
  }

  public void shareArticleReview(Context paramContext, String paramString1, String paramString2, String paramString3, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("img_url", paramString2);
    localRequestParams.put("text", paramString1);
    localRequestParams.put("url", paramString3);
    post(paramContext, "api/v1/common/share/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void shareCheckin(Context paramContext, String paramString, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = "/api/v1/checkin/share/";
    arrayOfObject[1] = paramString;
    arrayOfObject[2] = Long.valueOf(paramLong);
    get(paramContext, String.format("%s%s/%s/", arrayOfObject), null, paramAsyncHttpResponseHandler);
  }

  public void shareCheckinCalendar(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("text", paramString2);
    localRequestParams.put("img_url", paramString3);
    localRequestParams.put("url", paramString4);
    post(paramContext, "api/v1/common/share/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void shareFootprint(Context paramContext, String paramString1, String paramString2, String paramString3, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("text", paramString2);
    localRequestParams.put("url", paramString3);
    post(paramContext, "api/v1/common/share/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void shareImage(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/mobile/product/image/android", null, paramAsyncHttpResponseHandler);
  }

  public void shareInvite(Context paramContext, String paramString1, String paramString2, String paramString3, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("text", paramString2);
    localRequestParams.put("url", paramString3);
    post(paramContext, "api/v1/common/share/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void shareListenArticle(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/listen/article/share/{service}/{article_id}".replace("{service}", "weibo").replace("{article_id}", Long.toString(paramLong)), null, paramAsyncHttpResponseHandler);
  }

  public void shareRecommend(Context paramContext, String paramString1, String paramString2, String paramString3, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("img_url", paramString3);
    localRequestParams.put("text", paramString2);
    post(paramContext, "api/v1/common/share/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void shareTopicThread(Context paramContext, String paramString1, String paramString2, String paramString3, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("text", paramString2);
    localRequestParams.put("url", paramString3);
    post(paramContext, "api/v1/common/share/", localRequestParams, paramAsyncHttpResponseHandler);
  }

  public void shortMessage(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/message/", null, paramAsyncHttpResponseHandler);
  }

  public void shortMessageReply(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/message/" + paramLong + "/reply/", null, paramAsyncHttpResponseHandler);
  }

  public void stats(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/read/stats/", null, paramAsyncHttpResponseHandler);
  }

  public void statsLatest(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/read/stats//?latest", null, paramAsyncHttpResponseHandler);
  }

  public void statsProgress(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/stats/" + paramLong + "/?progress", null, paramAsyncHttpResponseHandler);
  }

  public void sysExamples(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/example/sys/?vocabulary_id=" + paramLong, null, paramAsyncHttpResponseHandler);
  }

  public void team(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/team/{team_id}/".replace("{team_id}", Long.toString(paramLong)), null, paramAsyncHttpResponseHandler);
  }

  public void user(Context paramContext, ModelResponseHandler<User> paramModelResponseHandler)
  {
    get(paramContext, "api/v1/common/user/", null, paramModelResponseHandler);
  }

  public void userAccount(Context paramContext, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/coins/useraccount/", null, paramAsyncHttpResponseHandler);
  }

  public void userCheckinCalendar(Context paramContext, long paramLong, int paramInt1, int paramInt2, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/checkin/user/{user_id}/?v=2&year={year}&month={month}".replace("{user_id}", Long.toString(paramLong)).replace("{year}", Integer.toString(paramInt1)).replace("{month}", Integer.toString(paramInt2)), null, paramAsyncHttpResponseHandler);
  }

  public void userCheckinDays(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/checkin/user/{user_id}".replace("{user_id}", Long.toString(paramLong)), null, paramAsyncHttpResponseHandler);
  }

  public void userTeam(Context paramContext, long paramLong, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "/api/v1/team/member/?u={user_id}".replace("{user_id}", Long.toString(paramLong)), null, paramAsyncHttpResponseHandler);
  }

  public void vocabulary(Context paramContext, ArrayList<Long> paramArrayList, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, "api/v1/bdc/vocabulary/?ids=" + Misc.idsToQueryString(paramArrayList), null, paramAsyncHttpResponseHandler);
  }
}