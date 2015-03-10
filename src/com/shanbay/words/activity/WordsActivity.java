package com.shanbay.words.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.words.WordsClient;
import com.shanbay.words.WordsSoundPlayer;
import com.umeng.analytics.MobclickAgent;

public class WordsActivity extends ShanbayActivity<WordsClient>
{
  protected final String ACTION_HOME_INIT = "home_init";
  protected final String ACTION_HOME_INIT_KEY = "session_date";
  protected final String ACTION_HOME_LOGOUT = "home_logout";
  protected final String ACTION_HOME_NORMAL = "home_normal";
  private AudioManager mAudioManger;
  protected WordsSoundPlayer mSoundPlayer;
  private Toast mToastNetworkFailure;

  public WordsClient getClient()
  {
    return WordsClient.getInstance();
  }

  public WordsSoundPlayer getSoundPlayer()
  {
    return this.mSoundPlayer;
  }

  public void goHome()
  {
    Intent localIntent = new Intent(this, HomeActivity.class);
    localIntent.setFlags(67108864);
    localIntent.setAction("home_normal");
    startActivity(localIntent);
  }

  public void goHomeInit(String paramString)
  {
    Intent localIntent = new Intent(this, HomeActivity.class);
    localIntent.setAction("home_init");
    localIntent.setFlags(67108864);
    localIntent.putExtra("session_date", paramString);
    startActivity(localIntent);
  }

  public void home(String paramString)
  {
    if (!(this instanceof HomeActivity))
    {
      Intent localIntent = new Intent(this, HomeActivity.class);
      localIntent.setFlags(67108864);
      if (paramString != null)
        localIntent.setAction(paramString);
      startActivity(localIntent);
    }
  }

  public void networkFailure()
  {
    showNetworkFailureToast();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mSoundPlayer = new WordsSoundPlayer(getClient());
    this.mAudioManger = ((AudioManager)getSystemService("audio"));
    d("onCreate:");
  }

  protected void onDestroy()
  {
    d("onDestroy");
    super.onDestroy();
    dismissProgressDialog();
    this.mSoundPlayer.stopSound();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default:
      return super.onKeyDown(paramInt, paramKeyEvent);
    case 24:
      this.mAudioManger.adjustStreamVolume(3, 1, 5);
      return true;
    case 25:
    }
    this.mAudioManger.adjustStreamVolume(3, -1, 5);
    return true;
  }

  public void onLogout()
  {
    home("home_logout");
    finish();
  }

  protected void onPause()
  {
    super.onPause();
    MobclickAgent.onPause(this);
    d("onPause");
  }

  public void onRequestLogin()
  {
    startActivity(new Intent(this, WelcomeActivity.class));
  }

  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    d("onRestoreInstanceState:" + paramBundle);
    super.onRestoreInstanceState(paramBundle);
  }

  protected void onResume()
  {
    super.onResume();
    MobclickAgent.onResume(this);
    d("onResume");
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    d("onSaveInstanceState:" + paramBundle);
    super.onSaveInstanceState(paramBundle);
  }

  protected void onStart()
  {
    super.onStart();
    d("onStart");
  }

  protected void onStop()
  {
    super.onStop();
    d("onStop");
  }

  public void serverFailure()
  {
    startActivity(new Intent(this, ServerFailureActivity.class));
    finish();
  }

  public void showNetworkFailureToast()
  {
    if (isFinishing())
      return;
    if (this.mToastNetworkFailure == null)
    {
      this.mToastNetworkFailure = new Toast(this);
      View localView = LayoutInflater.from(this).inflate(2130903258, null);
      this.mToastNetworkFailure.setView(localView);
      this.mToastNetworkFailure.setGravity(0, 0, 0);
      this.mToastNetworkFailure.setDuration(0);
      this.mToastNetworkFailure.show();
      return;
    }
    this.mToastNetworkFailure.show();
  }
}