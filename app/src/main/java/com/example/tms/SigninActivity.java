package com.example.tms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {

    private EditText ediEmail,ediPassword;
    private String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();

    }
    public void toSignup(View v){
        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    public void Signin(View v){
        ediEmail = findViewById(R.id.edEmail);
        ediPassword = findViewById(R.id.edPassword);

        email = ediEmail.getText().toString();
        password = ediPassword.getText().toString();

        if(email.equals("") || password.equals("")){
            ediEmail.setError("This Field Required");
            ediPassword.setError("This Field Required");
        }else {
            if(email.equals("admin@gmail.com")&&password.equals("1234")){
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SigninActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}