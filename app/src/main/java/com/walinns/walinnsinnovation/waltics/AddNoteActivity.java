package com.walinns.walinnsinnovation.waltics;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.walinns.walinnsinnovation.waltics.BeanClass.NoteItem;
import com.walinns.walinnsinnovation.waltics.DataBase.DatabaseHandler;
import com.walinns.walinnsapi.WalinnsAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner;
    Button btnsave;
    LinearLayout linear_txt;
    Calendar myCalendar = Calendar.getInstance();
    TextView txt_date,txt_note;
    EditText txt_add_note;
    DatabaseHandler db;
    int note_id = 0;

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
        txt_note = (TextView) findViewById(R.id.txt_note);
        linear_txt.setOnClickListener(this);
        btnsave.setOnClickListener(this);
        String[] years = {"Home","Office","Extras","Other"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(langAdapter);
        updateLabel();
        if(getIntent()!=null){
            System.out.println("Errorrrrr" +  "if...");

            if(getIntent().getStringExtra("note_text")!=null){
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                txt_note.setText("Note Detail");
                btnsave.setText("Update");
                WalinnsAPI.getInstance().track("UpdateNoteActivity");

                txt_add_note.setText(getIntent().getStringExtra("note_text"));
            }
            if(getIntent().getStringExtra("note_cat")!=null){
                int spinnerPosition = langAdapter.getPosition(getIntent().getStringExtra("note_cat"));
                spinner.setSelection(spinnerPosition);
            }
            if(getIntent().getStringExtra("note_date")!=null){
                txt_date.setText(getIntent().getStringExtra("note_date"));
            }
            if(getIntent().getIntExtra("note_id", 0)!=0) {
                System.out.println("Note id :" + getIntent().getIntExtra("note_id", 0));
                note_id = getIntent().getIntExtra("note_id", 0);
            }
        }else {
            System.out.println("Errorrrrr" + "else");

            WalinnsAPI.getInstance().track("AddNoteActivity");

        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                WalinnsAPI.getInstance().track("Spinner cat",spinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnsave:
                if(txt_note.getText().toString().equals("Note Detail")&&btnsave.getText().toString().equals("Update")){

                    if (!txt_add_note.getText().toString().isEmpty() && !spinner.getSelectedItem().toString().isEmpty()) {
                        NoteItem noteItem = new NoteItem(txt_add_note.getText().toString(), spinner.getSelectedItem().toString(), txt_date.getText().toString());
                        db.updateNote(noteItem, note_id);
                        WalinnsAPI.getInstance().track("Button","Update");
                        Intent intent = new Intent(AddNoteActivity.this, ListNoteActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (!txt_add_note.getText().toString().isEmpty() && !spinner.getSelectedItem().toString().isEmpty()) {
                        NoteItem noteItem = new NoteItem(txt_add_note.getText().toString(), spinner.getSelectedItem().toString(), txt_date.getText().toString());
                        db.addNote(noteItem);
                        WalinnsAPI.getInstance().track("Button","Save");

                        Toast.makeText(getApplicationContext(), "Note added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddNoteActivity.this, ListNoteActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();

                    }
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
                if(getIntent().getStringExtra("note_date")!=null){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date d = dateFormat.parse(getIntent().getStringExtra("note_date"));
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(d);
                        new DatePickerDialog(this, date, cal
                                .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                                cal.get(Calendar.DAY_OF_MONTH)).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else {
                    new DatePickerDialog(this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                break;

        }
    }
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date.setText(sdf.format(myCalendar.getTime()));
        WalinnsAPI.getInstance().track("Note Date" , txt_date.getText().toString());
        System.out.println("Errorrrrr" + txt_date.getText().toString());
    }

}
