package com.shanbay.words.activity;

import com.shanbay.words.R;
import com.shanbay.words.review.experience.ExpCategoryActivity;

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
    
    case R.id.experience:
      startActivityForResult(new Intent(this, ExpCategoryActivity.class), 37);
      return;
    case R.id.login:
      startActivityForResult(new Intent(this, LoginActivity.class), 32);
      return;
    case R.id.signup:
    	startActivityForResult(new Intent(this, SignupActivity.class), 33);
    	return;
    default:
        return;
    }
    
  }

  @Override
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_welcome);
    this.mBtnExperience = ((Button)findViewById(R.id.experience));
    this.mBtnLogin = ((Button)findViewById(R.id.login));
    this.mBtnSinup = ((Button)findViewById(R.id.signup));
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