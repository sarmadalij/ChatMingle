package com.sarmadali.chatmingle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.sarmadali.chatmingle.Models.Users;
import com.sarmadali.chatmingle.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding signInBinding;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    BeginSignInRequest signInRequest;
    GoogleSignInClient googleSignInClient;
    FirebaseDatabase firebaseDatabase;

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
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //for progress dialogue after signup if success or not
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account!");

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);



        //Google Sign In
        signInBinding.buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();

            }

        });
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
                            finish();
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
            finish();
        }


    }

    int RC_SIGN_IN = 65;
    private void signIn(){
        // clearing previous signin caches
        googleSignInClient.signOut();

        Intent signIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Result returned from launchin the intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            }catch (ApiException e){
                Log.w("TAG", "Google Sign in Failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken){
        //close progress dialogue
        progressDialog.show();
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //close progress dialogue
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            //sign in success, update UI with the signed-in user's infromation
                            Log.d("TAG","signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Users users = new Users();
                            users.setUserId(user.getUid());
                            users.setUserName(user.getDisplayName());
                            users.setProfilePic(user.getPhotoUrl().toString());

                            // to set the data on realtime database
                            firebaseDatabase.getReference().child("Users").child(user.getUid()).setValue(users);


                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            // updateUI(user);
                        } else {
                            // if sign in fails, display a message to a user
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                          Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                          updateUI(null);
                        }
                    }
                });
    }
}