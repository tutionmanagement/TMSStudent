package com.example.tms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.tms.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SignupActivity extends AppCompatActivity {

    private String name,email,password,tcname,phoneNumber;

    private ActivitySignupBinding binding;
    private String[] std = {"Select Standard", "1","2","3","4","5","6","7","8","9","10"};

//    private String[] tclasses = {"Select Tution Classes","ABC","DEF","GHI","JKL","MNO","PQR","STU","VWX","YZ"};
    private List<String> tclasses = new ArrayList<>();
    ArrayList<Integer> sublist = new ArrayList<>();
    boolean[] selectedSub;
    String[] subArray = {"Gujarati", "English", "Maths", "Science", "Social Science", "Hindi", "Environment","Sanskrit","Computer"};

    private FirebaseAuth mAuth;

    private DatabaseReference rootDatabaseref;

    private DatabaseReference teacherDatabaseref;

    private int finalSelectedStd;

    private String finalSelectedSub;

    private EditText txtEmail;
    private Button btnEditEmail;
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
        tclasses.add("Select Tuition Classes");

        mAuth = FirebaseAuth.getInstance();
        rootDatabaseref = FirebaseDatabase.getInstance().getReference().child("Students");
        teacherDatabaseref = FirebaseDatabase.getInstance().getReference("Teachers");

        // Get TC Name from Firebase
        teacherDatabaseref.addValueEventListener(new ValueEventListener() {
            String tcname1;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    tcname1 = (String) dataSnapshot.child("tcName").getValue();
                    tclasses.add(tcname1);
                }
//                Log.i("TcName",""+tcnames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Working
//        teacherDatabaseref.child("Teachers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if(task.isSuccessful()){
//                    Object s = String.valueOf(task.getResult().getValue());
////                    Log.i("List of tcname",String.valueOf(task.getResult().getValue()));
//                    Log.i("List of tcname",""+s);
//                }
//            }
//        });

//        Log.i("HELLO JUGAL","This Is Data - "+studentModels.isEmpty());

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
                    tcname = tclasses.get(i);
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
        phoneNumber = binding.edPhoneNumber.getText().toString();
        tcname = binding.sptcName.getSelectedItem().toString();

        if(email.equals("") || password.equals("")||name.equals("")|| tcname.equals("Select Tuition Classes") || phoneNumber.equals("")){
            binding.edEmail.setError("* This Field is Required");
            binding.edPassword.setError("* This Field is Required");
            binding.edName.setError("* This Field is Required");
            binding.edPhoneNumber.setError("* This Field is Required");
            Toast.makeText(this, "Please Select Tuition Classes Name", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            binding.progressSignup.setVisibility(View.VISIBLE);

                            LayoutInflater inflater= LayoutInflater.from(getApplicationContext());
                            View view=inflater.inflate(R.layout.alert_dialog,null);
                            AlertDialog builder = new AlertDialog.Builder(SignupActivity.this).create();
                            builder.setCancelable(false);
                            builder.setView(view);
                            builder.setTitle("Email Verification");
                            builder.setMessage("A Email has been send on below email \nYou will be redirected to Dashboard in few moments...\nNot You? Type New Email to Resend\n");
                            txtEmail = view.findViewById(R.id.edemailforalert);
                            btnEditEmail = view.findViewById(R.id.btneditemail);
                            txtEmail.setEnabled(false);
                            txtEmail.setText(email);
                            btnEditEmail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    txtEmail.setEnabled(true);
                                }
                            });
                            builder.setButton(Dialog.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    builder.dismiss();
                                }
                            });
                            builder.show();


                            //-------------------**************************------------------------*********************************


                            if (task.isSuccessful()) {


                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Timer timer = new Timer();
                                            timer.schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    mAuth.getCurrentUser().reload();
                                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                                        timer.cancel();
                                                        builder.dismiss();
                                                        SharedPreferences sharedPreferences = getSharedPreferences("SystemPre",MODE_PRIVATE);
                                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                                        editor.putBoolean("isLogin",true);
                                                        editor.putString("email",email);
                                                        editor.commit();
                                                        String[] finalsubs = finalSelectedSub.split(", ");
                                                        ArrayList<String> sublist = new ArrayList<String>(Arrays.asList(finalsubs));
                                                        StudentModel sm = new StudentModel(tcname,name,finalSelectedStd,sublist,email,phoneNumber);
                                                        String ukey = rootDatabaseref.push().getKey();
                                                        rootDatabaseref.child(ukey).setValue(sm);
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    else{
                                                        Log.i("EM","Email Not Verified");
                                                    }
                                                }
                                            },0,800);

                                        }
                                        else{
                                            Toast.makeText(SignupActivity.this, "Please Verify Your Email ("+email+")", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


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