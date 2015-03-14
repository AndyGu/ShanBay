package com.shanbay.http;

import com.google.renamedgson.JsonElement;
import com.google.renamedgson.JsonObject;
import org.apache.http.Header;

public class DataResponseHandler extends GsonResponseHandler
{
  protected static JsonElement getData(JsonObject paramJsonObject)
  {
    return paramJsonObject.get("data");
  }

  protected static int getStatusCode(JsonObject paramJsonObject)
  {
    if (paramJsonObject.has("status_code"))
      return paramJsonObject.get("status_code").getAsInt();
    return -1;
  }

  public void handleJsonData(int paramInt, Header[] paramArrayOfHeader, JsonElement paramJsonElement)
  {
    if (paramJsonElement.isJsonObject())
    {
      JsonObject localJsonObject = paramJsonElement.getAsJsonObject();
      int i = getStatusCode(localJsonObject);
      if (i == 0)
      {
        handleSuccessData(localJsonObject);
        return;
      }
      onFailure(new ModelResponseException(i, localJsonObject.get("msg").getAsString()), localJsonObject);
      return;
    }
    onFailure(new ModelResponseException(new Exception("Unexpected type " + paramJsonElement.getClass().getName())), (JsonElement)null);
  }

  protected void handleSuccessData(JsonObject paramJsonObject)
  {
    onSuccess(0, getData(paramJsonObject));
  }
}