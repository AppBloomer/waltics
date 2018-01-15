package com.example.walinnsinnovation.waltics;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.walinnsinnovation.waltics.BeanClass.NoteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walinnsinnovation on 12/01/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    List<NoteItem> nodeItemList = new ArrayList<>();
    Context mContext;
    Listener mListener;

    public ListAdapter(Context context, List<NoteItem> noteItems,Listener listener){
        this.mContext = context;
        this.nodeItemList = noteItems;
        this.mListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txt_note.setText(nodeItemList.get(position).getNote_text());
        holder.note_cat.setText(nodeItemList.get(position).getNote_cat());
        if(nodeItemList.get(position).getNote_cat().equals("Home")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(R.color.home_cat));
        }else if(nodeItemList.get(position).getNote_cat().equals("Office")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(R.color.office));

        }else if(nodeItemList.get(position).getNote_cat().equals("Extras")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(R.color.extra));

        }else if(nodeItemList.get(position).getNote_cat().equals("Other")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(R.color.other));

        }
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    mListener.onClick(view, motionEvent,position,nodeItemList);

                    return true;
                }else {
                    return false;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return nodeItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_note, note_cat;

        public MyViewHolder(View view) {
            super(view);
            txt_note = (TextView)view.findViewById(R.id.note_text);
            note_cat = (TextView)view.findViewById(R.id.note_cat);

        }
    }
    public interface Listener {
        void onClick(View view, MotionEvent motionEvent , int position , List<NoteItem> noteItemList);
    }

}
