package com.shanbay.words.activity;

import android.os.Bundle;
import android.view.View;
import com.shanbay.words.R;
import com.shanbay.words.WordsClient;
import com.shanbay.words.handler.WordsUserHandler;
import com.shanbay.words.helper.WordsSignupActivityHelper;

public class SignupActivity extends WordsActivity
{
  public static final int REQUEST_CODE_SIGNUP = 33;
  private WordsSignupActivityHelper<WordsClient> mSignupHelper;
  private WordsUserHandler mWordsUserHandler;

  @SuppressWarnings({ "rawtypes", "unchecked" })
protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_sign_up);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.mWordsUserHandler = new WordsUserHandler(this);
    this.mSignupHelper = new WordsSignupActivityHelper(this, this.mWordsUserHandler);
    this.mSignupHelper.onCreate(paramBundle);
  }

  public void signup(View view)
  {
    this.mSignupHelper.signup();
  }
}