package com.shanbay.words;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class Env
{
  private static String AUDIO_CACHE_DIR;
  private static final String EXTERNAL_AUDIO_URL = "Shanbay/words/audio";

  public static String getAudioCacheDir()
  {
    return getAudioCacheDir(AUDIO_CACHE_DIR);
  }

  private static String getAudioCacheDir(String paramString)
  {
    if (isExternalStorageWritable())
    {
      File localFile = new File(Environment.getExternalStorageDirectory(), "Shanbay/words/audio");
      if (!localFile.exists())
        localFile.mkdirs();
      paramString = localFile.getAbsolutePath();
    }
    return paramString;
  }

  public static File getAudioCacheFile(String paramString)
  {
    return new File(getAudioCacheDir(AUDIO_CACHE_DIR) + "/" + paramString);
  }

  public static void init(WordsApplication paramWordsApplication)
  {
    AUDIO_CACHE_DIR = paramWordsApplication.getCacheDir().getAbsolutePath() + "/audio";
    Log.e("Env.init", "AUDIO_CACHE_DIR="+AUDIO_CACHE_DIR);
    File localFile = new File(AUDIO_CACHE_DIR);
    if (!localFile.exists())
      localFile.mkdir();
  }

  private static boolean isExternalStorageWritable()
  {
    return "mounted".equals(Environment.getExternalStorageState());
  }
}