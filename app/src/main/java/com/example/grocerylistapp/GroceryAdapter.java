package com.example.grocerylistapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public GroceryAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {

        public TextView name_text;
        public TextView count_text;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);

            name_text = itemView.findViewById(R.id.tv_item_name);
            count_text = itemView.findViewById(R.id.amt);
        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grocery_item,parent,false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {

        if(!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_AMOUNT));
        long id=mCursor.getLong(mCursor.getColumnIndex(GroceryContract.GroceryEntry._ID));

        holder.name_text.setText(name);
        holder.count_text.setText(String.valueOf(amount));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swap_cursor(Cursor newCursor) {
        if(mCursor!=null) {
            mCursor.close();
        }
        mCursor=newCursor;
        if(mCursor!=null) {
            notifyDataSetChanged();
        }
    }
}