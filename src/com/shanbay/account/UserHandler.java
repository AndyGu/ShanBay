package com.shanbay.account;

import com.google.renamedgson.JsonElement;
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
    this.mActivity.finish();
    this.mActivity.onRequestLogin();
  }

  public void onFailure(ModelResponseException paramModelResponseException, JsonElement paramJsonElement)
  {
    this.mActivity.handleCommonException(paramModelResponseException);
  }

  public void onSuccess(int paramInt, User paramUser)
  {
    UserCache.update(this.mActivity, paramUser);
    onUserLoaded();
  }

  protected void onUserLoaded()
  {
  }

  public void user()
  {
    this.mActivity.getClient().user(this.mActivity, this);
  }
}