package com.shanbay.words.review.experience;

import android.view.ViewGroup;
import android.widget.TextView;
import com.shanbay.words.WordsSoundPlayer;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.model.ReviewData;
import com.shanbay.words.model.VocabularyData;
import com.shanbay.words.view.SpellView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;

public class ExpSpellView extends SpellView
{
  private WordsActivity mActivity;
  private String mAudioUrl;
  private boolean mIsEnableCollins;

  public ExpSpellView(WordsActivity paramWordsActivity, ViewGroup paramViewGroup, boolean paramBoolean, String paramString)
  {
    super(paramWordsActivity, paramViewGroup);
    this.mActivity = paramWordsActivity;
    this.mIsEnableCollins = paramBoolean;
    this.mAudioUrl = paramString;
  }

  public ExpSpellView(WordsActivity paramWordsActivity, boolean paramBoolean, String paramString)
  {
    super(paramWordsActivity);
    this.mActivity = paramWordsActivity;
    this.mIsEnableCollins = paramBoolean;
    this.mAudioUrl = paramString;
  }

  protected boolean hintWithAudio()
  {
    if (!StringUtil.isBlank(this.mAudioUrl))
    {
      this.mActivity.getSoundPlayer().playSoundByUrl(this.mAudioUrl, null);
      return true;
    }
    return false;
  }

  public void setAudiUrl(String paramString)
  {
    if (!StringUtil.isBlank(paramString))
      this.mAudioUrl = paramString;
  }

  protected void showDefinition(ReviewData paramReviewData)
  {
    VocabularyData localVocabularyData = paramReviewData.getVocabulary();
    if (this.mIsEnableCollins)
    {
      if (!StringUtil.isBlank(localVocabularyData.getEnDefn()))
      {
        String str1 = localVocabularyData.getEnDefn();
        Matcher localMatcher = Pattern.compile("<vocab>(.*?)</vocab>").matcher(str1);
        String str2 = getBlank(this.mTvWordDefiniton, localVocabularyData.getContent());
        while (localMatcher.find())
          str1 = str1.replaceFirst(localMatcher.group(0), str2);
        this.mTvWordDefiniton.setText(str1.trim());
        return;
      }
      this.mTvWordDefiniton.setText(StringUtils.trimToEmpty(localVocabularyData.getCnDefinition()));
      return;
    }
    this.mTvWordDefiniton.setText(StringUtils.trimToEmpty(localVocabularyData.getCnDefinition()));
  }
}