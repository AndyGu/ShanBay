package com.shanbay.sentry;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;
import com.joshdholtz.protocol.lib.ProtocolClient;
import com.joshdholtz.protocol.lib.requests.JSONRequestData;
import com.joshdholtz.protocol.lib.responses.ProtocolResponseHandler;
import com.shanbay.Config;
import com.shanbay.util.Misc;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sentry
{
//  private static final String BASE_URL = "https://app.getsentry.com";
//  private static final String TAG = "Sentry";
//  private static final String VERSION = "0.1.2";
//  private ProtocolClient client;
//  private String dsn;
//  private String id;
//  private String packageName;
//  private String packageVersion;
//
//  public static JSONObject buildTags()
//  {
//    HashMap localHashMap = new HashMap();
//    localHashMap.put("android_version", Build.VERSION.RELEASE);
//    localHashMap.put("brand", Build.BRAND);
//    localHashMap.put("model", Build.MODEL);
//    localHashMap.put("id", getInstance().id);
//    return new JSONObject(localHashMap);
//  }
//
//  public static void captureEvent(SentryEventBuilder paramSentryEventBuilder)
//  {
//    JSONRequestData localJSONRequestData = new JSONRequestData(paramSentryEventBuilder.event);
//    localJSONRequestData.addHeader("X-Sentry-Auth", createXSentryAuthHeader());
//    localJSONRequestData.addHeader("User-Agent", "sentry-android/0.1.2");
//    Log.d("Sentry", "Request - " + new JSONObject(paramSentryEventBuilder.event).toString());
//    getInstance().client.doPost("/api/" + getProjectId() + "/store/", localJSONRequestData, new ProtocolResponseHandler()
//    {
//      public void handleResponse(HttpResponse paramAnonymousHttpResponse, int paramAnonymousInt, byte[] paramAnonymousArrayOfByte)
//      {
//        String str = new String(paramAnonymousArrayOfByte);
//        Log.d("Sentry", "SendEvent - " + paramAnonymousInt + " " + str);
//      }
//    });
//  }
//
//  public static void captureException(Throwable paramThrowable)
//  {
//    captureException(paramThrowable, Sentry.SentryEventBuilder.SentryEventLevel.ERROR);
//  }
//
//  public static void captureException(Throwable paramThrowable, Sentry.SentryEventBuilder.SentryEventLevel paramSentryEventLevel)
//  {
//    String str = getCause(paramThrowable, paramThrowable.getMessage());
//    captureEvent(new SentryEventBuilder().setMessage(paramThrowable.getMessage()).setCulprit(str).setLevel(paramSentryEventLevel).setException(paramThrowable).setServerName(getInstance().packageName + "/" + getInstance().packageVersion).setTags(buildTags()));
//  }
//
//  public static void captureMessage(String paramString)
//  {
//    captureMessage(paramString, Sentry.SentryEventBuilder.SentryEventLevel.INFO);
//  }
//
//  public static void captureMessage(String paramString, Sentry.SentryEventBuilder.SentryEventLevel paramSentryEventLevel)
//  {
//    captureEvent(new SentryEventBuilder().setMessage(paramString).setLevel(paramSentryEventLevel));
//  }
//
//  private static String createXSentryAuthHeader()
//  {
//    Uri localUri = Uri.parse(getInstance().dsn);
//    String[] arrayOfString = localUri.getAuthority().replace("@" + localUri.getHost(), "").split(":");
//    String str1 = arrayOfString[0];
//    String str2 = arrayOfString[1];
//    String str3 = "" + "Sentry sentry_version=3, ";
//    String str4 = str3 + "sentry_client=sentry-android/0.1.2, ";
//    String str5 = str4 + "sentry_timestamp=" + System.currentTimeMillis() + ",";
//    String str6 = str5 + "sentry_key=" + str1 + ",";
//    String str7 = str6 + "sentry_secret=" + str2;
//    Log.d("Sentry", str7);
//    return str7;
//  }
//
//  private static String getCause(Throwable paramThrowable, String paramString)
//  {
//    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
//    int i = arrayOfStackTraceElement.length;
//    for (int j = 0; ; j++)
//      if (j < i)
//      {
//        StackTraceElement localStackTraceElement = arrayOfStackTraceElement[j];
//        if (localStackTraceElement.toString().contains(getInstance().packageName))
//          paramString = localStackTraceElement.toString();
//      }
//      else
//      {
//        return paramString;
//      }
//  }
//
//  private static Sentry getInstance()
//  {
//    return LazyHolder.instance;
//  }
//
//  private static String getProjectId()
//  {
//    String str = Uri.parse(getInstance().dsn).getPath();
//    return str.substring(1 + str.lastIndexOf("/"));
//  }
//
//  private static String getStackTrace(Throwable paramThrowable)
//  {
//    StringWriter localStringWriter = new StringWriter();
//    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
//    return localStringWriter.toString();
//  }
//
//  public static void init(Context paramContext, String paramString)
//  {
//    getInstance().dsn = paramString;
//    getInstance().packageName = paramContext.getPackageName();
//    try
//    {
//      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
//      getInstance().packageVersion = localPackageInfo.versionName;
//      getInstance().id = Misc.md5("-_-" + Misc.getId() + Build.MODEL);
//      Uri localUri = Uri.parse(getInstance().dsn);
//      getInstance().client = new ProtocolClient(localUri.getScheme() + "://" + localUri.getHost());
//      getInstance().client.setDebug(Config.SENTRY_DEBUG);
//      submitStackTraces(paramContext);
//      Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
//      if (localUncaughtExceptionHandler != null)
//        Log.d("Debugged", "current handler class=" + localUncaughtExceptionHandler.getClass().getName());
//      if (!(localUncaughtExceptionHandler instanceof SentryUncaughtExceptionHandler))
//        Thread.setDefaultUncaughtExceptionHandler(new SentryUncaughtExceptionHandler(localUncaughtExceptionHandler, paramContext.getFilesDir().getAbsolutePath()));
//      return;
//    }
//    catch (PackageManager.NameNotFoundException localNameNotFoundException)
//    {
//      while (true)
//      {
//        localNameNotFoundException.printStackTrace();
//        getInstance().packageVersion = "UnKnown Version";
//      }
//    }
//  }
//
//  private static String[] searchForStackTraces(Context paramContext)
//  {
//    File localFile = new File(paramContext.getFilesDir().getAbsolutePath() + "/");
//    localFile.mkdir();
//    return localFile.list(new FilenameFilter()
//    {
//      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
//      {
//        return paramAnonymousString.endsWith(".stacktrace");
//      }
//    });
//  }
//
//  public static void sentCustomEvent(String paramString, Map<String, String> paramMap)
//  {
//    try
//    {
//      captureEvent(new SentryEventBuilder().setMessage(paramString).setExtra(paramMap).setLevel(Sentry.SentryEventBuilder.SentryEventLevel.INFO).setServerName(getInstance().packageName + "/" + getInstance().packageVersion).setTags(buildTags()));
//      return;
//    }
//    catch (Exception localException)
//    {
//      Log.e("Sentry", localException.getMessage());
//    }
//  }
//
//  private static void submitStackTraces(Context paramContext)
//  {
//    try
//    {
//      Log.d("Sentry", "Looking for exceptions in thing");
//      String[] arrayOfString3 = searchForStackTraces(paramContext);
//      if ((arrayOfString3 != null) && (arrayOfString3.length > 0))
//      {
//        Log.d("Sentry", "Found " + arrayOfString3.length + " stacktrace(s)");
//        for (int m = 0; m < arrayOfString3.length; m++)
//        {
//          ObjectInputStream localObjectInputStream = new ObjectInputStream(new FileInputStream(paramContext.getFilesDir().getAbsolutePath() + "/" + arrayOfString3[m]));
//          Throwable localThrowable = (Throwable)localObjectInputStream.readObject();
//          localObjectInputStream.close();
//          captureException(localThrowable, Sentry.SentryEventBuilder.SentryEventLevel.FATAL);
//          Log.d("Sentry", localThrowable.getMessage());
//        }
//      }
//      try
//      {
//        String[] arrayOfString4 = searchForStackTraces(paramContext);
//        for (int k = 0; k < arrayOfString4.length; k++)
//          new File(paramContext.getFilesDir().getAbsolutePath() + "/" + arrayOfString4[k]).delete();
//      }
//      catch (Exception localException4)
//      {
//        localException4.printStackTrace();
//      }
//      return;
//    }
//    catch (Exception localException2)
//    {
//      while (true)
//      {
//        localException2.printStackTrace();
//        try
//        {
//          String[] arrayOfString2 = searchForStackTraces(paramContext);
//          for (int j = 0; j < arrayOfString2.length; j++)
//            new File(paramContext.getFilesDir().getAbsolutePath() + "/" + arrayOfString2[j]).delete();
//        }
//        catch (Exception localException3)
//        {
//          localException3.printStackTrace();
//          return;
//        }
//      }
//    }
//    finally
//    {
//      try
//      {
//        String[] arrayOfString1 = searchForStackTraces(paramContext);
//        for (int i = 0; i < arrayOfString1.length; i++)
//          new File(paramContext.getFilesDir().getAbsolutePath() + "/" + arrayOfString1[i]).delete();
//      }
//      catch (Exception localException1)
//      {
//        localException1.printStackTrace();
//      }
//    }
//  }
//
//  private static class LazyHolder
//  {
//    private static Sentry instance = new Sentry(null);
//  }
//
//  public static class SentryEventBuilder
//  {
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//    private Map<String, Object> event = new HashMap();
//
//    static
//    {
//      sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//    }
//
//    public SentryEventBuilder()
//    {
//      this.event.put("event_id", UUID.randomUUID().toString().replace("-", ""));
//      setPlatform("android");
//      setTimestamp(System.currentTimeMillis());
//    }
//
//    public static JSONObject getStackTrace(Throwable paramThrowable)
//      throws JSONException
//    {
//      JSONArray localJSONArray = new JSONArray();
//      while (paramThrowable != null)
//      {
//        StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
//        for (int i = 0; i < arrayOfStackTraceElement.length; i++)
//        {
//          if (i == 0)
//          {
//            JSONObject localJSONObject2 = new JSONObject();
//            String str = "Caused by: " + paramThrowable.getClass().getName();
//            if (paramThrowable.getMessage() != null)
//              str = str + " (\"" + paramThrowable.getMessage() + "\")";
//            localJSONObject2.put("filename", str);
//            localJSONObject2.put("lineno", -1);
//            localJSONArray.put(localJSONObject2);
//          }
//          StackTraceElement localStackTraceElement = arrayOfStackTraceElement[i];
//          JSONObject localJSONObject3 = new JSONObject();
//          localJSONObject3.put("filename", localStackTraceElement.getClassName());
//          localJSONObject3.put("function", localStackTraceElement.getMethodName());
//          localJSONObject3.put("lineno", localStackTraceElement.getLineNumber());
//          localJSONArray.put(localJSONObject3);
//        }
//        paramThrowable = paramThrowable.getCause();
//      }
//      JSONObject localJSONObject1 = new JSONObject();
//      localJSONObject1.put("frames", localJSONArray);
//      return localJSONObject1;
//    }
//
//    public SentryEventBuilder setCulprit(String paramString)
//    {
//      this.event.put("culprit", paramString);
//      return this;
//    }
//
//    public SentryEventBuilder setException(Throwable paramThrowable)
//    {
//      HashMap localHashMap = new HashMap();
//      localHashMap.put("type", paramThrowable.getClass().getSimpleName());
//      localHashMap.put("value", paramThrowable.getMessage());
//      localHashMap.put("module", paramThrowable.getClass().getPackage().getName());
//      this.event.put("sentry.interfaces.Exception", new JSONObject(localHashMap));
//      try
//      {
//        this.event.put("sentry.interfaces.Stacktrace", getStackTrace(paramThrowable));
//        return this;
//      }
//      catch (JSONException localJSONException)
//      {
//        localJSONException.printStackTrace();
//      }
//      return this;
//    }
//
//    public SentryEventBuilder setExtra(Map<String, String> paramMap)
//    {
//      this.event.put("extra", new JSONObject(paramMap));
//      return this;
//    }
//
//    public SentryEventBuilder setLevel(SentryEventLevel paramSentryEventLevel)
//    {
//      this.event.put("level", paramSentryEventLevel.value);
//      return this;
//    }
//
//    public SentryEventBuilder setLogger(String paramString)
//    {
//      this.event.put("logger", paramString);
//      return this;
//    }
//
//    public SentryEventBuilder setMessage(String paramString)
//    {
//      this.event.put("message", paramString);
//      return this;
//    }
//
//    public SentryEventBuilder setModules(List<String> paramList)
//    {
//      this.event.put("modules", paramList);
//      return this;
//    }
//
//    public SentryEventBuilder setPlatform(String paramString)
//    {
//      this.event.put("platform", paramString);
//      return this;
//    }
//
//    public SentryEventBuilder setServerName(String paramString)
//    {
//      this.event.put("server_name", paramString);
//      return this;
//    }
//
//    public SentryEventBuilder setTags(JSONObject paramJSONObject)
//    {
//      this.event.put("tags", paramJSONObject);
//      return this;
//    }
//
//    public SentryEventBuilder setTimestamp(long paramLong)
//    {
//      this.event.put("timestamp", sdf.format(new Date(paramLong)));
//      return this;
//    }
//
//    public static enum SentryEventLevel
//    {
//      private String value;
//
//      static
//      {
//        ERROR = new SentryEventLevel("ERROR", 1, "error");
//        WARNING = new SentryEventLevel("WARNING", 2, "warning");
//        INFO = new SentryEventLevel("INFO", 3, "info");
//        DEBUG = new SentryEventLevel("DEBUG", 4, "debug");
//        SentryEventLevel[] arrayOfSentryEventLevel = new SentryEventLevel[5];
//        arrayOfSentryEventLevel[0] = FATAL;
//        arrayOfSentryEventLevel[1] = ERROR;
//        arrayOfSentryEventLevel[2] = WARNING;
//        arrayOfSentryEventLevel[3] = INFO;
//        arrayOfSentryEventLevel[4] = DEBUG;
//      }
//
//      private SentryEventLevel(String paramString)
//      {
//        this.value = paramString;
//      }
//    }
//  }
//
//  private static class SentryUncaughtExceptionHandler
//    implements Thread.UncaughtExceptionHandler
//  {
//    private Thread.UncaughtExceptionHandler defaultExceptionHandler;
//    private String filePath;
//
//    public SentryUncaughtExceptionHandler(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, String paramString)
//    {
//      this.defaultExceptionHandler = paramUncaughtExceptionHandler;
//      this.filePath = paramString;
//    }
//
//    public void uncaughtException(Thread paramThread, Throwable paramThrowable)
//    {
//      StringWriter localStringWriter = new StringWriter();
//      paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
//      try
//      {
//        long l = System.currentTimeMillis();
//        String str = "Raven-" + String.valueOf(l);
//        Log.d("Sentry", "Writing unhandled exception to: " + this.filePath + "/" + str + ".stacktrace");
//        ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(new FileOutputStream(this.filePath + "/" + str + ".stacktrace"));
//        localObjectOutputStream.writeObject(paramThrowable);
//        localObjectOutputStream.flush();
//        localObjectOutputStream.close();
//        Log.d("Sentry", localStringWriter.toString());
//        this.defaultExceptionHandler.uncaughtException(paramThread, paramThrowable);
//        return;
//      }
//      catch (Exception localException)
//      {
//        while (true)
//          localException.printStackTrace();
//      }
//    }
//  }
}