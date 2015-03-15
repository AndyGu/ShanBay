package com.shanbay.words.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.shanbay.words.R;
import com.shanbay.words.WordsClient;
import com.shanbay.words.handler.WordsUserHandler;
import com.shanbay.words.helper.WordsLoginActivityHelper;

public class LoginActivity extends WordsActivity
{
  public static final int REQUEST_CODE_LOGIN = 32;
  private WordsLoginActivityHelper<WordsClient> mLoginHelper;
  private WordsUserHandler mUserHandler;

  public void login(View paramView)
  {
    this.mLoginHelper.login();
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent intent)
  {
    if ((requestCode == 33) && (resultCode == -1))
    {
      setResult(-1);
      finish();
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_log_in);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.mUserHandler = new WordsUserHandler(this);
    this.mLoginHelper = new WordsLoginActivityHelper(this, this.mUserHandler);
    this.mLoginHelper.onCreate(paramBundle);
  }

  public void reset(View paramView)
  {
    this.mLoginHelper.reset();
  }

  public void signup(View paramView)
  {
    startActivityForResult(new Intent(this, SignupActivity.class), 33);
  }
}