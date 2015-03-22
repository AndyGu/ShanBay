package com.shanbay.words.review.experience;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
    mRootsContainer.setBackgroundColor(mActivity.getResources().getColor(R.color.sw_hite_text_bg));
    mExampleLinearLayout.setBackgroundColor(mActivity.getResources().getColor(R.color.sw_hite_text_bg));
    mEnDefintionTextView.setBackgroundColor(mActivity.getResources().getColor(R.color.sw_hite_text_bg));
    mCnDefintionTextView.setBackgroundColor(mActivity.getResources().getColor(R.color.sw_hite_text_bg));
  }

  private void setKnowContainerPosition()
  {
    mScrollView.post(new Runnable()
    {
      public void run()
      {
        mScrollView.fullScroll(130);
      }
    });
  }

  private void setKnownContainerLable(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      mKnownButton.setText(R.string.label_known);
      mUnknownButton.setText(R.string.label_unknown);
      return;
    case 1:
    	mKnownButton.setText(R.string.label_remember_word);
        mUnknownButton.setText(R.string.label_forget_word);
        return;
    default:
        return;
    }
  }

  private boolean showRoots()
  {
    if (mSbRoots.length() >= 1)
      mSbRoots.delete(0, mSbRoots.length());
    List localList1 = mActivity.getStudyQueueController().getRootsRepresentative(0);
    if (localList1 != null)
    {
      List localList2 = mActivity.getStudyQueueController().getReviewData().getRoots().getRootsList();
      if (localList2.size() > 0)
      {
        RootsContent localRootsContent = (RootsContent)localList2.get(0);
        TextView localTextView1 = (TextView)mViewStubRoots.findViewById(R.id.meaning_cn);
        TextView localTextView2 = (TextView)mViewStubRoots.findViewById(R.id.roots_content);
        localTextView1.setTypeface(mActivity.getTypeface());
        localTextView2.setTypeface(mActivity.getTypeface());
        localTextView2.setText(localRootsContent.getContent());
        mSbRoots.append(localRootsContent.getMeaningCn());
        int i = Math.min(5, localList1.size());
        if (i >= 1)
          mSbRoots.append("<br>");
        for (int j = 0; j < i; j++)
        {
          mSbRoots.append(" <font color=\"#" + Integer.toHexString(0) + "\">" + ((Roots.Representative)(localList1.get(j))).word + "</font> ");
          if (j != i - 1)
            mSbRoots.append("|");
        }
        ((ImageView)mViewStubRoots.findViewById(R.id.more_roots)).setVisibility(View.GONE);
        localTextView1.setText(Html.fromHtml(mSbRoots.toString()));
        mRootsViewStub.setVisibility(View.VISIBLE);
        mRootsContainer.setBackgroundColor(mActivity.getResources().getColor(R.color.sw_hite_text_bg));
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
    if (!isPressUnKnowBtn){
        mActivity.getStudyQueueController().testSuccess();
    }
    mActivity.nextFragment();
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (!isRenderable()){
    	Log.e("ExpTestRecognitionFragment.onActivityCreated", "!isRenderable()");
        return;
    }
    mCollinsTextView.setTypeface(mActivity.getTypeface());
    mEnDefintionTextView.setTypeface(mActivity.getTypeface());
    mTestView = new TestView(mActivity, mWordsContainer, mTestContent, null);
    mTestView.init();
    render(rootView);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 0) && (paramInt2 == 1))
      setDisableCollinsVisibility(false);
  }

  public void onClick(View view)
  {
    if (view == mDetailButton)
      mActivity.nextFragment();
    
    if (view == mKnownButton)
    {
      view.setSelected(true);
      known();
      return;
    }
    if (view == mUnknownButton)
    {
      setKnownContainerLable(1);
      unknown();
      return;
    }
    
    if(view == mCollinsTextView){
//        startActivityForResult(PurchaseActivity.createIntent(mActivity, "applet_collins"), 0);
    }
  }

  public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle)
  {
    if (bundle != null){
    	mIsStateSaved = true;
    }else{
    	mIsStateSaved = false;
    }
    rootView = layoutInflater.inflate(R.layout.fragment_review_test_recognition, viewGroup, false);
    mProgressBar = ((ProgressBar)rootView.findViewById(R.id.progress_bar));
    mTestContent = ((LinearLayout)rootView.findViewById(R.id.recognition));
    mSoundImgBtn = ((ImageButton)rootView.findViewById(R.id.btn_sound_in_word));
    mWordsContainer = ((LinearLayout)rootView.findViewById(R.id.word));
    mDefinitionContainer = ((RelativeLayout)rootView.findViewById(R.id.definition_container));
    mEnDefinitionsContainer = ((RelativeLayout)rootView.findViewById(R.id.en_definitions_container));
    mDefinitionContainer.setVisibility(View.GONE);
    mEnDefinitionsContainer.setVisibility(View.GONE);
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
    ReviewData mReviewData = mActivity.getStudyQueueController().getReviewData();
    mProgressBar.setProgressData(new ProgressBarData(mActivity.getStudyQueueController().getReviewStat()));
    setHintBackground();
    int i;
    int j;
    if (mReviewData.getVocabulary().hasAudio())
    {
      i = 2;
      j = 8;
      if (mReviewData.getExamples() != null){
    	  mRootsViewStub.setVisibility(View.GONE);
          mEnDefintionViewStub.setVisibility(View.GONE);
          mCnDefintionViewStub.setVisibility(View.GONE);
          mCollinsViewStub.setVisibility(View.GONE);
          mKnownButton.setVisibility(View.VISIBLE);
          mUnknownButton.setVisibility(View.VISIBLE);
          mDetailButton.setVisibility(View.GONE);
          mKnownButton.setSelected(false);
          mUnknownButton.setSelected(false);
          wrongTimes = 0;
          isPressUnKnowBtn = false;
          setKnownContainerLable(View.VISIBLE);
          Log.e("mReviewData.getExamples() != null", (0x1 | (i | j))+"");
          mTestView.setViewMode((0x1 | (i | j)));
          mTestView.render(mReviewData);
      }
     
      switch (mActivity.getTestRecognitionMode())
      {
      
      case MODE_SHOW_NOTHING:
    	  Log.e("renderInternal", "getTestRecognitionMode()=MODE_SHOW_NOTHING "+MODE_SHOW_NOTHING);
    	  mTestView.setExampleVisibility(false, false);
    	  break;
      case MODE_SHOW_EXAMPLE:
    	  Log.e("renderInternal", "getTestRecognitionMode()=MODE_SHOW_EXAMPLE "+MODE_SHOW_EXAMPLE);
          mTestContent.removeView(mExampleLinearLayout);
          mTestContent.addView(mExampleLinearLayout, 0);
          if (mReviewData.getExamples().getExampleList().size() >= 1)
            mTestView.setExampleVisibility(true, false);
          else
            mTestView.setExampleVisibility(false, false);
    	  break;
      default:
          mActivity.showToast("学习数据状态错误!");
      }
    }
         
      if ((mReviewData.getVocabulary().hasAudio()) && (!mIsStateSaved))
    	  mActivity.getSoundPlayer().playSoundByUrl(mActivity.getStudyQueueController().getWordAudio(), mSoundImgBtn);
      mSoundImgBtn.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          mActivity.getSoundPlayer().playSoundByUrl(mActivity.getStudyQueueController().getWordAudio(), mSoundImgBtn);
        }
      });
  }

  public void setDisableCollinsVisibility(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      mCollinsViewStub.setVisibility(View.VISIBLE);
      return;
    }
    mCollinsViewStub.setVisibility(View.GONE);
  }

  void unknown()
  {
    isPressUnKnowBtn = true;
    switch (wrongTimes)
    {
    case 0:
    	mActivity.getStudyQueueController().testFail();
        if (mExampleLinearLayout.getVisibility() != View.GONE)
          mExampleLinearLayout.setBackgroundColor(mActivity.getResources().getColor(R.color.common_item_bg));
        if (showRoots())
        {
          wrongTimes = (1 + wrongTimes);
        }
        else
        {
          wrongTimes = (1 + wrongTimes);
          mRootsContainer.setBackgroundColor(mActivity.getResources().getColor(R.color.common_item_bg));
          
          if ((mActivity.getStudyQueueController().getReviewData().getExamples().getExampleList().size() >= 1) && 
        		  (mExampleLinearLayout.getVisibility() == View.GONE)){
            mTestView.setExampleVisibility(true, false);
//            wrongTimes = (1 + wrongTimes);
          }else{
            if (mActivity.getStudyQueueController().getReviewData().getExamples().getExampleList().size() < 1)
            {
              mTestView.setExampleVisibility(false, false);
              mExampleLinearLayout.setVisibility(View.GONE);
            }
//            wrongTimes = (1 + wrongTimes);
          }
          
        }
        break;
    case 1:
    	mExampleLinearLayout.setBackgroundColor(mActivity.getResources().getColor(R.color.common_item_bg));
    	if ((mActivity.getStudyQueueController().getReviewData().getExamples().getExampleList().size() >= 1) && 
      		  (mExampleLinearLayout.getVisibility() == View.GONE)){
          mTestView.setExampleVisibility(true, false);
          wrongTimes = (1 + wrongTimes);
        }else{
          if (mActivity.getStudyQueueController().getReviewData().getExamples().getExampleList().size() < 1)
          {
        	  mTestView.setExampleVisibility(true, false);
        	  mExampleLinearLayout.setVisibility(View.GONE);
          }else{
        	  if (mActivity.getStudyQueueController().getReviewData().getVocabulary().getEnDefn() != null)
              {
        		  mEnDefintionTextView.setText(mActivity.getStudyQueueController().getReviewData().getVocabulary().getEnDefn().trim());
        		  mEnDefintionTextView.setVisibility(View.VISIBLE);
              }
          }
          wrongTimes = (1 + wrongTimes);
        }
        break;
        
    case 2:
    	mEnDefintionTextView.setBackgroundColor(mActivity.getResources().getColor(R.color.common_item_bg));
        if (mActivity.getStudyQueueController().getReviewData().getVocabulary().getCnDefinition() != null)
        {
          mCnDefintionTextView.setText(mActivity.getStudyQueueController().getReviewData().getVocabulary().getCnDefinition().trim());
          mCnDefintionViewStub.setVisibility(View.VISIBLE);
        }
    	wrongTimes = (1 + wrongTimes);
    	break;
        
    case 3:
    	mRootsContainer.setBackgroundColor(mActivity.getResources().getColor(R.color.common_item_bg));
        mExampleLinearLayout.setBackgroundColor(mActivity.getResources().getColor(R.color.common_item_bg));
        mEnDefintionTextView.setBackgroundColor(mActivity.getResources().getColor(R.color.common_item_bg));
        mDetailButton.setVisibility(View.VISIBLE);
        mKnownButton.setVisibility(View.GONE);
        mUnknownButton.setVisibility(View.GONE);
        wrongTimes = 0;
    	break;
    	
    default:
    	
    }
    
    setKnowContainerPosition();
  }
}