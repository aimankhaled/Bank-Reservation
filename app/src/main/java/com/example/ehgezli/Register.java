package com.example.ehgezli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView bannerTextView,registerTextView;
    private EditText fullNameEditText,ageEditText,emailEditText,passwordEditText,passwordConfirmEditText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();

        bannerTextView=(TextView) findViewById(R.id.banner);
        bannerTextView.setOnClickListener(this);

        registerTextView = (Button) findViewById(R.id.register_Btn);
        registerTextView.setOnClickListener(this);

        fullNameEditText=(EditText) findViewById(R.id.full_Name);
        ageEditText=(EditText) findViewById(R.id.age);
        emailEditText=(EditText) findViewById(R.id.email_Register);
        passwordEditText=(EditText) findViewById(R.id.password_Register);
        passwordConfirmEditText=(EditText) findViewById(R.id.password_Register_Confirm);

        progressBar = (ProgressBar) findViewById(R.id.progress_Bar);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case  R.id.banner:
                startActivity(new Intent(this,Login.class));
                break;

            case  R.id.register_Btn:
                registerUser();
                break;
        }
    }

    private void  registerUser()
    {
        String email = emailEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String fullName = fullNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordConfirm = passwordConfirmEditText.getText().toString().trim();

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
        if(email.isEmpty())
        {
            emailEditText.setError("Email Address is required !");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError("Please provide valid email !");
            emailEditText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordEditText.setError("Password is required !");
            passwordEditText.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            passwordEditText.setError("Min password length should be 6 characters !");
            passwordEditText.requestFocus();
            return;
        }
        if(!password.equals(passwordConfirm))
        {
            passwordConfirmEditText.setError("Password does't match !");
            passwordConfirmEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user = new User(fullName,age,email,"","");
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                                        user.sendEmailVerification();
                                        Toast.makeText(Register.this,"User has been registered successfully !",Toast.LENGTH_LONG).show();
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(Register.this, Login.class));
                                        //redirect to login layout!
                                    }
                                    else
                                    {
                                        Toast.makeText(Register.this,"Failed to register ! Try Again !",Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(Register.this,"Failed to register ! Try Again !",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
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
}