package com.example.ehgezli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class OctoberBranches extends AppCompatActivity {
    private Button mallElArab_BranchBtn,dandyMall_BranchBtn,smartVillage_BranchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_october);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mallElArab_BranchBtn=(Button) findViewById(R.id.mallElArab_BranchBtn);
        mallElArab_BranchBtn.setOnClickListener(this::onClick);

        dandyMall_BranchBtn=(Button) findViewById(R.id.dandyMall_BranchBtn);
        dandyMall_BranchBtn.setOnClickListener(this::onClick);

        smartVillage_BranchBtn=(Button) findViewById(R.id.smartVillage_BranchBtn);
        smartVillage_BranchBtn.setOnClickListener(this::onClick);
    }
    public void onClick(View v) {
        Intent intent = new Intent(OctoberBranches.this, BankActivity.class);
        switch (v.getId()) {
            case R.id.mallElArab_BranchBtn:
                intent.putExtra("BranchNumber", "7");
                startActivity(intent);
                break;

            case R.id.dandyMall_BranchBtn:
                intent.putExtra("BranchNumber", "8");
                startActivity(intent);
                break;
            case R.id.smartVillage_BranchBtn:
                intent.putExtra("BranchNumber", "9");
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