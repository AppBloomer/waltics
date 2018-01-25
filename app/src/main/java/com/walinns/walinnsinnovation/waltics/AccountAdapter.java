package com.walinns.walinnsinnovation.waltics;

import android.app.Activity;
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
 * Created by walinnsinnovation on 25/01/18.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder>{

    List<String> accountlist = new ArrayList<>();
    MainActivity mContext;
    ListAdapter.Listener mListener;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

    public AccountAdapter(MainActivity context, List<String> accountlist_){
        this.mContext = context;
        this.accountlist = accountlist_;
    }

    @Override
    public AccountAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.google_list_item, parent, false);

        return new AccountAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AccountAdapter.MyViewHolder holder, final int position) {

        holder.txt_note.setText(accountlist.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Selected account :" + accountlist.get(position));
                getTask(mContext, accountlist.get(position), SCOPE).execute();
            }
        });



    }
    private AbstractGetNameTask getTask(MainActivity activity, String email,
                                        String scope) {
        return new GetNameInForeground(activity, email, scope);

    }

    @Override
    public int getItemCount() {
        return accountlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_note ;

        public MyViewHolder(View view) {
            super(view);
            txt_note = (TextView)view.findViewById(R.id.txt_email);


        }
    }


}

