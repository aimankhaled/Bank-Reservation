package com.example.ehgezli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class BankActivity extends AppCompatActivity {
    private Button teller_Btn,customerService_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        teller_Btn=(Button) findViewById(R.id.teller_Btn);
        teller_Btn.setOnClickListener(this::onClick);

        customerService_Btn=(Button) findViewById(R.id.customerService_Btn);
        customerService_Btn.setOnClickListener(this::onClick);

    }
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.teller_Btn:
                intent = new Intent(BankActivity.this, TellerActivity.class);
                intent.putExtra("BankService", "Teller");
                startActivity(intent);
                break;

            case R.id.customerService_Btn:
                intent = new Intent(BankActivity.this, CustomerServiceActivity.class);
                intent.putExtra("BankService", "Customer Service");
                startActivity(intent);
                break;
        }
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