package com.shanbay.words.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import com.shanbay.words.WordsClient;
import com.shanbay.words.handler.WordsUserHandler;
import com.shanbay.words.helper.WordsSignupActivityHelper;

public class SignupActivity extends WordsActivity
{
  public static final int REQUEST_CODE_SIGNUP = 33;
  private WordsSignupActivityHelper<WordsClient> mSignupHelper;
  private WordsUserHandler mUserHandler;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903114);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.mUserHandler = new WordsUserHandler(this);
    this.mSignupHelper = new WordsSignupActivityHelper(this, this.mUserHandler);
    this.mSignupHelper.onCreate(paramBundle);
  }

  public void signup(View paramView)
  {
    this.mSignupHelper.signup();
  }
}