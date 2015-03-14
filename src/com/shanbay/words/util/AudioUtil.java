package com.shanbay.words.util;

import com.shanbay.util.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class AudioUtil
{
  private static final String suffix = "word";
  private static final String ukSuffix = "uk.word";
  private static final String usSuffix = "us.word";

  public static String getFilenameByUrl(String paramString)
  {
    String str = FilenameUtils.getBaseName(paramString);
    if (StringUtils.contains(paramString, "audio/us"))
      return str + "us.word";
    if (StringUtils.contains(paramString, "audio/uk"))
      return str + "uk.word";
    return str + "word";
  }
}