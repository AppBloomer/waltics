package com.example.walinnsinnovation.waltics;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.walinnsinnovation.waltics.BeanClass.NoteItem;
import com.example.walinnsinnovation.waltics.DataBase.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class ListNoteActivity extends AppCompatActivity implements View.OnClickListener, ListAdapter.Listener{

    RecyclerView recycler_view,recycler_view2;
    public  List<NoteItem> noteItemList = new ArrayList<>();
    public  List<NoteItem> complete_noteItemList = new ArrayList<>();

    ListAdapter listAdapter;
    RelativeLayout txt_add;
    DatabaseHandler db;
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        activity = ListNoteActivity.this;
        db = new DatabaseHandler(ListNoteActivity.this);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view2 = (RecyclerView)findViewById(R.id.recycler_view2);
        txt_add = (RelativeLayout) findViewById(R.id.txt_add);
        txt_add.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());

        recycler_view2.setLayoutManager(mLayoutManager2);
        recycler_view2.setItemAnimator(new DefaultItemAnimator());
        if(db.getAllContacts().size()>0) {
            noteItemList = db.getAllContacts();
            System.out.println("get from database :" + noteItemList.size());
        }
        if(noteItemList.size()>0) {
            recycler_view.setAdapter(new ListAdapter(ListNoteActivity.this, noteItemList,this));
        }

        if(db.getAllCompletedList().size()>0){
            complete_noteItemList = db.getAllCompletedList();
            if(complete_noteItemList.size()>0) {
                recycler_view2.setAdapter(new CompleteAdapter(ListNoteActivity.this, complete_noteItemList));
            }
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_add:
                Intent intent = new Intent(ListNoteActivity.this,AddNoteActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onClick(View view, MotionEvent motionEvent,int position, List<NoteItem>noteItemList) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            view);
        view.startDrag(data, shadowBuilder, view, 0);
        recycler_view.setOnDragListener(new MyDragListener(position,noteItemList));

    }
    class MyDragListener implements View.OnDragListener {
        int pos;
        List<NoteItem> noteItems = new ArrayList<>();
        MyDragListener(int position, List<NoteItem> noteItemList){
            this.pos=position;
            this.noteItems = noteItemList;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    Log.d("Event..", "Drag event started");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("Event..", "Drag event entered");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("Event..", "Drag event exiut");
                    NoteItem noteItem = new NoteItem(noteItems.get(pos).getNote_text(),noteItems.get(pos).getNote_cat(),noteItems.get(pos).getNote_date());
                    complete_noteItemList.add(noteItem);
                    db.addCompleteItem(noteItem);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    System.out.println("Event.. Dragged data:" + "dropped");

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("Event..", "Drag event end" );
                    if(complete_noteItemList.size()>0) {
                        db.deleteTitle(noteItems.get(pos).getNote_text());
                        recycler_view2.setAdapter(new CompleteAdapter(activity, complete_noteItemList));
                        activity.startActivity(new Intent(activity,ListNoteActivity.class));
                        activity.overridePendingTransition(0, 0);

                    }

                 default:
                    break;
            }
            return true;
        }
    }
}
