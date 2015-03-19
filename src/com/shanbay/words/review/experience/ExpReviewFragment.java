package com.shanbay.words.review.experience;

import android.app.Activity;
import android.os.Bundle;
import com.shanbay.app.BaseFragment;
import com.shanbay.words.WordsClient;

public class ExpReviewFragment extends BaseFragment<WordsClient>
{
  protected ExpReviewActivity mActivity;
  private boolean mIsStateSaved = false;

  protected boolean isRenderable()
  {
    return (this.mActivity != null) && (!this.mIsStateSaved);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mActivity = ((ExpReviewActivity)paramActivity);
  }

  public void onCreate(Bundle bundle)
  {
    if (bundle != null){
        mIsStateSaved = true;
    }else{
    	mIsStateSaved = false;
    }
    super.onCreate(bundle);
  }
}