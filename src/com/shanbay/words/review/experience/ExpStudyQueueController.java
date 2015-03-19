package com.shanbay.words.review.experience;

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
  private List<ExpReview> mGroupStudyQueue = new ArrayList();
  private List<ExpReview> mLastGroupStudyQueue = new ArrayList();
  private String mNextFragment;
  private ReviewData mReviewData;
  private ReviewStat mReviewStat;
  private ExpReview mStudyData;
  private List<ExpReview> mTotalStudyQueue = new ArrayList();

  public ExpStudyQueueController(List<ExpReview> paramList, ExpMode paramExpMode)
  {
    this.mExpMode = paramExpMode;
    this.mTotalStudyQueue.addAll(paramList);
    this.mReviewStat = new ReviewStat();
    this.mReviewStat.setTotal(this.mTotalStudyQueue.size());
    for (int i = 0; i < this.mTotalStudyQueue.size(); i++)
      this.mReviewStat.incLevelByReviewStatus(0);
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
    Iterator localIterator = this.mTotalStudyQueue.iterator();
    while (localIterator.hasNext())
      if (((ExpReview)localIterator.next()).id == paramExpReview.id)
        localIterator.remove();
    if (paramExpReview.reviewStatus != 1)
      this.mTotalStudyQueue.add(paramExpReview);
  }

  private ReviewData buildReviewData(ExpReview paramExpReview)
  {
    ReviewData localReviewData = new ReviewData();
    if (paramExpReview != null)
    {
      ExpDataTransferHelper localExpDataTransferHelper = new ExpDataTransferHelper();
      VocabularyData localVocabularyData = localExpDataTransferHelper.getVocabularyData(paramExpReview);
      ExampleData localExampleData = localExpDataTransferHelper.getExampleData(paramExpReview);
      RootsData localRootsData = new RootsData();
      if (this.mExpMode.root)
        localRootsData = localExpDataTransferHelper.getRootsData(paramExpReview);
      localReviewData.setVocabularyData(localVocabularyData);
      localReviewData.setExampleData(localExampleData);
      localReviewData.setRootsData(localRootsData);
      localReviewData.setNoteData(new NoteData());
    }
    return localReviewData;
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
    if (this.mGroupStudyQueue.size() <= 0)
      return TAG_SUMMARY;
    nextWord();
    if (!isSingleWord(this.mStudyData.content))
      return TAG_TEST_RECOGNITION;
    if (this.mExpMode.spell)
      return TAG_TEST_SPELL;
    return TAG_TEST_RECOGNITION;
  }

  private String nextFragmentFromInital()
  {
    if (!isSingleWord(this.mStudyData.content))
      return TAG_TEST_RECOGNITION;
    if (this.mExpMode.spell)
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
    if (this.mTotalStudyQueue.size() <= 0)
      return "end";
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
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    if ((this.mTotalStudyQueue != null) && (this.mTotalStudyQueue.size() > 0))
    {
      int i = Math.min(7, this.mTotalStudyQueue.size());
      Iterator localIterator = this.mTotalStudyQueue.iterator();
      for (int j = 0; j < i; j++)
      {
        ExpReview localExpReview = (ExpReview)localIterator.next();
        localArrayList1.add(localExpReview);
        localArrayList2.add(localExpReview);
        localIterator.remove();
      }
      this.mGroupStudyQueue = localArrayList1;
      this.mLastGroupStudyQueue = localArrayList2;
    }
  }

  private void nextWord()
  {
    this.mStudyData = ((ExpReview)this.mGroupStudyQueue.remove(0));
    this.mReviewData = buildReviewData(this.mStudyData);
  }

  public ExpReview getExpReview()
  {
    return this.mStudyData;
  }

  public String getNextFragmentBackToSummary()
  {
    this.mNextFragment = TAG_SUMMARY;
    return this.mNextFragment;
  }

  public String getNextFragmentSummaryToExp(int paramInt)
  {
    this.mStudyData = ((ExpReview)getSummaryData().get(paramInt));
    this.mReviewData = buildReviewData(this.mStudyData);
    this.mNextFragment = TAG_EXPLORE;
    return this.mNextFragment;
  }

  public ReviewData getReviewData()
  {
    return this.mReviewData;
  }

  public long getReviewId()
  {
    return this.mStudyData.id;
  }

  public long getReviewSenseId()
  {
    return this.mStudyData.senseId;
  }

  public ReviewStat getReviewStat()
  {
    return this.mReviewStat;
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
    if (StringUtils.isNotBlank(this.mStudyData.usAudio))
      return this.mStudyData.usAudio;
    if (StringUtils.isNotBlank(this.mStudyData.ukAudio))
      return this.mStudyData.ukAudio;
    return "";
  }

  public String nextFragment()
  {
    if (StringUtils.isBlank(this.mNextFragment))
        mNextFragment = nextFragmentFromInital();
    
    if (TAG_EXPLORE.equals(this.mNextFragment))
        mNextFragment = nextFragmentFromExpl();
      else if (TAG_TEST_RECOGNITION.equals(this.mNextFragment))
        mNextFragment = nextFragmentFromRecogn();
      else if (TAG_SUMMARY.equals(this.mNextFragment))
        mNextFragment = nextFragmentFromSummary();
      else if (TAG_TEST_SPELL.equals(this.mNextFragment))
        mNextFragment = nextFragmentFromSpell();
    
    return mNextFragment;
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