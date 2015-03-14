package com.shanbay.widget;

import com.shanbay.words.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShanbayProgressDialog extends Dialog
{
  private Context context;
  private TextView tvMessage;

  public ShanbayProgressDialog(Context paramContext)
  {
    super(paramContext, R.style.ShanbayProgressDialog);
    this.context = paramContext;
    setContentView(R.layout.shanbay_progress_dialog);
    getWindow().getAttributes().gravity = 17;
    this.tvMessage = ((TextView)findViewById(R.id.msg));
  }

  public ShanbayProgressDialog(Context paramContext, int paramInt)
  {
    super(paramContext, R.style.ShanbayProgressDialog);
    this.context = paramContext;
  }

  public void setContentMessage(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0))
      return;
    ((RelativeLayout.LayoutParams)this.tvMessage.getLayoutParams()).leftMargin = ((int)this.context.getResources().getDimension(R.dimen.progress_margin));
    this.tvMessage.setText(paramString);
  }
}