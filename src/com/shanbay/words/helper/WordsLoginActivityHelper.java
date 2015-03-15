package com.shanbay.words.helper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;
import com.shanbay.account.LoginActivityHelper;
import com.shanbay.account.UserHandler;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.http.APIClient;
import com.shanbay.words.R;

public class WordsLoginActivityHelper<T extends APIClient> extends LoginActivityHelper<T>
  implements View.OnFocusChangeListener
{
  public WordsLoginActivityHelper(ShanbayActivity<T> paramShanbayActivity, UserHandler<T> paramUserHandler)
  {
    super(paramShanbayActivity, paramUserHandler);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mUsernameTextView.setOnFocusChangeListener(this);
    this.mPasswordTextView.setOnFocusChangeListener(this);
  }

  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    if (R.id.tv_username == paramView.getId())
    {
      this.mUsernameTextView.setHint("");
      this.mPasswordTextView.setHint("密 码");
    }
    
    if (R.id.tv_password == paramView.getId())
    {
    	this.mPasswordTextView.setHint("");
        this.mUsernameTextView.setHint("用户名");
    }
  }
}