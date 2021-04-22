package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;
import java.util.stream.Stream;

public class LogInActivity extends AppCompatActivity {
    Button regLogIn;
    EditText regUser, regPassword;
    Button createBtn;
    static SharedPreferences sharedPreferences;
    public static final String filename="login";
    public static final String Username="username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        regLogIn= findViewById(R.id.regLogIn);
        regUser= findViewById(R.id.regUser);
        regPassword= findViewById(R.id.regPassword);
        createBtn= findViewById(R.id.createBtn);

        sharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        if(sharedPreferences.contains(Username))
        {
            startActivity(new Intent(getApplicationContext(),NewActivity.class));
            finish();
        }

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);

            }
        });


        regLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser(v);


            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private Boolean validateUser()
    { String val= regUser.getText().toString();
        if(val.isEmpty())
        {

            return false;
        }
        else
        {

            return true;

        }
    }

    private Boolean validatePassword()
    { String val= regPassword.getText().toString();
        if(val.isEmpty())
        {
            return false;
        }
        else
        return true;

    }



    public void logInUser(View view)
    {
        if(validateUser()  && validatePassword()) {
            isUser();

        }

    }

    private void isUser(){
        String userEnteredUsername= regUser.getText().toString().trim();
        String userEnteredPassword= regPassword.getText().toString().trim();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");

        Query checkUser= reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    String passwordFromDB= snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if(passwordFromDB.equals(userEnteredPassword))
                    {
                        String nameFromDB= snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String emailFromDB= snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        String usernameFromDB= snapshot.child(userEnteredUsername).child("username").getValue(String.class);



                        /*intent.putExtra("name", nameFromDB);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("password",passwordFromDB);*/
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(Username,userEnteredUsername);
                        editor.commit();
                        SessionManagement sessionManagement=new SessionManagement(LogInActivity.this);
                        sessionManagement.createLoginSession(nameFromDB, emailFromDB,usernameFromDB,passwordFromDB);



                        Intent i= new Intent(getApplicationContext(), NewActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        finish();
                        startActivity(i);


                    }
                    else
                    {
                        regPassword.setError("Wrong password");
                        regPassword.requestFocus();
                    }
                }
                else
                {
                    regUser.setError("This user does not exist");
                    regUser.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}