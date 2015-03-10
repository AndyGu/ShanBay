package com.shanbay.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.content.CursorLoader;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Misc
{
  public static final SimpleDateFormat COMM_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
  public static final SimpleDateFormat COMM_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

  public static JSONArray arrayToJson(ArrayList<String> paramArrayList)
  {
    return new JSONArray(paramArrayList);
  }

  public static byte[] bmpToByteArray(Bitmap bmp, boolean isRecyle)
  {
	  ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
      bmp.compress(CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
      if(isRecyle)
    	  bmp.recycle();//自由选择是否进行回收
      
      byte[] result = output.toByteArray();//转换成功了
      try {
          output.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return result;
  }

  public static int compareDateWithoutTime(Date paramDate1, Date paramDate2)
  {
    return getDateWithoutTime(paramDate1).compareTo(getDateWithoutTime(paramDate2));
  }

  public static void compressImageAndWriteToFile(Bitmap paramBitmap, File paramFile, int paramInt)
  {
//	  待补充
  }

  public static void compressScaleImageAndWriteToFile(String paramString, File paramFile, int paramInt)
  {
    BitmapFactory.Options localOptions1 = new BitmapFactory.Options();
    localOptions1.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(paramString, localOptions1);
    int i = (int)(localOptions1.outWidth / 630.0F);
    if (i <= 0)
      i = 1;
    BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
    localOptions2.inSampleSize = i;
    Bitmap localBitmap = BitmapFactory.decodeFile(paramString, localOptions2);
    if (localBitmap != null)
      compressImageAndWriteToFile(localBitmap, paramFile, paramInt);
  }

  public static String convertStreamToString(InputStream paramInputStream)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuilder localStringBuilder = new StringBuilder();
    while (true)
    {
      String str = localBufferedReader.readLine();
      if (str == null)
        break;
      localStringBuilder.append(str).append("\n");
    }
    paramInputStream.close();
    return localStringBuilder.toString();
  }

  public static void disableConnectionReuseIfNecessary()
  {
      if (Build.VERSION.SDK_INT < 8)
        System.setProperty("http.keepAlive", "false");
  }

  @SuppressLint("NewApi")
public static void disableHardwareAcceleration(View paramView)
  {
    if (Build.VERSION.SDK_INT > 11)
      paramView.setLayerType(1, null);
  }

  public static Bitmap drawableToBitmap(Drawable paramDrawable)
  {
    return drawableToBitmap(paramDrawable, -1, -1);
  }

  public static Bitmap drawableToBitmap(Drawable paramDrawable, int paramInt1, int paramInt2)
  {
    if ((paramInt1 == -1) && (paramInt2 == -1))
    {
      paramInt1 = paramDrawable.getIntrinsicWidth();
      paramInt2 = paramDrawable.getIntrinsicHeight();
    }
    if (paramDrawable.getOpacity() != -1);
    for (Bitmap.Config localConfig = Bitmap.Config.ARGB_8888; ; localConfig = Bitmap.Config.RGB_565)
    {
      Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, localConfig);
      Canvas localCanvas = new Canvas(localBitmap);
      paramDrawable.setBounds(0, 0, paramInt1, paramInt2);
      paramDrawable.draw(localCanvas);
      return localBitmap;
    }
  }

  public static void forceHideSoftKeyboard(Context paramContext, View paramView)
  {
    ((InputMethodManager)paramContext.getSystemService("input_method")).hideSoftInputFromWindow(paramView.getWindowToken(), 0);
  }

  public static void forceShowSoftKeyboard(final Context paramContext, final View paramView)
  {
    paramView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public void onGlobalLayout()
      {
        paramView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        ((InputMethodManager)paramContext.getSystemService("input_method")).toggleSoftInput(0, 1);
      }
    });
  }

  public static Bitmap getCompressBmpByImageUri(Context paramContext, Uri paramUri)
  {
    if ((paramContext == null) || (paramUri == null))
      return null;
    InputStream localInputStream = null;
    try
    {
      localInputStream = paramContext.getContentResolver().openInputStream(paramUri);
      BitmapFactory.Options localOptions1 = new BitmapFactory.Options();
      localOptions1.inJustDecodeBounds = true;
      BitmapFactory.decodeStream(localInputStream, null, localOptions1);
      IOUtils.closeQuietly(localInputStream);
      int i = localOptions1.outWidth;
      int j = localOptions1.outHeight;
      int k = 1;
      while (true)
      {
        if ((i / 2 < 100) || (j / 2 < 100))
        {
          BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
          localOptions2.inSampleSize = k;
          localInputStream = paramContext.getContentResolver().openInputStream(paramUri);
          Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream, null, localOptions2);
          return localBitmap;
        }
        i /= 2;
        j /= 2;
        k *= 2;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    finally
    {
      IOUtils.closeQuietly(localInputStream);
    }
  }

  public static Date getDateWithoutTime(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    localCalendar.set(11, 0);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    return localCalendar.getTime();
  }

  public static String getDeviceId(Context paramContext)
  {
    return ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
  }

  public static String getId()
  {
    return "android_id";
  }

  public static String getRealPathFromVideoURI(Context paramContext, Uri paramUri)
  {
    Cursor localCursor = new CursorLoader(paramContext, paramUri, new String[] { "_data" }, null, null, null).loadInBackground();
    int i = localCursor.getColumnIndexOrThrow("_data");
    localCursor.moveToFirst();
    return localCursor.getString(i);
  }

  public static String idsIntToQueryString(List<Integer> paramList)
  {
    if (paramList != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        int j = ((Integer)localIterator.next()).intValue();
        localStringBuffer.append(j + ",");
      }
      if (localStringBuffer != null)
      {
        int i = localStringBuffer.lastIndexOf(",");
        if (i != -1)
          return localStringBuffer.substring(0, i);
      }
    }
    return "";
  }

  public static String idsToQueryString(List<Long> paramList)
  {
    if (paramList != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        long l = ((Long)localIterator.next()).longValue();
        localStringBuffer.append(l + ",");
      }
      if (localStringBuffer != null)
      {
        int i = localStringBuffer.lastIndexOf(",");
        if (i != -1)
          return localStringBuffer.substring(0, i);
      }
    }
    return "";
  }

  public static JSONObject intToMultiIdJson(ArrayList<Integer> paramArrayList)
  {
    if (paramArrayList.size() > 0)
    {
      JSONObject localJSONObject = new JSONObject();
      String str = "" + paramArrayList.get(0);
      for (int i = 1; i < paramArrayList.size(); i++)
        str = str + "," + paramArrayList.get(i);
      try
      {
        localJSONObject.put("ids", str);
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return null;
      }
    }
    return null;
  }

  public static boolean isAcsii(CharSequence paramCharSequence)
  {
    for (int i = 0; i < paramCharSequence.length(); i++)
      if ((paramCharSequence.charAt(i) > '~') || (paramCharSequence.charAt(i) < ' '))
        return false;
    return true;
  }

  public static boolean isExternalStorageWriteable()
  {
    String str = Environment.getExternalStorageState();
    if ("mounted".equals(str))
      return true;
    return !"mounted_ro".equals(str);
  }

  public static boolean isInternetAvailable(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager.getActiveNetworkInfo() != null)
      return localConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    return false;
  }

  public static boolean isNote3()
  {
    String str = "SM-900".toLowerCase(Locale.US);
    return Build.MODEL.toLowerCase(Locale.US).indexOf(str) >= 0;
  }

  public static ArrayList<String> jsonToArray(JSONArray paramJSONArray)
  {
    int i = paramJSONArray.length();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++)
      localArrayList.add(paramJSONArray.optString(j));
    return localArrayList;
  }

  public static Map<String, String> jsonToMap(JSONObject paramJSONObject)
  {
    HashMap localHashMap = new HashMap(paramJSONObject.length());
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, paramJSONObject.optString(str));
    }
    return localHashMap;
  }

  public static String jsonToQueryUrl(JSONObject paramJSONObject)
  {
    JSONArray localJSONArray = paramJSONObject.names();
    String str1 = "?";
    for (int i = 0; i < localJSONArray.length(); i++)
    {
      String str2 = URLEncoder.encode(localJSONArray.optString(i));
      String str3 = URLEncoder.encode(paramJSONObject.optString(str2));
      str1 = str1 + str2 + "=" + str3 + "&";
    }
    return str1.substring(0, -1 + str1.length());
  }

  public static JSONObject mapToJson(Map<String, String> paramMap)
  {
    if (paramMap == null)
      paramMap = new HashMap();
    return new JSONObject(paramMap);
  }

  public static String md5(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.reset();
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      int i = arrayOfByte.length;
      StringBuilder localStringBuilder = new StringBuilder(i << 1);
      for (int j = 0; j < i; j++)
      {
        localStringBuilder.append(Character.forDigit((0xF0 & arrayOfByte[j]) >> 4, 16));
        localStringBuilder.append(Character.forDigit(0xF & arrayOfByte[j], 16));
      }
      String str = localStringBuilder.toString();
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    return null;
  }

  @SuppressLint({"NewApi"})
  public static void openHardwareAcceleration(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT > 11)
      paramActivity.getWindow().setFlags(16777216, 16777216);
  }

  public static void playNotificationSound(Context paramContext)
  {
    try
    {
      RingtoneManager.getRingtone(paramContext, RingtoneManager.getDefaultUri(2)).play();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static boolean recursiveDelete(File paramFile)
  {
    if (paramFile.isDirectory())
    {
      if (paramFile.list().length == 0)
        return paramFile.delete();
      String[] arrayOfString = paramFile.list();
      int i = arrayOfString.length;
      for (int j = 0; j < i; j++)
        recursiveDelete(new File(paramFile, arrayOfString[j]));
      if (paramFile.list().length == 0)
        return paramFile.delete();
      return false;
    }
    return paramFile.delete();
  }

  public static JSONObject toMultiIdJson(ArrayList<String> paramArrayList)
  {
    if (paramArrayList.size() > 0)
    {
      JSONObject localJSONObject = new JSONObject();
      String str = "" + (String)paramArrayList.get(0);
      for (int i = 1; i < paramArrayList.size(); i++)
        str = str + "," + (String)paramArrayList.get(i);
      try
      {
        localJSONObject.put("ids", str);
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return null;
      }
    }
    return null;
  }
}