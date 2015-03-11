package com.shanbay.http;

import android.os.Message;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.io.File;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

public class SoundCacheResponseHandler extends AsyncHttpResponseHandler {
	File cache;
	private String mAllowedContentType = "audio/";
	AsyncHttpResponseHandler responseHandler;

	public SoundCacheResponseHandler(File paramFile,
			AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
		this.cache = paramFile;
		this.responseHandler = paramAsyncHttpResponseHandler;
	}

	public void onFailure(Throwable paramThrowable, String paramString) {
		paramThrowable.printStackTrace();
		this.responseHandler.onFailure(i, aheader, abyte0, throwable);
	}

	@Override
	public void onSuccess(int i, Header[] aheader, byte[] abyte0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(int i, Header[] aheader, byte[] abyte0,
			Throwable throwable) {
		// TODO Auto-generated method stub

	}
}