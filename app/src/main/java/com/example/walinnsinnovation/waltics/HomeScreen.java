package com.example.walinnsinnovation.waltics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.walinnsinnovation.waltics.DataBase.DatabaseHandler;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    TextView txtusername;
    RelativeLayout txt_add;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        txtusername = (TextView)findViewById(R.id.txtusername);
        txt_add = (RelativeLayout)findViewById(R.id.txt_add);
        txt_add.setOnClickListener(this);
        if(getIntent()!=null&&getIntent().getStringExtra("Email")!=null){
            System.out.println("Logged USER Name :" + getIntent().getStringExtra("Email"));
            txtusername.setText("Welcome "+getIntent().getStringExtra("Email")+" !");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_add:
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
}
