package com.example.walinnsinnovation.waltics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.walinnsinnovation.waltics.BeanClass.NoteItem;

import java.util.ArrayList;
import java.util.List;

public class ListNoteActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    public static List<NoteItem> noteItemList = new ArrayList<>();
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        if(noteItemList.size()>0) {
            recycler_view.setAdapter(new ListAdapter(ListNoteActivity.this, noteItemList));
        }
    }
}
