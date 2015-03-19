package com.shanbay.words.review.experience;

import android.view.ViewGroup;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.view.WordView;

public class ExpWordView extends WordView
{
  private boolean isEnableCollins;

  public ExpWordView(WordsActivity paramWordsActivity, ViewGroup paramViewGroup, boolean paramBoolean)
  {
    super(paramWordsActivity, paramViewGroup);
    this.isEnableCollins = paramBoolean;
  }

  public boolean isShowCollins()
  {
    return this.isEnableCollins;
  }
}