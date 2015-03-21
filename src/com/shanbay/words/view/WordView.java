package com.shanbay.words.view;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.shanbay.words.R;
import com.shanbay.words.WordsSoundPlayer;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.helper.AppletHelper;
import com.shanbay.words.model.Search;
import com.shanbay.words.model.Vocabulary;
import com.shanbay.words.model.Vocabulary.EnDefinition;
import com.shanbay.words.model.VocabularyData;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;

public class WordView
{
  WordsActivity mActivity;
  ImageView mArrowImgView;
  ImageButton mAudioButton;
  TextView mContentText;
  RelativeLayout mDefinitionContainer;
  TextView mDefinitionText;
  TextView mEnDefinitions;
  RelativeLayout mEnDefinitionsContainer;
  ToggleButton mEndefToggleButton;
  TextView mHint;
  RelativeLayout.LayoutParams mNewLinePronTextLP;
  RelativeLayout.LayoutParams mNormalPronTextLP;
  TextView mPronText;
  ViewGroup mRoot;
  private Typeface mTypeface;
  private Typeface mTypefacePhonet;
  RelativeLayout mWordContentContainer;

  public WordView(WordsActivity paramWordsActivity, ViewGroup paramViewGroup)
  {
    mActivity = paramWordsActivity;
    mRoot = paramViewGroup;
    mTypeface = Typeface.createFromAsset(mActivity.getAssets(), "fonts/NotoSans-Regular.ttf");
    mTypefacePhonet = Typeface.createFromAsset(mActivity.getAssets(), mActivity.getString(R.string.fonts_phonet));
  }

  public void clearAllViews()
  {
    mRoot.setVisibility(View.GONE);
    mPronText.setVisibility(View.GONE);
    mContentText.setVisibility(View.GONE);
    mDefinitionText.setVisibility(View.GONE);
    mEnDefinitionsContainer.setVisibility(View.GONE);
    mEndefToggleButton.setVisibility(View.GONE);
    mAudioButton.setVisibility(View.GONE);
    mHint.setVisibility(View.GONE);
  }

  public String getEnDefinition(VocabularyData paramVocabularyData)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((paramVocabularyData.getEnPos() != null) && (paramVocabularyData.getEnDefn() != null))
    {
      localStringBuilder.append("<I>");
      localStringBuilder.append(paramVocabularyData.getEnPos());
      localStringBuilder.append("</I>");
      localStringBuilder.append("&nbsp;&nbsp;");
      localStringBuilder.append(paramVocabularyData.getEnDefn());
    }
    return localStringBuilder.toString();
  }

  public String getEnDefinition(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((paramString1 != null) && (paramString2 != null))
    {
      localStringBuilder.append("<I>");
      localStringBuilder.append(paramString1);
      localStringBuilder.append("</I>");
      localStringBuilder.append("&nbsp;&nbsp;");
      localStringBuilder.append(paramString2);
    }
    return localStringBuilder.toString();
  }

  public String getEnDefinitionString(VocabularyData paramVocabularyData)
  {
    return getEnDefinition(paramVocabularyData);
  }

  public String getNormalEnDefinitionString(Vocabulary paramVocabulary)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((paramVocabulary.enDefinition.pos != null) && (paramVocabulary.enDefinition.defn != null))
    {
      localStringBuilder.append(paramVocabulary.enDefinition.pos).append("<br />");
      String[] arrayOfString = paramVocabulary.enDefinition.defn.toString().split(";");
      int i = 0;
      if (i < arrayOfString.length)
      {
        localStringBuilder.append(i + 1 + "." + arrayOfString[i]);
        if (!arrayOfString[i].endsWith(".")){
            localStringBuilder.append(";<br />");
        }else{
            localStringBuilder.append("<br />");
        }
          i++;
      }
    }
    return localStringBuilder.toString();
  }

  public View getRootView()
  {
    return mRoot;
  }

  public void init()
  {
    mContentText = ((TextView)mRoot.findViewById(R.id.content));
    mPronText = ((TextView)mRoot.findViewById(R.id.pron));
    mNormalPronTextLP = ((RelativeLayout.LayoutParams)mPronText.getLayoutParams());
    mDefinitionContainer = ((RelativeLayout)mRoot.findViewById(R.id.definition_container));
    mWordContentContainer = ((RelativeLayout)mRoot.findViewById(R.id.word_content_container));
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    localLayoutParams.addRule(RelativeLayout.BELOW, mContentText.getId());
    localLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, mContentText.getId());
    mNewLinePronTextLP = localLayoutParams;
    final ViewGroup localViewGroup = (ViewGroup)mPronText.getParent();
    localViewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public void onGlobalLayout()
      {
        localViewGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        WordView.this.layoutPronText();
      }
    });
    mDefinitionText = ((TextView)mRoot.findViewById(R.id.definition));
    mEnDefinitions = ((TextView)mRoot.findViewById(R.id.en_definitions));
    mEnDefinitionsContainer = ((RelativeLayout)mRoot.findViewById(R.id.en_definitions_container));
    mEndefToggleButton = ((ToggleButton)mRoot.findViewById(R.id.toggle_en));
    mAudioButton = ((ImageButton)mRoot.findViewById(R.id.btn_sound_in_word));
    mArrowImgView = ((ImageView)mRoot.findViewById(R.id.arrow));
    mEndefToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton compoundButton, boolean mboolean)
      {
        if (mboolean)
        {
          mEnDefinitionsContainer.setVisibility(View.VISIBLE);
          return;
        }
        mEnDefinitionsContainer.setVisibility(View.GONE);
      }
    });
    mEndefToggleButton.setChecked(false);
    if (isShowCollins())
    {
      mArrowImgView.setVisibility(View.VISIBLE);
      mEndefToggleButton.setVisibility(View.GONE);
      mEnDefinitionsContainer.setVisibility(View.VISIBLE);
    }else{
    	 mArrowImgView.setVisibility(View.GONE);
         mEnDefinitionsContainer.setBackgroundResource(R.drawable.btn_item_normal);
    }
      mHint = ((TextView)mRoot.findViewById(R.id.hint));
      mContentText.setTypeface(mTypeface);
      mPronText.setTypeface(mTypefacePhonet);
      mEnDefinitions.setTypeface(mTypeface);
  }

  public boolean isShowCollins()
  {
    int i = AppletHelper.getInstance().getAppletStatus(this.mActivity, "collins");
    return (i == 2) || (i == 1);
  }

  public void layoutPronText()
  {
    String str = mPronText.getText().toString() + "W";
    Rect localRect = new Rect();
    this.mPronText.getPaint().getTextBounds(str, 0, str.length(), localRect);
    int i = localRect.width() + mNormalPronTextLP.leftMargin;
    ViewGroup localViewGroup = (ViewGroup)mPronText.getParent();
    RelativeLayout.LayoutParams localLayoutParams;
    if (i + mContentText.getWidth() >= localViewGroup.getWidth() - localViewGroup.getPaddingLeft())
    {
      localLayoutParams = mNewLinePronTextLP;
      localRect.set(0, 0, 0, 0);
    }else{
    	localLayoutParams = mNormalPronTextLP;
        localRect.set(0, 0, 0, 0);
    }
      if (localLayoutParams != mPronText.getLayoutParams())
      {
        localViewGroup.removeView(mPronText);
        mPronText.setLayoutParams(localLayoutParams);
        localViewGroup.addView(mPronText);
        localViewGroup.setPadding(localRect.left, localRect.top, localRect.right, localRect.bottom);
      }
  }

  public void release()
  {
  }

  public void render(final Search paramSearch)
  {
//    mAudioButton.setVisibility(View.VISIBLE);
//    mWordContentContainer.setOnClickListener(new View.OnClickListener()
//    {
//      public void onClick(View paramAnonymousView)
//      {
//        mActivity.getSoundPlayer().playSoundByUrl(paramSearch.usAudio, mAudioButton);
//      }
//    });
//    
//    if ((paramSearch.pronunciation != null) && (paramSearch.pronunciation.length() > 0))
//    {
//      mPronText.setText(Html.fromHtml('[' + paramSearch.pronunciation + ']'));
//      mPronText.setLayoutParams(mNewLinePronTextLP);
//      mContentText.setText(paramSearch.content);
//      if (paramSearch.definition != null)
//        mDefinitionText.setText(paramSearch.definition.trim());
//      if ((paramSearch.definition != null) && ((paramSearch.enDefinition.defn != null) || (paramSearch.enDefinition.pos != null)))
//        mEndefToggleButton.setVisibility(View.GONE);
//    }else{
//    	
//    }
//    while (true)
//    {
//      if (!isShowCollins())
//        break;
//      this.mEndefToggleButton.setVisibility(View.GONE);
//      this.mEnDefinitionsContainer.setVisibility(View.VISIBLE);
//      this.mDefinitionContainer.setVisibility(View.GONE);
//      if (StringUtil.isBlank(paramSearch.enDefinition.defn))
//      {
//        this.mDefinitionContainer.setVisibility(View.VISIBLE);
//        this.mEnDefinitionsContainer.setVisibility(View.GONE);
//      }
//      return;
//      this.mPronText.setText("");
//      break;
//      label234: this.mEndefToggleButton.setVisibility(View.VISIBLE);
//      String str = wordsHighLight(getEnDefinition(paramSearch.enDefinition.pos, paramSearch.enDefinition.defn));
//      this.mEnDefinitions.setText(Html.fromHtml(str));
//    }
//    label279: this.mEnDefinitionsContainer.setClickable(false);
//    this.mDefinitionContainer.setVisibility(View.VISIBLE);
  }

  public void render(final VocabularyData vocabularyData)
  {
	  if (vocabularyData.hasAudio())
	    {
	      mAudioButton.setVisibility(View.VISIBLE);
	      mWordContentContainer.setOnClickListener(new View.OnClickListener()
	      {
	        public void onClick(View paramAnonymousView)
	        {
	          WordView.this.mActivity.getSoundPlayer().playSound(vocabularyData.getAudioName(), WordView.this.mAudioButton);
	        }
	      });
	    }
	  
	    if ((vocabularyData.getPron() != null) && (vocabularyData.getPron().length() > 0))
	    {
	      mPronText.setText(Html.fromHtml('[' + vocabularyData.getPron() + ']'));
	      mContentText.setText(vocabularyData.getContent());
	      if (vocabularyData.getCnDefinition() != null){
	    	  mDefinitionText.setText(vocabularyData.getCnDefinition().trim());
	      }
	      if (!(vocabularyData.getCnDefinition() != null) && ((vocabularyData.getEnDefn() != null) || (vocabularyData.getEnPos() != null)))
	    	  mEndefToggleButton.setVisibility(View.GONE);
	    }else{
	    	 mPronText.setText("");
	    }
	    
	    if (isShowCollins()){
	    	  mEndefToggleButton.setVisibility(View.GONE);
		      mEnDefinitionsContainer.setVisibility(View.VISIBLE);
		      mDefinitionContainer.setVisibility(View.GONE);
		      
		      String str = wordsHighLight(getEnDefinitionString(vocabularyData));
		      mEnDefinitions.setText(Html.fromHtml(str));
	      }else{
	    	mEndefToggleButton.setVisibility(View.VISIBLE);
	  	    
	    	mEnDefinitionsContainer.setClickable(false);
	  	    mDefinitionContainer.setVisibility(View.VISIBLE);
	      }
	      
	      if (StringUtils.isBlank(vocabularyData.getEnDefn()))
	      {
	        this.mDefinitionContainer.setVisibility(View.VISIBLE);
	        this.mEnDefinitionsContainer.setVisibility(View.GONE);
	      }
  }

  public String wordsHighLight(String paramString)
  {
    return Pattern.compile("<vocab>(.*?)</vocab>").matcher(paramString).replaceAll("<font color=\"#" + Integer.toHexString(0) + "\">$1</font>");
  }
}