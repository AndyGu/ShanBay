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
    this.mActivity = paramWordsActivity;
    this.mRoot = paramViewGroup;
    this.mTypeface = Typeface.createFromAsset(this.mActivity.getAssets(), "fonts/NotoSans-Regular.ttf");
    this.mTypefacePhonet = Typeface.createFromAsset(this.mActivity.getAssets(), this.mActivity.getString(2131362132));
  }

  public void clearAllViews()
  {
    this.mRoot.setVisibility(8);
    this.mPronText.setVisibility(8);
    this.mContentText.setVisibility(8);
    this.mDefinitionText.setVisibility(8);
    this.mEnDefinitionsContainer.setVisibility(8);
    this.mEndefToggleButton.setVisibility(8);
    this.mAudioButton.setVisibility(8);
    this.mHint.setVisibility(8);
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

//  public String getNormalEnDefinitionString(Vocabulary paramVocabulary)
//  {
//    StringBuilder localStringBuilder = new StringBuilder();
//    if ((paramVocabulary.enDefinition.pos != null) && (paramVocabulary.enDefinition.defn != null))
//    {
//      localStringBuilder.append(paramVocabulary.enDefinition.pos).append("<br />");
//      String[] arrayOfString = paramVocabulary.enDefinition.defn.toString().split(";");
//      int i = 0;
//      if (i < arrayOfString.length)
//      {
//        localStringBuilder.append(i + 1 + "." + arrayOfString[i]);
//        if (!arrayOfString[i].endsWith("."))
//          localStringBuilder.append(";<br />");
//        while (true)
//        {
//          i++;
//          break;
//          localStringBuilder.append("<br />");
//        }
//      }
//    }
//    return localStringBuilder.toString();
//  }

  public View getRootView()
  {
    return this.mRoot;
  }

  public void init()
  {
//    this.mContentText = ((TextView)this.mRoot.findViewById(2131034154));
//    this.mPronText = ((TextView)this.mRoot.findViewById(2131034356));
//    this.mNormalPronTextLP = ((RelativeLayout.LayoutParams)this.mPronText.getLayoutParams());
//    this.mDefinitionContainer = ((RelativeLayout)this.mRoot.findViewById(2131034892));
//    this.mWordContentContainer = ((RelativeLayout)this.mRoot.findViewById(2131034887));
//    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
//    localLayoutParams.addRule(3, this.mContentText.getId());
//    localLayoutParams.addRule(5, this.mContentText.getId());
//    this.mNewLinePronTextLP = localLayoutParams;
//    final ViewGroup localViewGroup = (ViewGroup)this.mPronText.getParent();
//    localViewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
//    {
//      public void onGlobalLayout()
//      {
//        localViewGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//        WordView.this.layoutPronText();
//      }
//    });
//    this.mDefinitionText = ((TextView)this.mRoot.findViewById(2131034357));
//    this.mEnDefinitions = ((TextView)this.mRoot.findViewById(2131034895));
//    this.mEnDefinitionsContainer = ((RelativeLayout)this.mRoot.findViewById(2131034894));
//    this.mEndefToggleButton = ((ToggleButton)this.mRoot.findViewById(2131034893));
//    this.mAudioButton = ((ImageButton)this.mRoot.findViewById(2131034891));
//    this.mArrowImgView = ((ImageView)this.mRoot.findViewById(2131034443));
//    this.mEndefToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
//    {
//      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
//      {
//        if (paramAnonymousBoolean)
//        {
//          WordView.this.mEnDefinitionsContainer.setVisibility(0);
//          return;
//        }
//        WordView.this.mEnDefinitionsContainer.setVisibility(8);
//      }
//    });
//    this.mEndefToggleButton.setChecked(false);
//    if (isShowCollins())
//    {
//      this.mArrowImgView.setVisibility(0);
//      this.mEndefToggleButton.setVisibility(8);
//      this.mEnDefinitionsContainer.setVisibility(0);
//    }
//    while (true)
//    {
//      this.mHint = ((TextView)this.mRoot.findViewById(2131034890));
//      this.mContentText.setTypeface(this.mTypeface);
//      this.mPronText.setTypeface(this.mTypefacePhonet);
//      this.mEnDefinitions.setTypeface(this.mTypeface);
//      return;
//      this.mArrowImgView.setVisibility(8);
//      this.mEnDefinitionsContainer.setBackgroundResource(2130837674);
//    }
  }

  public boolean isShowCollins()
  {
    int i = AppletHelper.getInstance().getAppletStatus(this.mActivity, "collins");
    return (i == 2) || (i == 1);
  }

  public void layoutPronText()
  {
//    String str = this.mPronText.getText().toString() + "W";
//    Rect localRect = new Rect();
//    this.mPronText.getPaint().getTextBounds(str, 0, str.length(), localRect);
//    int i = localRect.width() + this.mNormalPronTextLP.leftMargin;
//    ViewGroup localViewGroup = (ViewGroup)this.mPronText.getParent();
//    RelativeLayout.LayoutParams localLayoutParams;
//    if (i + this.mContentText.getWidth() >= localViewGroup.getWidth() - localViewGroup.getPaddingLeft())
//    {
//      localLayoutParams = this.mNewLinePronTextLP;
//      localRect.set(0, 0, 0, 0);
//    }
//    while (true)
//    {
//      if (localLayoutParams != this.mPronText.getLayoutParams())
//      {
//        localViewGroup.removeView(this.mPronText);
//        this.mPronText.setLayoutParams(localLayoutParams);
//        localViewGroup.addView(this.mPronText);
//        localViewGroup.setPadding(localRect.left, localRect.top, localRect.right, localRect.bottom);
//      }
//      return;
//      localLayoutParams = this.mNormalPronTextLP;
//      localRect.set(0, 0, 0, 0);
//    }
  }

  public void release()
  {
  }

  public void render(final Search paramSearch)
  {
//    this.mAudioButton.setVisibility(0);
//    this.mWordContentContainer.setOnClickListener(new View.OnClickListener()
//    {
//      public void onClick(View paramAnonymousView)
//      {
//        WordView.this.mActivity.getSoundPlayer().playSoundByUrl(paramSearch.usAudio, WordView.this.mAudioButton);
//      }
//    });
//    if ((paramSearch.pronunciation != null) && (paramSearch.pronunciation.length() > 0))
//    {
//      this.mPronText.setText(Html.fromHtml('[' + paramSearch.pronunciation + ']'));
//      this.mPronText.setLayoutParams(this.mNewLinePronTextLP);
//      this.mContentText.setText(paramSearch.content);
//      if (paramSearch.definition != null)
//        this.mDefinitionText.setText(paramSearch.definition.trim());
//      if ((paramSearch.definition != null) && ((paramSearch.enDefinition.defn != null) || (paramSearch.enDefinition.pos != null)))
//        break label234;
//      this.mEndefToggleButton.setVisibility(8);
//    }
//    while (true)
//    {
//      if (!isShowCollins())
//        break label279;
//      this.mEndefToggleButton.setVisibility(8);
//      this.mEnDefinitionsContainer.setVisibility(0);
//      this.mDefinitionContainer.setVisibility(8);
//      if (StringUtil.isBlank(paramSearch.enDefinition.defn))
//      {
//        this.mDefinitionContainer.setVisibility(0);
//        this.mEnDefinitionsContainer.setVisibility(8);
//      }
//      return;
//      this.mPronText.setText("");
//      break;
//      label234: this.mEndefToggleButton.setVisibility(0);
//      String str = wordsHighLight(getEnDefinition(paramSearch.enDefinition.pos, paramSearch.enDefinition.defn));
//      this.mEnDefinitions.setText(Html.fromHtml(str));
//    }
//    label279: this.mEnDefinitionsContainer.setClickable(false);
//    this.mDefinitionContainer.setVisibility(0);
  }

  public void render(final VocabularyData paramVocabularyData)
  {
//    if (paramVocabularyData.hasAudio())
//    {
//      this.mAudioButton.setVisibility(0);
//      this.mWordContentContainer.setOnClickListener(new View.OnClickListener()
//      {
//        public void onClick(View paramAnonymousView)
//        {
//          WordView.this.mActivity.getSoundPlayer().playSound(paramVocabularyData.getAudioName(), WordView.this.mAudioButton);
//        }
//      });
//    }
//    if ((paramVocabularyData.getPron() != null) && (paramVocabularyData.getPron().length() > 0))
//    {
//      this.mPronText.setText(Html.fromHtml('[' + paramVocabularyData.getPron() + ']'));
//      this.mContentText.setText(paramVocabularyData.getContent());
//      if (paramVocabularyData.getCnDefinition() != null)
//        this.mDefinitionText.setText(paramVocabularyData.getCnDefinition().trim());
//      if ((paramVocabularyData.getCnDefinition() != null) && ((paramVocabularyData.getEnDefn() != null) || (paramVocabularyData.getEnPos() != null)))
//        break label221;
//      this.mEndefToggleButton.setVisibility(8);
//    }
//    while (true)
//    {
//      if (!isShowCollins())
//        break label253;
//      this.mEndefToggleButton.setVisibility(8);
//      this.mEnDefinitionsContainer.setVisibility(0);
//      this.mDefinitionContainer.setVisibility(8);
//      if (StringUtils.isBlank(paramVocabularyData.getEnDefn()))
//      {
//        this.mDefinitionContainer.setVisibility(0);
//        this.mEnDefinitionsContainer.setVisibility(8);
//      }
//      return;
//      this.mPronText.setText("");
//      break;
//      label221: this.mEndefToggleButton.setVisibility(0);
//      String str = wordsHighLight(getEnDefinitionString(paramVocabularyData));
//      this.mEnDefinitions.setText(Html.fromHtml(str));
//    }
//    label253: this.mEnDefinitionsContainer.setClickable(false);
//    this.mDefinitionContainer.setVisibility(0);
  }

  public String wordsHighLight(String paramString)
  {
    return Pattern.compile("<vocab>(.*?)</vocab>").matcher(paramString).replaceAll("<font color=\"#" + Integer.toHexString(0) + "\">$1</font>");
  }
}