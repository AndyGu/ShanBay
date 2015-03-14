package com.shanbay.words.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.shanbay.account.UserCache;
import com.shanbay.http.APIClient;
import com.shanbay.util.DebugUtil;
import com.shanbay.util.Misc;
import com.shanbay.words.R;
import com.shanbay.words.handler.WordsUserHandler;
import com.shanbay.words.util.SessionDateUtils;

public class SplashActivity extends WordsActivity {
	private WordsUserHandler mUserHandler;

	private void checkDebugStatus() {
		Log.e("checkDebugStatus",
				"DebugUtil.isOn(this)=" + DebugUtil.isOn(this));
		if (DebugUtil.isOn(this)) {
			String str = DebugUtil.getDomain(this);
			Log.e("getDomain", "Domain=" + DebugUtil.getDomain(this));
			APIClient.setDomain(str);
			d("open debug, domain: " + str);
		}
	}

	private void checkExternalStorageSize() {
		StatFs localStatFs = new StatFs(Environment
				.getExternalStorageDirectory().getPath());
		Log.e("checkExternalStorageSize", "size=" + localStatFs.getBlockSize()
				* localStatFs.getAvailableBlocks() / 1048576L);
		if (localStatFs.getBlockSize() * localStatFs.getAvailableBlocks()
				/ 1048576L < 10L)
			showToast("手机存储空间不足，可能影响软件使用，请及时清理");
	}

	protected boolean isFirstActivity() {
		return true;
	}

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_splash);
		this.mUserHandler = new WordsUserHandler(this);
		checkDebugStatus();
		checkExternalStorageSize();
		// WebResourceService.startService(this);
		if (SessionDateUtils.isValid(this)) {
			goHome();
			finish();
			return;
		}

		if (Misc.isInternetAvailable(this)) {
			Log.e("Misc.isInternetAvailable(this)",
					Misc.isInternetAvailable(this) + "");
			this.mUserHandler.user();
			return;
		}
		if (UserCache.isUserLogin(this)) {
			goHome();
			finish();
			return;
		}
		startActivity(new Intent(this, WelcomeActivity.class));
		finish();
	}
}