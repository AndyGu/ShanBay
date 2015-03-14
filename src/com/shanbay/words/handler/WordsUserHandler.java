package com.shanbay.words.handler;

import com.shanbay.account.UserHandler;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.community.model.CheckinDate;
import com.shanbay.words.WordsClient;
import com.shanbay.words.activity.WordsActivity;

public class WordsUserHandler extends UserHandler<WordsClient>
{
  private WordsActivity mActivity;
  private WordSessionDateHandler mSessionDateHandler;

  public WordsUserHandler(ShanbayActivity<WordsClient> mActivity)
  {
    super(mActivity);
    this.mActivity = ((WordsActivity)mActivity);
  }

  protected void onUserLoaded()
  {
    this.mSessionDateHandler = new WordSessionDateHandler(this.mActivity)
    {
      protected void onCheckinSessisonFinish(boolean paramAnonymousBoolean, CheckinDate paramAnonymousCheckinDate)
      {
        if (paramAnonymousBoolean){
        	WordsUserHandler.this.mActivity.goHomeInit(paramAnonymousCheckinDate.sessionDate);
        }else{
        	WordsUserHandler.this.mActivity.setResult(-1);
            WordsUserHandler.this.mActivity.finish();
            WordsUserHandler.this.mActivity.goHome();
        }
      }
    };
    this.mSessionDateHandler.checkinSessionDate();
  }
}