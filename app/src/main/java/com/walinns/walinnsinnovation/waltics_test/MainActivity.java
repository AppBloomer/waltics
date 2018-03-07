package com.walinns.walinnsinnovation.waltics_test;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
 import com.walinns.walinnsinnovation.waltics_test.DataBase.SharedCommon;
import com.facebook.BuildConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    LinearLayout linear_g_plus,linear_fb;
    CallbackManager callbackManager;
    ProgressBar progress;
    SharedCommon sharedCommon;
    CleverTapAPI cleverTap;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int SIGN_IN = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.walinns.walinnsinnovation.waltics_test.R.layout.activity_splashscreen);
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        try {
            cleverTap = CleverTapAPI.getInstance(getApplicationContext());

        } catch (CleverTapMetaDataNotFoundException e) {
            // thrown if you haven't specified your CleverTap Account ID or Token in your AndroidManifest.xml
        } catch (CleverTapPermissionsNotSatisfied e) {
            // thrown if you haven’t requested the required permissions in your AndroidManifest.xml
        }
        linear_g_plus = (LinearLayout)findViewById(com.walinns.walinnsinnovation.waltics_test.R.id.linear_g_plus);
        linear_fb = (LinearLayout)findViewById(com.walinns.walinnsinnovation.waltics_test.R.id.linear_fb);
        linear_fb.setOnClickListener(this);
        linear_g_plus.setOnClickListener(this);
        progress = (ProgressBar)findViewById(com.walinns.walinnsinnovation.waltics_test.R.id.progress);
        sharedCommon = new SharedCommon(MainActivity.this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback < LoginResult > () {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Handle success
                        System.out.println("Facebook login :" + loginResult.getAccessToken().getToken());
                        if(loginResult.getAccessToken().getToken()!=null){
                            cleverTap.event.push("Login with Facebook");
                            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    Log.i("LoginActivity", response.toString());
                                    // Get facebook data from login
                                    Bundle bFacebookData = getFacebookData(object);
                                    if(bFacebookData.getString("first_name")!=null && bFacebookData.getString("last_name")!=null){
                                        sharedCommon.save(SharedCommon.email, bFacebookData.getString("first_name")+" "+bFacebookData.getString("last_name"));

                                        progress.setVisibility(View.GONE);
                                        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                                        intent.putExtra("Email",bFacebookData.getString("first_name")+" "+bFacebookData.getString("last_name"));
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                            request.setParameters(parameters);
                            request.executeAsync();
                            Toast.makeText(getApplicationContext(),"Login successfully",Toast.LENGTH_SHORT).show();

                        }else {
                            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                            intent.putExtra("Email","Fb Login");
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                       // WalinnsAPI.getInstance().track("Login","error while login with Fb");
                        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                        intent.putExtra("Email","Fb Login");
                        startActivity(intent);
                        finish();

                    }
                }
        );
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case com.walinns.walinnsinnovation.waltics_test.R.id.linear_fb:
                progress.setVisibility(View.VISIBLE);
                LoginManager.getInstance().logInWithReadPermissions(
                        this,
                        Arrays.asList("user_photos", "email", "user_birthday", "public_profile")
                );
                 break;
            case com.walinns.walinnsinnovation.waltics_test.R.id.linear_g_plus:
                progress.setVisibility(View.VISIBLE);
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, SIGN_IN);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN) {
            System.out.println("Google sign in :" + "onRequest code");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                //Calling a new function to handle signin
            handleSignInResult(result);

        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    private void handleSignInResult(GoogleSignInResult result) {
        System.out.println("Google sign in result" + result.isSuccess());
        if (result.isSuccess()) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            cleverTap.event.push("Login with Google");
            if(currentPerson !=null) {
                if (currentPerson.getDisplayName() != null) {
                    sharedCommon.save(SharedCommon.email, currentPerson.getDisplayName());
                }
            }


            Toast.makeText(getApplicationContext(),"Login successfully",Toast.LENGTH_SHORT).show();
            progress.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
            if(currentPerson !=null&&currentPerson.getDisplayName() !=null) {
                System.out.println("Google sign in result if" + currentPerson.getDisplayName());
                intent.putExtra("Email", currentPerson.getDisplayName());
            }else {
                intent.putExtra("Email", "Google");
            }
            startActivity(intent);
            finish();

        }else {
            System.out.println("Google login :"+ result.getStatus());
            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
            intent.putExtra("Email","Google Login");
            startActivity(intent);
            finish();

        }
    }
    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));




            return bundle;
        }
        catch(JSONException e) {
            Log.d("Login db","Error parsing JSON");
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedCommon = new SharedCommon(MainActivity.this);
        if(sharedCommon.getValue(SharedCommon.email)!=null && !sharedCommon.getValue(SharedCommon.email).isEmpty()){
            System.out.println("Google sign in OnDestroy :" + sharedCommon.getValue(SharedCommon.email));
            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
            intent.putExtra("Email",sharedCommon.getValue(SharedCommon.email));
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("OnDestroy :" + "MainActivity");
        cleverTap.event.push("LoginActivity");
        progress.setVisibility(View.GONE);


    }


    public boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) MainActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
    }


}