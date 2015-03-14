package com.shanbay.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.renamedgson.JsonElement;
import com.shanbay.app.BaseActivityHelper;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.http.APIClient;
import com.shanbay.http.DataResponseHandler;
import com.shanbay.http.ModelResponseException;
import com.shanbay.words.R;

import java.io.PrintStream;

public class LoginActivityHelper<T extends APIClient> extends BaseActivityHelper<T>
{
  private static final String LISP_NAME = "lisp";
  private static final String PASSWORD = "password";
  private static final String REMEMBER = "remember";
  private static final String USERNAME = "username";
  private InitHandler<T> mInitRH;
  protected TextView mPasswordTextView;
  protected CheckBox mRememberMeCheckBox;
  private UserHandler<T> mUserRH;
  protected TextView mUsernameTextView;

  public LoginActivityHelper(ShanbayActivity<T> paramShanbayActivity)
  {
    this(paramShanbayActivity, null, null);
  }

  public LoginActivityHelper(ShanbayActivity<T> paramShanbayActivity, InitHandler<T> paramInitHandler, UserHandler<T> paramUserHandler)
  {
    super(paramShanbayActivity);
    if (paramInitHandler == null)
      paramInitHandler = new InitHandler(paramShanbayActivity);
    if (paramUserHandler == null)
      paramUserHandler = new UserHandler(paramShanbayActivity)
      {
        protected void onUserLoaded()
        {
          LoginActivityHelper.this.mInitRH.init();
        }
      };
    this.mInitRH = paramInitHandler;
    this.mUserRH = paramUserHandler;
  }

  public LoginActivityHelper(final ShanbayActivity<T> paramShanbayActivity, UserHandler<T> paramUserHandler)
  {
    super(paramShanbayActivity);
    if (paramUserHandler == null)
      paramUserHandler = new UserHandler(paramShanbayActivity)
      {
        protected void onUserLoaded()
        {
          paramShanbayActivity.finish();
          paramShanbayActivity.home();
        }
      };
    this.mUserRH = paramUserHandler;
  }

  public void login()
  {
    String str1 = this.mUsernameTextView.getText().toString().trim();
    String str2 = this.mPasswordTextView.getText().toString().trim();
    if ("".equals(str1))
    {
      this.mUsernameTextView.requestFocus();
      this.mUsernameTextView.setError(getString(R.string.msg_empty_username));
      return;
    }
    if ("".equals(str2))
    {
      this.mPasswordTextView.requestFocus();
      this.mPasswordTextView.setError(getString(R.string.msg_empty_psw));
      return;
    }
    showProgressDialog();
    this.mClient.login(this.c, str1, str2, new DataResponseHandler()
    {
      public void onFailure(ModelResponseException paramAnonymousModelResponseException, JsonElement paramAnonymousJsonElement)
      {
        if (!LoginActivityHelper.this.handleCommonException(paramAnonymousModelResponseException))
        {
          LoginActivityHelper.this.dismissProgressDialog();
          System.out.println(paramAnonymousModelResponseException.getMessage());
          LoginActivityHelper.this.showExceptionDialog(paramAnonymousModelResponseException);
        }
      }

      public void onSuccess(int paramAnonymousInt, JsonElement paramAnonymousJsonElement)
      {
        LoginActivityHelper.this.storeLoginInfoIfNecessary();
        LoginActivityHelper.this.mUserRH.user();
      }
    });
  }

  public void onCreate(Bundle paramBundle)
  {
    this.mUsernameTextView = ((TextView)findViewById(R.id.tv_username));
    this.mPasswordTextView = ((TextView)findViewById(R.id.tv_password));
    this.mRememberMeCheckBox = ((CheckBox)findViewById(R.id.cb_remember_me));
    SharedPreferences localSharedPreferences = getSharedPreferences("lisp", 0);
    this.mUsernameTextView.setText(localSharedPreferences.getString("username", ""));
    this.mPasswordTextView.setText(localSharedPreferences.getString("password", ""));
    this.mRememberMeCheckBox.setChecked(localSharedPreferences.getBoolean("remember", false));
  }

  public void reset()
  {
    String str = getResources().getString(R.string.link_forget);
    try
    {
      startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void storeLoginInfoIfNecessary()
  {
    if (this.mRememberMeCheckBox.isChecked())
    {
      String str1 = this.mUsernameTextView.getText().toString().trim();
      String str2 = this.mPasswordTextView.getText().toString().trim();
      getSharedPreferences("lisp", 0).edit().putString("username", str1).putString("password", str2).putBoolean("remember", true).commit();
      return;
    }
    getSharedPreferences("lisp", 0).edit().clear().commit();
  }
}