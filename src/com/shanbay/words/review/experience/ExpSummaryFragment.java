package com.shanbay.words.review.experience;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.shanbay.words.activity.WelcomeActivity;
import com.shanbay.words.model.ExpReview;
import com.shanbay.words.model.VocabularyData;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class ExpSummaryFragment extends ExpReviewFragment
  implements View.OnClickListener
{
  private Button mNextGroup;
  private ViewHolder[] mSummaryItems = new ViewHolder[7];
  private LinearLayout mSummaryList;
  private View rootView;

  private void initSummaryItems()
  {
    for (int i = 0; i < this.mSummaryItems.length; i++)
    {
      this.mSummaryItems[i] = new ViewHolder();
      View localView = this.mActivity.getLayoutInflater().inflate(2130903295, null);
      localView.setOnClickListener(this);
      this.mSummaryItems[i].view = localView;
      this.mSummaryItems[i].content = ((TextView)localView.findViewById(2131034154));
      this.mSummaryItems[i].definition = ((TextView)localView.findViewById(2131034357));
    }
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

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    render(this.rootView);
  }

  public void onClick(View paramView)
  {
//    if (paramView == this.mNextGroup)
//      this.mActivity.nextFragment();
//    int i;
//    do
//    {
//      do
//        return;
//      while (!(paramView.getTag() instanceof Integer));
//      i = ((Integer)paramView.getTag()).intValue();
//    }
//    while (i < 0);
//    this.mActivity.goToExplore(i);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.rootView = paramLayoutInflater.inflate(2130903294, paramViewGroup, false);
    this.mSummaryList = ((LinearLayout)this.rootView.findViewById(2131034811));
    this.mNextGroup = ((Button)this.rootView.findViewById(2131034812));
    this.mNextGroup.setOnClickListener(this);
    return this.rootView;
  }

  public void renderInternal()
  {
//    if (this.mActivity.getStudyQueueController() == null)
//    {
//      Intent localIntent = new Intent(this.mActivity, WelcomeActivity.class);
//      this.mActivity.startActivity(localIntent);
//      this.mActivity.finish();
//      return;
//    }
//    this.mSummaryList.removeAllViews();
//    initSummaryItems();
//    ExpDataTransferHelper localExpDataTransferHelper = new ExpDataTransferHelper();
//    List localList = this.mActivity.getStudyQueueController().getSummaryData();
//    int i = 0;
//    ViewHolder localViewHolder;
//    VocabularyData localVocabularyData;
//    if (i < localList.size())
//    {
//      localViewHolder = this.mSummaryItems[i];
//      localViewHolder.view.setTag(Integer.valueOf(i));
//      localVocabularyData = localExpDataTransferHelper.getVocabularyData((ExpReview)localList.get(i));
//      localViewHolder.content.setText(localVocabularyData.getContent());
//      localViewHolder.content.setTypeface(this.mActivity.getTypefaceBlod());
//      this.mSummaryList.addView(localViewHolder.view);
//      if (StringUtils.isNotBlank(localVocabularyData.getCnDefinition()))
//        localViewHolder.definition.setText(localVocabularyData.getCnDefinition().trim());
//      if ((this.mActivity.isEnableCollins()) && (!StringUtils.isBlank(localVocabularyData.getEnDefn())))
//        break label217;
//    }
//    while (true)
//    {
//      i++;
//      break label73;
//      break;
//      String str = wordsHighLight(getEnDefinition(localVocabularyData.getEnPos(), localVocabularyData.getEnDefn()));
//      localViewHolder.definition.setText(Html.fromHtml(str));
//      localViewHolder.definition.setTypeface(this.mActivity.getTypeface());
//    }
  }

  public String wordsHighLight(String paramString)
  {
    return Pattern.compile("<vocab>(.*?)</vocab>").matcher(paramString).replaceAll("<font color=\"#" + Integer.toHexString(0) + "\">$1</font>");
  }

  final class ViewHolder
  {
    TextView content;
    TextView definition;
    View view;

    ViewHolder()
    {
    }
  }
}