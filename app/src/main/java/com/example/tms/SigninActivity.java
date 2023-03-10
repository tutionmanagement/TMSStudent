package com.example.tms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity {

    private EditText ediEmail,ediPassword;
    private String email,password;

    private FirebaseAuth mAuth;
    private ProgressBar progress_signin;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();
        progress_signin = findViewById(R.id.progress_signin);
        mAuth = FirebaseAuth.getInstance();

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
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progress_signin.setVisibility(View.VISIBLE);
                                SharedPreferences sharedPreferences = getSharedPreferences("SystemPre",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putBoolean("isLogin",true);
                                editor.putString("email",email);
                                editor.commit();

                                Intent intent = new Intent(SigninActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SigninActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}