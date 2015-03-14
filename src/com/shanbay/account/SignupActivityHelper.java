package com.shanbay.account;

import android.os.Bundle;
import android.widget.TextView;
import com.google.renamedgson.JsonElement;
import com.shanbay.app.BaseActivityHelper;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.http.APIClient;
import com.shanbay.http.DataResponseHandler;
import com.shanbay.http.ModelResponseException;
import com.shanbay.words.R;

import java.io.PrintStream;

public class SignupActivityHelper<T extends APIClient> extends BaseActivityHelper<T>
{
  protected TextView mEmailTextView;
  private InitHandler<T> mInitRH;
  protected TextView mPassword2TextView;
  protected TextView mPasswordTextView;
  private UserHandler<T> mUserRH;
  protected TextView mUsernameTextView;

  public SignupActivityHelper(ShanbayActivity<T> paramShanbayActivity)
  {
    this(paramShanbayActivity, null, null);
  }

  public SignupActivityHelper(ShanbayActivity<T> paramShanbayActivity, InitHandler<T> paramInitHandler, UserHandler<T> paramUserHandler)
  {
    super(paramShanbayActivity);
    if (paramInitHandler == null)
      paramInitHandler = new InitHandler(paramShanbayActivity);
    if (paramUserHandler == null)
      paramUserHandler = new UserHandler(paramShanbayActivity)
      {
        protected void onUserLoaded()
        {
          SignupActivityHelper.this.mInitRH.init();
        }
      };
    this.mInitRH = paramInitHandler;
    this.mUserRH = paramUserHandler;
  }

  public SignupActivityHelper(final ShanbayActivity<T> paramShanbayActivity, UserHandler<T> paramUserHandler)
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

  private void login(String paramString1, String paramString2)
  {
    this.mClient.login(this.c, paramString1, paramString2, new DataResponseHandler()
    {
      public void onFailure(ModelResponseException paramAnonymousModelResponseException, JsonElement paramAnonymousJsonElement)
      {
        if (!SignupActivityHelper.this.handleCommonException(paramAnonymousModelResponseException))
        {
          SignupActivityHelper.this.dismissProgressDialog();
          SignupActivityHelper.this.d(paramAnonymousModelResponseException.getMessage());
          SignupActivityHelper.this.showExceptionDialog(paramAnonymousModelResponseException);
        }
      }

      public void onSuccess(int paramAnonymousInt, JsonElement paramAnonymousJsonElement)
      {
        SignupActivityHelper.this.mUserRH.user();
      }
    });
  }

  public void onCreate(Bundle paramBundle)
  {
    this.mUsernameTextView = ((TextView)findViewById(R.id.tv_username));
    this.mEmailTextView = ((TextView)findViewById(R.id.tv_email));
    this.mPasswordTextView = ((TextView)findViewById(R.id.tv_password));
    this.mPassword2TextView = ((TextView)findViewById(R.id.tv_password_repeat));
  }

  public void signup()
  {
    String str1 = this.mUsernameTextView.getText().toString().trim();
    String str2 = this.mEmailTextView.getText().toString().trim();
    String str3 = this.mPasswordTextView.getText().toString();
    String str4 = this.mPassword2TextView.getText().toString();
    if ("".equals(str1))
    {
      this.mUsernameTextView.requestFocus();
      this.mUsernameTextView.setError(getString(R.string.msg_empty_username));
      return;
    }
    if ("".equals(str2))
    {
      this.mEmailTextView.requestFocus();
      this.mEmailTextView.setError(getString(R.string.msg_empty_email));
      return;
    }
    if ("".equals(str3))
    {
      this.mPasswordTextView.requestFocus();
      this.mPasswordTextView.setError(getString(R.string.msg_empty_psw));
      return;
    }
    if (!str3.equals(str4))
    {
      this.mPassword2TextView.requestFocus();
      this.mPassword2TextView.setError(getString(R.string.msg_psw_not_match));
      return;
    }
    showProgressDialog();
    this.mClient.createAccount(this.c, str1, str2, str3, str4, new DataResponseHandler()
    {
      public void onFailure(ModelResponseException paramAnonymousModelResponseException, JsonElement paramAnonymousJsonElement)
      {
        if (!SignupActivityHelper.this.handleCommonException(paramAnonymousModelResponseException))
        {
          SignupActivityHelper.this.dismissProgressDialog();
          System.out.println(paramAnonymousModelResponseException.getMessage());
          SignupActivityHelper.this.showExceptionDialog(paramAnonymousModelResponseException);
        }
      }

      public void onSuccess(int paramAnonymousInt, JsonElement paramAnonymousJsonElement)
      {
        SignupActivityHelper.this.showToast(SignupActivityHelper.this.getString(R.string.msg_signup_success));
        SignupActivityHelper.this.login(SignupActivityHelper.this.mUsernameTextView.getText().toString().trim(), SignupActivityHelper.this.mPasswordTextView.getText().toString());
      }
    });
  }
}