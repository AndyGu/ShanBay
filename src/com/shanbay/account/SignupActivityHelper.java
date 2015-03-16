package com.shanbay.account;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.shanbay.app.BaseActivityHelper;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.http.APIClient;
import com.shanbay.http.DataResponseHandler;
import com.shanbay.http.ModelResponseException;
import com.shanbay.words.R;
import com.google.renamedgson.JsonElement;

public class SignupActivityHelper<T extends APIClient> extends
		BaseActivityHelper<T> {
	protected TextView mEmailTextView;
	private InitHandler<T> mInitRH;
	protected TextView mPassword2TextView;
	protected TextView mPasswordTextView;
	private UserHandler<T> mUserHandler;
	protected TextView mUsernameTextView;

	public SignupActivityHelper(ShanbayActivity<T> mActivity) {
		this(mActivity, null, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SignupActivityHelper(ShanbayActivity<T> mActivity,
			InitHandler<T> initHandler, UserHandler<T> userHandler) {
		super(mActivity);
		if (initHandler == null)
			initHandler = new InitHandler(mActivity);
		if (userHandler == null)
			userHandler = new UserHandler(mActivity) {
				@Override
				protected void onUserLoaded() {
					SignupActivityHelper.this.mInitRH.init();
				}
			};
		this.mInitRH = initHandler;
		this.mUserHandler = userHandler;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SignupActivityHelper(final ShanbayActivity<T> mActivity,
			UserHandler<T> userHandler) {
		super(mActivity);
		if (userHandler == null)
			userHandler = new UserHandler(mActivity) {
				@Override
				protected void onUserLoaded() {
					mActivity.home();
					mActivity.finish();
				}
			};
		this.mUserHandler = userHandler;
	}

	private void login(String paramString1, String paramString2) {
		this.mClient.login(this.bActivity, paramString1, paramString2,
				new DataResponseHandler() {
					public void onFailure(
							ModelResponseException paramAnonymousModelResponseException,
							JsonElement paramAnonymousJsonElement) {
						Log.e("SignupActivityHelper", "onFailure-"+paramAnonymousJsonElement);
						if (!SignupActivityHelper.this
								.handleCommonException(paramAnonymousModelResponseException)) {
							SignupActivityHelper.this.dismissProgressDialog();
							SignupActivityHelper.this
									.d(paramAnonymousModelResponseException
											.getMessage());
							SignupActivityHelper.this
									.showExceptionDialog(paramAnonymousModelResponseException);
						}
					}

					public void onSuccess(int paramAnonymousInt,
							JsonElement paramAnonymousJsonElement) {
						Log.e("SignupActivityHelper", "onSuccess-"+paramAnonymousJsonElement);
						SignupActivityHelper.this.mUserHandler.user();
					}
				});
	}

	public void onCreate(Bundle paramBundle) {
		this.mUsernameTextView = ((TextView) findViewById(R.id.tv_username));
		this.mEmailTextView = ((TextView) findViewById(R.id.tv_email));
		this.mPasswordTextView = ((TextView) findViewById(R.id.tv_password));
		this.mPassword2TextView = ((TextView) findViewById(R.id.tv_password_repeat));
	}

	public void signup() {
		String userNameStr = this.mUsernameTextView.getText().toString().trim();
		String emailStr = this.mEmailTextView.getText().toString().trim();
		String passwordStr = this.mPasswordTextView.getText().toString();
		String password2Str = this.mPassword2TextView.getText().toString();
		if ("".equals(userNameStr)) {
			this.mUsernameTextView.requestFocus();
			this.mUsernameTextView
					.setError(getString(R.string.msg_empty_username));
			return;
		}
		if ("".equals(emailStr)) {
			this.mEmailTextView.requestFocus();
			this.mEmailTextView.setError(getString(R.string.msg_empty_email));
			return;
		}
		if ("".equals(passwordStr)) {
			this.mPasswordTextView.requestFocus();
			this.mPasswordTextView.setError(getString(R.string.msg_empty_psw));
			return;
		}
		if (!passwordStr.equals(password2Str)) {
			this.mPassword2TextView.requestFocus();
			this.mPassword2TextView
					.setError(getString(R.string.msg_psw_not_match));
			return;
		}
		showProgressDialog();
		
		this.mClient.createAccount(this.bActivity, userNameStr, emailStr, passwordStr, password2Str,
				new DataResponseHandler() {
					public void onFailure(
							ModelResponseException mrException,
							JsonElement jsonElement) {
						Log.e("SignupActivity.createAccount", "onFailure-"+mrException.getMessage());
						if (!SignupActivityHelper.this
								.handleCommonException(mrException)) {
							
							SignupActivityHelper.this.dismissProgressDialog();
							SignupActivityHelper.this
									.showExceptionDialog(mrException);
						}
					}

					public void onSuccess(int statusCode,
							JsonElement jsonElement) {
						Log.e("SignupActivity.createAccount", "onSuccess-"+jsonElement);
						SignupActivityHelper.this.showToast(SignupActivityHelper.this
								.getString(R.string.msg_signup_success));
						SignupActivityHelper.this.login(
								SignupActivityHelper.this.mUsernameTextView
										.getText().toString().trim(),
								SignupActivityHelper.this.mPasswordTextView
										.getText().toString());
					}
				});
	}
}