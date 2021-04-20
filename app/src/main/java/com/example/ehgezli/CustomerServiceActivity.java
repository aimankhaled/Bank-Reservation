package com.example.ehgezli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CustomerServiceActivity extends AppCompatActivity {
    private Button operation57_Btn,operation58_Btn,operation59_Btn,operation60_Btn,operation61_Btn,operation62_Btn,operation63_Btn,operation64_Btn,operation65_Btn,operation66_Btn,operation67_Btn,operation68_Btn,operation69_Btn,operation70_Btn,operation71_Btn,operation72_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        operation57_Btn=(Button) findViewById(R.id.operation57_Btn);
        operation57_Btn.setOnClickListener(this::onClick);
        operation58_Btn=(Button) findViewById(R.id.operation58_Btn);
        operation58_Btn.setOnClickListener(this::onClick);
        operation59_Btn=(Button) findViewById(R.id.operation59_Btn);
        operation59_Btn.setOnClickListener(this::onClick);
        operation60_Btn=(Button) findViewById(R.id.operation60_Btn);
        operation60_Btn.setOnClickListener(this::onClick);
        operation61_Btn=(Button) findViewById(R.id.operation61_Btn);
        operation61_Btn.setOnClickListener(this::onClick);
        operation62_Btn=(Button) findViewById(R.id.operation62_Btn);
        operation62_Btn.setOnClickListener(this::onClick);
        operation63_Btn=(Button) findViewById(R.id.operation63_Btn);
        operation63_Btn.setOnClickListener(this::onClick);
        operation64_Btn=(Button) findViewById(R.id.operation64_Btn);
        operation64_Btn.setOnClickListener(this::onClick);
        operation65_Btn=(Button) findViewById(R.id.operation65_Btn);
        operation65_Btn.setOnClickListener(this::onClick);
        operation66_Btn=(Button) findViewById(R.id.operation66_Btn);
        operation66_Btn.setOnClickListener(this::onClick);
        operation67_Btn=(Button) findViewById(R.id.operation67_Btn);
        operation67_Btn.setOnClickListener(this::onClick);
        operation68_Btn=(Button) findViewById(R.id.operation68_Btn);
        operation68_Btn.setOnClickListener(this::onClick);
        operation69_Btn=(Button) findViewById(R.id.operation69_Btn);
        operation69_Btn.setOnClickListener(this::onClick);
        operation70_Btn=(Button) findViewById(R.id.operation70_Btn);
        operation70_Btn.setOnClickListener(this::onClick);
        operation71_Btn=(Button) findViewById(R.id.operation71_Btn);
        operation71_Btn.setOnClickListener(this::onClick);
        operation72_Btn=(Button) findViewById(R.id.operation72_Btn);
        operation72_Btn.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        Intent intent = new Intent(CustomerServiceActivity.this, CustomerServiceReservation.class);
        switch (v.getId()) {
            case R.id.operation57_Btn:
                intent.putExtra("OperationNumber", "57");
                startActivity(intent);
                break;
            case R.id.operation58_Btn:
                intent.putExtra("OperationNumber", "58");
                startActivity(intent);
                break;
            case R.id.operation59_Btn:
                intent.putExtra("OperationNumber", "59");
                startActivity(intent);
                break;
            case R.id.operation60_Btn:
                intent.putExtra("OperationNumber", "60");
                startActivity(intent);
                break;
            case R.id.operation61_Btn:
                intent.putExtra("OperationNumber", "61");
                startActivity(intent);
                break;
            case R.id.operation62_Btn:
                intent.putExtra("OperationNumber", "62");
                startActivity(intent);
                break;
            case R.id.operation63_Btn:
                intent.putExtra("OperationNumber", "63");
                startActivity(intent);
                break;
            case R.id.operation64_Btn:
                intent.putExtra("OperationNumber", "64");
                startActivity(intent);
                break;
            case R.id.operation65_Btn:
                intent.putExtra("OperationNumber", "65");
                startActivity(intent);
                break;
            case R.id.operation66_Btn:
                intent.putExtra("OperationNumber", "66");
                startActivity(intent);
                break;
            case R.id.operation67_Btn:
                intent.putExtra("OperationNumber", "67");
                startActivity(intent);
                break;
            case R.id.operation68_Btn:
                intent.putExtra("OperationNumber", "68");
                startActivity(intent);
                break;
            case R.id.operation69_Btn:
                intent.putExtra("OperationNumber", "69");
                startActivity(intent);
                break;
            case R.id.operation70_Btn:
                intent.putExtra("OperationNumber", "70");
                startActivity(intent);
                break;
            case R.id.operation71_Btn:
                intent.putExtra("OperationNumber", "71");
                startActivity(intent);
                break;
            case R.id.operation72_Btn:
                intent.putExtra("OperationNumber", "72");
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