package com.smile.delite.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.smile.delite.R;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        Button plusButton;
        Button minusButton;
        TextView counterTextView;

        TextView textViewCompUsrNameTv;
        TextView textViewCompNameTv;
        TextView textViewLocation;
        TextView textViewDate;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
            this.plusButton = (Button) itemView.findViewById(R.id.plusBtn);
            this.minusButton = (Button) itemView.findViewById(R.id.minusBtn);
            this.counterTextView = (TextView) itemView.findViewById(R.id.counterTv);
           /* this.textViewEventName = (TextView) itemView.findViewById(R.id.eventNameTv);
            this.textViewCompUsrNameTv = (TextView) itemView.findViewById(R.id.companyUserNameTv);
            this.textViewCompNameTv = (TextView) itemView.findViewById(R.id.companyNameTv);
            this.textViewLocation = (TextView) itemView.findViewById(R.id.eventLocationTv);
            this.textViewDate = (TextView) itemView.findViewById(R.id.eventDateTv);*/
        }
    }

    public CartListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item_layout, parent, false);
        //view.setOnClickListener(EventListFragment.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        CardView cardViewForClick = holder.cardView;
        /*TextView textViewEventName = holder.textViewEventName;
        TextView textViewCompUsrNameTv = holder.textViewCompUsrNameTv;
        TextView textViewCompName = holder.textViewCompNameTv;
        TextView textViewLocationn = holder.textViewLocation;
        TextView textViewDate = holder.textViewDate;

        Log.v("ListPosition", String.valueOf(listPosition));


        textViewEventName.setText("name");
        textViewCompUsrNameTv.setText("company username");
        textViewCompName.setText("company name");
        textViewLocationn.setText("location");
        textViewDate.setText("Date");*/


        Button plusButton=holder.plusButton;
        Button minusButton=holder.minusButton;
        TextView counterTv=holder.counterTextView;
        int counter=0;
        //TextView counterTv.setT=holder.counterTextView;


        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        cardViewForClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (((ClientListActivity) context).eventDescFragment == null) {
                    ((ClientListActivity) context).eventDescFragment = EventDescFragment.newInstance();
                }*/


               // App.singleEventJson = ClientListActivity.mEventJsonList.get(listPosition);
               // context.startActivity(new Intent(context, EventDescriptionActivity.class));
                //((ClientListActivity) context).fragmentLoader(((ClientListActivity) context).eventDescFragment, "event description");

            }
        });

    }

    @Override
    public int getItemCount() {
        return 12;
    }
    // Filter Method

}