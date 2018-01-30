package com.walinns.walinnsinnovation.waltics;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.walinns.walinnsinnovation.waltics.DataBase.DatabaseHandler;
import com.walinns.walinnsapi.WalinnsAPI;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    TextView txtusername;
    RelativeLayout txt_add;
    DatabaseHandler databaseHandler;
    boolean doubleBackToExitPressedOnce = false;
    CleverTapAPI cleverTap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        WalinnsAPI.getInstance().track("HomeScreen");
        txtusername = (TextView)findViewById(R.id.txtusername);
        txt_add = (RelativeLayout)findViewById(R.id.txt_add);
        txt_add.setOnClickListener(this);
        if(getIntent()!=null&&getIntent().getStringExtra("Email")!=null){
            System.out.println("Logged USER Name :" + getIntent().getStringExtra("Email"));
            txtusername.setText("Welcome "+getIntent().getStringExtra("Email")+" !");
        }
        try {
            cleverTap = CleverTapAPI.getInstance(getApplicationContext());

        } catch (CleverTapMetaDataNotFoundException e) {
            // thrown if you haven't specified your CleverTap Account ID or Token in your AndroidManifest.xml
        } catch (CleverTapPermissionsNotSatisfied e) {
            // thrown if you haven’t requested the required permissions in your AndroidManifest.xml
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_add:
                WalinnsAPI.getInstance().track("Button","Add Note");
               // cleverTap.event.push("Add Note");
                System.out.println("Errorrrrr" + "main");
                Intent intent = new Intent(HomeScreen.this,AddNoteActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseHandler = new DatabaseHandler(HomeScreen.this);
        if(databaseHandler.getAllContacts()!=null&&databaseHandler.getAllContacts().size()>0){
            Intent intent = new Intent(HomeScreen.this,ListNoteActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
