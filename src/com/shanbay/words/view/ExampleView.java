package com.shanbay.words.view;

import android.app.Activity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.shanbay.words.model.ExampleContent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExampleView
{
  private ExampleContent mExample;
  private TextView mExampleCn;
  private TextView mExampleEn;
  private ViewGroup mRoot;

  public ExampleView(Activity paramActivity, ViewGroup paramViewGroup)
  {
    this.mRoot = paramViewGroup;
    this.mExampleEn = ((TextView)paramViewGroup.findViewById(2131034440));
    this.mExampleCn = ((TextView)paramViewGroup.findViewById(2131034441));
  }

  private void setRootVisibility(boolean paramBoolean)
  {
//    if (this.mRoot.getParent() != null);
//    for (ViewGroup localViewGroup = (ViewGroup)this.mRoot.getParent(); paramBoolean; localViewGroup = this.mRoot)
//    {
//      localViewGroup.setVisibility(0);
//      return;
//    }
//    localViewGroup.setVisibility(8);
  }

  private Spanned wrapExample(boolean paramBoolean)
  {
    if (this.mExample == null)
      return Html.fromHtml("______");
    String str1 = this.mExample.getAnnotation();
    Matcher localMatcher = Pattern.compile("<vocab>(.*?)</vocab>").matcher(str1);
    if (paramBoolean);
    for (String str2 = "<font color=\"#" + Integer.toHexString(0) + "\">$1</font>"; ; str2 = "______")
      return Html.fromHtml(localMatcher.replaceAll(str2));
  }

  public ExampleContent getExample()
  {
    return this.mExample;
  }

  public View getView()
  {
    return this.mRoot;
  }

  public void render(ExampleContent paramExampleContent, boolean paramBoolean1, boolean paramBoolean2)
  {
    setRootVisibility(true);
    this.mExample = paramExampleContent;
    this.mExampleEn.setText(wrapExample(paramBoolean1));
    if (paramBoolean2)
    {
      this.mExampleCn.setText(paramExampleContent.getTranslation());
      this.mExampleCn.setVisibility(0);
      return;
    }
    this.mExampleCn.setVisibility(8);
  }

  public void setVisibility(boolean paramBoolean1, boolean paramBoolean2)
  {
//    if (paramBoolean1)
//    {
//      this.mExampleEn.setVisibility(0);
//      if (!paramBoolean2)
//        break label54;
//      this.mExampleCn.setVisibility(0);
//    }
//    while (true)
//    {
//      boolean bool;
//      if (!paramBoolean1)
//      {
//        bool = false;
//        if (!paramBoolean2);
//      }
//      else
//      {
//        bool = true;
//      }
//      setRootVisibility(bool);
//      return;
//      this.mExampleEn.setVisibility(8);
//      break;
//      label54: this.mExampleCn.setVisibility(8);
//    }
  }
}