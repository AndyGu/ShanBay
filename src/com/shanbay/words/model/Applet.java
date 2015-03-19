package com.shanbay.words.model;

import com.shanbay.model.Model;
import java.util.List;

public class Applet extends Model
{
  public String applet;
  public String autoResume;
  public List<BuyRecord> buyRecords;
  public String buyTime;
  public String codeName;
  public int daysRemain;
  public String dueDate;
  public int price;
  public int state;
  public String stateName;

  public class BuyRecord extends Model
  {
    public PricePolicy pricePolicy;
    public String time;

    public BuyRecord()
    {
    }

    public class PricePolicy extends Model
    {
      public int appletId;
      public int duration;
      public int iapPrice;
      public int id;
      public boolean isIap;
      public boolean isTrial;
      public int price;

      public PricePolicy()
      {
      }
    }
  }
}