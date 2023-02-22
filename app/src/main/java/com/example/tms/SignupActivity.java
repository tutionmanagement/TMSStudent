package com.example.tms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private EditText ediEmail,ediPassword,ediName,ediAdminKey;
    private String name,email,password,adminKey;
    private ImageView im;
    private CheckBox cbAgree;

    private Button signUp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

    }
    public void toSignin(View v){
        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    public void Signup(View v){
        ediName = findViewById(R.id.edName);
        ediEmail = findViewById(R.id.edEmail);
        ediPassword = findViewById(R.id.edPassword);
        name = ediName.getText().toString();
        email = ediEmail.getText().toString();
        password = ediPassword.getText().toString();


        if(email.equals("") || password.equals("")||name.equals("")){
            ediEmail.setError("This Field Required");
            ediPassword.setError("This Field Required");
            ediName.setError("This Field Required");
        }else{
            Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
            startActivity(intent);

        }
    }


}