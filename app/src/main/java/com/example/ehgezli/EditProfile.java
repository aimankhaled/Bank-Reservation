package com.example.ehgezli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class EditProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private EditText mobileEditText,fullNameEditText,ageEditText,addressEditText;
    private Button updateProfileBtn;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_save);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullNameEditText = (EditText) findViewById(R.id.editUser_fullName);
        ageEditText = (EditText) findViewById(R.id.editUser_age);
        mobileEditText = (EditText) findViewById(R.id.editUser_mobile);
        addressEditText = (EditText) findViewById(R.id.editUser_address);
        progressBar = (ProgressBar) findViewById(R.id.progress_Bar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null)
                {
                    String fullName=userProfile.fullName;
                    String age=userProfile.age;
                    String mobile=userProfile.mobile;
                    String address=userProfile.address;

                    fullNameEditText.setText(fullName);
                    ageEditText.setText(age);
                    mobileEditText.setText(mobile);
                    addressEditText.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfile.this,"Something wrong happened",Toast.LENGTH_LONG).show();
            }
        });

        updateProfileBtn=(Button) findViewById(R.id.saveProfile_Btn);
        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName=fullNameEditText.getText().toString().trim();
                String age=ageEditText.getText().toString().trim();
                String mobile=mobileEditText.getText().toString().trim();
                String address=addressEditText.getText().toString().trim();


                if(fullName.isEmpty())
                {
                    fullNameEditText.setError("Full name is required !");
                    fullNameEditText.requestFocus();
                    return;
                }
                if(age.isEmpty())
                {
                    ageEditText.setError("Age is required !");
                    ageEditText.requestFocus();
                    return;
                }

                if(mobile.isEmpty())
                {
                    mobileEditText.setError("Mobile is required !");
                    mobileEditText.requestFocus();
                    return;
                }

                if(address.isEmpty())
                {
                    addressEditText.setError("Address is required !");
                    addressEditText.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                reference.child(userId).child("fullName").setValue(fullName);
                reference.child(userId).child("mobile").setValue(mobile);
                reference.child(userId).child("address").setValue(address);
                reference.child(userId).child("age").setValue(age).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(EditProfile.this,"User has been updated successfully !",Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(getApplicationContext(), com.example.ehgezli.MyProfile.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(EditProfile.this,"Failed to update ! Try Again !",Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}