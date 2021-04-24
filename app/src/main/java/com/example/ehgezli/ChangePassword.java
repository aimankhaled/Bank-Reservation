package com.example.ehgezli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    private FirebaseUser user;
    private EditText oldPassword_ChangePw,newPassword_ChangePw,confirmNewPassword_ChangePw;
    private Button changePassword_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        oldPassword_ChangePw = (EditText) findViewById(R.id.oldPassword_ChangePw);
        newPassword_ChangePw = (EditText) findViewById(R.id.newPassword_ChangePw);
        confirmNewPassword_ChangePw = (EditText) findViewById(R.id.confirmNewPassword_ChangePw);

        changePassword_Btn = (Button) findViewById(R.id.changePassword_Btn);

        changePassword_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPw = oldPassword_ChangePw.getText().toString().trim();
                String newPw = newPassword_ChangePw.getText().toString().trim();
                String confirmNewPw = confirmNewPassword_ChangePw.getText().toString().trim();


                if (oldPw.isEmpty()) {
                    oldPassword_ChangePw.setError("Old Password is required !");
                    oldPassword_ChangePw.requestFocus();
                    return;
                }
                if (newPw.isEmpty()) {
                    newPassword_ChangePw.setError("Old Password is required !");
                    newPassword_ChangePw.requestFocus();
                    return;
                }
                if (newPw.length() < 6) {
                    newPassword_ChangePw.setError("Min password length should be 6 characters !");
                    newPassword_ChangePw.requestFocus();
                    return;
                }
                if (!newPw.equals(confirmNewPw)) {
                    confirmNewPassword_ChangePw.setError("Password does't match !");
                    confirmNewPassword_ChangePw.requestFocus();
                    return;
                }

                user = FirebaseAuth.getInstance().getCurrentUser();
                final String email = user.getEmail();
                AuthCredential credential = EmailAuthProvider.getCredential(email, oldPw);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPw).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChangePassword.this, "Password has been changed successfully !", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangePassword.this, "Failed to change password !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangePassword.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
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