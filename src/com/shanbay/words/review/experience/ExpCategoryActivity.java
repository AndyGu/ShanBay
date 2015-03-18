package com.shanbay.words.review.experience;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
    public void onClick(View view)
    {
      int i = 0;
      while(i < mCategoryBtns.length){
    	  if (mCategoryBtns[i] == view){
    		  if (!StringUtils.isBlank(mCategoryBtns[i].getText().toString())){
            	  mCategoryBtns[i].setChecked(true);
                  mIvChoices[i].setVisibility(View.VISIBLE);
              }
    	  }else{
        	  mCategoryBtns[i].setChecked(false);
        	  mIvChoices[i].setVisibility(View.INVISIBLE);
          } 
    	  i++;
      }
        
      WordbookCategory wordbookCategory = (WordbookCategory)view.getTag();
      if(wordbookCategory != null){
        ExpCategoryActivity.this.startActivityForResult(ExpModeActivity.createIntent(ExpCategoryActivity.this, wordbookCategory), 35);
      }
    }
  };

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fetchCategories() {
		showProgressDialog();

		Log.e("fetchCategories", "fetchCategories");
		((WordsClient) this.mClient).experienceCategory(this,
				new ModelResponseHandler(WordbookCategory.class) {
					public void onFailure(ModelResponseException mre,
							JsonElement jsonElement) {
						if (!ExpCategoryActivity.this
								.handleCommonException(mre))
							showToast(mre.getMessage());
					}

					public void onSuccess(int paramInt, List paramList) {
						layout(paramList);
						dismissProgressDialog();
					}
				});
	}

  @SuppressLint("InflateParams")
  private void layout(List<WordbookCategory> paramList)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(this);
    int i = 2 * ((1 + paramList.size()) / 2);
    mCategoryBtns = new RadioButton[i];
    mIvChoices = new ImageView[i];
    for (int j = 0; j < i; j += 2)
    {
      Log.e("layout", "j="+j);
      RelativeLayout localRelativeLayout = (RelativeLayout)localLayoutInflater.inflate(R.layout.item_experience_category, null);
      Misc.disableHardwareAcceleration(localRelativeLayout.findViewById(R.id.dash_line_vertical));
      Misc.disableHardwareAcceleration(localRelativeLayout.findViewById(R.id.dash_line_horizontal));
      mCategoryBtns[j] = ((RadioButton)localRelativeLayout.findViewById(R.id.button1));
      mCategoryBtns[j].setOnClickListener(mOnClickListener);
      mIvChoices[j] = ((ImageView)localRelativeLayout.findViewById(R.id.img1));
      mIvChoices[(j + 1)] = ((ImageView)localRelativeLayout.findViewById(R.id.img2));
      mCategoryBtns[(j + 1)] = ((RadioButton)localRelativeLayout.findViewById(R.id.button2));
      mCategoryBtns[(j + 1)].setOnClickListener(mOnClickListener);
      mCategoryContainer.addView(localRelativeLayout, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }
    for (int k = 0; k < paramList.size(); k++)
    {
      RadioButton localRadioButton = mCategoryBtns[k];
      localRadioButton.setText(((WordbookCategory)paramList.get(k)).name);
      localRadioButton.setTag(paramList.get(k));
    }
  }

  public void dismissProgressDialog()
  {
    mIndicatorWrapper.hideIndicator();
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
    mIndicatorWrapper = ((IndicatorWrapper)findViewById(R.id.indicator_wrapper));
    mCategoryContainer = ((LinearLayout)findViewById(R.id.root));
    Misc.disableHardwareAcceleration(findViewById(R.id.bottom_line));

    fetchCategories();
  }

  public void showProgressDialog()
  {
    mIndicatorWrapper.showIndicator();
  }
}