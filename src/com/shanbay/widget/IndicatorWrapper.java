package com.shanbay.widget;

import com.shanbay.words.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class IndicatorWrapper extends ViewGroup
  implements View.OnClickListener
{
  private static final String TAG = "IndicatorWrapper";
  int h_screen;
  private Animation mAnimation;
  private AnimationDrawable mAnimationDrawable;
  private onHandleFailureListener mFailureListener;
  private TextView mFailureMessageView;
  private ImageView mIndicatorFailureView;
  private final ImageView mIndicatorView;
  private final TextView mMessageView;
  private boolean mShowIndicator;
  private int w_screen;

  public IndicatorWrapper(Context paramContext)
  {
    this(paramContext, null, 0);
  }

  public IndicatorWrapper(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public IndicatorWrapper(Context context, AttributeSet attributeSet, int mInt)
 {
		super(context, attributeSet, mInt);

		int color = getResources().getColor(R.color.common_text);
		int dimension = (int) getResources().getDimension(R.dimen.textsize13);
		mIndicatorView = new ImageView(context);
		mIndicatorFailureView = new ImageView(context);
		DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
		w_screen = localDisplayMetrics.widthPixels;
		h_screen = localDisplayMetrics.heightPixels;
		TypedArray localTypedArray = context.obtainStyledAttributes(
				attributeSet, R.styleable.IndicatorWrapper);

		Drawable drawable = localTypedArray.getDrawable(1);
		if ((drawable != null) && ((drawable instanceof AnimationDrawable))) {
			setIndicatorAnimationDrawable((AnimationDrawable) drawable);
			mIndicatorView.setImageDrawable(drawable);
		}

		mAnimation = AnimationUtils.loadAnimation(context,
				localTypedArray.getResourceId(0, R.anim.rotate));
		localTypedArray.recycle();
		mIndicatorView.setVisibility(View.GONE);
		addView(mIndicatorView);
		mIndicatorFailureView.setVisibility(View.GONE);
		Drawable localDrawable2 = getResources().getDrawable(
				R.drawable.icon_loading_failure);
		mIndicatorFailureView.setImageDrawable(localDrawable2);
		mIndicatorFailureView.setOnClickListener(this);
		addView(mIndicatorFailureView);
		mFailureMessageView = new TextView(context, attributeSet, mInt);
		mFailureMessageView.setVisibility(View.GONE);
		mFailureMessageView.setText("加载失败，点击重新加载");
		mFailureMessageView.setTextColor(color);
		mFailureMessageView.setTextSize(0, dimension);
		addView(mFailureMessageView);

		mMessageView = new TextView(context, attributeSet, mInt);
		mMessageView.setVisibility(View.GONE);
		mMessageView.setTextColor(color);
		mMessageView.setTextSize(0, dimension);
		addView(mMessageView);

		localTypedArray.recycle();
	}

  public AnimationDrawable getAnimationDrawable()
  {
    if (mAnimationDrawable == null)
      setIndicatorAnimation(R.drawable.indicator);
    return mAnimationDrawable;
  }

  public CharSequence getText()
  {
    return mMessageView.getText();
  }

  public void hideIndicator()
  {
    int i = getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView = getChildAt(j);
      if ((localView == mIndicatorView) || (localView == mMessageView) || (localView == mIndicatorFailureView) || (localView == mFailureMessageView));
      while (true)
      {
        j++;
        break;
//        localView.setVisibility(0);
      }
    }
    if (mAnimationDrawable != null)
      mAnimationDrawable.stop();
    mAnimation.cancel();
    mIndicatorView.clearAnimation();
    mIndicatorView.setVisibility(8);
    mMessageView.setVisibility(8);
    mIndicatorFailureView.setVisibility(8);
    mFailureMessageView.setVisibility(8);
    mShowIndicator = false;
  }

  public boolean isShowIndicator()
  {
    return mShowIndicator;
  }

  public void onClick(View paramView)
  {
    if (paramView == mIndicatorFailureView)
    {
      showIndicator();
      if (mFailureListener != null)
        mFailureListener.onTryAgain();
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView = getChildAt(j);
      if ((localView == mIndicatorView) || (localView == mIndicatorFailureView));
      while (true)
      {
        j++;
        break;
//        localView.layout(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + localView.getMeasuredWidth(), getPaddingTop() + localView.getMeasuredHeight());
      }
    }
    int k = Math.min(w_screen, getMeasuredWidth());
    int m = (k - mIndicatorView.getMeasuredWidth()) / 2;
    int n = (getHeight() - mIndicatorView.getMeasuredHeight()) / 2;
    mIndicatorView.layout(m, n, m + mIndicatorView.getMeasuredWidth(), n + mIndicatorView.getMeasuredHeight());
    int i1 = (getMeasuredWidth() - mMessageView.getMeasuredWidth()) / 2;
    int i2 = n + mIndicatorView.getMeasuredHeight();
    mMessageView.layout(i1, i2, i1 + mMessageView.getMeasuredWidth(), i2 + mMessageView.getMeasuredHeight());
    int i3 = (k - mIndicatorFailureView.getMeasuredWidth()) / 2;
    int i4 = 5 * (getHeight() - mIndicatorFailureView.getMeasuredHeight()) / 12;
    mIndicatorFailureView.layout(i3, i4, i3 + mIndicatorFailureView.getMeasuredWidth(), i4 + mIndicatorFailureView.getMeasuredHeight());
    int i5 = (int)getResources().getDimension(R.dimen.margin3);
    int i6 = (getMeasuredWidth() - mFailureMessageView.getMeasuredWidth()) / 2;
    int i7 = i4 + mIndicatorFailureView.getMeasuredHeight();
    mFailureMessageView.layout(i6, i7 + i5, i6 + mFailureMessageView.getMeasuredWidth(), i5 + (i7 + mFailureMessageView.getMeasuredHeight()));
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = 0;
    int m = 0;
    int n = i - getPaddingLeft() - getPaddingRight();
    int i1 = j - getPaddingTop() - getPaddingBottom();
    int i2 = getChildCount();
    if (i2 > 5)
      Log.e("IndicatorWrapper", "onMeasure: More than one child views are not supported.");
    int i3 = 0;
    while (i3 < i2)
    {
      View localView = getChildAt(i3);
      if (localView == mIndicatorView)
      {
        i3++;
      }
      else
      {
        ViewGroup.LayoutParams localLayoutParams = localView.getLayoutParams();
        int i4;
        int i5;
        if (localLayoutParams.width == -2)
        {
          i4 = View.MeasureSpec.makeMeasureSpec(n, -2147483648);
          if (localLayoutParams.height != -2)
            break ;
          i5 = View.MeasureSpec.makeMeasureSpec(i1, -2147483648);
        }
        while (true)
        {
//          localView.measure(i4, i5);
          k = Math.max(k, getPaddingLeft() + getPaddingRight() + localView.getMeasuredWidth());
          m = Math.max(m, getPaddingTop() + getPaddingBottom() + localView.getMeasuredHeight());
          break;
//          if (localLayoutParams.width == -1)
//          {
//            i4 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
//            break label125;
//          }
//          i4 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824);
//          break ;
//          label239: if (localLayoutParams.height == -1)
//            i5 = View.MeasureSpec.makeMeasureSpec(i1, 1073741824);
//          else
//            i5 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824);
        }
      }
    }
    mIndicatorView.measure(View.MeasureSpec.makeMeasureSpec(n, -2147483648), View.MeasureSpec.makeMeasureSpec(i1, -2147483648));
    mIndicatorView.measure(View.MeasureSpec.makeMeasureSpec(n, -2147483648), View.MeasureSpec.makeMeasureSpec(i1, -2147483648));
    setMeasuredDimension(resolveSize(k, paramInt1), resolveSize(m, paramInt2));
  }

  public void setIndicatorAnimation(int paramInt)
  {
    setIndicatorAnimationDrawable((AnimationDrawable)getResources().getDrawable(paramInt));
  }

  public void setIndicatorAnimationDrawable(AnimationDrawable mAnimationDrawable)
  {
    if (mAnimationDrawable != mAnimationDrawable)
    {
      mAnimationDrawable = mAnimationDrawable;
      mIndicatorView.setImageDrawable(mAnimationDrawable);
    }
  }

  public void setOnHandleFailureListener(onHandleFailureListener paramonHandleFailureListener)
  {
    mFailureListener = paramonHandleFailureListener;
  }

  public void setText(CharSequence paramCharSequence)
  {
    mMessageView.setText(paramCharSequence);
  }

  public void showFailureIndicator()
  {
    if (mAnimationDrawable != null)
      mAnimationDrawable.stop();
    mAnimation.cancel();
    mIndicatorView.clearAnimation();
    mIndicatorView.setVisibility(8);
    mMessageView.setVisibility(8);
    int i = getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView = getChildAt(j);
      if ((localView == mIndicatorFailureView) || (localView == mIndicatorFailureView));
      while (true)
      {
        j++;
        break;
//        localView.setVisibility(4);
      }
    }
    mIndicatorFailureView.setVisibility(0);
    mFailureMessageView.setVisibility(0);
    mShowIndicator = true;
  }

  public void showIndicator()
  {
    int i = getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView = getChildAt(j);
      if ((localView == mIndicatorView) || (localView == mMessageView));
      while (true)
      {
        j++;
        break;
//        localView.setVisibility(4);
      }
    }
    mIndicatorView.setVisibility(0);
    mMessageView.setVisibility(0);
    if (mAnimationDrawable != null)
      mAnimationDrawable.start();
    while (true)
    {
      mShowIndicator = true;
      return;
//      mIndicatorView.startAnimation(mAnimation);
    }
  }

  public static abstract interface onHandleFailureListener
  {
    public abstract void onTryAgain();
  }
}