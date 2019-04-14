package com.example.grocerylistapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListActivity extends AppCompatActivity {

    private GroceryAdapter mAdapter;
    SQLiteDatabase mDatabase;
    EditText et1;
    TextView amount;
    int amt = 0;
    Button incBtn;
    Button decBtn;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        GroceryDBHelper dbHelper = new GroceryDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroceryAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        et1 = findViewById(R.id.et1);
        amount = findViewById(R.id.amount);
        incBtn = (Button) findViewById(R.id.inc_btn);
        decBtn = (Button) findViewById(R.id.dec_btn);
        addBtn = (Button) findViewById(R.id.add_btn);

        incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt++;
                amount.setText(String.valueOf(amt));
            }
        });

        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amt > 0) {
                    amt--;
                    amount.setText(String.valueOf(amt));
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et1.getText().toString().trim().length() == 0 || amt == 0)
                    return;

                String name = et1.getText().toString().trim();
                ContentValues cv = new ContentValues();
                cv.put(GroceryContract.GroceryEntry.COLUMN_NAME, name);
                cv.put(GroceryContract.GroceryEntry.COLUMN_AMOUNT, amt);

                mDatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME, null, cv);
                mAdapter.swap_cursor(getAllItems());

                et1.setText("");
                amt=0;
                amount.setText("0");
            }
        });
    }

    private void removeItem(long id) {
        mDatabase.delete(GroceryContract.GroceryEntry.TABLE_NAME, GroceryContract.GroceryEntry._ID + "=" + id,null);
        mAdapter.swap_cursor(getAllItems());
    }

    private Cursor getAllItems() {
        return mDatabase.query(GroceryContract.GroceryEntry.TABLE_NAME, null, null, null, null, null,
                GroceryContract.GroceryEntry.COLUMN_TIMESTAMP + " DESC");
    }
}