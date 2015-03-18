package com.shanbay.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Toast;
import com.shanbay.http.APIClient;
import com.shanbay.http.ModelResponseException;
import com.shanbay.util.LogUtils;
import com.shanbay.widget.ShanbayProgressDialog;
import com.shanbay.words.R;

import java.util.LinkedList;

public abstract class BaseActivity<T extends APIClient> extends ActionBarActivity
{
  protected final String TAG = LogUtils.makeLogTag(getClass());
  protected T mClient;
  protected LinkedList<FragmentTransaction> mCommit;
  private ShanbayProgressDialog mProgressDialog;
  protected boolean mStateSaved;

  protected void checkUpdate()
  {
//    UmengUpdateAgent.update(this);
  }

  protected boolean commit(FragmentTransaction paramFragmentTransaction)
  {
    try
    {
      if (!this.mStateSaved)
        paramFragmentTransaction.commit();
      else
        this.mCommit.addLast(paramFragmentTransaction);
    }
    catch (IllegalStateException localIllegalStateException)
    {
      return false;
    }
    return true;
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
    if (this.mProgressDialog != null)
    {
      this.mProgressDialog.dismiss();
      this.mProgressDialog = null;
    }
  }

  public abstract T getClient();

  public boolean handleCommonException(ModelResponseException paramModelResponseException)
  {
    if (paramModelResponseException.getStatusCode() == 268369921)
    {
      dismissProgressDialog();
      serverFailure();
      return true;
    }
    if (paramModelResponseException.getStatusCode() == 983040)
    {
      dismissProgressDialog();
      networkFailure();
      return true;
    }
    if (paramModelResponseException.getStatusCode() == 16711680)
    {
      dismissProgressDialog();
      networkFailure();
      return true;
    }
    dismissProgressDialog();
    return false;
  }

  @Deprecated
  public boolean handleNetworkException(ModelResponseException paramModelResponseException)
  {
    if (paramModelResponseException.getStatusCode() == 983040)
    {
      dismissProgressDialog();
      showToast(getString(R.string.msg_connect_exception));
      return true;
    }
    if (paramModelResponseException.getStatusCode() == 16711680)
    {
      dismissProgressDialog();
      showToast(getString(R.string.msg_network_failure));
      return true;
    }
    dismissProgressDialog();
    return false;
  }
  
  public boolean isCommonException(ModelResponseException paramModelResponseException)
  {
    return (paramModelResponseException.getStatusCode() == 268369921) || (paramModelResponseException.getStatusCode() == 983040) || (paramModelResponseException.getStatusCode() == 16711680) || (paramModelResponseException.getStatusCode() == 268369922);
  }
  
  protected boolean isFirstActivity()
  {
    return false;
  }

  protected boolean isMainActivity()
  {
    return false;
  }

  public void networkFailure()
  {
    showToast(getString(R.string.msg_connect_exception));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    mClient = getClient();
    if (isFirstActivity())
      onFirstActivity();
    if (isMainActivity())
      onMainActivity();
    mCommit = new LinkedList();
    mStateSaved = false;
    d("onCreate");
  }

  protected void onDestroy()
  {
    d("onDestroy");
    dismissProgressDialog();
    mProgressDialog = null;
    mCommit.clear();
    super.onDestroy();
  }

  protected void onFirstActivity()
  {
//    if (Config.UMENG_ENABLE)
//      MobclickAgent.onError(this);
  }

  protected void onMainActivity()
  {
//    if (Config.UMENG_AUTO_UPDATE)
      checkUpdate();
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    d("onNewIntent:" + paramIntent);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 16908332:
    }
    finish();
    return true;
  }

  protected void onPause()
  {
    super.onPause();
//    if (Config.UMENG_ENABLE)
//      MobclickAgent.onPause(this);
    d("onPause");
  }

  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    d("onRestoreInstanceState");
    super.onRestoreInstanceState(paramBundle);
  }

  protected void onResume()
  {
    super.onResume();
//    if (Config.UMENG_ENABLE)
//      MobclickAgent.onResume(this);
    mStateSaved = false;
    d("onResume");
  }

  protected void onResumeFragments()
  {
    d("onResumeFragments");
    super.onResumeFragments();
    while (!mCommit.isEmpty())
      ((FragmentTransaction)this.mCommit.removeFirst()).commit();
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    d("onSaveInstanceState");
    super.onSaveInstanceState(paramBundle);
  }

  protected void onStart()
  {
    super.onStart();
    mStateSaved = false;
//    if (Config.GA_ENABLE)
//      EasyTracker.getInstance(this).activityStart(this);
    d("onStart");
  }

  protected void onStop()
  {
    super.onStop();
    mStateSaved = true;
//    if (Config.GA_ENABLE)
//      EasyTracker.getInstance(this).activityStop(this);
    d("onStop");
  }

  public void serverFailure()
  {
    showToast(getString(R.string.msg_server_failure));
  }

	public AlertDialog showExceptionDialog(ModelResponseException mre) {
		AlertDialog localAlertDialog = new AlertDialog.Builder(this)
				.setMessage(mre.getMessage())
				.setPositiveButton(getString(R.string.ok), null).create();
		if (!isFinishing())
			localAlertDialog.show();
		return localAlertDialog;
	}

	public AlertDialog showExceptionDialog(ModelResponseException mre,
			boolean paramBoolean) {
		AlertDialog localAlertDialog = new AlertDialog.Builder(this)
				.setMessage(mre.getMessage())
				.setPositiveButton(getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								BaseActivity.this.finish();
							}
						}).create();

		if (!isFinishing())
			localAlertDialog.show();
		return localAlertDialog;
	}

  public void showProgressDialog()
  {
    showProgressDialog(null);
  }

  public void showProgressDialog(String paramString)
  {
	  if(mProgressDialog == null){
		  mProgressDialog = new ShanbayProgressDialog(this);
		  mProgressDialog.setCanceledOnTouchOutside(false);
		  mProgressDialog.setCancelable(true);
		  mProgressDialog.show();
	  }else{
		  if (paramString != null)
	          mProgressDialog.setContentMessage(paramString);
		  if(!mProgressDialog.isShowing()){
			  mProgressDialog.setCanceledOnTouchOutside(false);
			  mProgressDialog.setCancelable(true);
			  mProgressDialog.show();
		  }
	  }
  }

  public void showToast(int str)
  {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
  }

  public void showToast(String str)
  {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
  }
}