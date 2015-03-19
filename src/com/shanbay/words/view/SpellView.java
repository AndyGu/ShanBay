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
    public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
    {
      EditText localEditText = (EditText)paramAnonymousView;
      SpellView localSpellView = SpellView.this;
      String str = localEditText.getText().toString();
      if (!paramAnonymousBoolean);
      for (boolean bool1 = true; ; bool1 = false)
      {
//        boolean bool2 = localSpellView.checkAnswer(str, bool1);
//        SpellView.this.setAnswerColor(localEditText, bool2);
        return;
      }
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

  public SpellView(Activity paramActivity)
  {
    this(paramActivity, (ViewGroup)paramActivity.getLayoutInflater().inflate(2130903269, null));
  }

  public SpellView(Activity paramActivity, ViewGroup paramViewGroup)
  {
//    this.mActivity = ((WordsActivity)paramActivity);
//    this.mTvWordDefiniton = ((TextView)paramViewGroup.findViewById(2131034765));
//    this.mTvExample = ((TextView)paramViewGroup.findViewById(2131034767));
//    this.mSpellContainer = ((AbsoluteLayout)paramViewGroup.findViewById(2131034256));
//    this.mTvTips = ((TextView)paramViewGroup.findViewById(2131034769));
//    this.mTvExampleTranslation = ((TextView)paramViewGroup.findViewById(2131034768));
//    this.mBtnHint = ((Button)paramViewGroup.findViewById(2131034771));
//    this.mBtnGoNext = ((Button)paramViewGroup.findViewById(2131034772));
//    this.mBtnHint.setOnClickListener(new View.OnClickListener()
//    {
//      public void onClick(View paramAnonymousView)
//      {
//        StateMachine.Signal localSignal = StateMachine.Signal.SPELL_INCORRECT;
//        State localState = SpellView.this.updateState(localSignal);
//        boolean bool = SpellView.this.checkAnswer(SpellView.this.mEditView.getText().toString(), true);
//        SpellView.this.setAnswerColor(SpellView.this.mEditView, bool);
//        SpellView.this.renderState(localState);
//      }
//    });
//    this.mBtnGoNext.setOnClickListener(new View.OnClickListener()
//    {
//      public void onClick(View paramAnonymousView)
//      {
//        SpellView localSpellView;
//        if (SpellView.this.mIsGoNext)
//        {
//          StateMachine.Signal localSignal = StateMachine.Signal.SPELL_CORRECT;
//          State localState = SpellView.this.updateState(localSignal);
//          SpellView.this.renderState(localState);
//          localSpellView = SpellView.this;
//          if (SpellView.this.mIsGoNext)
//            break label119;
//        }
//        label119: for (boolean bool = true; ; bool = false)
//        {
//          SpellView.access$302(localSpellView, bool);
//          return;
//          SpellView.this.mEditView.setTextColor(SpellView.this.mColorRightWord);
//          SpellView.this.mEditView.setText(SpellView.this.mExampleAnswer);
//          SpellView.this.mBtnGoNext.setText(2131362245);
//          SpellView.this.mTvTips.setVisibility(4);
//          break;
//        }
//      }
//    });
//    this.mColorWrongWord = this.mActivity.getResources().getColor(2131165323);
//    this.mColorRightWord = this.mActivity.getResources().getColor(2131165193);
//    this.mColorCommonText = this.mActivity.getResources().getColor(2131165325);
//    this.mTypeface = Typeface.createFromAsset(this.mActivity.getAssets(), "fonts/NotoSans-Regular.ttf");
//    this.mTvExample.setTypeface(this.mTypeface);
//    this.mTvWordDefiniton.setTypeface(this.mTypeface);
  }

  private void appendSpan(SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    paramSpannableStringBuilder.setSpan(new ForegroundColorSpan(this.mColorCommonText), paramInt1, paramSpannableStringBuilder.length(), 33);
    paramSpannableStringBuilder.append(paramString);
    paramSpannableStringBuilder.append(" ");
    paramSpannableStringBuilder.setSpan(new ForegroundColorSpan(paramInt3), paramInt2, paramSpannableStringBuilder.length(), 33);
    paramSpannableStringBuilder.setSpan(new RelativeSizeSpan(1.3F), paramInt2, paramSpannableStringBuilder.length(), 33);
  }

  private void hintWithConsonants()
  {
    String str1 = this.mReviewData.getVocabulary().getContent();
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(this.mActivity.getResources().getString(2131362238));
    localSpannableStringBuilder.setSpan(new ForegroundColorSpan(this.mColorCommonText), 0, localSpannableStringBuilder.length(), 33);
    localSpannableStringBuilder.append(" ");
    String str2 = str1.replaceAll("[aeiouyAEIOUY]", "_");
    int i = localSpannableStringBuilder.length();
    localSpannableStringBuilder.append(str2);
    localSpannableStringBuilder.setSpan(new ForegroundColorSpan(this.mColorRightWord), i, localSpannableStringBuilder.length(), 33);
    localSpannableStringBuilder.setSpan(new RelativeSizeSpan(1.3F), i, localSpannableStringBuilder.length(), 33);
    this.mTvTips.setText(localSpannableStringBuilder);
  }

  private void proceedToExplorer()
  {
    if (this.mReviewObserver != null)
      this.mReviewObserver.proceedToExplorer();
  }

  private void renderState(State paramState)
  {
//    if ((paramState instanceof EndState))
//    {
//      if (!this.isFailure)
//        setReviewResultSucceeded();
//      proceedToExplorer();
//    }
//    do
//    {
//      do
//      {
//        return;
//        if (!(paramState instanceof FirstFailureState))
//          break;
//      }
//      while (hintWithAudio());
//      hintWithConsonants();
//      return;
//      if ((paramState instanceof SecondFailureState))
//      {
//        hintWithAudio();
//        hintWithConsonants();
//        setReviewResultFailed();
//        return;
//      }
//      if ((paramState instanceof ThirdFailureState))
//      {
//        hintWithAudio();
//        compareAnswerWithInput();
//        setReviewResultFailed();
//        return;
//      }
//    }
//    while (!(paramState instanceof MatchOriginalFormState));
//    this.mBtnHint.setVisibility(8);
//    this.mBtnGoNext.setVisibility(0);
//    showOriginalFormMessage();
//    Misc.forceHideSoftKeyboard(this.mActivity, this.mEditView);
//    this.mEditView.setFocusable(false);
//    this.mIsGoNext = false;
  }

  private void setAnswerColor(EditText paramEditText, boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = this.mActivity.getResources().getColor(2131165193); ; i = this.mActivity.getResources().getColor(2131165323))
    {
      paramEditText.setTextColor(i);
      return;
    }
  }

  private void setExampleAnnotation(ReviewData paramReviewData)
  {
//    String str1 = paramReviewData.getVocabulary().getContent().trim();
//    List localList = paramReviewData.getExamples().getExampleList();
//    ExampleContent localExampleContent = null;
//    if (localList != null)
//    {
//      int i = paramReviewData.getExamples().getExampleList().size();
//      localExampleContent = null;
//      if (i > 0)
//      {
//        int j = paramReviewData.getExamples().getExampleList().size();
//        int k = (int)(Math.random() * j);
//        localExampleContent = (ExampleContent)paramReviewData.getExamples().getExampleList().get(k);
//      }
//    }
//    String str2;
//    if ((localExampleContent != null) && (localExampleContent.getAnnotation() != null))
//    {
//      Matcher localMatcher = Pattern.compile("<vocab>(.*?)</vocab>").matcher(localExampleContent.getAnnotation());
//      if (localMatcher.find())
//      {
//        this.mExampleAnswerWithTag = localMatcher.group(0);
//        this.mExampleAnswer = localMatcher.group(1);
//      }
//      if (StringUtils.isNotBlank(this.mExampleAnswer))
//      {
//        this.mOriginalAnswer = str1;
//        str2 = localExampleContent.getAnnotation();
//        this.mTvExampleTranslation.setText(localExampleContent.getTranslation());
//        this.mTvExampleTranslation.setVisibility(0);
//      }
//    }
//    while (true)
//    {
//      this.mTvExample.setText(str2);
//      final ViewTreeObserver localViewTreeObserver = this.mTvExample.getViewTreeObserver();
//      localViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
//      {
//        public void onGlobalLayout()
//        {
//          localViewTreeObserver.removeGlobalOnLayoutListener(this);
//          SpellView.this.mStringSpace = SpellView.this.getBlank(SpellView.this.mTvExample, SpellView.this.mExampleAnswer);
//          SpellView.this.setSpellTextPosition();
//        }
//      });
//      return;
//      this.mExampleAnswer = str1;
//      this.mExampleAnswerWithTag = str1;
//      this.mOriginalAnswer = str1;
//      str2 = str1;
//      this.mTvExampleTranslation.setVisibility(8);
//      continue;
//      this.mExampleAnswer = str1;
//      this.mExampleAnswerWithTag = str1;
//      this.mOriginalAnswer = str1;
//      str2 = str1;
//      this.mTvExampleTranslation.setVisibility(8);
//    }
  }

  private void setReviewResultFailed()
  {
    if (this.mReviewObserver != null)
    {
      this.mReviewObserver.reviewFailed();
      this.isFailure = true;
    }
  }

  private void setReviewResultSucceeded()
  {
    if (this.mReviewObserver != null)
      this.mReviewObserver.reviewSucceeded();
  }

  private State updateState(StateMachine.Signal paramSignal)
  {
    StateMachine localStateMachine = StateMachine.getInstance();
    localStateMachine.handleSignal(paramSignal);
    return localStateMachine.getCurrentState();
  }

//  public boolean checkAnswer(String paramString, boolean paramBoolean)
//  {
//    String str1 = paramString.toLowerCase(Locale.US);
//    String str2 = this.mExampleAnswer.toLowerCase(Locale.US);
//    String str3 = this.mOriginalAnswer.toLowerCase(Locale.US);
//    this.mIsRightToOrigin = false;
//    boolean bool1 = StringUtils.isNotBlank(str1);
//    boolean bool2 = false;
//    if (bool1)
//    {
//      if (!paramBoolean)
//        break label107;
//      bool2 = StringUtils.equals(str2, str1);
//      if (!bool2)
//        if (!paramBoolean)
//          break label118;
//    }
//    label107: label118: for (bool2 = StringUtils.equals(str3, str1); ; bool2 = str3.startsWith(str1))
//    {
//      boolean bool3 = false;
//      if (paramBoolean)
//      {
//        bool3 = false;
//        if (bool2)
//          bool3 = true;
//      }
//      this.mIsRightToOrigin = bool3;
//      return bool2;
//      bool2 = str2.startsWith(str1);
//      break;
//    }
//  }

  public void clear()
  {
    if (this.mEditView != null)
    {
      this.mEditView.setSelected(false);
      this.mEditView.setFocusable(false);
      this.mEditView.setText("");
      this.mEditView.clearFocus();
    }
  }

  public void compareAnswerWithInput()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(this.mActivity.getResources().getString(2131362246));
    localStringBuffer.append(" ");
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(localStringBuffer.toString());
    appendSpan(localSpannableStringBuilder, 0, localSpannableStringBuilder.length(), this.mColorRightWord, this.mExampleAnswer);
    localSpannableStringBuilder.append("\n");
    int i = localSpannableStringBuilder.length();
    localSpannableStringBuilder.append(this.mActivity.getResources().getString(2131362247));
    localSpannableStringBuilder.append(" ");
    appendSpan(localSpannableStringBuilder, i, localSpannableStringBuilder.length(), this.mColorWrongWord, this.mEditView.getText().toString());
    this.mTvTips.setText(localSpannableStringBuilder);
  }

  public String getBlank(TextView paramTextView, String paramString)
  {
    if (paramString == null)
      paramString = " ";
    float f1 = 0.0F;
    float f2 = paramTextView.getPaint().measureText(paramString);
    float f3 = paramTextView.getPaint().measureText("_");
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
    if (this.mReviewData.getVocabulary().hasAudio())
    {
      this.mActivity.getSoundPlayer().playSound(this.mReviewData.getVocabulary().getAudioName(), null);
      return true;
    }
    return false;
  }

  public void renderSpell(ReviewData paramReviewData)
  {
    this.mReviewData = paramReviewData;
    StateMachine.getInstance().InitState();
    this.mTvTips.setVisibility(0);
    this.mTvTips.setText(2131362242);
    this.isFailure = false;
    this.mIsRightToOrigin = false;
    this.mStringSpace = "";
    this.mExampleAnswer = "";
    this.mExampleAnswerWithTag = "";
    this.mOriginalAnswer = "";
    this.mBtnHint.setVisibility(0);
    this.mBtnGoNext.setVisibility(8);
    setExampleAnnotation(paramReviewData);
    showDefinition(paramReviewData);
  }

  public void setInputBox(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.mEditView = new EditText(this.mActivity);
    int i = this.mActivity.getResources().getDimensionPixelSize(2131231558);
    this.mEditView.setLayoutParams(new AbsoluteLayout.LayoutParams((int)(paramFloat3 - paramFloat1), (int)(paramFloat4 - paramFloat2), (int)paramFloat1, (int)(paramFloat2 - i)));
    this.mEditView.setPadding(0, 0, 0, 0);
    this.mEditView.setSingleLine();
    this.mEditView.setTextSize(0, this.mTvExample.getTextSize());
    this.mEditView.setTypeface(this.mTypeface);
    this.mEditView.setGravity(17);
    this.mEditView.setBackgroundColor(this.mActivity.getResources().getColor(2131165212));
    this.mEditView.setInputType(524432);
    this.mEditView.setImeOptions(6);
    this.mEditView.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
//        boolean bool = SpellView.this.checkAnswer(paramAnonymousCharSequence.toString(), false);
//        SpellView.this.setAnswerColor(SpellView.this.mEditView, bool);
//        SpellView.this.mBtnHint.setVisibility(0);
//        SpellView.this.mTvTips.setText(SpellView.this.mActivity.getResources().getString(2131362242));
      }
    });
    this.mSpellInputFilter = new SpellInputFilter();
    EditText localEditText = this.mEditView;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = this.mSpellInputFilter;
    localEditText.setFilters(arrayOfInputFilter);
    this.mEditView.setOnFocusChangeListener(this.mFocusLinstener);
    this.mSpellContainer.addView(this.mEditView);
    this.mEditView.requestFocus();
    Misc.forceShowSoftKeyboard(this.mActivity, this.mEditView);
  }

  public void setReviewResultObserver(ReviewResultObserver paramReviewResultObserver)
  {
    this.mReviewObserver = paramReviewResultObserver;
  }

  public void setSpellTextPosition()
  {
    String str1 = this.mTvExample.getText().toString();
    int i = str1.indexOf(this.mExampleAnswerWithTag);
    String str2 = str1.replaceFirst(this.mExampleAnswerWithTag, this.mStringSpace);
    this.mTvExample.setText(str2);
    Layout localLayout = this.mTvExample.getLayout();
    if (localLayout == null)
      return;
    int j = localLayout.getLineForOffset(i);
    Rect localRect = new Rect();
    localLayout.getLineBounds(j, localRect);
    float f1 = localRect.top;
    float f2 = localRect.bottom;
    float f3 = localLayout.getPrimaryHorizontal(i);
    setInputBox(f3, f1, f3 + this.mTvExample.getPaint().measureText(this.mStringSpace), f2);
  }

  protected void showDefinition(ReviewData paramReviewData)
  {
    VocabularyData localVocabularyData = paramReviewData.getVocabulary();
    int i = AppletUtil.getAppletStatus(this.mActivity, "collins");
    if ((i == 2) || (i == 1))
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
      this.mTvWordDefiniton.setText(localVocabularyData.getCnDefinition().trim());
      return;
    }
    this.mTvWordDefiniton.setText(localVocabularyData.getCnDefinition().trim());
  }

  public void showHint()
  {
    this.mTvTips.setTextColor(this.mColorCommonText);
    this.mTvTips.setText(2131362248);
  }

  public void showOriginalFormMessage()
  {
    this.mTvTips.setTextColor(this.mColorCommonText);
    this.mTvTips.setText(2131362249);
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
//      for (int i = paramInt1; i < paramInt2; i++)
//        if (Character.isWhitespace(paramCharSequence.charAt(i)))
//        {
//          boolean bool = SpellView.this.checkAnswer(SpellView.this.mEditView.getText().toString(), true);
//          SpellView.this.setAnswerColor(SpellView.this.mEditView, bool);
//          State localState;
//          if ((bool) && (SpellView.this.mIsRightToOrigin))
//          {
//            StateMachine.Signal localSignal3 = StateMachine.Signal.SPELL_ORIGINALFORM;
//            localState = SpellView.this.updateState(localSignal3);
//          }
//          while (true)
//          {
//            SpellView.this.renderState(localState);
//            return "";
//            if (bool)
//            {
//              StateMachine.Signal localSignal2 = StateMachine.Signal.SPELL_CORRECT;
//              localState = SpellView.this.updateState(localSignal2);
//            }
//            else
//            {
//              StateMachine.Signal localSignal1 = StateMachine.Signal.SPELL_INCORRECT;
//              localState = SpellView.this.updateState(localSignal1);
//            }
//          }
//        }
      return null;
    }
  }
}