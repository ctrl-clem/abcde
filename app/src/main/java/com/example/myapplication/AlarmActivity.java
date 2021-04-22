package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

import static com.example.myapplication.LogInActivity.filename;

public class AlarmActivity extends AppCompatActivity {
    EditText titletodo,desctodo;
    TextView datetodo;
    Button btnSaveTask, btnCancel;
    DatabaseReference reference;
    SharedPreferences sharedPreferences;
    int h1,min1;
    Integer todoNum= new Random().nextInt();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        titletodo = findViewById(R.id.titletodo);
        desctodo = findViewById(R.id.desctodo);
        datetodo = findViewById(R.id.datetodo);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);
        sharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);

        datetodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(
                        AlarmActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                h1=hourOfDay;
                                min1=minute;
                                Calendar calendar= Calendar.getInstance();
                                calendar.set(0,0,0,h1,min1);
                                datetodo.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(h1,min1);
                timePickerDialog.show();
            }
        });

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert to database
                /*reference = FirebaseDatabase.getInstance().getReference().child("ToDoApp").
                        child("ToDo"+ todoNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("titletodo").setValue(titletodo.getText().toString());
                        snapshot.getRef().child("desctodo").setValue(desctodo.getText().toString());
                        snapshot.getRef().child("datetodo").setValue(datetodo.getText().toString());
                        Intent a= new Intent(AlarmActivity.this,NewActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

                Intent a= new Intent(AlarmActivity.this,AlarmReceiver.class);

                finish();
                startActivity(a);
            }
        });

    }
}