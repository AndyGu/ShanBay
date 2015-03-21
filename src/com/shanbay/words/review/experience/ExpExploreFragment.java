package com.shanbay.words.review.experience;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.shanbay.model.Model;
import com.shanbay.words.R;
import com.shanbay.words.WordsSoundPlayer;
import com.shanbay.words.model.ExampleContent;
import com.shanbay.words.model.ExampleData;
import com.shanbay.words.model.ReviewData;
import com.shanbay.words.model.Roots;
import com.shanbay.words.model.Roots.Representative;
import com.shanbay.words.model.RootsContent;
import com.shanbay.words.model.RootsData;
import com.shanbay.words.model.VocabularyData;
import com.shanbay.words.view.WordView;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpExploreFragment extends ExpReviewFragment
  implements View.OnClickListener
{
  private Button mBtnNext;
  private RelativeLayout mEnDefinitionContainer;
  private LinearLayout mExampleContainer;
  private TextView mExampleLabel;
  private boolean mIsStateSaved = false;
  private View mRootView;
  private ViewGroup mRootsContainer;
  private View.OnClickListener mRootsItemOnClickListener = new View.OnClickListener()
  {
    public void onClick(View view)
    {
      if (view.getTag() instanceof Integer){
//    	  ExpExploreFragment.access$202(ExpExploreFragment.this, ((Integer)view.getTag()).intValue());
//          List localList = getRootsRepresentative(rootsContentsIdx);
//          RootsContent localRootsContent = (RootsContent)ExpExploreFragment.this.getRootsContent().get(rootsContentsIdx);
//          Intent localIntent = ExpRootsActivity.createIntent(mActivity, Model.toJson(localList), localRootsContent.getMeaningCn(), localRootsContent.getContent());
//          startActivityForResult(localIntent, 20);
      }
    }
  };
  private TextView mRootsLabel;
  private ImageButton mSoundImgBtn;
  private WordView mWordView;
  private int rootsContentsIdx;

  private List<RootsContent> getRootsContent()
  {
    return getStudyQueueController().getReviewData().getRoots().getRootsList();
  }

  private List<Roots.Representative> getRootsRepresentative(int paramInt)
  {
    return getStudyQueueController().getRootsRepresentative(paramInt);
  }

  private Spanned getSpannedAnnotation(String paramString)
  {
    return Html.fromHtml(Pattern.compile("<vocab>(.*?)</vocab>").matcher(paramString).replaceAll("<font color=\"#" + Integer.toHexString(0) + "\">$1</font>"));
  }

  private ExpStudyQueueController getStudyQueueController()
  {
    return mActivity.getStudyQueueController();
  }

  private void playSound(View paramView)
  {
    mActivity.getSoundPlayer().playSoundByUrl(getStudyQueueController().getWordAudio(), paramView);
  }

  private void renderExamples(List<ExampleContent> paramList)
  {
    int i = 2;
    int j;
    if ((paramList != null) && (paramList.size() > 0))
    {
      mExampleContainer.removeAllViews();
      mExampleContainer.setVisibility(View.VISIBLE);
      mExampleContainer.addView(mExampleLabel);
      if (paramList.size() < i)
        i = paramList.size();
      j = 0;
      
      while (j < i)
      {
        ExampleContent localExampleContent = (ExampleContent)paramList.get(j);
        LinearLayout localLinearLayout = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.item_exp_example, null);
        TextView localTextView1 = (TextView)localLinearLayout.findViewById(R.id.tv_example_en);
        TextView localTextView2 = (TextView)localLinearLayout.findViewById(R.id.tv_example_cn);
        localTextView1.setText(getSpannedAnnotation(localExampleContent.getAnnotation()));
        localTextView2.setText(localExampleContent.getTranslation());
        mExampleContainer.addView(localLinearLayout);
        j++;
        continue;
      }
    }else{
    	mExampleContainer.setVisibility(View.GONE);
    }
  }

  private void renderRoots(List<RootsContent> paramList)
  {
    int i;
    if ((paramList != null) && (paramList.size() > 0))
    {
      mRootsContainer.setVisibility(View.VISIBLE);
      mRootsContainer.removeAllViews();
      mRootsContainer.addView(mRootsLabel);
      mRootsLabel.setVisibility(View.VISIBLE);
      i = 0;
      
      while (i < paramList.size())
      {
        RootsContent localRootsContent = (RootsContent)paramList.get(i);
        View localView = mActivity.getLayoutInflater().inflate(R.layout.item_roots, null);
        localView.setOnClickListener(mRootsItemOnClickListener);
        localView.setTag(Integer.valueOf(i));
        TextView localTextView1 = (TextView)localView.findViewById(R.id.meaning_cn);
        TextView localTextView2 = (TextView)localView.findViewById(R.id.roots_content);
        localTextView2.setText(localRootsContent.getContent());
        localTextView1.setText(localRootsContent.getMeaningCn());
        localTextView2.setTypeface(mActivity.getTypeface());
        localTextView1.setTypeface(mActivity.getTypeface());
        mRootsContainer.addView(localView);
        i++;
        continue;
      }
      
    }else{
    	mRootsContainer.setVisibility(View.GONE);
    }
    
  }

  private void showNextButton()
  {
    if (mActivity.isFromSummary())
    {
      mBtnNext.setVisibility(View.GONE);
      return;
    }
    mBtnNext.setVisibility(View.VISIBLE);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (!isRenderable())
      return;
    mWordView = new ExpWordView(mActivity, (ViewGroup)mRootView.findViewById(R.id.word), mActivity.isEnableCollins());
    mWordView.init();
    if (mActivity.isEnableCollins())
      ((ImageView)mEnDefinitionContainer.findViewById(R.id.arrow)).setVisibility(View.GONE);
    render(mRootView);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 != 20) || (paramInt2 != 33))
      return;
    long[] arrayOfLong = paramIntent.getLongArrayExtra("roots_modified");
    Iterator localIterator = getRootsRepresentative(this.rootsContentsIdx).iterator();
    while (localIterator.hasNext())
    {
      Roots.Representative localRepresentative = (Roots.Representative)localIterator.next();
      int i = arrayOfLong.length;
      for (int j = 0; j < i; j++)
      {
        long l = arrayOfLong[j];
        if (localRepresentative.vocabularyId == l)
          localRepresentative.hasLearned = true;
      }
    }
  }

  public void onClick(View paramView)
  {
    this.mActivity.nextFragment();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (paramBundle == null){
        mIsStateSaved = false;
    }else{
    	mIsStateSaved = true;
    }
    mRootView = paramLayoutInflater.inflate(R.layout.fragment_exp_explore, paramViewGroup, false);
    mSoundImgBtn = ((ImageButton)mRootView.findViewById(R.id.btn_sound_in_word));
    mRootsLabel = ((TextView)mRootView.findViewById(R.id.roots_label));
    mRootsContainer = ((ViewGroup)mRootView.findViewById(R.id.roots_container));
    mBtnNext = ((Button)mRootView.findViewById(R.id.next_button));
    mBtnNext.setOnClickListener(this);
    mExampleLabel = ((TextView)mRootView.findViewById(R.id.example_label));
    mExampleContainer = ((LinearLayout)mRootView.findViewById(R.id.example_container));
    mEnDefinitionContainer = ((RelativeLayout)mRootView.findViewById(R.id.en_definitions_container));
    return mRootView;
  }

  public void renderInternal()
  {
    ReviewData localReviewData = getStudyQueueController().getReviewData();
    VocabularyData localVocabularyData = localReviewData.getVocabulary();
    mWordView.render(localVocabularyData);
    if (mActivity.isEnableRoots())
      renderRoots(getRootsContent());
    mExampleContainer.removeAllViews();
    renderExamples(localReviewData.getExamples().getExampleList());
    if ((localVocabularyData.hasAudio()) && (!mIsStateSaved))
      playSound(mSoundImgBtn);
    mSoundImgBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        playSound(mSoundImgBtn);
      }
    });
    showNextButton();
  }
}