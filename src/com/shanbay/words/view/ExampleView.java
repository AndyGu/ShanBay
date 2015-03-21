package com.shanbay.words.view;

import android.app.Activity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shanbay.words.R;
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
    mRoot = paramViewGroup;
    mExampleEn = ((TextView)paramViewGroup.findViewById(R.id.tv_example_en));
    mExampleCn = ((TextView)paramViewGroup.findViewById(R.id.tv_example_cn));
  }

  private void setRootVisibility(boolean isVisible)
  {
	 ViewGroup localViewGroup;
    if (mRoot.getParent() != null){
    	localViewGroup = (ViewGroup)mRoot.getParent();
    	if(isVisible){
    		localViewGroup.setVisibility(View.VISIBLE);
    	}else{
    		localViewGroup.setVisibility(View.GONE);
    	}
    }else{
    	localViewGroup = mRoot;
    	if(isVisible){
    		localViewGroup.setVisibility(View.VISIBLE);
    	}else{
    		localViewGroup.setVisibility(View.GONE);
    	}
    }
  }

  private Spanned wrapExample(boolean paramBoolean)
  {
    if (mExample == null)
      return Html.fromHtml("______");
    String str1 = mExample.getAnnotation();
    Matcher localMatcher = Pattern.compile("<vocab>(.*?)</vocab>").matcher(str1);
    String str2;
    if (paramBoolean){
        str2 = "<font color=\"#" + Integer.toHexString(0) + "\">$1</font>";
    }else{
    	str2 = "______";
    }
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

  public void render(ExampleContent exampleContent, boolean mBoolean1, boolean mBoolean2)
  {
    setRootVisibility(true);
    mExample = exampleContent;
    mExampleEn.setText(wrapExample(mBoolean1));
    if (mBoolean2)
    {
      mExampleCn.setText(exampleContent.getTranslation());
      mExampleCn.setVisibility(View.VISIBLE);
      return;
    }
    mExampleCn.setVisibility(View.GONE);
  }

  public void setVisibility(boolean isENVisible, boolean isCNVisible)
  {
    if (isENVisible){
    	mExampleEn.setVisibility(View.VISIBLE);
    }else{
    	mExampleEn.setVisibility(View.GONE);
    }
    
	if (isCNVisible){
  	  	mExampleCn.setVisibility(View.VISIBLE);
    }else{
    	mExampleCn.setVisibility(View.GONE);
    }
    
    if (!isENVisible && !isCNVisible)
    {
    	setRootVisibility(false);
    }else{
    	setRootVisibility(true);
    }
  }
}