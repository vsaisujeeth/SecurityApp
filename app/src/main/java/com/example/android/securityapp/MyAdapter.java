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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;
    private List<Admin_List_Item> listItems;
    private Context context;

    public MyAdapter(List<Admin_List_Item> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textView;
        public TextView textViewMobile;

        public MyViewHolder(View v) {
            super(v);

            textViewHead=(TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc=(TextView) itemView.findViewById(R.id.textViewDesc);
            textViewMobile=(TextView) itemView.findViewById(R.id.textViewMobile);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
  /*  public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }*/

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_list_item, parent, false);

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
       Admin_List_Item admin_list_item= listItems.get(position);
        holder.textViewHead.setText(admin_list_item.head);
        holder.textViewDesc.setText(admin_list_item.desc);
        holder.textViewMobile.setText(admin_list_item.mobile);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listItems.size() ;
    }
}
