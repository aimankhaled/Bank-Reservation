package com.example.ehgezli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.SplittableRandom;

public class MyProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;


    private String userId;
    private Button editProfileBtn;
    private TextView fullNameTextView,ageTextView,emailTextView,mobileTextView,addressTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                if (id==R.id.profile)
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
                else if(id==R.id.reservations)
                {
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.MyReservations.class);
                    startActivity(intent);
                    finish();
                }
                else if(id==R.id.help)
                {
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.Help.class);
                    startActivity(intent);
                    finish();
                }
                else if(id==R.id.logout)
                {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MyProfile.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.Login.class);
                    startActivity(intent);
                    finish();
                }
                return true;

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();

        fullNameTextView = (TextView) findViewById(R.id.user_fullName);
        ageTextView = (TextView) findViewById(R.id.user_age);
        emailTextView = (TextView) findViewById(R.id.user_email);
        mobileTextView = (TextView) findViewById(R.id.user_mobile);
        addressTextView = (TextView) findViewById(R.id.user_address);
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null)
                {
                    String fullName=userProfile.fullName;
                    String age=userProfile.age;
                    String email=userProfile.email;
                    String mobile=userProfile.mobile;
                    String address=userProfile.address;

                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    ageTextView.setText(age);
                    mobileTextView.setText(mobile);
                    addressTextView.setText(address);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyProfile.this,"Something wrong happened",Toast.LENGTH_LONG).show();
            }
        });

        editProfileBtn = (Button) findViewById(R.id.editProfile_Btn);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfile.this, EditProfile.class));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}