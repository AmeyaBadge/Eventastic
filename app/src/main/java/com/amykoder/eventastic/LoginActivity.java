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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView txtRegister, txtForgotPassword;
    EditText edtLoginEmail, edtLoginPassword;
    Button btnLogin;
    Intent iSignUp;
    CustomProgressDialogClass customProgressDialog;
    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        txtRegister = (TextView) findViewById(R.id.txtRegisterGoTo);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);

        edtLoginEmail = (EditText) findViewById(R.id.edtLoginEmail);
        edtLoginPassword = (EditText) findViewById(R.id.edtLoginPassword);

        btnLogin = findViewById(R.id.btnLogin);

        iSignUp = new Intent(getApplicationContext(), SignUpActivity.class);

        customProgressDialog = new CustomProgressDialogClass(LoginActivity.this);

        //On go to Register TextView click
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iSignUp);
                finish();
            }
        });

        // Put tick after correct mail
        edtLoginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(edtLoginEmail.getText().toString()) && edtLoginEmail.getText().toString().contains("@") && edtLoginEmail.getText().toString().contains(".")){
                    edtLoginEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.at_email_icon,0,R.drawable.check_tick_icon,0);
                }else{
                    edtLoginEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.at_email_icon,0,0,0);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                //Nothing
            }
        });

        //On Forgot password textView clicked
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(!TextUtils.isEmpty(edtLoginEmail.getText().toString())){
                    mAuth.sendPasswordResetEmail(edtLoginEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Password reset link sucessfully sent to enetered Email-ID !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Email address required !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //On login btn click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtLoginEmail.getText().toString();
                String password = edtLoginPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "All fields required !", Toast.LENGTH_SHORT).show();
                }else {
                    customProgressDialog.show();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("FBAuth", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        customProgressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Sign in Successfull !", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("FBAuth", "createUserWithEmail:failure", task.getException());
                                        customProgressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                }
            }
        });

    }
}