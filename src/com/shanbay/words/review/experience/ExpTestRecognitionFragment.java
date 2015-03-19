package com.shanbay.words.review.experience;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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
import com.shanbay.words.view.TestView;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpTestRecognitionFragment extends ExpReviewFragment
  implements View.OnClickListener
{
  private static final int LABLE_MODE_KNOWN = 0;
  private static final int LABLE_MODE_REMEMBER = 1;
  public static final int MODE_SHOW_EXAMPLE = 34;
  public static final int MODE_SHOW_NOTHING = 33;
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
  private boolean mIsStateSaved = false;
  private Button mKnownButton;
  private ProgressBar mProgressBar;
  private RelativeLayout mRootsContainer;
  private ViewStub mRootsViewStub;
  private StringBuffer mSbRoots = new StringBuffer();
  private ScrollView mScrollView;
  private ImageButton mSoundImgBtn;
  private LinearLayout mTestContent;
  private TestView mTestView;
  private Button mUnknownButton;
  private View mViewStubRoots;
  private LinearLayout mWordsContainer;
  private View rootView;
  private int wrongTimes = 0;

  private void setHintBackground()
  {
    this.mRootsContainer.setBackgroundColor(this.mActivity.getResources().getColor(2131165317));
    this.mExampleLinearLayout.setBackgroundColor(this.mActivity.getResources().getColor(2131165317));
    this.mEnDefintionTextView.setBackgroundColor(this.mActivity.getResources().getColor(2131165317));
    this.mCnDefintionTextView.setBackgroundColor(this.mActivity.getResources().getColor(2131165317));
  }

  private void setKnowContainerPosition()
  {
    this.mScrollView.post(new Runnable()
    {
      public void run()
      {
        ExpTestRecognitionFragment.this.mScrollView.fullScroll(130);
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
    List localList1 = this.mActivity.getStudyQueueController().getRootsRepresentative(0);
    if (localList1 != null)
    {
      List localList2 = this.mActivity.getStudyQueueController().getReviewData().getRoots().getRootsList();
      if (localList2.size() > 0)
      {
        RootsContent localRootsContent = (RootsContent)localList2.get(0);
        TextView localTextView1 = (TextView)this.mViewStubRoots.findViewById(2131034363);
        TextView localTextView2 = (TextView)this.mViewStubRoots.findViewById(2131034362);
        localTextView1.setTypeface(this.mActivity.getTypeface());
        localTextView2.setTypeface(this.mActivity.getTypeface());
        localTextView2.setText(localRootsContent.getContent());
        this.mSbRoots.append(localRootsContent.getMeaningCn());
        int i = Math.min(5, localList1.size());
        if (i >= 1)
          this.mSbRoots.append("<br>");
        for (int j = 0; j < i; j++)
        {
          this.mSbRoots.append(" <font color=\"#" + Integer.toHexString(0) + "\">" + ((Roots.Representative)(localList1.get(j))).word + "</font> ");
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
      this.mActivity.getStudyQueueController().testSuccess();
    this.mActivity.nextFragment();
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (!isRenderable())
      return;
    this.mCollinsTextView.setTypeface(this.mActivity.getTypeface());
    this.mEnDefintionTextView.setTypeface(this.mActivity.getTypeface());
    this.mTestView = new TestView(this.mActivity, this.mWordsContainer, this.mTestContent, null);
    this.mTestView.init();
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
//    if (paramView == this.mDetailButton)
//      this.mActivity.nextFragment();
//    do
//    {
//      return;
//      if (paramView == this.mKnownButton)
//      {
//        paramView.setSelected(true);
//        known();
//        return;
//      }
//      if (paramView == this.mUnknownButton)
//      {
//        setKnownContainerLable(1);
//        unknown();
//        return;
//      }
//    }
//    while (paramView != this.mCollinsTextView);
//    startActivityForResult(PurchaseActivity.createIntent(this.mActivity, "applet_collins"), 0);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (paramBundle != null);
    for (boolean bool = true; ; bool = false)
    {
      this.mIsStateSaved = bool;
      this.rootView = paramLayoutInflater.inflate(2130903192, paramViewGroup, false);
      this.mProgressBar = ((ProgressBar)this.rootView.findViewById(2131034437));
      this.mTestContent = ((LinearLayout)this.rootView.findViewById(2131034548));
      this.mSoundImgBtn = ((ImageButton)this.rootView.findViewById(2131034891));
      this.mWordsContainer = ((LinearLayout)this.rootView.findViewById(2131034459));
      this.mDefinitionContainer = ((RelativeLayout)this.rootView.findViewById(2131034892));
      this.mEnDefinitionsContainer = ((RelativeLayout)this.rootView.findViewById(2131034894));
      this.mDefinitionContainer.setVisibility(8);
      this.mEnDefinitionsContainer.setVisibility(8);
      this.mKnownButton = ((Button)this.rootView.findViewById(2131034549));
      this.mUnknownButton = ((Button)this.rootView.findViewById(2131034550));
      this.mDetailButton = ((Button)this.rootView.findViewById(2131034551));
      this.mRootsViewStub = ((ViewStub)this.rootView.findViewById(2131034252));
      this.mViewStubRoots = this.mRootsViewStub.inflate();
      this.mRootsContainer = ((RelativeLayout)this.mViewStubRoots.findViewById(2131034679));
      this.mEnDefintionViewStub = ((ViewStub)this.rootView.findViewById(2131034763));
      this.mEnDefintionTextView = ((TextView)this.mEnDefintionViewStub.inflate().findViewById(2131034702));
      this.mCnDefintionViewStub = ((ViewStub)this.rootView.findViewById(2131034764));
      this.mCnDefintionTextView = ((TextView)this.mCnDefintionViewStub.inflate().findViewById(2131034702));
      this.mExampleLinearLayout = ((LinearLayout)this.rootView.findViewById(2131034461));
      this.mCollinsViewStub = ((ViewStub)this.rootView.findViewById(2131034762));
      this.mCollinsViewStub.inflate();
      this.mScrollView = ((ScrollView)this.rootView.findViewById(2131034264));
      this.mCollinsTextView = ((TextView)this.rootView.findViewById(2131034615));
      this.mDetailButton.setOnClickListener(this);
      this.mKnownButton.setOnClickListener(this);
      this.mUnknownButton.setOnClickListener(this);
      this.mCollinsTextView.setOnClickListener(this);
      return this.rootView;
    }
  }

  public void renderInternal()
  {
    ReviewData localReviewData = this.mActivity.getStudyQueueController().getReviewData();
    this.mProgressBar.setProgressData(new ProgressBarData(this.mActivity.getStudyQueueController().getReviewStat()));
    setHintBackground();
    TestView localTestView = this.mTestView;
    int i;
    int j;
    if (localReviewData.getVocabulary().hasAudio())
    {
      i = 2;
      if (localReviewData.getExamples() == null)
//        break label280;
      j = 8;
//      label67: localTestView.setViewMode(0x1 | (i | j));
      this.mRootsViewStub.setVisibility(8);
      this.mEnDefintionViewStub.setVisibility(8);
      this.mCnDefintionViewStub.setVisibility(8);
      this.mCollinsViewStub.setVisibility(8);
      this.mKnownButton.setVisibility(0);
      this.mUnknownButton.setVisibility(0);
      this.mDetailButton.setVisibility(8);
      this.mKnownButton.setSelected(false);
      this.mUnknownButton.setSelected(false);
      this.wrongTimes = 0;
      this.isPressUnKnowBtn = false;
      setKnownContainerLable(0);
      this.mTestView.render(localReviewData);
      switch (this.mActivity.getTestRecognitionMode())
      {
      default:
        this.mActivity.showToast("学习数据状态错误!");
      case 33:
      case 34:
      }
    }
//    while (true)
//    {
//      if ((localReviewData.getVocabulary().hasAudio()) && (!this.mIsStateSaved))
//        this.mActivity.getSoundPlayer().playSoundByUrl(this.mActivity.getStudyQueueController().getWordAudio(), this.mSoundImgBtn);
//      this.mSoundImgBtn.setOnClickListener(new View.OnClickListener()
//      {
//        public void onClick(View paramAnonymousView)
//        {
//          ExpTestRecognitionFragment.this.mActivity.getSoundPlayer().playSoundByUrl(ExpTestRecognitionFragment.this.mActivity.getStudyQueueController().getWordAudio(), ExpTestRecognitionFragment.this.mSoundImgBtn);
//        }
//      });
//      return;
//      i = 0;
//      break;
//      label280: j = 0;
//      break label67;
//      this.mTestView.setExampleVisibility(false, false);
//      continue;
//      this.mTestContent.removeView(this.mExampleLinearLayout);
//      this.mTestContent.addView(this.mExampleLinearLayout, 0);
//      if (localReviewData.getExamples().getExampleList().size() >= 1)
//        this.mTestView.setExampleVisibility(true, false);
//      else
//        this.mTestView.setExampleVisibility(false, false);
//    }
  }

  public void setDisableCollinsVisibility(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mCollinsViewStub.setVisibility(0);
      return;
    }
    this.mCollinsViewStub.setVisibility(8);
  }

  void unknown()
  {
    this.isPressUnKnowBtn = true;
    switch (this.wrongTimes)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      this.mRootsContainer.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
      this.mExampleLinearLayout.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
      this.mEnDefintionTextView.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
      this.mDetailButton.setVisibility(0);
      this.mKnownButton.setVisibility(8);
      this.mUnknownButton.setVisibility(8);
      this.wrongTimes = 0;
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
      this.wrongTimes = (1 + this.wrongTimes);
      this.mEnDefintionTextView.setBackgroundColor(this.mActivity.getResources().getColor(2131165199));
      if (this.mActivity.getStudyQueueController().getReviewData().getVocabulary().getCnDefinition() != null)
      {
        this.mCnDefintionTextView.setText(this.mActivity.getStudyQueueController().getReviewData().getVocabulary().getCnDefinition().trim());
        this.mCnDefintionViewStub.setVisibility(0);
      }
      this.wrongTimes = (1 + this.wrongTimes);
    }
  }
}