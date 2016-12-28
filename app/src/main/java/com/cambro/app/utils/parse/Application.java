package com.cambro.app.utils.parse;

import com.cambro.app.interfce.Common;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Parse.enableLocalDatastore(this);
    Parse.initialize(this, "C9ezsbvaYBirvfqamTI7lV0tRqea2YsvdRhw3UwI", "DIb0NBwDCyKIdbYoYqI2BRnslPJRLkIxvpvy96OX");
    ParseFacebookUtils.initialize(this);
    ParseTwitterUtils.initialize(Common.CONSUMER_KEY,Common.CONSUMER_SECRET_KEY);
  }
}
//[Parse setApplicationId:@"C9ezsbvaYBirvfqamTI7lV0tRqea2YsvdRhw3UwI" clientKey:@"DIb0NBwDCyKIdbYoYqI2BRnslPJRLkIxvpvy96OX"];