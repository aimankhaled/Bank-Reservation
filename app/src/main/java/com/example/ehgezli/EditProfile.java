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
import com.squareup.picasso.Picasso;

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
    private Button updateProfileBtn,editProfilePic_Btn;
    private ProgressBar progressBar;
    private ImageView profilePic_Image;
    private StorageReference storageReference;
    private static final int image_pick_code = 1000;
    private static final int permission_code = 1001;
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

        profilePic_Image = (ImageView) findViewById(R.id.profilePic_Image);

        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef=storageReference.child("users/"+user.getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic_Image);
            }
        });
        editProfilePic_Btn=(Button)findViewById(R.id.editProfilePic_Btn);
        editProfilePic_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent openGallaryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(openGallaryIntent,1000);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
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
                Toast.makeText(EditProfile.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditProfile.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }


}