package com.shanbay.words.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
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
    this.numReviewed.prepare(this.whitePaint);
    this.numPassed.prepare(this.whitePaint);
    this.numFailed.prepare(this.whitePaint);
    this.numFresh.prepare(this.blackPaint);
    this.bubbleList.clear();
    insert(this.numReviewed);
    insert(this.numPassed);
    insert(this.numFailed);
    insert(this.numFresh);
    float f1 = getRight() - getLeft();
    float f2 = 0.0F;
    int i = this.bubbleList.size();
    int j = 0;
    if (j < i)
    {
      Bubble localBubble = (Bubble)this.bubbleList.get(j);
      float f3 = f2 / (i - j);
      localBubble.width -= f3;
      f2 -= f3;
      if (localBubble.width < localBubble.minWidth)
        f2 += localBubble.minWidth - localBubble.width;
      for (localBubble.realWidth = localBubble.minWidth; ; localBubble.realWidth = localBubble.width)
      {
        if (localBubble.realWidth > f1)
          localBubble.realWidth = f1;
        f1 -= localBubble.realWidth;
        j++;
        break;
      }
    }
    this.numReviewed.setup(this.whitePaint);
    this.numPassed.setup(this.whitePaint);
    this.numFailed.setup(this.whitePaint);
    this.numFresh.setup(this.blackPaint);
    this.numReviewed.rect.left = getLeft();
    this.numReviewed.rect.right = (this.numPassed.realWidth + this.numReviewed.realWidth);
    Rect localRect1 = this.numReviewed.tRect;
    localRect1.left = ((int)(localRect1.left + this.numPassed.realWidth));
    this.numFresh.rect.left = getLeft();
    this.numFresh.rect.right = getRight();
    Rect localRect2 = this.numFresh.tRect;
    localRect2.left = ((int)(localRect2.left + (this.numReviewed.realWidth + this.numPassed.realWidth)));
    this.numFailed.rect.right = getRight();
    this.numFailed.rect.left = (getRight() - this.numFailed.realWidth);
    Rect localRect3 = this.numFailed.tRect;
    localRect3.left = ((int)(localRect3.left + (getRight() - this.numFailed.realWidth)));
  }

  private void init()
  {
    this.numPassed = new Bubble(getResources().getDrawable(2130837598));
    this.numReviewed = new Bubble(getResources().getDrawable(2130837599));
    this.numFresh = new Bubble(getResources().getDrawable(2130837597));
    this.numFailed = new Bubble(getResources().getDrawable(2130837600));
    this.whitePaint = new Paint(1);
    this.blackPaint = new Paint(1);
    this.whitePaint.setColor(-1);
    this.blackPaint.setColor(-16777216);
    barHeight = getResources().getDimension(2131231482);
  }

  private void insert(Bubble paramBubble)
  {
//    if (paramBubble.value == 0)
//      return;
//    for (int i = 0; i < this.bubbleList.size(); i++)
//    {
//      Bubble localBubble = (Bubble)this.bubbleList.get(i);
//      if (paramBubble.width <= localBubble.width)
//      {
//        this.bubbleList.add(i, paramBubble);
//        return;
//      }
//    }
//    this.bubbleList.add(i, paramBubble);
  }

  private void refresh()
  {
    if (this.mData == null)
      return;
    this.numToday = this.mData.getSum();
    this.numReviewed.value = this.mData.getRecognition();
    this.numPassed.value = this.mData.getSuccess();
    this.numFailed.value = this.mData.getFailure();
    this.numFresh.value = this.mData.getInit();
    if (this.numToday != 0)
    {
      this.unit = (1.0F * (getRight() - getLeft()) / this.numToday);
      calc();
    }
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    this.numFresh.forceDraw(paramCanvas, this.blackPaint);
    this.numReviewed.draw(paramCanvas, this.whitePaint);
    this.numFailed.draw(paramCanvas, this.whitePaint);
    this.numPassed.draw(paramCanvas, this.whitePaint);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
//    int i = View.MeasureSpec.getMode(paramInt1);
//    int j = View.MeasureSpec.getMode(paramInt2);
//    int k = View.MeasureSpec.getSize(paramInt1);
//    int m = View.MeasureSpec.getSize(paramInt2);
//    int n;
//    int i1;
//    if (i == 1073741824)
//    {
//      n = k;
//      if (j != 1073741824)
//        break label61;
//      i1 = m;
//    }
//    while (true)
//    {
//      setMeasuredDimension(n, i1);
//      return;
//      n = 320;
//      break;
//      label61: i1 = (int)(0.5F + barHeight);
//      this.whitePaint.setTextSize(i1 * 2 / 3);
//      this.blackPaint.setTextSize(i1 * 2 / 3);
//    }
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 != paramInt3) || (paramInt2 != paramInt4))
      refresh();
  }

  public void setProgressData(ProgressBarData paramProgressBarData)
  {
    this.mData = paramProgressBarData;
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
//      Object localObject;
//      this.nine = localObject;
//      this.value = 0;
//      this.rect = new RectF();
//      this.tRect = new Rect();
    }

    private void mesureText(Paint paramPaint)
    {
      int i = this.tRect.right - this.tRect.left;
      int j = this.tRect.bottom - this.tRect.top;
      float f = this.rect.bottom - this.rect.top;
      this.tRect.left = ((int)(this.rect.right - i - ProgressBar.this.base() / 2));
      this.tRect.bottom = ((int)(this.rect.bottom - (f - j) / 2.0F));
    }

    void draw(Canvas paramCanvas, Paint paramPaint)
    {
      if (this.value > 0)
      {
        this.nine.setBounds((int)this.rect.left, (int)this.rect.top, (int)this.rect.right, (int)this.rect.bottom);
        this.nine.draw(paramCanvas);
        paramCanvas.drawText(this.s, this.tRect.left, this.tRect.bottom, paramPaint);
      }
    }

    void forceDraw(Canvas paramCanvas, Paint paramPaint)
    {
      this.nine.setBounds((int)this.rect.left, (int)this.rect.top, (int)this.rect.right, (int)this.rect.bottom);
      this.nine.draw(paramCanvas);
      if (this.value > 0)
        paramCanvas.drawText(this.s, this.tRect.left, this.tRect.bottom, paramPaint);
    }

    void prepare(Paint paramPaint)
    {
//      this.s = Integer.toString(this.value);
//      this.rect.top = ProgressBar.this.getTop();
//      this.rect.bottom = ProgressBar.this.getBottom();
//      paramPaint.getTextBounds(this.s, 0, this.s.length(), this.tRect);
//      int i = this.tRect.right - this.tRect.left;
//      int j = 2 * ProgressBar.this.base();
//      if (i > j);
//      while (true)
//      {
//        this.minWidth = i;
//        this.width = (ProgressBar.this.unit * this.value);
//        this.realWidth = 0.0F;
//        return;
//        i = j;
//      }
    }

    void setup(Paint paramPaint)
    {
      this.rect.left = ProgressBar.this.getLeft();
      this.rect.right = (this.rect.left + this.realWidth);
      mesureText(paramPaint);
    }
  }
}