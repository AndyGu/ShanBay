package com.shanbay.app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.google.gson.JsonElement;
import com.shanbay.http.APIClient;
import com.shanbay.http.DataResponseHandler;
import com.shanbay.http.HttpClient;
import com.shanbay.http.ModelResponseException;
import com.shanbay.model.App;
import com.shanbay.words.R;

public abstract class ShanbayActivity<T extends APIClient> extends BaseActivity<T>
{
  public void home()
  {
    home(null);
  }

  public abstract void home(String paramString);

  protected void installApp(App paramApp)
  {
    if (!isFinishing());
    try
    {
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.setData(Uri.parse(HttpClient.getAbsoluteUrl(paramApp.rateUrl, APIClient.HOST)));
      startActivity(localIntent);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void logout()
  {
    this.mClient.logout(this, new DataResponseHandler()
    {
      @Override
      public void onFailure(ModelResponseException paramAnonymousModelResponseException, JsonElement paramAnonymousJsonElement)
      {
        if (!ShanbayActivity.this.handleCommonException(paramAnonymousModelResponseException))
          ShanbayActivity.this.showToast(paramAnonymousModelResponseException.getMessage());
      }

      @Override
      public void onSuccess(int paramAnonymousInt, JsonElement paramAnonymousJsonElement)
      {
        ShanbayActivity.this.dismissProgressDialog();
        ShanbayActivity.this.onLogout();
      }
    });
  }

  public void logoutDialog()
  {
    new AlertDialog.Builder(this).setMessage(R.string.msg_logout).setTitle(R.string.logout).setPositiveButton(R.string.logout, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ShanbayActivity.this.logout();
      }
    }).setNegativeButton(getString(R.string.cancel), null).show();
  }

  protected void onDestroy()
  {
    this.mClient.cancelRequest(this, true);
    super.onDestroy();
  }

  public abstract void onLogout();

  public abstract void onRequestLogin();

  @SuppressLint("NewApi")
  public void startApp(App paramApp)
  {
    Intent localIntent = getPackageManager().getLaunchIntentForPackage(paramApp.identifier);
    if (localIntent == null)
    {
      installApp(paramApp);
      return;
    }
    startActivity(localIntent);
  }
}