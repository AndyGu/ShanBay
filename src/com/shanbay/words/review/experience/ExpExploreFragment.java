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
    public void onClick(View paramAnonymousView)
    {
//      if (!(paramAnonymousView.getTag() instanceof Integer))
//        return;
//      ExpExploreFragment.access$202(ExpExploreFragment.this, ((Integer)paramAnonymousView.getTag()).intValue());
//      List localList = ExpExploreFragment.this.getRootsRepresentative(ExpExploreFragment.this.rootsContentsIdx);
//      RootsContent localRootsContent = (RootsContent)ExpExploreFragment.this.getRootsContent().get(ExpExploreFragment.this.rootsContentsIdx);
//      Intent localIntent = ExpRootsActivity.createIntent(ExpExploreFragment.this.mActivity, Model.toJson(localList), localRootsContent.getMeaningCn(), localRootsContent.getContent());
//      ExpExploreFragment.this.startActivityForResult(localIntent, 20);
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
    return this.mActivity.getStudyQueueController();
  }

  private void playSound(View paramView)
  {
    this.mActivity.getSoundPlayer().playSoundByUrl(getStudyQueueController().getWordAudio(), paramView);
  }

  private void renderExamples(List<ExampleContent> paramList)
  {
//    int i = 2;
//    int j;
//    if ((paramList != null) && (paramList.size() > 0))
//    {
//      this.mExampleContainer.removeAllViews();
//      this.mExampleContainer.setVisibility(0);
//      this.mExampleContainer.addView(this.mExampleLabel);
//      if (paramList.size() < i)
//        i = paramList.size();
//      j = 0;
//    }
//    while (j < i)
//    {
//      ExampleContent localExampleContent = (ExampleContent)paramList.get(j);
//      LinearLayout localLinearLayout = (LinearLayout)getActivity().getLayoutInflater().inflate(2130903218, null);
//      TextView localTextView1 = (TextView)localLinearLayout.findViewById(2131034440);
//      TextView localTextView2 = (TextView)localLinearLayout.findViewById(2131034441);
//      localTextView1.setText(getSpannedAnnotation(localExampleContent.getAnnotation()));
//      localTextView2.setText(localExampleContent.getTranslation());
//      this.mExampleContainer.addView(localLinearLayout);
//      j++;
//      continue;
//      this.mExampleContainer.setVisibility(8);
//    }
  }

  private void renderRoots(List<RootsContent> paramList)
  {
//    int i;
//    if ((paramList != null) && (paramList.size() > 0))
//    {
//      this.mRootsContainer.setVisibility(0);
//      this.mRootsContainer.removeAllViews();
//      this.mRootsContainer.addView(this.mRootsLabel);
//      this.mRootsLabel.setVisibility(0);
//      i = 0;
//    }
//    while (i < paramList.size())
//    {
//      RootsContent localRootsContent = (RootsContent)paramList.get(i);
//      View localView = this.mActivity.getLayoutInflater().inflate(2130903251, null);
//      localView.setOnClickListener(this.mRootsItemOnClickListener);
//      localView.setTag(Integer.valueOf(i));
//      TextView localTextView1 = (TextView)localView.findViewById(2131034363);
//      TextView localTextView2 = (TextView)localView.findViewById(2131034362);
//      localTextView2.setText(localRootsContent.getContent());
//      localTextView1.setText(localRootsContent.getMeaningCn());
//      localTextView2.setTypeface(this.mActivity.getTypeface());
//      localTextView1.setTypeface(this.mActivity.getTypeface());
//      this.mRootsContainer.addView(localView);
//      i++;
//      continue;
//      this.mRootsContainer.setVisibility(8);
//    }
  }

  private void showNextButton()
  {
    if (this.mActivity.isFromSummary())
    {
      this.mBtnNext.setVisibility(8);
      return;
    }
    this.mBtnNext.setVisibility(0);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (!isRenderable())
      return;
    this.mWordView = new ExpWordView(this.mActivity, (ViewGroup)this.mRootView.findViewById(2131034459), this.mActivity.isEnableCollins());
    this.mWordView.init();
    if (this.mActivity.isEnableCollins())
      ((ImageView)this.mEnDefinitionContainer.findViewById(2131034443)).setVisibility(8);
    render(this.mRootView);
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
    mRootView = paramLayoutInflater.inflate(2130903168, paramViewGroup, false);
    mSoundImgBtn = ((ImageButton)this.mRootView.findViewById(2131034891));
    mRootsLabel = ((TextView)this.mRootView.findViewById(2131034460));
    mRootsContainer = ((ViewGroup)this.mRootView.findViewById(2131034252));
    mBtnNext = ((Button)this.mRootView.findViewById(2131034458));
    mBtnNext.setOnClickListener(this);
    mExampleLabel = ((TextView)this.mRootView.findViewById(2131034462));
    mExampleContainer = ((LinearLayout)this.mRootView.findViewById(2131034461));
    mEnDefinitionContainer = ((RelativeLayout)this.mRootView.findViewById(2131034894));
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
    this.mSoundImgBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ExpExploreFragment.this.playSound(ExpExploreFragment.this.mSoundImgBtn);
      }
    });
    showNextButton();
  }
}