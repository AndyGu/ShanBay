package com.shanbay.account;

import com.google.gson.JsonElement;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.http.APIClient;
import com.shanbay.http.DataResponseHandler;
import com.shanbay.http.ModelResponseException;
import com.shanbay.util.Misc;

public class InitHandler<T extends APIClient> extends DataResponseHandler
{
  private ShanbayActivity<T> mActivity;

  public InitHandler(ShanbayActivity<T> paramShanbayActivity)
  {
    this.mActivity = paramShanbayActivity;
  }

  public void init()
  {
    if (Misc.isInternetAvailable(this.mActivity))
    {
      this.mActivity.getClient().init(this.mActivity, this);
      return;
    }
    this.mActivity.networkFailure();
  }

  protected void initIntenal()
  {
    this.mActivity.getClient().init(this.mActivity, this);
  }

  protected void onAuthenticationFailure()
  {
    if (this.mActivity.isFinishing())
      return;
    this.mActivity.onRequestLogin();
    this.mActivity.finish();
  }

  public void onFailure(ModelResponseException paramModelResponseException, JsonElement paramJsonElement)
  {
    this.mActivity.handleCommonException(paramModelResponseException);
  }

  protected void onInitSuccess()
  {
    if (this.mActivity.isFinishing())
      return;
    this.mActivity.finish();
    this.mActivity.home();
  }

  protected void onRecommendedAppSuccess()
  {
    initIntenal();
  }

  public void onSuccess(int paramInt, JsonElement paramJsonElement)
  {
    onInitSuccess();
  }
}