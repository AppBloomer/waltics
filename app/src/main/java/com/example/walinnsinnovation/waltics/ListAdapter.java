package com.example.walinnsinnovation.waltics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    public ListAdapter(Context context, List<NoteItem> noteItems){
        this.mContext = context;
        this.nodeItemList = noteItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txt_note.setText(nodeItemList.get(position).getNote_text());
        holder.note_cat.setText(nodeItemList.get(position).getNote_cat());

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
}
