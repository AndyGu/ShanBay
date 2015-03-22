package com.shanbay.words.view;

import android.util.Log;
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
    if (vg2 != null){
        mExampleView = new ExampleView(mActivity, (ViewGroup)vg2.findViewById(R.id.example_view));
    	Log.e("TestView", "new ExampleView");
    }
    if (vg3 != null){
        mSpellView = new SpellView(this.mActivity, vg3);
    	Log.e("TestView", "new SpellView");
    }
  }

  private void handleMode(ReviewData reviewData)
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
      mRoot.setVisibility(View.VISIBLE);
      ((View)mDefinitionText.getParent()).setVisibility(View.VISIBLE);
      mDefinitionText.setVisibility(View.VISIBLE);
      mDefinitionText.setText(reviewData.getVocabulary().getCnDefinition());
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
      Log.e("handleMode", "VIEW_EXAMPLE");
      mExampleView.setVisibility(true, false);
      List localList = reviewData.getExamples().getExampleList();
      ExampleContent localExampleContent = null;
      if (localList != null)
      {
        int i = reviewData.getExamples().getExampleList().size();
        localExampleContent = null;
        if (i > 0)
        {
          int j = reviewData.getExamples().getExampleList().size();
          int k = (int)(Math.random() * j);
          localExampleContent = (ExampleContent)reviewData.getExamples().getExampleList().get(k);
          System.out.println("Math.random()--->" + k);
        }
      }
      
      if (localExampleContent != null){
          mExampleView.render(localExampleContent, true, false);
    	  Log.e("handleMode", "localExampleContent != null");
      }
    }else{
    	return;
    }
    mExampleView.setVisibility(false, false);
    Log.e("handleMode", "setVisibility false false");
  }

  public void clearAllViews()
  {
    super.clearAllViews();

	  Log.e("clearAllViews", "clearAllViews");
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
	  boolean b = ((paramInt & mViewMode) != 0);
	  Log.e("isMode", "para="+paramInt+" mViewMode="+mViewMode+ " b="+b);
    return b;
  }

  public void render(ReviewData reviewData)
  {
    super.render(reviewData.getVocabulary());
    handleMode(reviewData);
  }

  public void renderSpell(ReviewData paramReviewData)
  {
    mSpellView.renderSpell(paramReviewData);
  }

  public void setExampleVisibility(boolean isENVisible, boolean isCNVisible)
  {
	  Log.e("setExampleVisibility", "setExampleVisibility");
    mExampleView.setVisibility(isENVisible, isCNVisible);
  }

  public void setViewMode(int paramInt)
  {
    System.out.println(Integer.toBinaryString(paramInt));
    mViewMode = paramInt;
  }
}