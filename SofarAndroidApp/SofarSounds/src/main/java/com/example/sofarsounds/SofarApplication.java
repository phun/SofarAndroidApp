package com.example.sofarsounds;

import android.app.Application;
import com.parse.Parse;


/**
 * Created by frank on 5/1/14.
 */
public class SofarApplication extends Application {
    @Override
    public void onCreate() {
        Parse.initialize(this, "ddHVzDPcp2MBFW8vu1Vj5ypeFG8m360pfW3gvrAX", "i49U2aOTPgl341tqJfAFe9vcp5NbohPwJSbBZKlg");
    }
}
