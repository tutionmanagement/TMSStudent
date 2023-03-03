package com.example.tms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tms.databinding.ActivitySignupBinding;
//import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
public class SignupActivity extends AppCompatActivity {

    private String name,email,password,tcname;

    private ActivitySignupBinding binding;
    private String[] std = {"Select Standard", "1","2","3","4","5","6","7","8","9","10"};

    private String[] tclasses = {"Select Tution Classes","ABC","DEF","GHI","JKL","MNO","PQR","STU","VWX","YZ"};

    ArrayList<Integer> sublist = new ArrayList<>();
    boolean[] selectedSub;
    String[] subArray = {"Gujarati", "English", "Maths", "Science", "Social Science", "Hindi", "Environment","Sanskrit","Computer"};

    private FirebaseAuth mAuth;

    private DatabaseReference rootDatabaseref;

    private int finalSelectedStd;

    private String finalSelectedSub;

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
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        rootDatabaseref = FirebaseDatabase.getInstance().getReference().child("Students");
        selectedSub = new boolean[subArray.length];

        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,std);
        binding.spStd.setSelection(1);
        binding.spStd.setAdapter(adapter);

        ArrayAdapter<String> adapterTc=new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,tclasses);
        binding.sptcName.setSelection(1);
        binding.sptcName.setAdapter(adapterTc);

        binding.spStd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0) {
                    finalSelectedStd = Integer.parseInt(std[i]);
                    Toast.makeText(getApplicationContext(), "Your standard is " + std[i], Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Nothing selected..", Toast.LENGTH_SHORT).show();
            }
        });

        binding.sptcName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0) {
                    tcname = tclasses[i];
//                    Toast.makeText(getApplicationContext(), "Your standard is " + std[i], Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Nothing selected..", Toast.LENGTH_SHORT).show();
            }
        });

        binding.spSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                // set title
                builder.setTitle("Select Std");

                // set dialog non cancelable
                builder.setMultiChoiceItems(subArray, selectedSub, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            sublist.add(i);
                            // Sort array list
                            Collections.sort(sublist);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            sublist.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < sublist.size(); j++) {
                            // concat array value
                            stringBuilder.append(subArray[sublist.get(j)]);
                            // check condition
                            if (j != sublist.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        binding.spSub.setText(stringBuilder.toString());
                        finalSelectedSub = stringBuilder.toString();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedSub.length; j++) {
                            // remove all selection
                            selectedSub[j] = false;
                            // clear language list
                            sublist.clear();
                            // clear text view value
                            binding.spSub.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
    }
    public void toSignin(View v){
        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    public void Signup(View v){
        name = binding.edName.getText().toString();
        email = binding.edEmail.getText().toString();
        password = binding.edPassword.getText().toString();

        if(email.equals("") || password.equals("")||name.equals("")|| tcname.equals("")){
            binding.edEmail.setError("This Field Required");
            binding.edPassword.setError("This Field Required");
            binding.edName.setError("This Field Required");

        }else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                SharedPreferences sharedPreferences = getSharedPreferences("SystemPre",MODE_PRIVATE);

                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putBoolean("isLogin",true);
                                editor.putString("email",email);
                                editor.commit();

                                String[] finalsubs = finalSelectedSub.split(", ");
                                ArrayList<String> sublist = new ArrayList<String>(Arrays.asList(finalsubs));

                                StudentModel sm = new StudentModel(tcname,name,finalSelectedStd,sublist,email);

                                String ukey = rootDatabaseref.push().getKey();

                                rootDatabaseref.child(ukey).setValue(sm);

                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


}