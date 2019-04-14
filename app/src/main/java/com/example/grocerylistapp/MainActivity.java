package com.example.grocerylistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Button btn;
    EditText username;
    EditText password;
    TextView ftv;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=(Button)findViewById(R.id.btn);
        count=5;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=(EditText)findViewById(R.id.userName);
                password=(EditText)findViewById(R.id.passWord);

                String un=username.getText().toString();
                String ps=password.getText().toString();

                validate(un,ps);
            }
        });

    }

    private void validate(String un, String ps) {
        if(un.equals("Admin") && ps.equals("abc123")) {
            username.setText("");
            password.setText("");
            Intent intent=new Intent(MainActivity.this,ListActivity.class);
            startActivity(intent);
        }
        else {
            ftv=(TextView)findViewById(R.id.ftv);
            Button btn=(Button)findViewById(R.id.btn);
            count--;
            if(count==0) {
                ftv.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
            }
            else {
                username.setText("");
                password.setText("");
                ftv.setText("No. of attempts left : "+count);
                ftv.setVisibility(View.VISIBLE);
            }
        }
    }

}