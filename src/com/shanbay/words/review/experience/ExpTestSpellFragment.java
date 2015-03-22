package com.shanbay.words.review.experience;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.shanbay.util.Misc;
import com.shanbay.words.R;
import com.shanbay.words.WordsSoundPlayer;
import com.shanbay.words.model.ExampleData;
import com.shanbay.words.model.ProgressBarData;
import com.shanbay.words.model.ReviewData;
import com.shanbay.words.model.Roots;
import com.shanbay.words.model.Roots.Representative;
import com.shanbay.words.model.RootsContent;
import com.shanbay.words.model.RootsData;
import com.shanbay.words.model.VocabularyData;
import com.shanbay.words.view.ProgressBar;
import com.shanbay.words.view.SpellView;
import com.shanbay.words.view.SpellView.ReviewResultObserver;
import java.io.PrintStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpTestSpellFragment extends ExpReviewFragment
  implements View.OnClickListener
{
  private static final int LABLE_MODE_KNOWN = 0;
  private static final int LABLE_MODE_REMEMBER = 1;
  public static final int MODE_SHOW_EXAMPLE = 34;
  public static final int MODE_SHOW_NOTHING = 33;
  public static final int MODE_SHOW_SPELL = 35;
  public static final int REVIEW_RESULT_FAILED = 0;
  public static final int REVIEW_RESULT_RECOGNITION = 1;
  public static final int REVIEW_RESULT_SUCCESS = 2;
//  public static final int WRONG_TIMES_DEFAULT;
  private boolean isPressUnKnowBtn = false;
  private TextView mCnDefintionTextView;
  private ViewStub mCnDefintionViewStub;
  private TextView mCollinsTextView;
  private ViewStub mCollinsViewStub;
  private RelativeLayout mDefinitionContainer;
  private Button mDetailButton;
  private RelativeLayout mEnDefinitionsContainer;
  private TextView mEnDefintionTextView;
  private ViewStub mEnDefintionViewStub;
  private LinearLayout mExampleLinearLayout;
  private Handler mHandler = new Handler();
  private boolean mIsReviewFalied = false;
  private Button mKnownButton;
  private ProgressBar mProgressBar;
  private RelativeLayout mRootsContainer;
  private ViewStub mRootsViewStub;
  private StringBuffer mSbRoots = new StringBuffer();
  private ScrollView mScrollView;
  private ImageButton mSoundImgBtn;
  private RelativeLayout mSpellContainer;
  private ExpSpellView mSpellView;
  private LinearLayout mTestContent;
  private ExpTestView mTestView;
  private Button mUnknownButton;
  private View mViewStubRoots;
  private LinearLayout mWordsContainer;
  SpellView.ReviewResultObserver reviewResultObserver = new SpellView.ReviewResultObserver()
  {
    public void proceedToExplorer()
    {
      Misc.forceHideSoftKeyboard(ExpTestSpellFragment.this.mActivity, ExpTestSpellFragment.this.mSpellView.mEditView);
      ExpTestSpellFragment.this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          ExpTestSpellFragment.this.mActivity.nextFragment();
        }
      }
      , 200L);
    }

    public void reviewFailed()
    {
//      if (!ExpTestSpellFragment.this.mIsReviewFalied)
//      {
//        ExpTestSpellFragment.this.mActivity.getStudyQueueController().testFail();
//        ExpTestSpellFragment.access$002(ExpTestSpellFragment.this, true);
//      }
    }

    public void reviewSucceeded()
    {
      ExpTestSpellFragment.this.mActivity.getStudyQueueController().testSuccess();
    }
  };
  private View rootView;
  private int spellTestMode;
  private int wrongTimes = 0;

  private void setHintBackground()
  {
    this.mRootsContainer.setBackgroundColor(this.mActivity.getResources().getColor(2131165317));
    this.mExampleLinearLayout.setBackgroundColor(this.mActivity.getResources().getColor(2131165317));
    this.mEnDefintionTextView.setBackgroundColor(this.mActivity.getResources().getColor(2131165317));
    this.mCnDefintionTextView.setBackgroundColor(this.mActivity.getResources().getColor(2131165317));
  }

  private void setHintVisibility(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mRootsViewStub.setVisibility(0);
      this.mExampleLinearLayout.setVisibility(0);
      this.mEnDefintionTextView.setVisibility(0);
      this.mCnDefintionTextView.setVisibility(0);
      return;
    }
    this.mRootsViewStub.setVisibility(8);
    this.mTestView.setExampleVisibility(false, false);
    this.mExampleLinearLayout.setVisibility(8);
    this.mEnDefintionTextView.setVisibility(8);
    this.mCnDefintionTextView.setVisibility(8);
    this.mCollinsViewStub.setVisibility(8);
  }

  private void setKnowContainerPosition()
  {
    this.mScrollView.post(new Runnable()
    {
      public void run()
      {
        ExpTestSpellFragment.this.mScrollView.fullScroll(130);
      }
    });
  }

  private void setKnownContainerLable(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      this.mKnownButton.setText(2131362112);
      this.mUnknownButton.setText(2131362113);
      return;
    case 1:
    }
    this.mKnownButton.setText(2131362114);
    this.mUnknownButton.setText(2131362115);
  }

  private boolean showRoots()
  {
    if (this.mSbRoots.length() >= 1)
      this.mSbRoots.delete(0, this.mSbRoots.length());
    if ((this.mActivity.isEnableRoots()) && (this.mActivity.getStudyQueueController().getReviewData().getRoots() != null))
    {
      List localList1 = this.mActivity.getStudyQueueController().getReviewData().getRoots().getRootsList();
      List localList2 = this.mActivity.getStudyQueueController().getRootsRepresentative(0);
      if ((localList1.size() > 0) && (localList2 != null))
      {
        RootsContent localRootsContent = (RootsContent)localList1.get(0);
        TextView localTextView1 = (TextView)this.mViewStubRoots.findViewById(2131034363);
        TextView localTextView2 = (TextView)this.mViewStubRoots.findViewById(2131034362);
        localTextView1.setTypeface(this.mActivity.getTypeface());
        localTextView2.setTypeface(this.mActivity.getTypeface());
        localTextView2.setText(localRootsContent.getContent());
        this.mSbRoots.append(localRootsContent.getMeaningCn());
        int i = Math.min(5, localList2.size());
        if (i >= 1)
          this.mSbRoots.append("<br>");
        for (int j = 0; j < i; j++)
        {
          this.mSbRoots.append(" <font color=\"#" + Integer.toHexString(0) + "\">" + ((Roots.Representative)(localList2.get(j))).word + "</font> ");
          if (j != i - 1)
            this.mSbRoots.append("|");
        }
        ((ImageView)this.mViewStubRoots.findViewById(2131034680)).setVisibility(8);
        localTextView1.setText(Html.fromHtml(this.mSbRoots.toString()));
        this.mRootsViewStub.setVisibility(0);
        this.mRootsContainer.setBackgroundColor(this.mActivity.getResources().getColor(2131165317));
        return true;
      }
    }
    return false;
  }

  private String wordsHighLight(String paramString)
  {
    return Pattern.compile("<vocab>(.*?)</vocab>").matcher(paramString).replaceAll("<font color=\"#" + Integer.toHexString(0) + "\">$1</font>");
  }

  void known()
  {
    if (!this.isPressUnKnowBtn)
    {
      this.spellTestMode = 35;
      renderInternal();
      return;
    }
    this.mActivity.nextFragment();
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (!isRenderable())
      return;
    this.mCollinsTextView.setTypeface(this.mActivity.getTypeface());
    this.mEnDefintionTextView.setTypeface(this.mActivity.getTypeface());
    this.mTestView = new ExpTestView(this.mActivity, this.mWordsContainer, this.mTestContent, this.mSpellContainer, this.mActivity.isEnableCollins(), "");
    this.mTestView.init();
    this.mSpellView = ((ExpSpellView)this.mTestView.getSpellView());
    render(this.rootView);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 0) && (paramInt2 == 1))
      setDisableCollinsVisibility(false);
  }

  public void onClick(View paramView)
  {
//    if (paramView.getId() == 2131034551)
//      this.mActivity.nextFragment();
//    do
//    {
//      return;
//      if (paramView.getId() == 2131034549)
//      {
//        paramView.setSelected(true);
//        known();
//        return;
//      }
//      if (paramView.getId() == 2131034550)
//      {
//        setKnownContainerLable(1);
//        unknown();
//        return;
//      }
//    }
//    while (paramView != this.mSpellContainer);
//    System.out.println("mSpellContainer onclick");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    rootView = paramLayoutInflater.inflate(R.layout.fragment_review_test_spell, paramViewGroup, false);
    mProgressBar = ((ProgressBar)rootView.findViewById(R.id.progress_bar));
    mTestContent = ((LinearLayout)rootView.findViewById(R.id.recognition));
    mSoundImgBtn = ((ImageButton)rootView.findViewById(R.id.btn_sound_in_word));
    mWordsContainer = ((LinearLayout)rootView.findViewById(R.id.word));
    mDefinitionContainer = ((RelativeLayout)rootView.findViewById(R.id.definition_container));
    mEnDefinitionsContainer = ((RelativeLayout)rootView.findViewById(R.id.en_definitions_container));
    mDefinitionContainer.setVisibility(View.GONE);
    mEnDefinitionsContainer.setVisibility(View.GONE);
    mSpellContainer = ((RelativeLayout)rootView.findViewById(R.id.spell));
    mKnownButton = ((Button)rootView.findViewById(R.id.known));
    mUnknownButton = ((Button)rootView.findViewById(R.id.unknown));
    mDetailButton = ((Button)rootView.findViewById(R.id.detail_button));
    mRootsViewStub = ((ViewStub)rootView.findViewById(R.id.roots_container));
    mViewStubRoots = mRootsViewStub.inflate();
    mRootsContainer = ((RelativeLayout)mViewStubRoots.findViewById(R.id.roots_item_container));
    mEnDefintionViewStub = ((ViewStub)rootView.findViewById(R.id.word_en_definition_viewstub));
    mEnDefintionTextView = ((TextView)mEnDefintionViewStub.inflate().findViewById(R.id.word_definition));
    mCnDefintionViewStub = ((ViewStub)rootView.findViewById(R.id.word_cn_definition_viewstub));
    mCnDefintionTextView = ((TextView)mCnDefintionViewStub.inflate().findViewById(R.id.word_definition));
    mExampleLinearLayout = ((LinearLayout)rootView.findViewById(R.id.example_container));
    mCollinsViewStub = ((ViewStub)rootView.findViewById(R.id.word_collins_viewstub));
    mCollinsViewStub.inflate();
    mScrollView = ((ScrollView)rootView.findViewById(R.id.scroll));
    mCollinsTextView = ((TextView)rootView.findViewById(R.id.review_collins));
    mDetailButton.setOnClickListener(this);
    mKnownButton.setOnClickListener(this);
    mUnknownButton.setOnClickListener(this);
    mCollinsTextView.setOnClickListener(this);
    return rootView;
  }

  public void renderInternal()
  {
//    ReviewData localReviewData = this.mActivity.getStudyQueueController().getReviewData();
//    this.mSpellView.setAudiUrl(this.mActivity.getStudyQueueController().getWordAudio());
//    this.mProgressBar.setProgressData(new ProgressBarData(this.mActivity.getStudyQueueController().getReviewStat()));
//    setHintBackground();
//    int i;
//    if (this.spellTestMode == 35)
//    {
//      i = this.spellTestMode;
//      switch (i)
//      {
//      default:
//        label96: this.mSpellContainer.setVisibility(8);
//        this.mRootsViewStub.setVisibility(8);
//        this.mEnDefintionViewStub.setVisibility(8);
//        this.mCnDefintionViewStub.setVisibility(8);
//        this.mCollinsViewStub.setVisibility(8);
//        this.mKnownButton.setVisibility(8);
//        this.mUnknownButton.setVisibility(8);
//        this.mDetailButton.setVisibility(8);
//        this.mKnownButton.setSelected(false);
//        this.mUnknownButton.setSelected(false);
//        this.mSpellView.clear();
//        this.mSpellView.mSpellContainer.removeAllViews();
//        this.mSpellView.mSpellContainer.setVisibility(8);
//        this.wrongTimes = 0;
//        this.spellTestMode = -1;
//        this.isPressUnKnowBtn = false;
//        this.mIsReviewFalied = false;
//        setKnownContainerLable(0);
//        this.mTestView.render(localReviewData);
//        switch (i)
//        {
//        default:
//          this.mActivity.showToast("学习数据状态错误!");
//        case 33:
//        case 34:
//        case 35:
//        }
//        break;
//      case 33:
//      case 34:
//      case 35:
//      }
//    }
//    while (true)
//    {
//      if ((localReviewData.getVocabulary().hasAudio()) && (i != 35))
//        this.mActivity.getSoundPlayer().playSoundByUrl(this.mActivity.getStudyQueueController().getWordAudio(), this.mSoundImgBtn);
//      return;
//      i = this.mActivity.getTestSpellMode();
//      break;
//      ExpTestView localExpTestView = this.mTestView;
//      int j;
//      if (localReviewData.getVocabulary().hasAudio())
//      {
//        j = 2;
//        label352: if (localReviewData.getExamples() == null)
//          break label383;
//      }
//      label383: for (int k = 8; ; k = 0)
//      {
//        localExpTestView.setViewMode(0x1 | (j | k));
//        break;
//        j = 0;
//        break label352;
//      }
//      this.mTestView.setViewMode(0);
//      break label96;
//      this.mTestView.setExampleVisibility(false, false);
//      this.mKnownButton.setVisibility(0);
//      this.mUnknownButton.setVisibility(0);
//      continue;
//      this.mTestContent.removeView(this.mExampleLinearLayout);
//      this.mTestContent.addView(this.mExampleLinearLayout, 0);
//      if (localReviewData.getExamples().getExampleList().size() >= 1)
//        this.mTestView.setExampleVisibility(true, false);
//      while (true)
//      {
//        this.mKnownButton.setVisibility(0);
//        this.mUnknownButton.setVisibility(0);
//        break;
//        this.mTestView.setExampleVisibility(false, false);
//      }
//      this.mSpellView.mSpellContainer.setVisibility(0);
//      this.mSpellView.setReviewResultObserver(this.reviewResultObserver);
//      this.mSpellContainer.setVisibility(0);
//      setHintVisibility(false);
//      this.mTestView.renderSpell(localReviewData);
//    }
  }

  public void setDisableCollinsVisibility(boolean paramBoolean)
  {
//    if (paramBoolean)
//    {
//      this.mCollinsViewStub.setVisibility(0);
//      return;
//    }
//    this.mCollinsViewStub.setVisibility(8);
//  }
//
//  void unknown()
//  {
//    this.isPressUnKnowBtn = true;
//    switch (this.wrongTimes)
//    {
//    default:
//    case 0:
//    case 1:
//    case 2:
//    case 3:
//    }
//    while (true)
//    {
//      this.mRootsContainer.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
//      this.mExampleLinearLayout.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
//      this.mEnDefintionTextView.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
//      this.mDetailButton.setVisibility(0);
//      this.mKnownButton.setVisibility(8);
//      this.mUnknownButton.setVisibility(8);
//      this.wrongTimes = 0;
//      while (true)
//      {
//        setKnowContainerPosition();
//        return;
//        this.mActivity.getStudyQueueController().testFail();
//        if (this.mExampleLinearLayout.getVisibility() != 8)
//          this.mExampleLinearLayout.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
//        if (showRoots())
//        {
//          this.wrongTimes = (1 + this.wrongTimes);
//        }
//        else
//        {
//          this.wrongTimes = (1 + this.wrongTimes);
//          this.mRootsContainer.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
//          if ((this.mActivity.getStudyQueueController().getReviewData().getExamples().getExampleList().size() >= 1) && (this.mExampleLinearLayout.getVisibility() == 8))
//          {
//            this.mTestView.setExampleVisibility(true, false);
//            this.wrongTimes = (1 + this.wrongTimes);
//          }
//          else
//          {
//            if (this.mActivity.getStudyQueueController().getReviewData().getExamples().getExampleList().size() < 1)
//            {
//              this.mTestView.setExampleVisibility(false, false);
//              this.mExampleLinearLayout.setVisibility(8);
//            }
//            this.wrongTimes = (1 + this.wrongTimes);
//            this.mExampleLinearLayout.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
//            VocabularyData localVocabularyData = this.mActivity.getStudyQueueController().getReviewData().getVocabulary();
//            if (localVocabularyData.getEnDefn() == null)
//              break;
//            String str = wordsHighLight(this.mTestView.getEnDefinitionString(localVocabularyData));
//            this.mEnDefintionTextView.setText(Html.fromHtml(str));
//            this.mEnDefintionViewStub.setVisibility(0);
//            this.wrongTimes = (1 + this.wrongTimes);
//          }
//        }
//      }
//      this.wrongTimes = (1 + this.wrongTimes);
//      this.mEnDefintionTextView.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
//      if (this.mActivity.getStudyQueueController().getReviewData().getVocabulary().getCnDefinition() != null)
//      {
//        this.mCnDefintionTextView.setText(this.mActivity.getStudyQueueController().getReviewData().getVocabulary().getCnDefinition().trim());
//        this.mCnDefintionViewStub.setVisibility(0);
//      }
//      this.wrongTimes = (1 + this.wrongTimes);
//    }
  }
}