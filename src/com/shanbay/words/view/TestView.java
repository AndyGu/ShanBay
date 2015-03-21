package com.shanbay.words.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shanbay.words.R;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.model.ExampleContent;
import com.shanbay.words.model.ExampleData;
import com.shanbay.words.model.ReviewData;
import com.shanbay.words.model.VocabularyData;
import java.io.PrintStream;
import java.util.List;

public class TestView extends WordView
{
  public static final int VIEW_DEFINITION = 4;
  public static final int VIEW_EXAMPLE = 8;
  public static final int VIEW_SOUND = 2;
  public static final int VIEW_WORD = 1;
  private ExampleView mExampleView;
  protected SpellView mSpellView;
  private int mViewMode;

  public TestView(WordsActivity wordsActivity, ViewGroup vg1, ViewGroup vg2, ViewGroup vg3)
  {
    super(wordsActivity, (ViewGroup)vg1.findViewById(R.id.word));
    if (vg2 != null)
      mExampleView = new ExampleView(mActivity, (ViewGroup)vg2.findViewById(R.id.example_view));
    if (vg3 != null)
      mSpellView = new SpellView(this.mActivity, vg3);
  }

  private void handleMode(ReviewData paramReviewData)
  {
    clearAllViews();
    if (isMode(VIEW_WORD))
    {
      System.out.println("VIEW_WORD");
      ((View)mAudioButton.getParent()).setVisibility(View.VISIBLE);
      mRoot.setVisibility(View.VISIBLE);
      mContentText.setVisibility(View.VISIBLE);
      mPronText.setVisibility(View.VISIBLE);
    }
    if (isMode(VIEW_DEFINITION))
    {
      System.out.println("VIEW_DEFINITION");
      mRoot.setVisibility(0);
      ((View)mDefinitionText.getParent()).setVisibility(View.VISIBLE);
      mDefinitionText.setVisibility(View.VISIBLE);
      mDefinitionText.setText(paramReviewData.getVocabulary().getCnDefinition());
    }
    if (isMode(VIEW_SOUND))
    {
      System.out.println("VIEW_SOUND");
      mRoot.setVisibility(View.VISIBLE);
      ((View)mAudioButton.getParent()).setVisibility(View.VISIBLE);
      if ((mDefinitionText.getVisibility() != View.VISIBLE) && (mContentText.getVisibility() != View.VISIBLE))
        mHint.setVisibility(View.VISIBLE);
      mAudioButton.setVisibility(View.VISIBLE);
    }
    if (isMode(VIEW_EXAMPLE))
    {
      System.out.println("VIEW_EXAMPLE");
      this.mExampleView.setVisibility(true, false);
      List localList = paramReviewData.getExamples().getExampleList();
      ExampleContent localExampleContent = null;
      if (localList != null)
      {
        int i = paramReviewData.getExamples().getExampleList().size();
        localExampleContent = null;
        if (i > 0)
        {
          int j = paramReviewData.getExamples().getExampleList().size();
          int k = (int)(Math.random() * j);
          localExampleContent = (ExampleContent)paramReviewData.getExamples().getExampleList().get(k);
          System.out.println("Math.random()--->" + k);
        }
      }
      if (localExampleContent != null)
        mExampleView.render(localExampleContent, true, false);
    }
    else
    {
      return;
    }
    mExampleView.setVisibility(false, false);
  }

  public void clearAllViews()
  {
    super.clearAllViews();
    mExampleView.setVisibility(false, false);
    ((View)mAudioButton.getParent()).setVisibility(8);
    ((View)mDefinitionText.getParent()).setVisibility(8);
  }

  public SpellView getSpellView()
  {
    return mSpellView;
  }

  public void init()
  {
    super.init();
  }

  public boolean isMode(int paramInt)
  {
    return (paramInt & mViewMode) != 0;
  }

  public void render(ReviewData paramReviewData)
  {
    super.render(paramReviewData.getVocabulary());
    handleMode(paramReviewData);
  }

  public void renderSpell(ReviewData paramReviewData)
  {
    mSpellView.renderSpell(paramReviewData);
  }

  public void setExampleVisibility(boolean paramBoolean1, boolean paramBoolean2)
  {
    mExampleView.setVisibility(paramBoolean1, paramBoolean2);
  }

  public void setViewMode(int paramInt)
  {
    System.out.println(Integer.toBinaryString(paramInt));
    mViewMode = paramInt;
  }
}