package com.example.ehgezli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CairoBranches extends AppCompatActivity {
    private Button wustElBalad_BranchBtn,madinetNasr_BranchBtn,heliopolis_BranchBtn,elMaadi_BranchBtn,elMohandessin_BranchBtn,eldokki_BranchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cairo_branches);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wustElBalad_BranchBtn=(Button) findViewById(R.id.wustElBalad_BranchBtn);
        wustElBalad_BranchBtn.setOnClickListener(this::onClick);

        madinetNasr_BranchBtn=(Button) findViewById(R.id.madinetNasr_BranchBtn);
        madinetNasr_BranchBtn.setOnClickListener(this::onClick);

        heliopolis_BranchBtn=(Button) findViewById(R.id.heliopolis_BranchBtn);
        heliopolis_BranchBtn.setOnClickListener(this::onClick);

        elMaadi_BranchBtn=(Button) findViewById(R.id.elMaadi_BranchBtn);
        elMaadi_BranchBtn.setOnClickListener(this::onClick);

        elMohandessin_BranchBtn=(Button) findViewById(R.id.elMohandessin_BranchBtn);
        elMohandessin_BranchBtn.setOnClickListener(this::onClick);

        eldokki_BranchBtn=(Button) findViewById(R.id.eldokki_BranchBtn);
        eldokki_BranchBtn.setOnClickListener(this::onClick);
    }
    public void onClick(View v) {
        Intent intent = new Intent(CairoBranches.this, BankActivity.class);
        switch (v.getId()) {
            case R.id.wustElBalad_BranchBtn:
                intent.putExtra("BranchNumber", "1");
                startActivity(intent);
                break;

            case R.id.madinetNasr_BranchBtn:
                intent.putExtra("BranchNumber", "2");
                startActivity(intent);
                break;
            case R.id.heliopolis_BranchBtn:
                intent.putExtra("BranchNumber", "3");
                startActivity(intent);
                break;
            case R.id.elMaadi_BranchBtn:
                intent.putExtra("BranchNumber", "4");
                startActivity(intent);
                break;
            case R.id.elMohandessin_BranchBtn:
                intent.putExtra("BranchNumber", "5");
                startActivity(intent);
                break;
            case R.id.eldokki_BranchBtn:
                intent.putExtra("BranchNumber", "6");
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