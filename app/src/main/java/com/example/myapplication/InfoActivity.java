package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.myapplication.LogInActivity.filename;

public class InfoActivity extends AppCompatActivity {
    Button doneBtn;
    TextView helloText;
    TextView wakeTimeText, sleepTimeText ;
    SharedPreferences sh;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    int h1,h2,min1,min2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        doneBtn=findViewById(R.id.doneBtn);
        helloText=findViewById(R.id.helloText);
        sh=getSharedPreferences(filename,MODE_PRIVATE);
        wakeTimeText=findViewById(R.id.wakeTimeText);
        sleepTimeText=findViewById(R.id.sleepTimeText);



        SessionManagement sessionManagement= new SessionManagement(this);
        HashMap<String,String> userDetails= sessionManagement.getUserDetailFromSession();
        String name=userDetails.get(SessionManagement.KEY_FULLNAME);
        String username=userDetails.get(SessionManagement.KEY_USERNAME);
        String email= userDetails.get(SessionManagement.KEY_EMAIL);
        String password=userDetails.get(SessionManagement.KEY_PASSWORD);
        helloText.setText("Hello " + name+"!"+ "\n"+ "Tell us more about yourself!");

        wakeTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(
                        InfoActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                h1=hourOfDay;
                                min1=minute;
                                Calendar calendar= Calendar.getInstance();
                                calendar.set(0,0,0,h1,min1);
                                wakeTimeText.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        },12,0,false
                        );
                timePickerDialog.updateTime(h1,min1);
                timePickerDialog.show();
            }
        });

        sleepTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(
                        InfoActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                h2=hourOfDay;
                                min2=minute;
                                Calendar calendar= Calendar.getInstance();
                                calendar.set(0,0,0,h2,min2);
                                sleepTimeText.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(h2,min2);
                timePickerDialog.show();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode= FirebaseDatabase.getInstance();
                reference= rootNode.getReference("users");
                String wakeTime= wakeTimeText.getText().toString();
                String sleepTime= sleepTimeText.getText().toString();
                UserHelper userHelper= new UserHelper(name,username,email,password,wakeTime,sleepTime);
                reference.child(username).setValue(userHelper);


                Intent i=new Intent(getApplicationContext(),NewActivity.class);
                finish();
                startActivity(i);

            }
        });
    }
}