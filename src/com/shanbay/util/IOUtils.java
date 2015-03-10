package com.shanbay.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class IOUtils
{
  public static void closeQuietly(InputStream paramInputStream)
  {
    if (paramInputStream != null);
    try
    {
      paramInputStream.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public static void closeQuietly(OutputStream paramOutputStream)
  {
    if (paramOutputStream != null);
    try
    {
      paramOutputStream.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public static void closeQuietly(Reader paramReader)
  {
    if (paramReader != null);
    try
    {
      paramReader.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public static void closeQuietly(Writer paramWriter)
  {
    if (paramWriter != null);
    try
    {
      paramWriter.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }
}