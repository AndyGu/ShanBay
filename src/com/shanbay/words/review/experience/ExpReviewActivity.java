package com.shanbay.words.review.experience;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.google.renamedgson.JsonElement;
import com.shanbay.http.ModelResponseException;
import com.shanbay.http.ModelResponseHandler;
import com.shanbay.model.Model;
import com.shanbay.util.Misc;
import com.shanbay.words.R;
import com.shanbay.words.WordsClient;
import com.shanbay.words.activity.WelcomeActivity;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.model.ExpMode;
import com.shanbay.words.model.ExpReview;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ExpReviewActivity extends WordsActivity
{
  public static final int REQUEST_CODE_REVIEW = 35;
  public static final int RESULT_CODE_REVIEW = 34;
  private ExpReviewFragment curFragment;
  float downX;
  float downY;
  private LinearLayout mErrorViewContainer;
  private ExpMode mExpMode;
  private ExpStudyQueueController mExpStudyQueueController;
  private boolean mIsFetchingData = true;
  private boolean mIsFromSummary = false;
  private Typeface mTypeface;
  private Typeface mTypefaceBlod;

  private void backToSummary()
  {
    this.mIsFromSummary = false;
    renderPanel(this.mExpStudyQueueController.getNextFragmentBackToSummary());
  }

  public static Intent createIntent(Context paramContext, ExpMode paramExpMode)
  {
    Intent localIntent = new Intent(paramContext, ExpReviewActivity.class);
    localIntent.putExtra("exp_mode", Model.toJson(paramExpMode));
    return localIntent;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
private void fetchExpData()
  {
	  showProgressDialog("正在加载数据...");
      this.mIsFetchingData = true;
      ((WordsClient)this.mClient).experienceData(this, mExpMode.root?1:0, mExpMode.collins?1:0, mExpMode.categoryId, new ModelResponseHandler(ExpReview.class)
      {
        public void onFailure(ModelResponseException modelResponseException, JsonElement jsonElement)
        {
          dismissProgressDialog();
          mIsFetchingData = false;
          mErrorViewContainer.setVisibility(View.VISIBLE);
        }

        public void onSuccess(int statusCode, List list)
        {
          if ((list != null) && (list.size() > 0))
          {
        	  mExpStudyQueueController = new ExpStudyQueueController(list, mExpMode);
        	  nextFragment();
          }
          dismissProgressDialog();
          mIsFetchingData = false;
          mErrorViewContainer.setVisibility(View.GONE);
        }
      });
  }

  private int[] getBound(View view)
  {
    int[] arrayOfInt1 = { 0, 0, 0, 0 };
    int[] arrayOfInt2 = { 0, 0 };
    view.getLocationInWindow(arrayOfInt2);
    int i = arrayOfInt2[0];
    int j = arrayOfInt2[1];
    int k = i + view.getWidth();
    int m = j + view.getHeight();
    arrayOfInt1[0] = i;
    arrayOfInt1[1] = j;
    arrayOfInt1[2] = k;
    arrayOfInt1[3] = m;
    return arrayOfInt1;
  }

  private boolean isFragmentEnd(String tag)
  {
    return (!ExpStudyQueueController.TAG_TEST_RECOGNITION.equals(tag)) && (!ExpStudyQueueController.TAG_EXPLORE.equals(tag)) && (!ExpStudyQueueController.TAG_SUMMARY.equals(tag)) && (!ExpStudyQueueController.TAG_TEST_SPELL.equals(tag));
  }

  private boolean isViewPressed(float motionX, float motionY, View view)
  {
		boolean bool = true;
		if (view == null)
			return false;
		int[] arrayOfInt = getBound(view);
		if ((motionX > arrayOfInt[0]) && (motionX < arrayOfInt[2])
				&& (motionY > arrayOfInt[1]) && (motionY < arrayOfInt[3])) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
  }

  private void renderPanel(String tag)
  {
    ExpReviewFragment expReviewFragment = (ExpReviewFragment)getSupportFragmentManager().findFragmentByTag(tag);
    if (expReviewFragment == null)
      expReviewFragment = (ExpReviewFragment)Fragment.instantiate(this, tag);
    if(!expReviewFragment.isVisible())
    {
      switchContent(curFragment, expReviewFragment, tag);
    }
    expReviewFragment.render();
  }

  public boolean dispatchTouchEvent(MotionEvent event)
  {
    float f1 = event.getX();
    float f2 = event.getY();
    
    switch (event.getAction())
    {
    case MotionEvent.ACTION_DOWN:
    	downX = f1;
        downY = f2;
    	break;
    case MotionEvent.ACTION_UP:
    	if ((Math.abs(f1 - downX) < 5.0F) && (Math.abs(f2 - downY) < 5.0F))
        {
          View root = findViewById(R.id.root);
          View searchPlate = findViewById(R.id.search_plate);
          View spellContainer = findViewById(R.id.spell_container);
          View btnHintContainer = findViewById(R.id.btn_hint_container);
          if ((!isViewPressed(f1, f2, searchPlate)) && (!isViewPressed(f1, f2, spellContainer)) && (!isViewPressed(f1, f2, btnHintContainer)))
            Misc.forceHideSoftKeyboard(this, root);
        }
    	break;
    default:
    	break;
    }
      return super.dispatchTouchEvent(event);
  }

  public ExpStudyQueueController getStudyQueueController()
  {
    return mExpStudyQueueController;
  }

  public int getTestRecognitionMode()
  {
    return getStudyQueueController().getTestRecognitionMode();
  }

  public int getTestSpellMode()
  {
    return getStudyQueueController().getTestSpellMode();
  }

  public Typeface getTypeface()
  {
    return mTypeface;
  }

  public Typeface getTypefaceBlod()
  {
    return mTypefaceBlod;
  }

  public void goToExplore(int paramInt)
  {
    String str = mExpStudyQueueController.getNextFragmentSummaryToExp(paramInt);
    mIsFromSummary = true;
    renderPanel(str);
  }

  public boolean isEnableCollins()
  {
    return mExpMode.collins;
  }

  public boolean isEnableRoots()
  {
    return mExpMode.root;
  }

  public boolean isFromSummary()
  {
    return mIsFromSummary;
  }

  public void nextFragment()
  {
    if (isFinishing())
      return;
    mIsFromSummary = false;
    String str = mExpStudyQueueController.nextFragment();
    if (!isFragmentEnd(str))
    {
      renderPanel(str);
      return;
    }
    startActivity(new Intent(this, ExpFinishedActivity.class));
    finish();
  }

  public void onBackPressed()
  {
    if (isFromSummary())
    {
      backToSummary();
      return;
    }
    startActivity(new Intent(this, WelcomeActivity.class));
    super.onBackPressed();
  }

  protected void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setContentView(R.layout.activity_exp_review);
    if (bundle != null)
    {
      startActivity(new Intent(this, WelcomeActivity.class));
      finish();
    }
    
    mTypeface = Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Regular.ttf");
    mTypefaceBlod = Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Bold.ttf");
    mErrorViewContainer = ((LinearLayout)findViewById(R.id.error_container));
    mErrorViewContainer.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!ExpReviewActivity.this.mIsFetchingData)
          ExpReviewActivity.this.fetchExpData();
      }
    });
    
    String str = getIntent().getStringExtra("exp_mode");
    if (!StringUtils.isNotBlank(str));
    	mExpMode = ((ExpMode)Model.fromJson(str, ExpMode.class));
    fetchExpData();
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 16908332)
    {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }

  public void switchContent(ExpReviewFragment currentFragment, ExpReviewFragment newFragment, String tag)
  {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    
    if (curFragment != newFragment)
    {
      curFragment = newFragment;
      if (newFragment.isAdded())
        return;
      if (currentFragment != null)
        return;
      ft.add(R.id.root, newFragment, tag).commitAllowingStateLoss();
    }else{
    	ft.hide(currentFragment).add(R.id.root, newFragment, tag).commitAllowingStateLoss();
    	
    	if (currentFragment == null)
            ft.show(newFragment).commitAllowingStateLoss();
        else
            ft.hide(currentFragment).show(newFragment).commitAllowingStateLoss();
    }
    newFragment.render();
  }
}