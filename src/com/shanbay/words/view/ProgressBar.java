package com.shanbay.words.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import com.shanbay.words.R;
import com.shanbay.words.model.ProgressBarData;
import java.util.ArrayList;

public class ProgressBar extends View
{
  private static float barHeight;
  Paint blackPaint;
  ArrayList<Bubble> bubbleList = new ArrayList();
  private ProgressBarData mData;
  private Bubble numFailed;
  private Bubble numFresh;
  private Bubble numPassed;
  private Bubble numReviewed;
  private int numToday;
  Rect textRect = new Rect();
  float unit;
  Paint whitePaint;

  public ProgressBar(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public ProgressBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private int base()
  {
    return (int)(8.0F * getContext().getResources().getDisplayMetrics().density);
  }

  private void calc()
  {
    numReviewed.prepare(whitePaint);
    numPassed.prepare(whitePaint);
    numFailed.prepare(whitePaint);
    numFresh.prepare(blackPaint);
    bubbleList.clear();
    insert(numReviewed);
    insert(numPassed);
    insert(numFailed);
    insert(numFresh);
    float width = getRight() - getLeft();
    float pbWidth = 0.0F;
    int i = bubbleList.size();
    int j = 0;
    if (j < i)
    {
      Bubble mBubble = (Bubble)bubbleList.get(j);
      float itemWidth = pbWidth / (i - j);
      mBubble.width -= itemWidth;
      pbWidth -= itemWidth;
      if (mBubble.width < mBubble.minWidth){
          pbWidth += mBubble.minWidth - mBubble.width;
      }else{
    	  mBubble.realWidth = mBubble.minWidth;
      }
        
      for (mBubble.realWidth = mBubble.minWidth; ; mBubble.realWidth = mBubble.width)
      {
    	  if (mBubble.realWidth > width)
    		  mBubble.realWidth = width;
    	  width -= mBubble.realWidth;
          j++;
    	  break;
      }
    }
    numReviewed.setup(whitePaint);
    numPassed.setup(whitePaint);
    numFailed.setup(whitePaint);
    numFresh.setup(blackPaint);
    numReviewed.rect.left = getLeft();
    numReviewed.rect.right = (numPassed.realWidth + numReviewed.realWidth);
    Rect localRect1 = numReviewed.tRect;
    localRect1.left = ((int)(localRect1.left + numPassed.realWidth));
    numFresh.rect.left = getLeft();
    numFresh.rect.right = getRight();
    Rect localRect2 = numFresh.tRect;
    localRect2.left = ((int)(localRect2.left + (numReviewed.realWidth + numPassed.realWidth)));
    numFailed.rect.right = getRight();
    numFailed.rect.left = (getRight() - numFailed.realWidth);
    Rect localRect3 = numFailed.tRect;
    localRect3.left = ((int)(localRect3.left + (getRight() - numFailed.realWidth)));
  }

  private void init()
  {
    numPassed = new Bubble(getResources().getDrawable(R.drawable.bar_green));
    numReviewed = new Bubble(getResources().getDrawable(R.drawable.bar_light_green));
    numFresh = new Bubble(getResources().getDrawable(R.drawable.bar_gray));
    numFailed = new Bubble(getResources().getDrawable(R.drawable.bar_orange));
    whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    whitePaint.setColor(Color.WHITE);
    blackPaint.setColor(Color.BLACK);
    barHeight = getResources().getDimension(R.dimen.progress_bar_height);
  }

  private void insert(Bubble bubble)
  {
    if (bubble.value == 0)
      return;
    for (int i = 0; i < bubbleList.size(); i++)
    {
      Bubble mBubble = (Bubble)bubbleList.get(i);
      if (bubble.width <= mBubble.width)
      {
        bubbleList.add(i, bubble);
        return;
      }
    }
//    bubbleList.add(i, bubble);
  }

  private void refresh()
  {
    if (mData == null)
      return;
    numToday = mData.getSum();
    numReviewed.value = mData.getRecognition();
    numPassed.value = mData.getSuccess();
    numFailed.value = mData.getFailure();
    numFresh.value = mData.getInit();
    if (numToday != 0)
    {
      unit = (1.0F * (getRight() - getLeft()) / numToday);
      calc();
    }
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    numFresh.forceDraw(paramCanvas, blackPaint);
    numReviewed.draw(paramCanvas, whitePaint);
    numFailed.draw(paramCanvas, whitePaint);
    numPassed.draw(paramCanvas, whitePaint);
  }

  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
  {
    int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
    int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
    int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
    int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
    int width;
    int height;
    if (widthMode == MeasureSpec.EXACTLY){
    	width = widthSize;
    }else{
    	width = 320;
    }
    
    if (heightMode == MeasureSpec.EXACTLY){
        height = heightSize;
    }else{
  	  	height = (int)(0.5F + barHeight);
    }
    whitePaint.setTextSize(height * 2 / 3);
    blackPaint.setTextSize(height * 2 / 3);
    
    setMeasuredDimension(width, height);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 != paramInt3) || (paramInt2 != paramInt4))
      refresh();
  }

  public void setProgressData(ProgressBarData pbd)
  {
	  Log.e("setProgressData", "pbd init="+pbd.getInit()+
			  " Recognition="+pbd.getRecognition()+
			  " Success="+pbd.getSuccess()+
			  " sum="+pbd.getSum()+
			  " failure="+pbd.getFailure());
    mData = pbd;
    refresh();
  }

  private class Bubble
  {
    int minWidth;
    Drawable nine;
    float realWidth;
    RectF rect;
    String s;
    Rect tRect;
    int value;
    float width;

    public Bubble(Drawable arg2)
    {
      nine = arg2;
      value = 0;
      rect = new RectF();
      tRect = new Rect();
    }

    private void mesureText(Paint paint)
    {
      int i = tRect.right - tRect.left;
      int j = tRect.bottom - tRect.top;
      float f = rect.bottom - rect.top;
      tRect.left = ((int)(rect.right - i - base() / 2));
      tRect.bottom = ((int)(rect.bottom - (f - j) / 2.0F));
    }

    void draw(Canvas canvas, Paint paint)
    {
      if (this.value > 0)
      {
        nine.setBounds((int)rect.left, (int)rect.top, (int)rect.right, (int)rect.bottom);
        nine.draw(canvas);
        canvas.drawText(s, tRect.left, tRect.bottom, paint);
      }
    }

    void forceDraw(Canvas canvas, Paint paint)
    {
      nine.setBounds((int)rect.left, (int)rect.top, (int)rect.right, (int)rect.bottom);
      nine.draw(canvas);
      if (value > 0)
        canvas.drawText(s, tRect.left, tRect.bottom, paint);
    }

    void prepare(Paint paramPaint)
    {
      s = Integer.toString(value);
      rect.top = getTop();
      rect.bottom = getBottom();
      paramPaint.getTextBounds(s, 0, s.length(), tRect);
      int i = tRect.right - tRect.left;
      int j = 2 * base();
      if (i < j){
          i = j; 
      };
        minWidth = i;
        width = (unit * value);
        realWidth = 0.0F;
    }

    void setup(Paint paramPaint)
    {
      rect.left = getLeft();
      rect.right = (rect.left + realWidth);
      mesureText(paramPaint);
    }
  }
}