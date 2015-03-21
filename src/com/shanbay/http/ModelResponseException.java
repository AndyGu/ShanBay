package com.shanbay.http;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;

public class ModelResponseException extends Exception
{
  public static final int STATUS_CODE_CONNECT_EXCEPTION = 983040;
  public static final int STATUS_CODE_JSON_PARSE_EXCEPTION = 268369920;
  public static final int STATUS_CODE_NETWORK_404 = 268369921;
  public static final int STATUS_CODE_NETWORK_EXCEPTION = 16711680;
  public static final int STATUS_CODE_NEWWORK_TIME_OUT = 268369922;
  public static final int STATUS_CODE_OBJECT_MAPPING_EXCEPTION = -65536;
  public static final int STATUS_CODE_UNKNOWN_EXCEPTION = -15;
  private static final long serialVersionUID = -1870921441796123137L;
  private String msg;
  private int statusCode;

  public ModelResponseException(int paramInt, String paramString)
  {
    this.statusCode = paramInt;
    this.msg = paramString;
  }

  public ModelResponseException(Throwable paramThrowable)
  {
    if ((paramThrowable instanceof HttpResponseException))
    {
      HttpResponseException localHttpResponseException = (HttpResponseException)paramThrowable;
      if (localHttpResponseException.getStatusCode() == 404){
    	  statusCode = STATUS_CODE_NETWORK_404;
      }else{
    	  statusCode = localHttpResponseException.getStatusCode();
      }
      msg = localHttpResponseException.getMessage();
    }
    if ((paramThrowable instanceof ConnectException))
    {
      statusCode = 983040;
      msg = paramThrowable.getMessage();
      return;
    }
    if ((paramThrowable instanceof UnknownHostException))
    {
      statusCode = 16711680;
      msg = paramThrowable.getMessage();
      return;
    }
    if ((paramThrowable instanceof UnknownHostException))
    {
      statusCode = 16711680;
      msg = paramThrowable.getMessage();
      return;
    }
    if (((paramThrowable instanceof SocketTimeoutException)) || ((paramThrowable instanceof ConnectTimeoutException)))
    {
      statusCode = 268369922;
      msg = paramThrowable.getMessage();
      return;
    }
    statusCode = -15;
    msg = ("unknown exception:" + paramThrowable.getClass().getName() + " | Message:" + paramThrowable.getMessage());
  }

  public String getMessage()
  {
    return msg;
  }

  public int getStatusCode()
  {
    return statusCode;
  }

  public boolean isDefinedStatus()
  {
    return (0x8030000 & statusCode) == 134414336;
  }

  public void setMsg(String paramString)
  {
    msg = paramString;
  }

  public void setStatusCode(int paramInt)
  {
    statusCode = paramInt;
  }
}