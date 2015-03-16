package com.shanbay.words.review.experience;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import com.google.renamedgson.JsonElement;
import com.shanbay.http.ModelResponseException;
import com.shanbay.http.ModelResponseHandler;
import com.shanbay.util.Misc;
import com.shanbay.widget.IndicatorWrapper;
import com.shanbay.words.R;
import com.shanbay.words.WordsClient;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.model.WordbookCategory;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ExpCategoryActivity extends WordsActivity
{
  public static final int REQUEST_CODE_CATEGORY = 37;
  public static final int RESULT_CODE_CATEGORY = 38;
  private RadioButton[] mCategoryBtns;
  private LinearLayout mCategoryContainer;
  private IndicatorWrapper mIndicatorWrapper;
  private ImageView[] mIvChoices;
  private View.OnClickListener mOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      int i = 0;
      if (i < ExpCategoryActivity.this.mCategoryBtns.length)
        if (ExpCategoryActivity.this.mCategoryBtns[i] == paramAnonymousView)
          if (!StringUtils.isBlank(ExpCategoryActivity.this.mCategoryBtns[i].getText().toString()));
      WordbookCategory localWordbookCategory;
      do
      {
//        return;
        ExpCategoryActivity.this.mCategoryBtns[i].setChecked(true);
        ExpCategoryActivity.this.mIvChoices[i].setVisibility(0);
//        while (true)
//        {
//          i++;
//          break;
//          ExpCategoryActivity.this.mCategoryBtns[i].setChecked(false);
//          ExpCategoryActivity.this.mIvChoices[i].setVisibility(4);
//        }
        localWordbookCategory = (WordbookCategory)paramAnonymousView.getTag();
      }
      while (localWordbookCategory == null);
//      ExpCategoryActivity.this.startActivityForResult(ExpModeActivity.createIntent(ExpCategoryActivity.this, localWordbookCategory), 35);
    }
  };

  @SuppressWarnings({ "unchecked", "rawtypes" })
private void fetchCategories()
  {
    showProgressDialog();
    ((WordsClient)this.mClient).experienceCategory(this, new ModelResponseHandler(WordbookCategory.class)
    {
      public void onFailure(ModelResponseException paramAnonymousModelResponseException, JsonElement paramAnonymousJsonElement)
      {
        if (!ExpCategoryActivity.this.handleCommonException(paramAnonymousModelResponseException))
          ExpCategoryActivity.this.showToast(paramAnonymousModelResponseException.getMessage());
      }

	public void onSuccess(int paramInt, List paramList) {
		ExpCategoryActivity.this.layout(paramList);
      ExpCategoryActivity.this.dismissProgressDialog();
	}
    });
  }

  private void layout(List<WordbookCategory> paramList)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(this);
    int i = 2 * ((1 + paramList.size()) / 2);
    this.mCategoryBtns = new RadioButton[i];
    this.mIvChoices = new ImageView[i];
    for (int j = 0; j < i; j += 2)
    {
      RelativeLayout localRelativeLayout = (RelativeLayout)localLayoutInflater.inflate(2130903219, null);
      Misc.disableHardwareAcceleration(localRelativeLayout.findViewById(2131034624));
      Misc.disableHardwareAcceleration(localRelativeLayout.findViewById(2131034623));
      this.mCategoryBtns[j] = ((RadioButton)localRelativeLayout.findViewById(2131034586));
      this.mCategoryBtns[j].setOnClickListener(this.mOnClickListener);
      this.mIvChoices[j] = ((ImageView)localRelativeLayout.findViewById(2131034621));
      this.mIvChoices[(j + 1)] = ((ImageView)localRelativeLayout.findViewById(2131034622));
      this.mCategoryBtns[(j + 1)] = ((RadioButton)localRelativeLayout.findViewById(2131034587));
      this.mCategoryBtns[(j + 1)].setOnClickListener(this.mOnClickListener);
      this.mCategoryContainer.addView(localRelativeLayout, new LinearLayout.LayoutParams(-1, -2));
    }
    for (int k = 0; k < paramList.size(); k++)
    {
      RadioButton localRadioButton = this.mCategoryBtns[k];
      localRadioButton.setText(((WordbookCategory)paramList.get(k)).name);
      localRadioButton.setTag(paramList.get(k));
    }
  }

  public void dismissProgressDialog()
  {
    this.mIndicatorWrapper.hideIndicator();
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 35) && (paramInt2 == 34))
    {
      setResult(38);
      finish();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_experience_category);
    this.mIndicatorWrapper = ((IndicatorWrapper)findViewById(R.id.indicator_wrapper));
    this.mCategoryContainer = ((LinearLayout)findViewById(R.id.root));
    Misc.disableHardwareAcceleration(findViewById(R.id.bottom_line));
    fetchCategories();
  }

  public void showProgressDialog()
  {
    this.mIndicatorWrapper.showIndicator();
  }
}