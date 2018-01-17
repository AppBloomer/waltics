package com.walinns.walinnsinnovation.waltics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walinns.walinnsinnovation.waltics.BeanClass.NoteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walinnsinnovation on 15/01/18.
 */

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.MyViewHolder>{

    List<NoteItem> nodeItemList = new ArrayList<>();
    Context mContext;
    ListAdapter.Listener mListener;

     
    public CompleteAdapter(Context context, List<NoteItem> noteItems){
        this.mContext = context;
        this.nodeItemList = noteItems;
    }

    @Override
    public CompleteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(com.walinns.walinnsinnovation.waltics.R.layout.note_list_item, parent, false);

        return new CompleteAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompleteAdapter.MyViewHolder holder, final int position) {

        holder.txt_note.setText(nodeItemList.get(position).getNote_text());
        holder.note_cat.setText(nodeItemList.get(position).getNote_cat());
        if(nodeItemList.get(position).getNote_cat().equals("Home")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(com.walinns.walinnsinnovation.waltics.R.color.home_cat));
        }else if(nodeItemList.get(position).getNote_cat().equals("Office")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(com.walinns.walinnsinnovation.waltics.R.color.office));

        }else if(nodeItemList.get(position).getNote_cat().equals("Extras")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(com.walinns.walinnsinnovation.waltics.R.color.extra));

        }else if(nodeItemList.get(position).getNote_cat().equals("Other")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(com.walinns.walinnsinnovation.waltics.R.color.other));

        }


    }

    @Override
    public int getItemCount() {
        return nodeItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_note, note_cat;

        public MyViewHolder(View view) {
            super(view);
            txt_note = (TextView)view.findViewById(com.walinns.walinnsinnovation.waltics.R.id.note_text);
            note_cat = (TextView)view.findViewById(com.walinns.walinnsinnovation.waltics.R.id.note_cat);

        }
    }


}
