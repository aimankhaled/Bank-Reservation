package com.example.ehgezli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Help extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    Button sendQuestion_Btn;
    TextInputEditText textInputEditTextQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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
                else if(id==R.id.help)
                {
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.Help.class);
                    startActivity(intent);
                    finish();
                }
                else if(id==R.id.reservations)
                {
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.MyReservations.class);
                    startActivity(intent);
                    finish();
                }
                else if(id==R.id.logout)
                {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(Help.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.Login.class);
                    startActivity(intent);
                    finish();
                }
                return true;

            }
        });

        textInputEditTextQuestion=findViewById(R.id.question_InputText);
        sendQuestion_Btn = findViewById(R.id.sendQuestion_Btn);
        sendQuestion_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=textInputEditTextQuestion.getText().toString();
                if(text.isEmpty())
                {
                    Toast.makeText(Help.this,"Please write your question",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Help.this,"Someone will contact you as soon as possible",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}