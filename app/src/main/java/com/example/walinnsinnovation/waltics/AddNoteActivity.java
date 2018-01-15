package com.example.walinnsinnovation.waltics;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.walinnsinnovation.waltics.BeanClass.NoteItem;
import com.example.walinnsinnovation.waltics.DataBase.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner;
    Button btnsave;
    LinearLayout linear_txt;
    Calendar myCalendar = Calendar.getInstance();
    TextView txt_date;
    EditText txt_add_note;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        db = new DatabaseHandler(AddNoteActivity.this);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnsave = (Button) findViewById(R.id.btnsave);
        linear_txt = (LinearLayout) findViewById(R.id.linear_txt);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_add_note = (EditText) findViewById(R.id.txt_add_note);

        linear_txt.setOnClickListener(this);
        btnsave.setOnClickListener(this);
        String[] years = {"Home","Office","Extras","Other"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(langAdapter);
        updateLabel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnsave:
                if(!txt_add_note.getText().toString().isEmpty() && !spinner.getSelectedItem().toString().isEmpty()) {
                    NoteItem noteItem = new NoteItem(txt_add_note.getText().toString(),spinner.getSelectedItem().toString(),txt_date.getText().toString());
                    db.addNote(noteItem);
                    Toast.makeText(getApplicationContext(),"Note adeed",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNoteActivity.this, ListNoteActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"Enter all fields",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.linear_txt:
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }

                };
                new DatePickerDialog(this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

        }
    }
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date.setText(sdf.format(myCalendar.getTime()));
    }
}
