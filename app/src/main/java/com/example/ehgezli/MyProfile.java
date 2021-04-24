package com.example.ehgezli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.SplittableRandom;

public class MyProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private StorageReference storageReference;

    private static final int image_pick_code = 1000;
    private static final int permission_code = 1001;


    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;


    private ImageView profilePic_Image;
    private String userId;
    private Button editProfileBtn,editProfilePic_Btn,changePassword_NavBtn;
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
        changePassword_NavBtn = (Button) findViewById(R.id.changePassword_NavBtn);
        changePassword_NavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.ChangePassword.class);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id==R.id.profile)
                {
                    Intent intent= new Intent(MyProfile.this,MyProfile.class);
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




        fullNameTextView = (TextView) findViewById(R.id.user_fullName);
        ageTextView = (TextView) findViewById(R.id.user_age);
        emailTextView = (TextView) findViewById(R.id.user_email);
        mobileTextView = (TextView) findViewById(R.id.user_mobile);
        addressTextView = (TextView) findViewById(R.id.user_address);

        profilePic_Image = (ImageView) findViewById(R.id.profilePic_Image);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();

        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef=storageReference.child("users/"+user.getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic_Image);
            }
        });


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

        
        editProfilePic_Btn=(Button)findViewById(R.id.editProfilePic_Btn);
        editProfilePic_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission not granted ask for it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, permission_code);
                    } else {
                        //permission granted
                        pickImageFromGallery();
                    }
                } else {
                    //system os less than marshmallow
                    pickImageFromGallery();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, image_pick_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case permission_code:{
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    pickImageFromGallery();
                }
                else
                {
                    Toast.makeText(this,"Permission denied... !",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri)
    {
        StorageReference fileRef = storageReference.child("users/"+user.getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MyProfile.this,"Profile picture changed successfully!",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilePic_Image);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MyProfile.this,"Failed to change profile picture!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}