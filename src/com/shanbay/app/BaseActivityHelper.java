package com.shanbay.app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import com.shanbay.http.APIClient;
import com.shanbay.http.ModelResponseException;
import com.shanbay.util.LogUtils;

public class BaseActivityHelper<T extends APIClient>
{
  protected final String TAG = LogUtils.makeLogTag(getClass());
  protected BaseActivity<T> c;
  protected T mClient;

  public BaseActivityHelper(BaseActivity<T> paramBaseActivity)
  {
    this.c = paramBaseActivity;
    this.mClient = getClient();
  }

  public boolean bindService(Intent paramIntent, ServiceConnection paramServiceConnection, int paramInt)
  {
    return this.c.bindService(paramIntent, paramServiceConnection, paramInt);
  }

  public void checkUpdate()
  {
    this.c.checkUpdate();
  }

  protected void d(String paramString)
  {
    LogUtils.LOGD(this.TAG, paramString);
  }

  protected void d(String paramString, Throwable paramThrowable)
  {
    LogUtils.LOGE(this.TAG, paramString, paramThrowable);
  }

  public void dismissProgressDialog()
  {
    this.c.dismissProgressDialog();
  }

  public View findViewById(int paramInt)
  {
    return this.c.findViewById(paramInt);
  }

  public void finish()
  {
    this.c.finish();
  }

  public T getClient()
  {
    return this.c.getClient();
  }

  public Intent getIntent()
  {
    return this.c.getIntent();
  }

  public LayoutInflater getLayoutInflater()
  {
    return this.c.getLayoutInflater();
  }

  public Resources getResources()
  {
    return this.c.getResources();
  }

  public SharedPreferences getSharedPreferences(String paramString, int paramInt)
  {
    return this.c.getSharedPreferences(paramString, paramInt);
  }

  public String getString(int paramInt)
  {
    return this.c.getString(paramInt);
  }

  public boolean handleCommonException(ModelResponseException paramModelResponseException)
  {
    return this.c.handleCommonException(paramModelResponseException);
  }

  @SuppressLint("NewApi")
  public void onBackPressed()
  {
    this.c.onBackPressed();
  }

  public void onCreate(Bundle paramBundle)
  {
  }

  public void onDestroy()
  {
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    return false;
  }

  public void onPause()
  {
  }

  public void onRestoreInstanceState(Bundle paramBundle)
  {
  }

  public void onResume()
  {
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
  }

  public void onStart()
  {
  }

  public void onStop()
  {
  }

  public void setContentView(int paramInt)
  {
    this.c.setContentView(paramInt);
  }

  public AlertDialog showExceptionDialog(ModelResponseException paramModelResponseException)
  {
    return this.c.showExceptionDialog(paramModelResponseException);
  }

  public AlertDialog showExceptionDialog(ModelResponseException paramModelResponseException, boolean paramBoolean)
  {
    return this.c.showExceptionDialog(paramModelResponseException, paramBoolean);
  }

  public void showProgressDialog()
  {
    this.c.showProgressDialog();
  }

  public void showProgressDialog(String paramString)
  {
    this.c.showProgressDialog(paramString);
  }

  public void showToast(int paramInt)
  {
    this.c.showToast(paramInt);
  }

  public void showToast(String paramString)
  {
    this.c.showToast(paramString);
  }

  public void startActivity(Intent paramIntent)
  {
    this.c.startActivity(paramIntent);
  }

  public void unbindService(ServiceConnection paramServiceConnection)
  {
    this.c.unbindService(paramServiceConnection);
  }
}