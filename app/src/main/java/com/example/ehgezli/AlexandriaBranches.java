package com.example.ehgezli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AlexandriaBranches extends AppCompatActivity {
    private Button sultanHussien_BranchBtn,smouha_BranchBtn,gleem_BranchBtn,elsaraya_BranchBtn,kafrabdou_BranchBtn,louran_BranchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alexandria_branches);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sultanHussien_BranchBtn=(Button) findViewById(R.id.sultanHussien_BranchBtn);
        sultanHussien_BranchBtn.setOnClickListener(this::onClick);

        smouha_BranchBtn=(Button) findViewById(R.id.smouha_BranchBtn);
        smouha_BranchBtn.setOnClickListener(this::onClick);

        gleem_BranchBtn=(Button) findViewById(R.id.gleem_BranchBtn);
        gleem_BranchBtn.setOnClickListener(this::onClick);

        elsaraya_BranchBtn=(Button) findViewById(R.id.elsaraya_BranchBtn);
        elsaraya_BranchBtn.setOnClickListener(this::onClick);

        kafrabdou_BranchBtn=(Button) findViewById(R.id.kafrabdou_BranchBtn);
        kafrabdou_BranchBtn.setOnClickListener(this::onClick);

        louran_BranchBtn=(Button) findViewById(R.id.louran_BranchBtn);
        louran_BranchBtn.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        Intent intent = new Intent(AlexandriaBranches.this, BankActivity.class);
        switch (v.getId()) {
            case R.id.sultanHussien_BranchBtn:
                intent.putExtra("BranchNumber", "10");
                startActivity(intent);
                break;

            case R.id.smouha_BranchBtn:
                intent.putExtra("BranchNumber", "11");
                startActivity(intent);
                break;
            case R.id.gleem_BranchBtn:
                intent.putExtra("BranchNumber", "12");
                startActivity(intent);
                break;
            case R.id.elsaraya_BranchBtn:
                intent.putExtra("BranchNumber", "13");
                startActivity(intent);
                break;
            case R.id.kafrabdou_BranchBtn:
                intent.putExtra("BranchNumber", "14");
                startActivity(intent);
                break;
            case R.id.louran_BranchBtn:
                intent.putExtra("BranchNumber", "15");
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