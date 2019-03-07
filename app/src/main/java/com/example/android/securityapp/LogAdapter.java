package com.example.android.securityapp;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.MyViewHolder> {
    private String[] mDataset;
    private List<Log_List_Item> listItems;
    private Context context;

    public LogAdapter(List<Log_List_Item> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView vehicleno;
        public TextView type;
        public TextView date;
        public TextView time;

        public MyViewHolder(View v) {
            super(v);

            vehicleno=(TextView) itemView.findViewById(R.id.vehicleno);
            type=(TextView) itemView.findViewById(R.id.type);
            date=(TextView) itemView.findViewById(R.id.date);
            time=(TextView) itemView.findViewById(R.id.time);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
  /*  public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }*/

    // Create new views (invoked by the layout manager)
    @Override
    public LogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        /*    holder.textView.setText(mDataset[position]);

         */
        Log_List_Item log_list_item= listItems.get(position);
        holder.vehicleno.setText(log_list_item.vehicleno);
        holder.type.setText(log_list_item.type);
        holder.date.setText(log_list_item.date);
        holder.time.setText(log_list_item.time);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listItems.size() ;
    }
}
