package com.example.ehgezli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TellerActivity extends AppCompatActivity {
    private Button operation50_Btn,operation51_Btn,operation52_Btn,operation53_Btn,operation54_Btn,operation55_Btn,operation56_Btn;
    private String branchId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teller);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        branchId =  getIntent().getStringExtra("BranchNumber");

        operation50_Btn=(Button) findViewById(R.id.operation50_Btn);
        operation50_Btn.setOnClickListener(this::onClick);
        operation51_Btn=(Button) findViewById(R.id.operation51_Btn);
        operation51_Btn.setOnClickListener(this::onClick);
        operation52_Btn=(Button) findViewById(R.id.operation52_Btn);
        operation52_Btn.setOnClickListener(this::onClick);
        operation53_Btn=(Button) findViewById(R.id.operation53_Btn);
        operation53_Btn.setOnClickListener(this::onClick);
        operation54_Btn=(Button) findViewById(R.id.operation54_Btn);
        operation54_Btn.setOnClickListener(this::onClick);
        operation55_Btn=(Button) findViewById(R.id.operation55_Btn);
        operation55_Btn.setOnClickListener(this::onClick);
        operation56_Btn=(Button) findViewById(R.id.operation56_Btn);
        operation56_Btn.setOnClickListener(this::onClick);
    }
    public void onClick(View v) {
        Intent intent = new Intent(TellerActivity.this, TellerReservation.class);
        intent.putExtra("BranchNumber", branchId);
        switch (v.getId()) {
            case R.id.operation50_Btn:
                intent.putExtra("OperationNumber", "50");
                startActivity(intent);
                break;
            case R.id.operation51_Btn:
                intent.putExtra("OperationNumber", "51");
                startActivity(intent);
                break;
            case R.id.operation52_Btn:
                intent.putExtra("OperationNumber", "52");
                startActivity(intent);
                break;
            case R.id.operation53_Btn:
                intent.putExtra("OperationNumber", "53");
                startActivity(intent);
                break;
            case R.id.operation54_Btn:
                intent.putExtra("OperationNumber", "54");
                startActivity(intent);
                break;
            case R.id.operation55_Btn:
                intent.putExtra("OperationNumber", "55");
                startActivity(intent);
                break;
            case R.id.operation56_Btn:
                intent.putExtra("OperationNumber", "56");
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