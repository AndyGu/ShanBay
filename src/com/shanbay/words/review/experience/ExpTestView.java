package com.shanbay.words.review.experience;

import android.view.ViewGroup;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.model.ReviewData;
import com.shanbay.words.view.SpellView;
import com.shanbay.words.view.TestView;

public class ExpTestView extends TestView
{
  public ExpTestView(WordsActivity paramWordsActivity, ViewGroup paramViewGroup1, ViewGroup paramViewGroup2, ViewGroup paramViewGroup3, boolean paramBoolean, String paramString)
  {
    super(paramWordsActivity, paramViewGroup1, paramViewGroup2, paramViewGroup3);
    this.mSpellView = new ExpSpellView(paramWordsActivity, paramViewGroup3, paramBoolean, paramString);
  }

  public SpellView getSpellView()
  {
    return this.mSpellView;
  }

  public void renderSpell(ReviewData paramReviewData)
  {
    this.mSpellView.renderSpell(paramReviewData);
  }
}