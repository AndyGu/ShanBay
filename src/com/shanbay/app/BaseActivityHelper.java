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
  protected BaseActivity<T> bActivity;
  protected T mClient;

  public BaseActivityHelper(BaseActivity<T> paramBaseActivity)
  {
    this.bActivity = paramBaseActivity;
    this.mClient = getClient();
  }

  public boolean bindService(Intent paramIntent, ServiceConnection paramServiceConnection, int paramInt)
  {
    return this.bActivity.bindService(paramIntent, paramServiceConnection, paramInt);
  }

  public void checkUpdate()
  {
    this.bActivity.checkUpdate();
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
    this.bActivity.dismissProgressDialog();
  }

  public View findViewById(int paramInt)
  {
    return this.bActivity.findViewById(paramInt);
  }

  public void finish()
  {
    this.bActivity.finish();
  }

  public T getClient()
  {
    return this.bActivity.getClient();
  }

  public Intent getIntent()
  {
    return this.bActivity.getIntent();
  }

  public LayoutInflater getLayoutInflater()
  {
    return this.bActivity.getLayoutInflater();
  }

  public Resources getResources()
  {
    return this.bActivity.getResources();
  }

  public SharedPreferences getSharedPreferences(String paramString, int paramInt)
  {
    return this.bActivity.getSharedPreferences(paramString, paramInt);
  }

  public String getString(int paramInt)
  {
    return this.bActivity.getString(paramInt);
  }

  public boolean handleCommonException(ModelResponseException paramModelResponseException)
  {
    return this.bActivity.handleCommonException(paramModelResponseException);
  }

  @SuppressLint("NewApi")
  public void onBackPressed()
  {
    this.bActivity.onBackPressed();
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
    this.bActivity.setContentView(paramInt);
  }

  public AlertDialog showExceptionDialog(ModelResponseException paramModelResponseException)
  {
    return this.bActivity.showExceptionDialog(paramModelResponseException);
  }

  public AlertDialog showExceptionDialog(ModelResponseException paramModelResponseException, boolean paramBoolean)
  {
    return this.bActivity.showExceptionDialog(paramModelResponseException, paramBoolean);
  }

  public void showProgressDialog()
  {
    this.bActivity.showProgressDialog();
  }

  public void showProgressDialog(String paramString)
  {
    this.bActivity.showProgressDialog(paramString);
  }

  public void showToast(int paramInt)
  {
    this.bActivity.showToast(paramInt);
  }

  public void showToast(String paramString)
  {
    this.bActivity.showToast(paramString);
  }

  public void startActivity(Intent paramIntent)
  {
    this.bActivity.startActivity(paramIntent);
  }

  public void unbindService(ServiceConnection paramServiceConnection)
  {
    this.bActivity.unbindService(paramServiceConnection);
  }
}