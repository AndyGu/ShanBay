package com.shanbay.account;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.http.APIClient;
import com.shanbay.http.ModelResponseException;
import com.shanbay.http.ModelResponseHandler;
import com.shanbay.model.User;

public class UserHandler<T extends APIClient> extends ModelResponseHandler<User>
{
  private ShanbayActivity<T> mActivity;

  public UserHandler(ShanbayActivity<T> paramShanbayActivity)
  {
    super(User.class);
    this.mActivity = paramShanbayActivity;
  }

  protected void onAuthenticationFailure()
  {
	  Log.e("UserHandler", "onAuthenticationFailure()");
    this.mActivity.finish();
    this.mActivity.onRequestLogin();
  }

  public void onFailure(ModelResponseException mrException, JsonElement jsonElement)
  {

	  Log.e("UserHandler", "onFailure()");
    this.mActivity.handleCommonException(mrException);
  }
  
  public void onSuccess(int paramInt, User user)
  {
	  Log.e("UserHandler", "onSuccess()");
    UserCache.update(this.mActivity, user);
    onUserLoaded();
  }

  protected void onUserLoaded()
  {
	  Log.e("UserHandler", "onUserLoaded()");
  }

	public void user() {
		Log.e("UserHandler", "user()");
		this.mActivity.getClient().user(this.mActivity, this);
	}
}