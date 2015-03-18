//package com.shanbay.words.review.experience;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.ActionBar;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.LinearLayout;
//import com.google.renamedgson.JsonElement;
//import com.shanbay.http.ModelResponseException;
//import com.shanbay.http.ModelResponseHandler;
//import com.shanbay.model.Model;
//import com.shanbay.util.Misc;
//import com.shanbay.words.WordsClient;
//import com.shanbay.words.activity.WelcomeActivity;
//import com.shanbay.words.activity.WordsActivity;
//import com.shanbay.words.model.ExpMode;
//import com.shanbay.words.model.ExpReview;
//import java.util.List;
//import org.apache.commons.lang.StringUtils;
//
//public class ExpReviewActivity extends WordsActivity
//{
//  public static final int REQUEST_CODE_REVIEW = 35;
//  public static final int RESULT_CODE_REVIEW = 34;
//  private ExpReviewFragment curFragment;
//  float downX;
//  float downY;
//  private LinearLayout mErrorViewContainer;
//  private ExpMode mExpMode;
//  private ExpStudyQueueController mExpStudyQueueController;
//  private boolean mIsFetchingData = true;
//  private boolean mIsFromSummary = false;
//  private Typeface mTypeface;
//  private Typeface mTypefaceBlod;
//
//  private void backToSummary()
//  {
//    this.mIsFromSummary = false;
//    renderPanel(this.mExpStudyQueueController.getNextFragmentBackToSummary());
//  }
//
//  public static Intent createIntent(Context paramContext, ExpMode paramExpMode)
//  {
//    Intent localIntent = new Intent(paramContext, ExpReviewActivity.class);
//    localIntent.putExtra("exp_mode", Model.toJson(paramExpMode));
//    return localIntent;
//  }
//
//  private void fetchExpData()
//  {
//    int i;
//    if (this.mExpMode.root)
//    {
//      i = 1;
//      if (!this.mExpMode.collins)
//        break label71;
//    }
//    label71: for (int j = 1; ; j = 0)
//    {
//      showProgressDialog("正在加载数据...");
//      this.mIsFetchingData = true;
//      ((WordsClient)this.mClient).experienceData(this, i, j, this.mExpMode.categoryId, new ModelResponseHandler(ExpReview.class)
//      {
//        public void onFailure(ModelResponseException paramAnonymousModelResponseException, JsonElement paramAnonymousJsonElement)
//        {
//          ExpReviewActivity.this.dismissProgressDialog();
//          ExpReviewActivity.access$002(ExpReviewActivity.this, false);
//          ExpReviewActivity.this.mErrorViewContainer.setVisibility(0);
//        }
//
//        public void onSuccess(int paramAnonymousInt, List<ExpReview> paramAnonymousList)
//        {
//          if ((paramAnonymousList != null) && (paramAnonymousList.size() > 0))
//          {
//            ExpReviewActivity.access$202(ExpReviewActivity.this, new ExpStudyQueueController(paramAnonymousList, ExpReviewActivity.this.mExpMode));
//            ExpReviewActivity.this.nextFragment();
//          }
//          ExpReviewActivity.this.dismissProgressDialog();
//          ExpReviewActivity.access$002(ExpReviewActivity.this, false);
//          ExpReviewActivity.this.mErrorViewContainer.setVisibility(8);
//        }
//      });
//      return;
//      i = 0;
//      break;
//    }
//  }
//
//  private int[] getBound(View paramView)
//  {
//    int[] arrayOfInt1 = { 0, 0, 0, 0 };
//    int[] arrayOfInt2 = { 0, 0 };
//    paramView.getLocationInWindow(arrayOfInt2);
//    int i = arrayOfInt2[0];
//    int j = arrayOfInt2[1];
//    int k = i + paramView.getWidth();
//    int m = j + paramView.getHeight();
//    arrayOfInt1[0] = i;
//    arrayOfInt1[1] = j;
//    arrayOfInt1[2] = k;
//    arrayOfInt1[3] = m;
//    return arrayOfInt1;
//  }
//
//  private boolean isFragmentEnd(String paramString)
//  {
//    return (!ExpStudyQueueController.TAG_TEST_RECOGNITION.equals(paramString)) && (!ExpStudyQueueController.TAG_EXPLORE.equals(paramString)) && (!ExpStudyQueueController.TAG_SUMMARY.equals(paramString)) && (!ExpStudyQueueController.TAG_TEST_SPELL.equals(paramString));
//  }
//
//  private boolean isViewPressed(float paramFloat1, float paramFloat2, View paramView)
//  {
//    boolean bool = true;
//    if (paramView == null)
//      return false;
//    int[] arrayOfInt = getBound(paramView);
//    if ((paramFloat1 > arrayOfInt[0]) && (paramFloat1 < arrayOfInt[2]) && (paramFloat2 > arrayOfInt[bool]) && (paramFloat2 < arrayOfInt[3]));
//    while (true)
//    {
//      return bool;
//      bool = false;
//    }
//  }
//
//  private void renderPanel(String paramString)
//  {
//    ExpReviewFragment localExpReviewFragment = (ExpReviewFragment)getSupportFragmentManager().findFragmentByTag(paramString);
//    if (localExpReviewFragment == null)
//      localExpReviewFragment = (ExpReviewFragment)Fragment.instantiate(this, paramString);
//    while (!localExpReviewFragment.isVisible())
//    {
//      switchContent(this.curFragment, localExpReviewFragment, paramString);
//      return;
//    }
//    localExpReviewFragment.render();
//  }
//
//  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
//  {
//    float f1 = paramMotionEvent.getX();
//    float f2 = paramMotionEvent.getY();
//    switch (paramMotionEvent.getAction())
//    {
//    default:
//    case 0:
//    case 1:
//    }
//    while (true)
//    {
//      return super.dispatchTouchEvent(paramMotionEvent);
//      this.downX = f1;
//      this.downY = f2;
//      continue;
//      if ((Math.abs(f1 - this.downX) < 5.0F) && (Math.abs(f2 - this.downY) < 5.0F))
//      {
//        View localView1 = findViewById(2131034161);
//        View localView2 = findViewById(2131034202);
//        View localView3 = findViewById(2131034256);
//        View localView4 = findViewById(2131034770);
//        if ((!isViewPressed(f1, f2, localView2)) && (!isViewPressed(f1, f2, localView3)) && (!isViewPressed(f1, f2, localView4)))
//          Misc.forceHideSoftKeyboard(this, localView1);
//      }
//    }
//  }
//
//  public ExpStudyQueueController getStudyQueueController()
//  {
//    return this.mExpStudyQueueController;
//  }
//
//  public int getTestRecognitionMode()
//  {
//    return getStudyQueueController().getTestRecognitionMode();
//  }
//
//  public int getTestSpellMode()
//  {
//    return getStudyQueueController().getTestSpellMode();
//  }
//
//  public Typeface getTypeface()
//  {
//    return this.mTypeface;
//  }
//
//  public Typeface getTypefaceBlod()
//  {
//    return this.mTypefaceBlod;
//  }
//
//  public void goToExplore(int paramInt)
//  {
//    String str = this.mExpStudyQueueController.getNextFragmentSummaryToExp(paramInt);
//    this.mIsFromSummary = true;
//    renderPanel(str);
//  }
//
//  public boolean isEnableCollins()
//  {
//    return this.mExpMode.collins;
//  }
//
//  public boolean isEnableRoots()
//  {
//    return this.mExpMode.root;
//  }
//
//  public boolean isFromSummary()
//  {
//    return this.mIsFromSummary;
//  }
//
//  public void nextFragment()
//  {
//    if (isFinishing())
//      return;
//    this.mIsFromSummary = false;
//    String str = this.mExpStudyQueueController.nextFragment();
//    if (!isFragmentEnd(str))
//    {
//      renderPanel(str);
//      return;
//    }
//    startActivity(new Intent(this, ExpFinishedActivity.class));
//    finish();
//  }
//
//  public void onBackPressed()
//  {
//    if (isFromSummary())
//    {
//      backToSummary();
//      return;
//    }
//    startActivity(new Intent(this, WelcomeActivity.class));
//    super.onBackPressed();
//  }
//
//  protected void onCreate(Bundle paramBundle)
//  {
//    super.onCreate(paramBundle);
//    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    setContentView(2130903074);
//    if (paramBundle != null)
//    {
//      startActivity(new Intent(this, WelcomeActivity.class));
//      finish();
//    }
//    String str;
//    do
//    {
//      return;
//      this.mTypeface = Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Regular.ttf");
//      this.mTypefaceBlod = Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Bold.ttf");
//      this.mErrorViewContainer = ((LinearLayout)findViewById(2131034241));
//      this.mErrorViewContainer.setOnClickListener(new View.OnClickListener()
//      {
//        public void onClick(View paramAnonymousView)
//        {
//          if (!ExpReviewActivity.this.mIsFetchingData)
//            ExpReviewActivity.this.fetchExpData();
//        }
//      });
//      str = getIntent().getStringExtra("exp_mode");
//    }
//    while (!StringUtils.isNotBlank(str));
//    this.mExpMode = ((ExpMode)Model.fromJson(str, ExpMode.class));
//    fetchExpData();
//  }
//
//  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
//  {
//    if (paramMenuItem.getItemId() == 16908332)
//    {
//      onBackPressed();
//      return true;
//    }
//    return super.onOptionsItemSelected(paramMenuItem);
//  }
//
//  public void switchContent(ExpReviewFragment paramExpReviewFragment1, ExpReviewFragment paramExpReviewFragment2, String paramString)
//  {
//    FragmentTransaction localFragmentTransaction;
//    if (this.curFragment != paramExpReviewFragment2)
//    {
//      this.curFragment = paramExpReviewFragment2;
//      localFragmentTransaction = getSupportFragmentManager().beginTransaction();
//      if (paramExpReviewFragment2.isAdded())
//        break label71;
//      if (paramExpReviewFragment1 != null)
//        break label51;
//      localFragmentTransaction.add(2131034161, paramExpReviewFragment2, paramString).commitAllowingStateLoss();
//    }
//    while (true)
//    {
//      paramExpReviewFragment2.render();
//      return;
//      label51: localFragmentTransaction.hide(paramExpReviewFragment1).add(2131034161, paramExpReviewFragment2, paramString).commitAllowingStateLoss();
//      continue;
//      label71: if (paramExpReviewFragment1 == null)
//        localFragmentTransaction.show(paramExpReviewFragment2).commitAllowingStateLoss();
//      else
//        localFragmentTransaction.hide(paramExpReviewFragment1).show(paramExpReviewFragment2).commitAllowingStateLoss();
//    }
//  }
//}