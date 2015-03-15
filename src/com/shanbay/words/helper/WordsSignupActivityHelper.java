package com.shanbay.words.helper;

import android.os.Bundle;
import android.view.View;
import com.shanbay.account.SignupActivityHelper;
import com.shanbay.account.UserHandler;
import com.shanbay.app.ShanbayActivity;
import com.shanbay.http.APIClient;
import com.shanbay.words.R;

public class WordsSignupActivityHelper<T extends APIClient> extends
		SignupActivityHelper<T> implements View.OnFocusChangeListener {
	public WordsSignupActivityHelper(ShanbayActivity<T> mShanbayActivity,
			UserHandler<T> mUserHandler) {
		super(mShanbayActivity, mUserHandler);
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		this.mUsernameTextView.setOnFocusChangeListener(this);
		this.mEmailTextView.setOnFocusChangeListener(this);
		this.mPasswordTextView.setOnFocusChangeListener(this);
		this.mPassword2TextView.setOnFocusChangeListener(this);
	}

	public void onFocusChange(View paramView, boolean paramBoolean) {
		if (R.id.tv_username == paramView.getId()) {
			this.mUsernameTextView.setHint("");
			this.mEmailTextView.setHint("Email");
			this.mPasswordTextView.setHint("密 码");
			this.mPassword2TextView.setHint("确认密码");
		}

		if (R.id.tv_email == paramView.getId()) {
			this.mUsernameTextView.setHint("用户名");
			this.mEmailTextView.setHint("");
			this.mPasswordTextView.setHint("密 码");
			this.mPassword2TextView.setHint("确认密码");
		}
		if (R.id.tv_password == paramView.getId()) {
			this.mUsernameTextView.setHint("用户名");
			this.mEmailTextView.setHint("Email");
			this.mPasswordTextView.setHint("");
			this.mPassword2TextView.setHint("确认密码");
		}

		if (R.id.tv_password_repeat == paramView.getId()) {
			this.mUsernameTextView.setHint("用户名");
			this.mEmailTextView.setHint("Email");
			this.mPasswordTextView.setHint("密 码");
			this.mPassword2TextView.setHint("");
		}
	}
}