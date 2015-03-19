package com.shanbay.words.activity;

import android.os.Bundle;
import android.view.View;

import com.shanbay.util.Misc;
import com.shanbay.words.R;
import com.shanbay.words.handler.WordInitHandler;
import com.shanbay.words.handler.WordsUserHandler;

public class NetworkFailureActivity extends WordsActivity {

	private WordsUserHandler mUserHandler;
	private WordInitHandler mInitHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_failure);
		// getSupportActionBar().hide();

		mInitHandler = new WordInitHandler(this);
		mUserHandler = new WordsUserHandler(this) {

			@Override
			protected void onUserLoaded() {
				mInitHandler.init();
			}
		};
	}

	public void home(View v) {
		if (!Misc.isInternetAvailable(this)) {
			showToast("网络不可用！");
		} else {
			mUserHandler.user();
		}
	}
}
