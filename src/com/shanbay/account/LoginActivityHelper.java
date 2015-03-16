package com.shanbay.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import com.shanbay.app.BaseActivityHelper;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.http.APIClient;
import com.shanbay.http.DataResponseHandler;
import com.shanbay.http.ModelResponseException;
import com.shanbay.words.R;
import com.google.renamedgson.JsonElement;
import java.io.PrintStream;

public class LoginActivityHelper<T extends APIClient> extends
		BaseActivityHelper<T> {
	private static final String LISP_NAME = "lisp";
	private static final String PASSWORD = "password";
	private static final String REMEMBER = "remember";
	private static final String USERNAME = "username";
	private InitHandler<T> mInitRH;
	protected TextView mPasswordTextView;
	protected CheckBox mRememberMeCheckBox;
	private UserHandler<T> mUserRH;
	protected TextView mUsernameTextView;

	public LoginActivityHelper(ShanbayActivity<T> mShanbayActivity) {
		this(mShanbayActivity, null, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LoginActivityHelper(ShanbayActivity<T> mShanbayActivity,
			InitHandler<T> mInitHandler, UserHandler<T> mUserHandler) {
		super(mShanbayActivity);
		if (mInitHandler == null)
			mInitHandler = new InitHandler(mShanbayActivity);
		if (mUserHandler == null)
			mUserHandler = new UserHandler(mShanbayActivity) {
				protected void onUserLoaded() {
					Log.e("LoginActivityHelper111", "onUserLoaded()");
					LoginActivityHelper.this.mInitRH.init();
				}
			};
		this.mInitRH = mInitHandler;
		this.mUserRH = mUserHandler;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LoginActivityHelper(final ShanbayActivity<T> mShanbayActivity,
			UserHandler<T> userHandler) {
		super(mShanbayActivity);
		if (userHandler == null)
			userHandler = new UserHandler(mShanbayActivity) {
				protected void onUserLoaded() {
					Log.e("LoginActivityHelper222", "onUserLoaded()");
					mShanbayActivity.finish();
					mShanbayActivity.home();
				}
			};
		this.mUserRH = userHandler;
	}

	public void login() {
		String usernameStr = this.mUsernameTextView.getText().toString().trim();
		String passewordStr = this.mPasswordTextView.getText().toString().trim();
		if ("".equals(usernameStr)) {
			this.mUsernameTextView.requestFocus();
			this.mUsernameTextView
					.setError(getString(R.string.msg_empty_username));
			return;
		}
		if ("".equals(passewordStr)) {
			this.mPasswordTextView.requestFocus();
			this.mPasswordTextView.setError(getString(R.string.msg_empty_psw));
			return;
		}
		showProgressDialog();
		Log.e("LoginActivityHelper", "login()");
		this.mClient.login(this.bActivity, usernameStr, passewordStr,
				new DataResponseHandler() {
					public void onFailure(
							ModelResponseException mModelResponseException,
							JsonElement jsonElement) {
						Log.e("LoginActivityHelper",
								"onFailure"
										+ mModelResponseException
												.getMessage());
						if (!LoginActivityHelper.this
								.handleCommonException(mModelResponseException)) {
							LoginActivityHelper.this.dismissProgressDialog();
							LoginActivityHelper.this
									.showExceptionDialog(mModelResponseException);
						}
					}

					public void onSuccess(int paramAnonymousInt,
							JsonElement paramAnonymousJsonElement) {
						Log.e("LoginActivityHelper", "onSuccess"
								+ paramAnonymousJsonElement.toString());
						LoginActivityHelper.this.storeLoginInfoIfNecessary();
						LoginActivityHelper.this.mUserRH.user();
					}
				});
	}

	public void onCreate(Bundle paramBundle) {
		this.mUsernameTextView = ((TextView) findViewById(R.id.tv_username));
		this.mPasswordTextView = ((TextView) findViewById(R.id.tv_password));
		this.mRememberMeCheckBox = ((CheckBox) findViewById(R.id.cb_remember_me));
		SharedPreferences localSharedPreferences = getSharedPreferences("lisp",
				0);
		this.mUsernameTextView.setText(localSharedPreferences.getString(
				"username", ""));
		this.mPasswordTextView.setText(localSharedPreferences.getString(
				"password", ""));
		this.mRememberMeCheckBox.setChecked(localSharedPreferences.getBoolean(
				"remember", false));
	}

	public void reset() {
		String str = getResources().getString(R.string.link_forget);
		try {
			startActivity(new Intent("android.intent.action.VIEW",
					Uri.parse(str)));
			return;
		} catch (Exception localException) {
		}
	}

	public void storeLoginInfoIfNecessary() {
		Log.e("LoginActivity", "storeLoginInfoIfNecessary()-"+this.mRememberMeCheckBox.isChecked());
		if (this.mRememberMeCheckBox.isChecked()) {
			String str1 = this.mUsernameTextView.getText().toString().trim();
			String str2 = this.mPasswordTextView.getText().toString().trim();
			getSharedPreferences("lisp", 0).edit().putString("username", str1)
					.putString("password", str2).putBoolean("remember", true)
					.commit();
			return;
		}
		getSharedPreferences("lisp", 0).edit().clear().commit();
	}
}