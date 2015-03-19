package com.shanbay.words.review.experience;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.shanbay.words.activity.LoginActivity;
import com.shanbay.words.activity.SignupActivity;
import com.shanbay.words.activity.WelcomeActivity;
import com.shanbay.words.activity.WordsActivity;

public class ExpFinishedActivity extends WordsActivity
  implements View.OnClickListener
{
  private Button mBtnLogin;
  private Button mBtnSinup;

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (((paramInt1 == 32) || (paramInt1 == 33)) && (paramInt2 == -1))
      finish();
  }

  public void onBackPressed()
  {
    startActivity(new Intent(this, WelcomeActivity.class));
    super.onBackPressed();
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131034247:
      startActivityForResult(new Intent(this, LoginActivity.class), 32);
      return;
    case 2131034246:
    }
    startActivityForResult(new Intent(this, SignupActivity.class), 33);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903076);
    this.mBtnLogin = ((Button)findViewById(2131034247));
    this.mBtnSinup = ((Button)findViewById(2131034246));
    this.mBtnLogin.setOnClickListener(this);
    this.mBtnSinup.setOnClickListener(this);
  }
}