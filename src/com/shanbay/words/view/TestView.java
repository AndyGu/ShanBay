package com.shanbay.words.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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

  public TestView(WordsActivity paramWordsActivity, ViewGroup paramViewGroup1, ViewGroup paramViewGroup2, ViewGroup paramViewGroup3)
  {
    super(paramWordsActivity, (ViewGroup)paramViewGroup1.findViewById(2131034459));
    if (paramViewGroup2 != null)
      this.mExampleView = new ExampleView(this.mActivity, (ViewGroup)paramViewGroup2.findViewById(2131034442));
    if (paramViewGroup3 != null)
      this.mSpellView = new SpellView(this.mActivity, paramViewGroup3);
  }

  private void handleMode(ReviewData paramReviewData)
  {
    clearAllViews();
    if (isMode(1))
    {
      System.out.println("VIEW_WORD");
      ((View)this.mAudioButton.getParent()).setVisibility(0);
      this.mRoot.setVisibility(0);
      this.mContentText.setVisibility(0);
      this.mPronText.setVisibility(0);
    }
    if (isMode(4))
    {
      System.out.println("VIEW_DEFINITION");
      this.mRoot.setVisibility(0);
      ((View)this.mDefinitionText.getParent()).setVisibility(0);
      this.mDefinitionText.setVisibility(0);
      this.mDefinitionText.setText(paramReviewData.getVocabulary().getCnDefinition());
    }
    if (isMode(2))
    {
      System.out.println("VIEW_SOUND");
      this.mRoot.setVisibility(0);
      ((View)this.mAudioButton.getParent()).setVisibility(0);
      if ((this.mDefinitionText.getVisibility() != 0) && (this.mContentText.getVisibility() != 0))
        this.mHint.setVisibility(0);
      this.mAudioButton.setVisibility(0);
    }
    if (isMode(8))
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
        this.mExampleView.render(localExampleContent, true, false);
    }
    else
    {
      return;
    }
    this.mExampleView.setVisibility(false, false);
  }

  public void clearAllViews()
  {
    super.clearAllViews();
    this.mExampleView.setVisibility(false, false);
    ((View)this.mAudioButton.getParent()).setVisibility(8);
    ((View)this.mDefinitionText.getParent()).setVisibility(8);
  }

  public SpellView getSpellView()
  {
    return this.mSpellView;
  }

  public void init()
  {
    super.init();
  }

  public boolean isMode(int paramInt)
  {
    return (paramInt & this.mViewMode) != 0;
  }

  public void render(ReviewData paramReviewData)
  {
    super.render(paramReviewData.getVocabulary());
    handleMode(paramReviewData);
  }

  public void renderSpell(ReviewData paramReviewData)
  {
    this.mSpellView.renderSpell(paramReviewData);
  }

  public void setExampleVisibility(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mExampleView.setVisibility(paramBoolean1, paramBoolean2);
  }

  public void setViewMode(int paramInt)
  {
    System.out.println(Integer.toBinaryString(paramInt));
    this.mViewMode = paramInt;
  }
}