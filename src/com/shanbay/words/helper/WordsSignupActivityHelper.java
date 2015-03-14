package com.shanbay.words.helper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;
import com.shanbay.account.SignupActivityHelper;
import com.shanbay.account.UserHandler;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.http.APIClient;

public class WordsSignupActivityHelper<T extends APIClient> extends SignupActivityHelper<T>
  implements View.OnFocusChangeListener
{
  public WordsSignupActivityHelper(ShanbayActivity<T> paramShanbayActivity, UserHandler<T> paramUserHandler)
  {
    super(paramShanbayActivity, paramUserHandler);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mUsernameTextView.setOnFocusChangeListener(this);
    this.mEmailTextView.setOnFocusChangeListener(this);
    this.mPasswordTextView.setOnFocusChangeListener(this);
    this.mPassword2TextView.setOnFocusChangeListener(this);
  }

  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    if (2131034148 == paramView.getId())
    {
      this.mUsernameTextView.setHint("");
      this.mEmailTextView.setHint("Email");
      this.mPasswordTextView.setHint("密 码");
      this.mPassword2TextView.setHint("确认密码");
    }
    
    if (2131034150 == paramView.getId())
    {
      this.mUsernameTextView.setHint("用户名");
      this.mEmailTextView.setHint("");
      this.mPasswordTextView.setHint("密 码");
      this.mPassword2TextView.setHint("确认密码");
    }
      if (2131034151 == paramView.getId())
      {
        this.mUsernameTextView.setHint("用户名");
        this.mEmailTextView.setHint("Email");
        this.mPasswordTextView.setHint("");
        this.mPassword2TextView.setHint("确认密码");
      }
    while (2131034149 != paramView.getId());
    this.mUsernameTextView.setHint("用户名");
    this.mEmailTextView.setHint("Email");
    this.mPasswordTextView.setHint("密 码");
    this.mPassword2TextView.setHint("");
  }
}