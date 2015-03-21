package com.shanbay.words.view;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.shanbay.util.Misc;
import com.shanbay.words.R;
import com.shanbay.words.WordsSoundPlayer;
import com.shanbay.words.activity.WordsActivity;
import com.shanbay.words.fsm.EndState;
import com.shanbay.words.fsm.FirstFailureState;
import com.shanbay.words.fsm.MatchOriginalFormState;
import com.shanbay.words.fsm.SecondFailureState;
import com.shanbay.words.fsm.State;
import com.shanbay.words.fsm.StateMachine;
import com.shanbay.words.fsm.StateMachine.Signal;
import com.shanbay.words.fsm.ThirdFailureState;
import com.shanbay.words.model.ExampleContent;
import com.shanbay.words.model.ExampleData;
import com.shanbay.words.model.ReviewData;
import com.shanbay.words.model.VocabularyData;
import com.shanbay.words.util.AppletUtil;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;

public class SpellView
{
  public static final float WORD_WIDTH = 1.3F;
  private boolean isFailure;
  private WordsActivity mActivity;
  private Button mBtnGoNext;
  public Button mBtnHint;
  private int mColorCommonText;
  private int mColorRightWord;
  private int mColorWrongWord;
  public EditText mEditView;
  private String mExampleAnswer = "";
  private String mExampleAnswerWithTag = "";
  private View.OnFocusChangeListener mFocusLinstener = new View.OnFocusChangeListener()
  {
    public void onFocusChange(View view, boolean isChanged)
    {
      EditText editText = (EditText)view;
      String str = editText.getText().toString();
      if (isChanged){
        setAnswerColor(editText, checkAnswer(str, true));
      };
    }
  };
  private boolean mIsGoNext;
  private boolean mIsRightToOrigin;
  private String mOriginalAnswer = "";
  private ReviewData mReviewData;
  private ReviewResultObserver mReviewObserver;
  public AbsoluteLayout mSpellContainer;
  private SpellInputFilter mSpellInputFilter;
  protected String mStringSpace;
  private TextView mTvExample;
  private TextView mTvExampleTranslation;
  private TextView mTvTips;
  protected TextView mTvWordDefiniton;
  private Typeface mTypeface;

  public SpellView(Activity mActivity)
  {
    this(mActivity, (ViewGroup)mActivity.getLayoutInflater().inflate(R.layout.layout_test_spell, null));
  }

  public SpellView(Activity activity, ViewGroup viewGroup)
  {
    mActivity = ((WordsActivity)activity);
    mTvWordDefiniton = ((TextView)viewGroup.findViewById(R.id.text_definition));
    mTvExample = ((TextView)viewGroup.findViewById(R.id.text_content));
    mSpellContainer = ((AbsoluteLayout)viewGroup.findViewById(R.id.spell_container));
    mTvTips = ((TextView)viewGroup.findViewById(R.id.text_tips));
    mTvExampleTranslation = ((TextView)viewGroup.findViewById(R.id.text_translation));
    mBtnHint = ((Button)viewGroup.findViewById(R.id.btn_hint));
    mBtnGoNext = ((Button)viewGroup.findViewById(R.id.btn_go_next));
    mBtnHint.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        StateMachine.Signal localSignal = StateMachine.Signal.SPELL_INCORRECT;
        State localState = updateState(localSignal);
        boolean bool = checkAnswer(mEditView.getText().toString(), true);
        setAnswerColor(mEditView, bool);
        renderState(localState);
      }
    });
    
    mBtnGoNext.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (mIsGoNext)
        {
          StateMachine.Signal localSignal = StateMachine.Signal.SPELL_CORRECT;
          State localState = updateState(localSignal);
          renderState(localState);
          
          setAnswerColor(mEditView, true);
          mEditView.setTextColor(mColorRightWord);
          mEditView.setText(mExampleAnswer);
          mBtnGoNext.setText(R.string.go_next);
          mTvTips.setVisibility(View.INVISIBLE);
          
        }
      }
    });
    mColorWrongWord = mActivity.getResources().getColor(R.color.sw_word_wrong);
    mColorRightWord = mActivity.getResources().getColor(R.color.common_green);
    mColorCommonText = mActivity.getResources().getColor(R.color.sw_common_text);
    mTypeface = Typeface.createFromAsset(mActivity.getAssets(), "fonts/NotoSans-Regular.ttf");
    mTvExample.setTypeface(mTypeface);
    mTvWordDefiniton.setTypeface(mTypeface);
  }

  private void appendSpan(SpannableStringBuilder ssb, int startIndex, int startIndex2, int colorId, String str)
  {
    ssb.setSpan(new ForegroundColorSpan(mColorCommonText), startIndex, ssb.length(), Spanned.SPAN_PARAGRAPH);
    ssb.append(str);
    ssb.append(" ");
    ssb.setSpan(new ForegroundColorSpan(colorId), startIndex2, ssb.length(), Spanned.SPAN_PARAGRAPH);
    ssb.setSpan(new RelativeSizeSpan(1.3F), startIndex2, ssb.length(), Spanned.SPAN_PARAGRAPH);
  }

  private void hintWithConsonants()
  {
    String str1 = mReviewData.getVocabulary().getContent();
    SpannableStringBuilder ssb = new SpannableStringBuilder(mActivity.getResources().getString(R.string.consonants_hint));
    ssb.setSpan(new ForegroundColorSpan(mColorCommonText), 0, ssb.length(), Spanned.SPAN_PARAGRAPH);
    ssb.append(" ");
    String str2 = str1.replaceAll("[aeiouyAEIOUY]", "_");
    int i = ssb.length();
    ssb.append(str2);
    ssb.setSpan(new ForegroundColorSpan(this.mColorRightWord), i, ssb.length(), Spanned.SPAN_PARAGRAPH);
    ssb.setSpan(new RelativeSizeSpan(1.3F), i, ssb.length(), Spanned.SPAN_PARAGRAPH);
    this.mTvTips.setText(ssb);
  }

  private void proceedToExplorer()
  {
    if (mReviewObserver != null)
      mReviewObserver.proceedToExplorer();
  }

  private void renderState(State paramState)
  {
    if ((paramState instanceof EndState))
    {
      if (!isFailure){
    	  setReviewResultSucceeded();
          proceedToExplorer();
          return;
      }
    }
    
    
    if(paramState instanceof FirstFailureState){
    	hintWithAudio();
        hintWithConsonants();
        return;
    }
    
    if ((paramState instanceof SecondFailureState))
    {
      hintWithAudio();
      hintWithConsonants();
      setReviewResultFailed();
      return;
    }
    if ((paramState instanceof ThirdFailureState))
    {
      hintWithAudio();
      compareAnswerWithInput();
      setReviewResultFailed();
      return;
    }
    
    if(paramState instanceof MatchOriginalFormState){
    	mBtnHint.setVisibility(View.GONE);
        mBtnGoNext.setVisibility(View.VISIBLE);
        showOriginalFormMessage();
        Misc.forceHideSoftKeyboard(mActivity, mEditView);
        mEditView.setFocusable(false);
        mIsGoNext = false;
    }
  }

  private void setAnswerColor(EditText paramEditText, boolean isCorrect)
  {
    if (isCorrect){
    	paramEditText.setTextColor(mActivity.getResources().getColor(R.color.common_green));
    }else{
    	paramEditText.setTextColor(mActivity.getResources().getColor(R.color.sw_word_wrong));
    }
  }

  private void setExampleAnnotation(ReviewData reviewData)
  {
    String content = reviewData.getVocabulary().getContent().trim();
    List exampleList = reviewData.getExamples().getExampleList();
    ExampleContent exampleContent = null;
    if (exampleList != null)
    {
      int i = reviewData.getExamples().getExampleList().size();
      if (i > 0)
      {
        int j = reviewData.getExamples().getExampleList().size();
        int k = (int)(Math.random() * j);
        exampleContent = (ExampleContent)reviewData.getExamples().getExampleList().get(k);
      }
    }
    String str2;
    if ((exampleContent != null) && (exampleContent.getAnnotation() != null))
    {
      Matcher localMatcher = Pattern.compile("<vocab>(.*?)</vocab>").matcher(exampleContent.getAnnotation());
      if (localMatcher.find())
      {
        mExampleAnswerWithTag = localMatcher.group(View.VISIBLE);
        mExampleAnswer = localMatcher.group(1);
      }
      if (StringUtils.isNotBlank(mExampleAnswer))
      {
        mOriginalAnswer = content;
        str2 = exampleContent.getAnnotation();
        mTvExampleTranslation.setText(exampleContent.getTranslation());
        mTvExampleTranslation.setVisibility(View.VISIBLE);
      }else{
    	mExampleAnswer = content;
        mExampleAnswerWithTag = content;
        mOriginalAnswer = content;
        str2 = content;
        mTvExampleTranslation.setVisibility(View.GONE);
      }
      
      mTvExample.setText(str2);
      final ViewTreeObserver localViewTreeObserver = mTvExample.getViewTreeObserver();
      localViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public void onGlobalLayout()
        {
          localViewTreeObserver.removeGlobalOnLayoutListener(this);
          mStringSpace = getBlank(mTvExample, mExampleAnswer);
          setSpellTextPosition();
        }
      });
    }
  }

  private void setReviewResultFailed()
  {
    if (mReviewObserver != null)
    {
      mReviewObserver.reviewFailed();
      isFailure = true;
    }
  }

  private void setReviewResultSucceeded()
  {
    if (mReviewObserver != null)
      mReviewObserver.reviewSucceeded();
  }

  private State updateState(StateMachine.Signal paramSignal)
  {
    StateMachine localStateMachine = StateMachine.getInstance();
    localStateMachine.handleSignal(paramSignal);
    return localStateMachine.getCurrentState();
  }

  public boolean checkAnswer(String answerStr, boolean mBoolean)
  {
    String str1 = answerStr.toLowerCase(Locale.US);
    String str2 = mExampleAnswer.toLowerCase(Locale.US);
    String str3 = mOriginalAnswer.toLowerCase(Locale.US);
    mIsRightToOrigin = false;
    
    if(mBoolean){
    	if(StringUtils.isNotBlank(str1)){
    		if(StringUtils.equals(str2, str3)){
    			if(StringUtils.equals(str2, str1)){
    				mIsRightToOrigin = true;
        			return true;
        		}else{
        			return false;
        		}
    		}else{
    			if(StringUtils.equals(str2, str1)){
        			return true;
        		}else if(StringUtils.equals(str3, str1)){
        			mIsRightToOrigin = true;
        			return true;
        		}
    		}
    	}else{
    		return false;
    	}
    }
    return false;
  }

  public void clear()
  {
    if (mEditView != null)
    {
      mEditView.setSelected(false);
      mEditView.setFocusable(false);
      mEditView.setText("");
      mEditView.clearFocus();
    }
  }

  public void compareAnswerWithInput()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(mActivity.getResources().getString(R.string.text_right_answer));
    localStringBuffer.append(" ");
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(localStringBuffer.toString());
    appendSpan(localSpannableStringBuilder, 0, localSpannableStringBuilder.length(), this.mColorRightWord, this.mExampleAnswer);
    localSpannableStringBuilder.append("\n");
    int i = localSpannableStringBuilder.length();
    localSpannableStringBuilder.append(mActivity.getResources().getString(R.string.text_your_answer));
    localSpannableStringBuilder.append(" ");
    appendSpan(localSpannableStringBuilder, i, localSpannableStringBuilder.length(), mColorWrongWord, mEditView.getText().toString());
    this.mTvTips.setText(localSpannableStringBuilder);
  }

  public String getBlank(TextView textView, String str)
  {
    if (str == null)
      str = " ";
    float f1 = 0.0F;
    float f2 = textView.getPaint().measureText(str);
    float f3 = textView.getPaint().measureText("_");
    StringBuilder localStringBuilder = new StringBuilder();
    while (f2 > f1)
    {
      localStringBuilder.append("_");
      f1 += f3;
    }
    return localStringBuilder.toString();
  }

  protected boolean hintWithAudio()
  {
    if (mReviewData.getVocabulary().hasAudio())
    {
      mActivity.getSoundPlayer().playSound(mReviewData.getVocabulary().getAudioName(), null);
      return true;
    }
    return false;
  }

  public void renderSpell(ReviewData paramReviewData)
  {
    mReviewData = paramReviewData;
    StateMachine.getInstance().InitState();
    mTvTips.setVisibility(0);
    mTvTips.setText(R.string.tips);
    isFailure = false;
    mIsRightToOrigin = false;
    mStringSpace = "";
    mExampleAnswer = "";
    mExampleAnswerWithTag = "";
    mOriginalAnswer = "";
    mBtnHint.setVisibility(View.VISIBLE);
    mBtnGoNext.setVisibility(View.GONE);
    setExampleAnnotation(paramReviewData);
    showDefinition(paramReviewData);
  }

  public void setInputBox(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    mEditView = new EditText(mActivity);
    int i = mActivity.getResources().getDimensionPixelSize(2131231558);
    mEditView.setLayoutParams(new AbsoluteLayout.LayoutParams((int)(paramFloat3 - paramFloat1), (int)(paramFloat4 - paramFloat2), (int)paramFloat1, (int)(paramFloat2 - i)));
    mEditView.setPadding(0, 0, 0, 0);
    mEditView.setSingleLine();
    mEditView.setTextSize(0, mTvExample.getTextSize());
    mEditView.setTypeface(mTypeface);
    mEditView.setGravity(17);
    mEditView.setBackgroundColor(mActivity.getResources().getColor(R.color.common_transparent));
    mEditView.setInputType(524432);
    mEditView.setImeOptions(6);
    mEditView.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        boolean bool = checkAnswer(paramAnonymousCharSequence.toString(), false);
        setAnswerColor(mEditView, bool);
        mBtnHint.setVisibility(View.VISIBLE);
        mTvTips.setText(mActivity.getResources().getString(R.string.tips));
      }
    });
    mSpellInputFilter = new SpellInputFilter();
    EditText localEditText = mEditView;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = mSpellInputFilter;
    localEditText.setFilters(arrayOfInputFilter);
    mEditView.setOnFocusChangeListener(mFocusLinstener);
    mSpellContainer.addView(mEditView);
    mEditView.requestFocus();
    Misc.forceShowSoftKeyboard(mActivity, mEditView);
  }

  public void setReviewResultObserver(ReviewResultObserver paramReviewResultObserver)
  {
    mReviewObserver = paramReviewResultObserver;
  }

  public void setSpellTextPosition()
  {
    String str1 = mTvExample.getText().toString();
    int i = str1.indexOf(mExampleAnswerWithTag);
    String str2 = str1.replaceFirst(mExampleAnswerWithTag, mStringSpace);
    mTvExample.setText(str2);
    Layout localLayout = mTvExample.getLayout();
    if (localLayout == null)
      return;
    int j = localLayout.getLineForOffset(i);
    Rect localRect = new Rect();
    localLayout.getLineBounds(j, localRect);
    float f1 = localRect.top;
    float f2 = localRect.bottom;
    float f3 = localLayout.getPrimaryHorizontal(i);
    setInputBox(f3, f1, f3 + mTvExample.getPaint().measureText(mStringSpace), f2);
  }

  protected void showDefinition(ReviewData paramReviewData)
  {
    VocabularyData localVocabularyData = paramReviewData.getVocabulary();
    int i = AppletUtil.getAppletStatus(mActivity, "collins");
    if ((i == 2) || (i == 1))
    {
      if (!StringUtil.isBlank(localVocabularyData.getEnDefn()))
      {
        String str1 = localVocabularyData.getEnDefn();
        Matcher localMatcher = Pattern.compile("<vocab>(.*?)</vocab>").matcher(str1);
        String str2 = getBlank(this.mTvWordDefiniton, localVocabularyData.getContent());
        while (localMatcher.find())
          str1 = str1.replaceFirst(localMatcher.group(0), str2);
        mTvWordDefiniton.setText(str1.trim());
        return;
      }
      mTvWordDefiniton.setText(localVocabularyData.getCnDefinition().trim());
      return;
    }
    mTvWordDefiniton.setText(localVocabularyData.getCnDefinition().trim());
  }

  public void showHint()
  {
    mTvTips.setTextColor(this.mColorCommonText);
    mTvTips.setText(R.string.text_hint_second_wrong);
  }

  public void showOriginalFormMessage()
  {
    mTvTips.setTextColor(mColorCommonText);
    mTvTips.setText(R.string.text_original_form);
  }

  public static abstract interface ReviewResultObserver
  {
    public abstract void proceedToExplorer();

    public abstract void reviewFailed();

    public abstract void reviewSucceeded();
  }

  class SpellInputFilter
    implements InputFilter
  {
    SpellInputFilter()
    {
    }

    public CharSequence filter(CharSequence paramCharSequence, int paramInt1, int paramInt2, Spanned paramSpanned, int paramInt3, int paramInt4)
    {
      for (int i = paramInt1; i < paramInt2; i++)
        if (Character.isWhitespace(paramCharSequence.charAt(i)))
        {
          boolean bool = checkAnswer(mEditView.getText().toString(), true);
          setAnswerColor(mEditView, bool);
          State localState;
          if ((bool) && (mIsRightToOrigin))
          {
            StateMachine.Signal localSignal3 = StateMachine.Signal.SPELL_ORIGINALFORM;
            localState = updateState(localSignal3);
          }else{
        	  if (bool)
              {
                StateMachine.Signal localSignal2 = StateMachine.Signal.SPELL_CORRECT;
                localState = SpellView.this.updateState(localSignal2);
              }
              else
              {
                StateMachine.Signal localSignal1 = StateMachine.Signal.SPELL_INCORRECT;
                localState = SpellView.this.updateState(localSignal1);
              }
          }
          renderState(localState);
          return "";
        }
      return null;
    }
  }
}