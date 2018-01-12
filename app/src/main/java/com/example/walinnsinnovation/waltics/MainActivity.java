package com.example.walinnsinnovation.waltics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linear_g_plus,linear_fb;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        linear_g_plus = (LinearLayout)findViewById(R.id.linear_g_plus);
        linear_fb = (LinearLayout)findViewById(R.id.linear_fb);
        linear_fb.setOnClickListener(this);
        linear_g_plus.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback < LoginResult > () {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Handle success
                        System.out.println("Facebook login :" + loginResult.getAccessToken().getToken());
                        if(loginResult.getAccessToken().getToken()!=null){
                            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                }
        );
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linear_fb:
                LoginManager.getInstance().logInWithReadPermissions(
                        this,
                        Arrays.asList("user_photos", "email", "user_birthday", "public_profile")
                );
                break;
            case R.id.linear_g_plus:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
