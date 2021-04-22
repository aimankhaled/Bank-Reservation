package com.example.ehgezli;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyReservations extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private FirebaseDatabase fire;
    private DatabaseReference ref;
    private FirebaseUser user;
    private  String userId;
    private ListView listView;
    private ArrayList<String> reservationList;
    private ArrayAdapter<String> adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreservations);
        listView = findViewById(R.id.listView);
        reservationList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.reservation_info,R.id.resrevationInfo,reservationList);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId=user.getUid();
        fire = FirebaseDatabase.getInstance();
        ref = fire.getReference("Reservations");

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
                    Toast.makeText(MyReservations.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.Login.class);
                    startActivity(intent);
                    finish();
                }
                return true;

            }
        });


ref.child(userId).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
if(snapshot.exists()){
    reservationList.clear();
    for(DataSnapshot Ds:snapshot.getChildren()){
ReservationEntity res = Ds.getValue(ReservationEntity.class);
reservationList.add(res.Operation + "   "+res.Date);
    }
}
listView.setAdapter(adapter);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
