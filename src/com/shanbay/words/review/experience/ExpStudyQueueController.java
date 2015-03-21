package com.shanbay.words.review.experience;

import android.util.Log;

import com.shanbay.words.constant.Result;
import com.shanbay.words.constant.ReviewStatus;
import com.shanbay.words.constant.ReviewType;
import com.shanbay.words.model.ExampleData;
import com.shanbay.words.model.ExpMode;
import com.shanbay.words.model.ExpReview;
import com.shanbay.words.model.NoteData;
import com.shanbay.words.model.ReviewData;
import com.shanbay.words.model.ReviewStat;
import com.shanbay.words.model.Roots;
import com.shanbay.words.model.Roots.InnerRoots;
import com.shanbay.words.model.Roots.Representative;
import com.shanbay.words.model.RootsData;
import com.shanbay.words.model.VocabularyData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ExpStudyQueueController
{
  private static final int REVIEW_GROUP_SIZE = 7;
  public static final String TAG_END = "end";
  public static final String TAG_EXPLORE = ExpExploreFragment.class.getName();
  public static final String TAG_SUMMARY = ExpSummaryFragment.class.getName();
  public static final String TAG_TEST_RECOGNITION = ExpTestRecognitionFragment.class.getName();
  public static final String TAG_TEST_SPELL = ExpTestSpellFragment.class.getName();
  private ExpMode mExpMode;
  private List<ExpReview> mGroupStudyQueue = new ArrayList<ExpReview>();
  private List<ExpReview> mLastGroupStudyQueue = new ArrayList<ExpReview>();
  private String mNextFragmentStr;
  private ReviewData mReviewData;
  private ReviewStat mReviewStat;
  private ExpReview mStudyData;
  private List<ExpReview> mTotalStudyQueue = new ArrayList<ExpReview>();

  public ExpStudyQueueController(List<ExpReview> list, ExpMode expMode)
  {
    mExpMode = expMode;
    mTotalStudyQueue.addAll(list);
    mReviewStat = new ReviewStat();
    mReviewStat.setTotal(mTotalStudyQueue.size());
    for (int i = 0; i < mTotalStudyQueue.size(); i++)
      mReviewStat.incLevelByReviewStatus(0);
    nextGroup();
    nextWord();
  }

  private void adjustReviewStat(int paramInt1, int paramInt2)
  {
    this.mReviewStat.decLevelByReviewStatus(paramInt1);
    this.mReviewStat.incLevelByReviewStatus(paramInt2);
  }

  private void adjustStudyQueue(ExpReview paramExpReview)
  {
    Iterator localIterator = mTotalStudyQueue.iterator();
    while (localIterator.hasNext())
      if (((ExpReview)localIterator.next()).id == paramExpReview.id)
        localIterator.remove();
    if (paramExpReview.reviewStatus != 1)
      mTotalStudyQueue.add(paramExpReview);
  }

  private ReviewData buildReviewData(ExpReview eExpReview)
  {
    ReviewData reviewData = new ReviewData();
    if (eExpReview != null)
    {
      ExpDataTransferHelper mExpDataTransferHelper = new ExpDataTransferHelper();
      VocabularyData mVocabularyData = mExpDataTransferHelper.getVocabularyData(eExpReview);
      ExampleData localExampleData = mExpDataTransferHelper.getExampleData(eExpReview);
      RootsData localRootsData = new RootsData();
      if (mExpMode.root)
        localRootsData = mExpDataTransferHelper.getRootsData(eExpReview);
      reviewData.setVocabularyData(mVocabularyData);
      reviewData.setExampleData(localExampleData);
      reviewData.setRootsData(localRootsData);
      reviewData.setNoteData(new NoteData());
    }
    return reviewData;
  }

  private boolean isSingleWord(String paramString)
  {
    if (StringUtils.isBlank(paramString))
      return false;
    String str = StringUtils.trim(paramString);
    int i = str.length();
    for (int j = 0; j < i; j++)
    {
      if (!Character.isLetter(str.charAt(j)))
    	 return false;
    }
    return true;
  }

  private String nextFragmentFromExpl()
  {
    if (mGroupStudyQueue.size() <= 0)
      return TAG_SUMMARY;
    nextWord();
    if (!isSingleWord(mStudyData.content))
      return TAG_TEST_RECOGNITION;
    if (mExpMode.spell)
      return TAG_TEST_SPELL;
    return TAG_TEST_RECOGNITION;
  }

  private String nextFragmentFromInital()
  {
    if (!isSingleWord(mStudyData.content)){
        return TAG_TEST_RECOGNITION;
    }
    if (mExpMode.spell)
      return TAG_TEST_SPELL;
    return TAG_TEST_RECOGNITION;
  }

  private String nextFragmentFromRecogn()
  {
    return TAG_EXPLORE;
  }

  private String nextFragmentFromSpell()
  {
    return TAG_EXPLORE;
  }

  private String nextFragmentFromSummary()
  {
    if (mTotalStudyQueue.size() <= 0)
      return TAG_END;
    nextGroup();
    nextWord();
    if (!isSingleWord(this.mStudyData.content))
      return TAG_TEST_RECOGNITION;
    if (this.mExpMode.spell)
      return TAG_TEST_SPELL;
    return TAG_TEST_RECOGNITION;
  }

  private void nextGroup()
  {
    ArrayList<ExpReview> localArrayList1 = new ArrayList<ExpReview>();
    ArrayList<ExpReview> localArrayList2 = new ArrayList<ExpReview>();
    if ((mTotalStudyQueue != null) && (mTotalStudyQueue.size() > 0))
    {
      int i = Math.min(REVIEW_GROUP_SIZE, mTotalStudyQueue.size());
      Iterator<ExpReview> localIterator = mTotalStudyQueue.iterator();
      for (int j = 0; j < i; j++)
      {
        ExpReview localExpReview = (ExpReview)localIterator.next();
        localArrayList1.add(localExpReview);
        localArrayList2.add(localExpReview);
        localIterator.remove();
      }
      mGroupStudyQueue = localArrayList1;
      mLastGroupStudyQueue = localArrayList2;
    }
  }

  private void nextWord()
  {
    mStudyData = ((ExpReview)mGroupStudyQueue.remove(0));
    mReviewData = buildReviewData(mStudyData);
  }

  public ExpReview getExpReview()
  {
    return mStudyData;
  }

  public String getNextFragmentBackToSummary()
  {
    mNextFragmentStr = TAG_SUMMARY;
    return mNextFragmentStr;
  }

  public String getNextFragmentSummaryToExp(int paramInt)
  {
    mStudyData = ((ExpReview)getSummaryData().get(paramInt));
    mReviewData = buildReviewData(mStudyData);
    mNextFragmentStr = TAG_EXPLORE;
    return mNextFragmentStr;
  }

  public ReviewData getReviewData()
  {
    return mReviewData;
  }

  public long getReviewId()
  {
    return mStudyData.id;
  }

  public long getReviewSenseId()
  {
    return mStudyData.senseId;
  }

  public ReviewStat getReviewStat()
  {
    return mReviewStat;
  }

  public List<Roots.Representative> getRootsRepresentative(int paramInt)
  {
    if ((getExpReview().roots == null) || (getExpReview().roots.size() <= 0))
      return null;
    return ((Roots.InnerRoots)getExpReview().roots.get(paramInt)).representatives;
  }

  public List<ExpReview> getSummaryData()
  {
    return this.mLastGroupStudyQueue;
  }

  public int getTestRecognitionMode()
  {
    switch (this.mStudyData.reviewStatus)
    {
    case 1:
    default:
      return -1;
    case 0:
    case 2:
      return 33;
    case 3:
    }
    return 34;
  }

  public int getTestSpellMode()
  {
    switch (this.mStudyData.reviewStatus)
    {
    case 1:
    default:
      return -1;
    case 0:
    case 2:
      return 33;
    case 3:
    }
    return 34;
  }

  public String getWordAudio()
  {
    if (StringUtils.isNotBlank(mStudyData.usAudio))
      return mStudyData.usAudio;
    if (StringUtils.isNotBlank(mStudyData.ukAudio))
      return mStudyData.ukAudio;
    return "";
  }

  public String nextFragment()
  {
	  Log.e("nextFragment", "mNextFragmentStr="+mNextFragmentStr);
    if (StringUtils.isBlank(mNextFragmentStr))
        mNextFragmentStr = nextFragmentFromInital();
	  Log.e("nextFragment", "nextFragmentFromInital="+mNextFragmentStr);
    
    if (TAG_EXPLORE.equals(mNextFragmentStr)){
        mNextFragmentStr = nextFragmentFromExpl();
        Log.e("nextFragment", "nextFragmentFromExpl="+mNextFragmentStr);
    }else if (TAG_TEST_RECOGNITION.equals(mNextFragmentStr)){
        mNextFragmentStr = nextFragmentFromRecogn();
        Log.e("nextFragment", "nextFragmentFromRecogn="+mNextFragmentStr);
    }else if (TAG_SUMMARY.equals(mNextFragmentStr)){
        mNextFragmentStr = nextFragmentFromSummary();
        Log.e("nextFragment", "nextFragmentFromSummary="+mNextFragmentStr);
    }else if (TAG_TEST_SPELL.equals(mNextFragmentStr)){
        mNextFragmentStr = nextFragmentFromSpell();
        Log.e("nextFragment", "nextFragmentFromSpell="+mNextFragmentStr);
    }
    return mNextFragmentStr;
  }

  public void setResult(Result result)
  {
    mStudyData.reviewStatus = ReviewStatus.nextStatus(mStudyData.reviewStatus, result, ReviewType.REVIEW_TYPE_FRESH);
    adjustStudyQueue(mStudyData);
    adjustReviewStat(mStudyData.reviewStatus, mStudyData.reviewStatus);
  }

  public void testFail()
  {
    setResult(Result.FAILURE);
  }

  public void testSuccess()
  {
    setResult(Result.SUCCESS);
  }
}