package com.shanbay.words.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends WordsActivity
  implements View.OnClickListener
{
  private Button mBtnExperience;
  private Button mBtnLogin;
  private Button mBtnSinup;

  
//  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
//  {
//    int i = 1;
//    int j;
//    int k;
//    if ((paramInt1 == 32) && (paramInt2 == -1))
//    {
//      j = i;
//      if ((paramInt1 != 33) || (paramInt2 != -1))
//        break ;
//      k = i;
//      if ((paramInt1 != 37) || (paramInt2 != 38))
//        break ;
//    }
//    while (true)
//    {
//      if ((j != 0) || (k != 0) || (i != 0))
//        finish();
//      return;
//      j = 0;
//      break;
//      label71: k = 0;
//      break label33;
//      label77: i = 0;
//    }
//  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131034258:
//      startActivityForResult(new Intent(this, ExpCategoryActivity.class), 37);
      return;
    case 2131034247:
      startActivityForResult(new Intent(this, LoginActivity.class), 32);
      return;
    case 2131034246:
    }
    startActivityForResult(new Intent(this, SignupActivity.class), 33);
  }

  @Override
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903122);
    this.mBtnExperience = ((Button)findViewById(2131034258));
    this.mBtnLogin = ((Button)findViewById(2131034247));
    this.mBtnSinup = ((Button)findViewById(2131034246));
    this.mBtnExperience.setOnClickListener(this);
    this.mBtnLogin.setOnClickListener(this);
    this.mBtnSinup.setOnClickListener(this);
  }

  @Override
  protected void onActivityResult(int arg0, int arg1, Intent arg2) {
	// TODO Auto-generated method stub
	super.onActivityResult(arg0, arg1, arg2);
}
  
  
}