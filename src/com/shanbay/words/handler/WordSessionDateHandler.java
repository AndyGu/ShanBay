package com.shanbay.words.handler;

import android.content.Intent;
import android.util.Log;

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
	  Log.e("WordSessionDateHandler", "checkinSessionDate()");
    if (this.mIsChecking)
      return;
    this.mIsChecking = true;
    this.mActivity.getClient().sessionDate(this.mActivity, this);
  }

  protected void onAuthenticationFailure()
  {
	  Log.e("WordSessionDateHandler", "onAuthenticationFailure()");
    this.mActivity.finish();
    this.mActivity.startActivity(new Intent(this.mActivity, LoginActivity.class));
  }

  protected void onCheckinSessisonFailure()
  {
	  Log.e("WordSessionDateHandler", "onCheckinSessisonFailure()");
  }

  protected void onCheckinSessisonFinish(boolean paramBoolean, CheckinDate paramCheckinDate)
  {
	  Log.e("WordSessionDateHandler", "onCheckinSessisonFinish()");
  }

  public void onFailure(ModelResponseException paramModelResponseException, JsonElement paramJsonElement)
  {

	  Log.e("WordSessionDateHandler", "onFailure()");
    this.mIsChecking = false;
    onCheckinSessisonFailure();
  }

  public void onSuccess(int paramInt, CheckinDate paramCheckinDate)
  {
	  Log.e("WordSessionDateHandler", "onSuccess()");
    boolean bool = SessionDateUtils.isExpired(this.mActivity, paramCheckinDate.sessionDate);
    this.mIsChecking = false;
    onCheckinSessisonFinish(bool, paramCheckinDate);
  }
}