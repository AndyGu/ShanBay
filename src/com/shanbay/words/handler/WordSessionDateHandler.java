package com.shanbay.words.handler;

import android.content.Intent;
import com.google.renamedgson.JsonElement;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.community.model.CheckinDate;
import com.shanbay.http.ModelResponseException;
import com.shanbay.http.ModelResponseHandler;
import com.shanbay.words.WordsClient;
import com.shanbay.words.activity.LoginActivity;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.util.SessionDateUtils;

public class WordSessionDateHandler extends ModelResponseHandler<CheckinDate>
{
  private WordsActivity mActivity;
  private boolean mIsChecking = false;

  public WordSessionDateHandler(ShanbayActivity<WordsClient> paramShanbayActivity)
  {
    super(CheckinDate.class);
    this.mActivity = ((WordsActivity)paramShanbayActivity);
  }

  public void checkinSessionDate()
  {
    if (this.mIsChecking)
      return;
    this.mIsChecking = true;
    this.mActivity.getClient().sessionDate(this.mActivity, this);
  }

  protected void onAuthenticationFailure()
  {
    this.mActivity.finish();
    this.mActivity.startActivity(new Intent(this.mActivity, LoginActivity.class));
  }

  protected void onCheckinSessisonFailure()
  {
  }

  protected void onCheckinSessisonFinish(boolean paramBoolean, CheckinDate paramCheckinDate)
  {
  }

  public void onFailure(ModelResponseException paramModelResponseException, JsonElement paramJsonElement)
  {
    this.mIsChecking = false;
    onCheckinSessisonFailure();
  }

  public void onSuccess(int paramInt, CheckinDate paramCheckinDate)
  {
    boolean bool = SessionDateUtils.isExpired(this.mActivity, paramCheckinDate.sessionDate);
    this.mIsChecking = false;
    onCheckinSessisonFinish(bool, paramCheckinDate);
  }
}