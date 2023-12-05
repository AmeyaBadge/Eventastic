package com.amykoder.eventastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference userDatabase; // FireBase Database Reference
    private FirebaseAuth mAuth;
    boolean checkSignUpDataInserted = false;
    EditText edtSignUpEmail,edtSignUpPassword, edtSignUpCnfPassword;
    TextView txtLogin;
    Button btnRegister;
    CheckBox chkbxTaC;
    Intent iSignIn;
    CustomProgressDialogClass customProgressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(this, "Already Signed in !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userDatabase = FirebaseDatabase.getInstance().getReference("Users"); // Set reference in mDatabase Object
        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth

        edtSignUpEmail = (EditText) findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword = (EditText) findViewById(R.id.edtSignUpPassword);
        edtSignUpCnfPassword = (EditText) findViewById(R.id.edtSignUpCnfPassword);

        txtLogin = (TextView) findViewById(R.id.txtLoginGoTo);

        btnRegister = findViewById(R.id.btnRegister);

        chkbxTaC = (CheckBox) findViewById(R.id.chkbxTandC);

        iSignIn = new Intent(getApplicationContext(), LoginActivity.class);

        //Create object of CustomProgressDialog class
        customProgressDialog= new CustomProgressDialogClass(SignUpActivity.this);

        //Open Login Activity on already have account click
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iSignIn);
                finish();
            }
        });

        //add tick after correct email
        edtSignUpEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(edtSignUpEmail.getText().toString()) && edtSignUpEmail.getText().toString().contains("@") && edtSignUpEmail.getText().toString().contains(".")){
                    edtSignUpEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.at_email_icon,0,R.drawable.check_tick_icon,0);
                }else{
                    edtSignUpEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.at_email_icon,0,0,0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Nothing
            }
        });

        //On Register Process Start
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtSignUpEmail.getText().toString();
                String password = edtSignUpPassword.getText().toString();
                String cnfPassword = edtSignUpCnfPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cnfPassword)){
                    Toast.makeText(SignUpActivity.this, "All fields required !", Toast.LENGTH_SHORT).show();
                }else if(!TextUtils.equals(password,cnfPassword)){
                    Toast.makeText(SignUpActivity.this, "Passwords does not match!", Toast.LENGTH_SHORT).show();
                }else if(!chkbxTaC.isChecked()){
                    Toast.makeText(SignUpActivity.this, "Please agree to our Terms & Conditions", Toast.LENGTH_SHORT).show();
                }else {
                    customProgressDialog.show();
//                    String uid = userDatabase.push().getKey();
//                    userDatabase.child(uid).setValue(new UserClass(email,password));

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("FBAuth", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String uid = user.getUid();
                                        checkSignUpDataInserted = true;
                                        userDatabase.child(uid).setValue(new UserClass(email,password));
                                        customProgressDialog.dismiss();
                                        Toast.makeText(SignUpActivity.this, "Registration Successfull !", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        customProgressDialog.dismiss();
                                        Log.w("FBAuth", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}