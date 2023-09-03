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
import com.google.firebase.database.FirebaseDatabase;
import com.sarmadali.chatmingle.Models.Users;
import com.sarmadali.chatmingle.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding signUpBinding;

    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());

        //on change in activity signup to sign in
        signUpBinding.AlreadhaveAccountSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //for progress dialogue after signup if success or not
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account!");

        signUpBinding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show progress dialogue
                progressDialog.show();

                auth.createUserWithEmailAndPassword(
                        signUpBinding.textEmailsignup.getText().toString(),
                        signUpBinding.textPasswordsignup.getText().toString())
                        .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if(task.isSuccessful()){
                                    //using Users constructor for signup
                                    Users users = new Users(
                                            signUpBinding.textUsernameSignUp.getText().toString(),
                                            signUpBinding.textEmailsignup.getText().toString(),
                                            signUpBinding.textPasswordsignup.getText().toString()
                                    );

                                    //* !to remember --> always check rules in firebase realtime database
                                    //  to retrieve and store data.

                                    //to get the UID of user from AuthResult
                                    String id = task.getResult().getUser().getUid();
                                    //setting values in realtime database
                                    firebaseDatabase.getReference().child("Users").
                                            //we can use id and username or anyother string as we
                                            // want in the below child to name the user node.
                                            child(signUpBinding.textUsernameSignUp.getText().toString())
                                            .setValue(users);


                                    Toast.makeText(SignUpActivity.this, "User Created Successfully ",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

    }

}