package com.shanbay.words.review.experience;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.shanbay.model.Model;
import com.shanbay.util.Misc;
import com.shanbay.words.R;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.model.ExpMode;
import com.shanbay.words.model.WordbookCategory;
import org.apache.commons.lang.StringUtils;

public class ExpModeActivity extends WordsActivity
  implements CompoundButton.OnCheckedChangeListener, View.OnClickListener
{
  public static final int REQUEST_CODE_OPTIONS = 35;
  public static final int RESULT_CODE_CONFIRM = 34;
  private Button mBtnGoExP;
  private CheckBox mCbCollins;
  private CheckBox mCbRootWord;
  private CheckBox mCbSpell;
  private LinearLayout mCollinsContainer;
  private ExpMode mExpMode;
  private LinearLayout mRootsContainer;
  private LinearLayout mSpellContainer;
  private TextView mTvCategory;
  private WordbookCategory mWordbookCategory;

  public static Intent createIntent(Context paramContext, WordbookCategory paramWordbookCategory)
  {
    Intent localIntent = new Intent(paramContext, ExpModeActivity.class);
    localIntent.putExtra("category", Model.toJson(paramWordbookCategory));
    return localIntent;
  }

  private void renderModeOption()
  {
    if (this.mWordbookCategory == null){
    	return;
    }
    String str = mWordbookCategory.name;
    
    if ((StringUtils.equals("初中", str)) || (StringUtils.equals("高中", str)) || (StringUtils.equals("四级", str)))
    {
      LayoutParams lp = (LayoutParams) mSpellContainer.getLayoutParams();
      lp.addRule(RelativeLayout.CENTER_VERTICAL);
      mCollinsContainer.setVisibility(View.GONE);
      mRootsContainer.setVisibility(View.GONE);
      mExpMode.collins = false;
      mExpMode.root = false;
      return;
    }
    if (StringUtils.equals("六级", str))
    {
      mCollinsContainer.setVisibility(View.GONE);
      mExpMode.collins = false;
      return;
    }
    
    if(StringUtils.equals("GRE", str)){
    	mSpellContainer.setVisibility(View.GONE);
    	mExpMode.spell = false;
    }
  }

  public void onCheckedChanged(CompoundButton compoundButton, boolean isSelected)
  {
    if (compoundButton.getId() == R.id.word_root)
      mExpMode.root = isSelected;
    if (compoundButton.getId() == R.id.word_spell)
      mExpMode.spell = isSelected;
    if(compoundButton.getId() == R.id.word_collins)
      mExpMode.collins = isSelected;
  }

  public void onClick(View paramView)
  {
    startActivity(ExpReviewActivity.createIntent(this, mExpMode));
    setResult(34);
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_experience_mode);
    mTvCategory = ((TextView)findViewById(R.id.category));
    mCbRootWord = ((CheckBox)findViewById(R.id.word_root));
    mCbSpell = ((CheckBox)findViewById(R.id.word_spell));
    mCbCollins = ((CheckBox)findViewById(R.id.word_collins));
    mRootsContainer = ((LinearLayout)findViewById(R.id.roots_container));
    mCollinsContainer = ((LinearLayout)findViewById(R.id.collins_container));
    mSpellContainer = ((LinearLayout)findViewById(R.id.spell_container));
    mBtnGoExP = ((Button)findViewById(R.id.experience));
    mCbRootWord.setOnCheckedChangeListener(this);
    mCbCollins.setOnCheckedChangeListener(this);
    mCbSpell.setOnCheckedChangeListener(this);
    mBtnGoExP.setOnClickListener(this);
    Misc.disableHardwareAcceleration(findViewById(R.id.top_line));
    Misc.disableHardwareAcceleration(findViewById(R.id.bottom_line));
    mExpMode = new ExpMode();
    String str = getIntent().getStringExtra("category");
    if (StringUtils.isNotBlank(str))
    {
      mWordbookCategory = ((WordbookCategory)Model.fromJson(str, WordbookCategory.class));
      mExpMode.categoryId = mWordbookCategory.id;
      mTvCategory.setText(mWordbookCategory.name);
      renderModeOption();
    }
  }
}