package com.shanbay.words.constant;

public class ReviewStatus
{
  public static final int INIT = 0;
  public static final int SUCCESS = 1;
  public static final int RECOGNITION = 2;
  public static final int FAILURE = 3;

  public static int nextStatus(int reviewStatus, Result result, int reviewType){
	  if(ReviewType.isTest(reviewType)){
		  
	  }else{
		  
	  }
	  switch(reviewStatus){
	  case 0:
		  return 0;
	  case 1:
		  return 1;
	  case 2:
		  return 2;
	  case 3:
		  return 3;
	  default:
		  return -1;
	  }
	  
	  
	  
	  
	  
	  
	  

//	    if (ReviewType.isTest(reviewType))
//	      if (!Result.isSuccessOrPass(paramResult));
//	    do
//	    {
//	      do
//	      {
//	        do
//	        {
//	          do
//	          {
//	            return 1;
//	            return 3;
//	            switch (paramInt1)
//	            {
//	            default:
//	              return 1;
//	            case 0:
//	            case 2:
//	            case 3:
//	            case 1:
//	            }
//	          }
//	          while (Result.isSuccessOrPass(paramResult));
//	          return 3;
//	        }
//	        while (Result.isSuccessOrPass(paramResult));
//	        return 3;
//	        if (Result.isSuccess(paramResult))
//	          return 2;
//	      }
//	      while (Result.isPass(paramResult));
//	      return 3;
//	    }
//	    while (Result.isSuccessOrPass(paramResult));
//	    return 3;
  }
}