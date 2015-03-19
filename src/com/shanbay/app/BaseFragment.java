package com.shanbay.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shanbay.http.APIClient;
import com.shanbay.http.ModelResponseException;
import com.shanbay.util.LogUtils;

public class BaseFragment<T extends APIClient> extends Fragment
{
  protected final String TAG = LogUtils.makeLogTag(getClass());
  protected T mClient;

  protected void d(String paramString)
  {
    LogUtils.LOGD(this.TAG, paramString);
  }

  protected void d(String paramString, Throwable paramThrowable)
  {
    LogUtils.LOGD(this.TAG, paramString, paramThrowable);
  }

  public void dismissProgressDialog()
  {
    if (isAttached())
      getBaseActivity().dismissProgressDialog();
  }

  public View findViewById(int paramInt)
  {
    if (getView() != null)
      return getView().findViewById(paramInt);
    if (isAttached())
      return getActivity().findViewById(paramInt);
    return null;
  }

  public BaseActivity<T> getBaseActivity()
  {
    return (BaseActivity)getActivity();
  }

  public boolean handleCommonException(ModelResponseException paramModelResponseException)
  {
    if (isAttached())
      return getBaseActivity().handleCommonException(paramModelResponseException);
    return false;
  }

  public boolean isAttached()
  {
    return (getActivity() != null) && (!getActivity().isFinishing());
  }

  public boolean isCommonException(ModelResponseException paramModelResponseException)
  {
    if (isAttached())
      return getBaseActivity().isCommonException(paramModelResponseException);
    return false;
  }

  protected boolean isRenderable()
  {
    return true;
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mClient = getBaseActivity().getClient();
    d(LogUtils.getMethodName());
  }

  public void onAttach(Activity paramActivity)
  {
    if (!(paramActivity instanceof BaseActivity))
      throw new ClassCastException(this + " must attach to BaseActivity");
    super.onAttach(paramActivity);
    d(LogUtils.getMethodName());
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    d(LogUtils.getMethodName());
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    d(LogUtils.getMethodName());
    return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }

  public void onDestroy()
  {
    super.onDestroy();
    d(LogUtils.getMethodName());
  }

  public void onDestroyView()
  {
    super.onDestroyView();
    d(LogUtils.getMethodName());
  }

  public void onDetach()
  {
    super.onDetach();
    this.mClient = null;
    d(LogUtils.getMethodName());
  }

  public void onPause()
  {
    super.onPause();
    d(LogUtils.getMethodName());
  }

  public void onResume()
  {
    super.onResume();
    d(LogUtils.getMethodName());
  }

  public void onStart()
  {
    super.onStart();
    d(LogUtils.getMethodName());
  }

  public void onStop()
  {
    super.onStop();
    d(LogUtils.getMethodName());
  }

  public void onViewStateRestored(Bundle paramBundle)
  {
    super.onViewStateRestored(paramBundle);
    d(LogUtils.getMethodName());
  }

  public final void render()
  {
    render(getView());
  }

  protected final void render(View view)
  {
    if (isRenderable())
    {
      renderInternal();
      if (view != null)
        view.setVisibility(View.VISIBLE);
    }else{
      if (view != null)
        view.setVisibility(View.INVISIBLE);
    }
  }

  protected void renderInternal()
  {
  }

  public void showProgressDialog()
  {
    if (isAttached())
      getBaseActivity().showProgressDialog();
  }

  public void showToast(int paramInt)
  {
    if (isAttached())
      getBaseActivity().showToast(paramInt);
  }

  public void showToast(String paramString)
  {
    if (isAttached())
      getBaseActivity().showToast(paramString);
  }
}