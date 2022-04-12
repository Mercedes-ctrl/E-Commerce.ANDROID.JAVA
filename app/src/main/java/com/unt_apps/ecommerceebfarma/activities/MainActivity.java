package com.unt_apps.ecommerceebfarma.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.SliderView;
import com.unt_apps.ecommerceebfarma.R;
import com.unt_apps.ecommerceebfarma.adapters.SliderAdapter;
import com.unt_apps.ecommerceebfarma.adapters.sliderBanner;
import com.unt_apps.ecommerceebfarma.fragments.HomeFragment;
import com.unt_apps.ecommerceebfarma.models.SliderData;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    FirebaseAuth auth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);


        homeFragment = new HomeFragment();
        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment homeFragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
          int id=item.getItemId();
          if(id==R.id.menu_logout){
             auth.signOut();
             startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
             finish();
          }else
          if(id==R.id.meny_my_cart){
              startActivity(new Intent(MainActivity.this,CartActivity.class));
          }
          return true;
    }

}