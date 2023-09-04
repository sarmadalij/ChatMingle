package com.sarmadali.chatmingle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sarmadali.chatmingle.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding signInBinding;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(signInBinding.getRoot());

        //on change in activity signin to signup
        signInBinding.DonthaveAccountSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();

        //for progress dialogue after signup if success or not
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account!");

        //for sign in functionality
        signInBinding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show progress dialogue
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(
                        signInBinding.textEmailsignin.getText().toString(),
                        signInBinding.textPasswordsignin.getText().toString()
                        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //close progress dialogue
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        // if user is already login then start with mainActivity
        if (firebaseAuth.getCurrentUser()!=null){
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        }


    }
}