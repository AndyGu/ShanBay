package com.shanbay.words.review.experience;

import android.view.ViewGroup;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.model.ReviewData;
import com.shanbay.words.view.SpellView;
import com.shanbay.words.view.TestView;

public class ExpTestView extends TestView
{
  public ExpTestView(WordsActivity mWordsActivity, ViewGroup viewGroup1, ViewGroup viewGroup2, ViewGroup viewGroup3, boolean mBoolean, String str)
  {
    super(mWordsActivity, viewGroup1, viewGroup2, viewGroup3);
    mSpellView = new ExpSpellView(mWordsActivity, viewGroup3, mBoolean, str);
  }

  public SpellView getSpellView()
  {
    return mSpellView;
  }

  public void renderSpell(ReviewData reviewData)
  {
    mSpellView.renderSpell(reviewData);
  }
}