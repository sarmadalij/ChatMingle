package com.sarmadali.chatmingle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.sarmadali.chatmingle.Adapters.FragmentsAdapter;
import com.sarmadali.chatmingle.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    FirebaseAuth firebaseAuth;

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    String namesOftabLayout [] = {"Chats","Status","Calls"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //3 vertical dots in toolbar color change
        Objects.requireNonNull(toolbar.getOverflowIcon()).setColorFilter(Color.WHITE ,
                PorterDuff.Mode.SRC_ATOP);

        firebaseAuth = FirebaseAuth.getInstance();


        //find id
        viewPager2 = findViewById(R.id.viewPagermain);

        FragmentsAdapter fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(fragmentsAdapter);

        tabLayout = findViewById(R.id.tablayoutMain);
//        tabLayout.setupWithViewPager(viewPager2);

        // to connect the tabLayout and viewPager2
        //and also, it sets the title of the tabLayout
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(namesOftabLayout[position]);
        }).attach();


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
            Toast.makeText(this, "This is New newbroadcast", Toast.LENGTH_SHORT).show();

        } else if (itemId == R.id.linkeddevices) {
            Toast.makeText(this, "This is New linkeddevices", Toast.LENGTH_SHORT).show();

        } else if (itemId == R.id.starredmessages) {
            Toast.makeText(this, "This is New starredmessages", Toast.LENGTH_SHORT).show();

        } else if (itemId == R.id.settings) {
            Toast.makeText(this, "This is New settings", Toast.LENGTH_SHORT).show();

        }
        //for Sign out
        else if (itemId == R.id.logout) {

            firebaseAuth.signOut();
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}