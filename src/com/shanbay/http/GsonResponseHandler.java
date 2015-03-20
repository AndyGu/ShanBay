package com.shanbay.http;

import android.os.Message;
import android.util.Log;

import com.google.renamedgson.JsonElement;
import com.google.renamedgson.JsonObject;
import com.google.renamedgson.JsonParseException;
import com.google.renamedgson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shanbay.util.LogUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpResponseException;

public abstract class GsonResponseHandler extends AsyncHttpResponseHandler {
	protected static final int SUCCESS_JSON_MESSAGE = 100;
	protected final String TAG = LogUtils.makeLogTag(getClass());

	protected void dd(String paramString) {
		LogUtils.LOGD(this.TAG, paramString);
	}

	protected void dd(String paramString, Throwable paramThrowable) {
		LogUtils.LOGD(this.TAG, paramString, paramThrowable);
	}

	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] bytes, Throwable throwable) {
		try {
			throwable.printStackTrace();
			if (((throwable instanceof HttpResponseException))
					&& (((HttpResponseException) throwable)
							.getStatusCode() == 401)) {
				onAuthenticationFailure();
				return;
			}
			if (new String(bytes) != null) {
				JsonElement jsonElement = parseResponse(new String(bytes));
				onFailure(new ModelResponseException(throwable), jsonElement);
				return;
			}
		} catch (JsonParseException localJsonParseException) {
			localJsonParseException.printStackTrace();
			onFailure(new ModelResponseException(throwable), (JsonObject) null);
			return;
		}
		onFailure(new ModelResponseException(throwable), (JsonObject) null);
	}

	@Override
	public void onSuccess(int statue, Header[] header, byte[] bytes) {
		sendSuccessMsg(statue, header, bytes);
	}

	public abstract void handleJsonData(int paramInt,
			Header[] paramArrayOfHeader, JsonElement paramJsonElement);

	protected void handleMessage(Message paramMessage) {
		switch (paramMessage.what) {
		case 100:
			Object[] arrayOfObject = (Object[]) paramMessage.obj;
			handleJsonData(((Integer) arrayOfObject[0]).intValue(),
					(Header[]) arrayOfObject[1], (JsonElement) arrayOfObject[2]);
		default:
			super.handleMessage(paramMessage);
			return;
		}
	}

	protected void onAuthenticationFailure() {
	}

	public void onFailure(ModelResponseException paramModelResponseException,
			JsonElement paramJsonElement) {
		paramModelResponseException.printStackTrace();
		LogUtils.LOGW(this.TAG, "onFailure escape");
	}

	public void onStart() {
		dd("onstart");
	}

	public void onSuccess(int paramInt, JsonElement paramJsonElement) {
		LogUtils.LOGW(this.TAG, "onSuccess escape");
	}

	protected JsonElement parseResponse(String paramString) {
		Log.e("parseResponse", "paramString="+paramString);
		String str = paramString.trim();
		return new JsonParser().parse(str);
	}

	protected void sendSuccessMsg(int status,
			Header[] header, byte[] bytes) {
		if (status != 204)
			try {
				JsonElement localJsonElement = parseResponse(new String(bytes));
				Object[] arrayOfObject2 = new Object[3];
				arrayOfObject2[0] = Integer.valueOf(status);
				arrayOfObject2[1] = header;
				arrayOfObject2[2] = localJsonElement;
				sendMessage(obtainMessage(100, arrayOfObject2));
				return;
			} catch (JsonParseException localJsonParseException) {
				sendFailureMessage(status, header, bytes, localJsonParseException);
				return;
			}
		Object[] arrayOfObject1 = new Object[3];
		arrayOfObject1[0] = Integer.valueOf(status);
		arrayOfObject1[1] = header;
		arrayOfObject1[2] = new JsonObject();
		sendMessage(obtainMessage(100, arrayOfObject1));
	}
}