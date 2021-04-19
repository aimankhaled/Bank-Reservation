package com.example.ehgezli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class RateUs extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    RatingBar ratingStars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);
        dl=(DrawerLayout)findViewById(R.id.dl);
        abdt=new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id==R.id.profile)
                {
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.MyProfile.class);
                    startActivity(intent);
                    finish();
                }
                else if(id==R.id.rate)
                {
                    Intent intent = new Intent(getApplicationContext(), com.example.ehgezli.RateUs.class);
                    startActivity(intent);
                    finish();
                }
                else if(id==R.id.home)
                {
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(id==R.id.logout)
                {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(RateUs.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.Login.class);
                    startActivity(intent);
                    finish();
                }
                return true;

            }
        });

        ratingStars = findViewById(R.id.ratingBar);
        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int r = (int) rating;
                String message = null;
                switch (r)
                {
                    case 1:
                        message="Sorry to hear that! :(";
                        break;
                    case 2:
                        message="We always accept suggestions";
                        break;
                    case 3:
                        message="Good enough!";
                        break;
                    case 4:
                        message="Great! Thank you :)";
                        break;
                    case 5:
                        message="Awesome! You are the best :)";
                        break;

                }
                Toast.makeText(RateUs.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}