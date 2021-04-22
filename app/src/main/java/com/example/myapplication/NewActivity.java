package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

import static com.example.myapplication.LogInActivity.Username;
import static com.example.myapplication.LogInActivity.filename;

public class NewActivity extends AppCompatActivity {
    Button logOutBtn,setAlarmBtn;
    SharedPreferences prf;
    TextView alarmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        logOutBtn=findViewById(R.id.logOutBtn);
        prf=getSharedPreferences(filename, MODE_PRIVATE);
        setAlarmBtn=findViewById(R.id.setAlarmBtn);
        alarmText=findViewById(R.id.alarmText);

        TextView textView= findViewById(R.id.textView);
        SessionManagement sessionManagement= new SessionManagement(this);
        HashMap<String,String> userDetails= sessionManagement.getUserDetailFromSession();
        String name=userDetails.get(SessionManagement.KEY_FULLNAME);
        String username=userDetails.get(SessionManagement.KEY_USERNAME);
        textView.setText(name+"\n"+username);



        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),AlarmActivity.class);
                startActivity(i);
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=prf.edit();
                editor.clear();
                editor.commit();

                Intent i=new Intent(getApplicationContext(),LogInActivity.class);
                startActivity(i);
                finish();

            }
        });

    }
}
