package com.example.tms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private EditText ediEmail,ediPassword,ediName,ediAdminKey;
    private String name,email,password,adminKey;
    private ImageView im;
    private CheckBox cbAgree;

    private Button signUp;

    private Spinner spStd;
    private String[] std = {"-----Select----", "1","2","3","4","5","6","7","8","9","10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        spStd = findViewById(R.id.spStd);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,std);
        spStd.setSelection(1);
        spStd.setAdapter(adapter);
        spStd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0)
                    Toast.makeText(getApplicationContext(), "Your standard is "+std[i], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Nothing selected..", Toast.LENGTH_SHORT).show();
            }
        });




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