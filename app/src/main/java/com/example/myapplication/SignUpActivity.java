package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.myapplication.LogInActivity.filename;

public class SignUpActivity extends AppCompatActivity {
    EditText name,username,email,password;
    Button signUpBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpBtn=  findViewById(R.id.signUpBtn);
        name= findViewById(R.id.name);
        username=findViewById(R.id. username);
        email= findViewById(R.id.email);
        password=findViewById(R.id. password);
        sharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            rootNode= FirebaseDatabase.getInstance();
            reference= rootNode.getReference("users");
            String Name= name.getText().toString();
            String Username= username.getText().toString();
            String Email= email.getText().toString();
            String Password= password.getText().toString();
            String WakeUp=null;
            String Sleep=null;
            SessionManagement sessionManagement=new SessionManagement(SignUpActivity.this);
            sessionManagement.createLoginSession(Name, Username, Email,Password);

            UserHelper helperClass= new UserHelper(Name, Username,Email,Password,WakeUp,Sleep);
            reference.child(Username).setValue(helperClass);

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(LogInActivity.Username,Username);
            editor.commit();


            Intent i= new Intent(getApplicationContext(), InfoActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();

            startActivity(i);


            }
        });
    }
}