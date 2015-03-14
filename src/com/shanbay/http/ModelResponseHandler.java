package com.shanbay.http;

import android.util.Log;

import com.google.renamedgson.JsonElement;
import com.google.renamedgson.JsonObject;
import com.google.renamedgson.JsonSyntaxException;
import com.shanbay.model.Model;
import java.util.List;

public class ModelResponseHandler<T extends Model> extends DataResponseHandler
{
  private Class<T> tClass;

  public ModelResponseHandler()
  {
    this.tClass = null;
  }

  public ModelResponseHandler(Class<T> paramClass)
  {
    this.tClass = paramClass;
  }

  @Override
  protected void handleSuccessData(JsonObject jsonObject)
  {
	  Log.e("handleSuccessData", "JsonObject="+jsonObject);
    JsonElement jsonElement = getData(jsonObject);
    if ((jsonElement == null) || (jsonElement.isJsonNull()) || ((jsonElement.isJsonObject()) && (((JsonObject)jsonElement).entrySet().size() == 0)))
    {
      onSuccess(0, (Model)null);
      return;
    }
    try
    {
      if (jsonElement.isJsonObject())
      {
        onSuccess(0, (Model)Model.create(jsonElement, this.tClass));
        return;
      }
      
      if (jsonElement.isJsonArray())
      {
        onSuccess(0, Model.createList(jsonElement, this.tClass));
        return;
      }
    }
    catch (JsonSyntaxException localJsonSyntaxException)
    {
      onFailure(new ModelResponseException(-65536, "fail map " + jsonElement + " to " + this.tClass.getCanonicalName()), (JsonElement)null);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      onFailure(new ModelResponseException(-65536, "fail map " + jsonElement + " to " + this.tClass.getCanonicalName()), (JsonElement)null);
      return;
    }
    onFailure(new ModelResponseException(268369920, "Unexpected type " + jsonElement.getClass().getName()), jsonElement);
  }

  public void onSuccess(int paramInt, Model model)
  {
	  Log.e("ModelResponseHandler.onSuccess()", "onSuccess(int paramInt, Model model)");
  }

  public void onSuccess(int paramInt, List<T> paramList)
  {
	  Log.e("ModelResponseHandler.onSuccess()", "onSuccess(int paramInt, List<T> paramList)");
  }
}