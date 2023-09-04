package com.sarmadali.chatmingle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.sarmadali.chatmingle.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //3 vertical dots in toolbar color change
        Objects.requireNonNull(toolbar.getOverflowIcon()).setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        firebaseAuth = FirebaseAuth.getInstance();


    }

    //to inflate the menu && toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbarmenu, menu);

        return super.onCreateOptionsMenu(menu);

    }
    //action on menu item clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.newgroup) {
            Toast.makeText(this, "This is New Group", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.newbroadcast) {
            Toast.makeText(this, "This is New Broadcast", Toast.LENGTH_SHORT).show();
        }
        //for Sign out
        else if (itemId == R.id.logout) {

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}