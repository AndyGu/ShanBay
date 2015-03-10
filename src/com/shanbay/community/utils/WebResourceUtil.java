package com.shanbay.community.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public class WebResourceUtil
{
  private static final String WEB_RESOURCE_BASE_DIR_LISTEN = "Shanbay/listen/web_resource";
  private static final String WEB_RESOURCE_BASE_DIR_NEWS = "Shanbay/news/web_resource";
  private static final String WEB_RESOURCE_BASE_DIR_READER = "Shanbay/reader/web_resource";
  private static final String WEB_RESOURCE_BASE_DIR_SENTENCE = "Shanbay/sentence/web_resource";
  private static final String WEB_RESOURCE_BASE_DIR_WORDS = "Shanbay/words/web_resource";
  private static final String WEB_RESOURCE_DELETED_YAML = "deleted.yml";
  private static final String WEB_RESOURCE_MANIFEST_YAML = "manifest.yml";
  private static final String WEB_RESOURCE_ZIP_FILE_NAME = "resource.zip";

  public static void createResourceDir(Context context)
  {
    new File(getResourceBaseDir(context)).mkdirs();
  }

  public static String getDeletedFilePath(Context paramContext)
  {
    return new File(getResourceBaseDir(paramContext), WEB_RESOURCE_DELETED_YAML).getAbsolutePath();
  }

  public static String getManifestFilePath(Context paramContext)
  {
    return new File(getResourceBaseDir(paramContext), WEB_RESOURCE_MANIFEST_YAML).getAbsolutePath();
  }

  public static String getResourceBaseDir(Context paramContext)
  {
    String str = paramContext.getPackageName();
    if ("com.shanbay.words".equals(str))
      return new File(Environment.getExternalStorageDirectory(), WEB_RESOURCE_BASE_DIR_WORDS).getAbsolutePath();
    if ("com.shanbay.sentence".equals(str))
      return new File(Environment.getExternalStorageDirectory(), WEB_RESOURCE_BASE_DIR_SENTENCE).getAbsolutePath();
    if ("com.shanbay.news".equals(str))
      return new File(Environment.getExternalStorageDirectory(), WEB_RESOURCE_BASE_DIR_NEWS).getAbsolutePath();
    if ("com.shanbay.listen".equals(str))
      return new File(Environment.getExternalStorageDirectory(), WEB_RESOURCE_BASE_DIR_LISTEN).getAbsolutePath();
    if ("com.shanbay.reader".equals(str))
      return new File(Environment.getExternalStorageDirectory(), WEB_RESOURCE_BASE_DIR_READER).getAbsolutePath();
    return null;
  }

  public static String getResourceFile(Context paramContext, String paramString)
  {
    return new File(getResourceBaseDir(paramContext), paramString).getAbsolutePath();
  }

  public static String getZipExtractDir(Context paramContext)
  {
    return new File(getResourceBaseDir(paramContext)).getAbsolutePath();
  }

  public static String getZipFilePath(Context paramContext)
  {
    return new File(getResourceBaseDir(paramContext), WEB_RESOURCE_ZIP_FILE_NAME).getAbsolutePath();
  }

  public static boolean isDeletedFileExisted(Context paramContext)
  {
    return new File(getResourceBaseDir(paramContext), WEB_RESOURCE_DELETED_YAML).exists();
  }

  public static boolean isManifestFileExisted(Context paramContext)
  {
    return new File(getResourceBaseDir(paramContext), WEB_RESOURCE_MANIFEST_YAML).exists();
  }

  public static boolean isResourceFileExisted(Context paramContext, String paramString)
  {
    return new File(getResourceBaseDir(paramContext), paramString).exists();
  }
}