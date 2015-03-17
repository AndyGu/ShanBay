package com.shanbay.widget;

import com.shanbay.words.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

		Drawable drawable = localTypedArray.getDrawable(R.styleable.IndicatorWrapper_drawable);
		if ((drawable != null) && ((drawable instanceof AnimationDrawable))) {
			setIndicatorAnimationDrawable((AnimationDrawable) drawable);
			mIndicatorView.setImageDrawable(drawable);
		}

		mAnimation = AnimationUtils.loadAnimation(context,
				localTypedArray.getResourceId(R.styleable.IndicatorWrapper_animi, R.anim.rotate));
		mIndicatorView.setVisibility(View.GONE);
		addView(mIndicatorView);
		
		mIndicatorFailureView.setVisibility(View.GONE);
		Drawable drawableFailure = getResources().getDrawable(
				R.drawable.icon_loading_failure);
		mIndicatorFailureView.setImageDrawable(drawableFailure);
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

	public void hideIndicator() {
		int i = getChildCount();
		int j = 0;
		while (j < i) {
			View localView = getChildAt(j);
			if ((localView == mIndicatorView) || (localView == mMessageView)
					|| (localView == mIndicatorFailureView)
					|| (localView == mFailureMessageView)) {
				localView.setVisibility(View.VISIBLE);
			}
			j++;
		}
		if (mAnimationDrawable != null){
			mAnimationDrawable.stop();
		}
		mAnimation.cancel();
		mIndicatorView.clearAnimation();
		mIndicatorView.setVisibility(View.GONE);
		mMessageView.setVisibility(View.GONE);
		mIndicatorFailureView.setVisibility(View.GONE);
		mFailureMessageView.setVisibility(View.GONE);
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
    while (j < i)
    {
      View localView = getChildAt(j);
      if ((localView == mIndicatorView) || (localView == mIndicatorFailureView)){
    	  localView.layout(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + localView.getMeasuredWidth(), getPaddingTop() + localView.getMeasuredHeight());
      }
      j++;
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

  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//  {
//	  int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
//      int measureHeigth = MeasureSpec.getSize(heightMeasureSpec);
//      setMeasuredDimension(measureWidth, measureHeigth);
//      for(int i= 0;i<getChildCount();i++){
//          View v = getChildAt(i);
//          Log.v(TAG, "measureWidth is " +v.getMeasuredWidth() + "measureHeight is " +v.getMeasuredHeight());
//          int widthSpec = 0;
//          int heightSpec = 0;
//          LayoutParams params = v.getLayoutParams();
//          if(params.width > 0){
//              widthSpec = MeasureSpec.makeMeasureSpec(params.width, MeasureSpec.EXACTLY);
//          }else if (params.width == -1) {
//              widthSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY);
//          } else if (params.width == -2) {
//              widthSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.AT_MOST);
//          }
//           
//          if(params.height > 0){
//              heightSpec = MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
//          }else if (params.height == -1) {
//              heightSpec = MeasureSpec.makeMeasureSpec(measureHeigth, MeasureSpec.EXACTLY);
//          } else if (params.height == -2) {
//              heightSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.AT_MOST);
//          }
//          v.measure(widthSpec, heightSpec);
//           
//      }
//  }
  {
    int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
    int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
    
    int k = 0;
    int m = 0;
    int widthWithoutPadding = widthSize - getPaddingLeft() - getPaddingRight();
    int heigthWithoutPadding = heightSize - getPaddingTop() - getPaddingBottom();

    Log.e("onMeasure", " widthWithoutPadding="+widthWithoutPadding);
    Log.e("onMeasure", " heigthWithoutPadding="+heigthWithoutPadding);
    int childCount = getChildCount();
    Log.e("onMeasure", " childCount="+childCount);
    if (childCount > 5)
      Log.e("IndicatorWrapper", "onMeasure: More than one child views are not supported.");
    int index = 0;
    while (index < childCount)
    {
    	
      View childView = getChildAt(index);
      if (childView == mIndicatorView){
    	  Log.e("aaaaaaaa", "index="+index);
    	  ViewGroup.LayoutParams localLayoutParams = childView.getLayoutParams();

    	  mIndicatorView.measure(View.MeasureSpec.makeMeasureSpec(widthWithoutPadding, MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(heigthWithoutPadding, MeasureSpec.AT_MOST));
    	    
    	  Log.e("aaaaaaaa", "localLayoutParams.width="+localLayoutParams.width+" localLayoutParams.height="+localLayoutParams.height);
  	  }else{
  		  Log.e("bbbbbbbb", "index="+index);
  		ViewGroup.LayoutParams localLayoutParams = childView.getLayoutParams();
        int childWidthMeasureSpec;
        int childHeightMeasureSpec;

		  Log.e("bbbbbbbb", "localLayoutParams.width="+localLayoutParams.width+" localLayoutParams.height="+localLayoutParams.height);
        if (localLayoutParams.width == LayoutParams.WRAP_CONTENT)
        {
          childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthWithoutPadding, MeasureSpec.AT_MOST);
          childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, MeasureSpec.AT_MOST);
          if (localLayoutParams.height != LayoutParams.WRAP_CONTENT){
              childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heigthWithoutPadding, MeasureSpec.AT_MOST);
          }else{
        	  childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, MeasureSpec.EXACTLY);
          }
          Log.e("childWidthMeasureSpec1", index+" childWidthMeasureSpec="+childWidthMeasureSpec);
          Log.e("childHeightMeasureSpec1", index+" childHeightMeasureSpec="+childHeightMeasureSpec);
          childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
          k = Math.max(k, getPaddingLeft() + getPaddingRight() + childView.getMeasuredWidth());
          m = Math.max(m, getPaddingTop() + getPaddingBottom() + childView.getMeasuredHeight());
          
        }else if(localLayoutParams.width == LayoutParams.MATCH_PARENT){
          childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthWithoutPadding, MeasureSpec.EXACTLY);
          if (localLayoutParams.height == LayoutParams.MATCH_PARENT){
              childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heigthWithoutPadding, MeasureSpec.EXACTLY);
          }else{
              childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, MeasureSpec.EXACTLY);
          }
          childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
          k = Math.max(k, getPaddingLeft() + getPaddingRight() + childView.getMeasuredWidth());
          m = Math.max(m, getPaddingTop() + getPaddingBottom() + childView.getMeasuredHeight());

          Log.e("childWidthMeasureSpec2", index+" childWidthMeasureSpec="+childWidthMeasureSpec);
          Log.e("childHeightMeasureSpec2", index+" childHeightMeasureSpec="+childHeightMeasureSpec);
        }
  	  }
      
      
      
//      if (childView == mIndicatorView){
        index++;
//      }else{
        
//      }
    }
    setMeasuredDimension(resolveSize(k, widthMeasureSpec), resolveSize(m, heightMeasureSpec));
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

	public void showIndicator() {
		int i = getChildCount();
		int j = 0;
		if (j < i) {
			View localView = getChildAt(j);
			if ((localView == mIndicatorView) || (localView == mMessageView))
				;
			while (true) {
				j++;
				break;
				// localView.setVisibility(4);
			}
		}
		mIndicatorView.setVisibility(View.VISIBLE);
		mMessageView.setVisibility(View.VISIBLE);
		if (mAnimationDrawable != null) {
			mAnimationDrawable.start();
		}
		mIndicatorView.startAnimation(mAnimation);
		mShowIndicator = true;
	}

  public static abstract interface onHandleFailureListener
  {
    public abstract void onTryAgain();
  }
}