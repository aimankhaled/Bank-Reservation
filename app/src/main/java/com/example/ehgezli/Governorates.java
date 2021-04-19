package com.example.ehgezli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Governorates extends AppCompatActivity {


    private Button alxBtn,cairoBtn,octoberBtn,mansouraBtn,portSaidBtn,asyutBtn,sharmBtn,hurghadaBtn,elgounaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_governorates);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        alxBtn=(Button) findViewById(R.id.alex_Btn);
        alxBtn.setOnClickListener(this::onClick);

        cairoBtn=(Button) findViewById(R.id.cairo_Btn);
        cairoBtn.setOnClickListener(this::onClick);

        octoberBtn=(Button) findViewById(R.id.october_Btn);
        octoberBtn.setOnClickListener(this::onClick);

        mansouraBtn=(Button) findViewById(R.id.elmansoura_Btn);
        mansouraBtn.setOnClickListener(this::onClick);

        portSaidBtn=(Button) findViewById(R.id.portsaid_Btn);
        portSaidBtn.setOnClickListener(this::onClick);

        asyutBtn=(Button) findViewById(R.id.asyut_Btn);
        asyutBtn.setOnClickListener(this::onClick);

        sharmBtn=(Button) findViewById(R.id.sharm_Btn);
        sharmBtn.setOnClickListener(this::onClick);

        hurghadaBtn=(Button) findViewById(R.id.hurghada_Btn);
        hurghadaBtn.setOnClickListener(this::onClick);

        elgounaBtn=(Button) findViewById(R.id.elgouna_Btn);
        elgounaBtn.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alex_Btn:
                startActivity(new Intent(Governorates.this, AlexandriaBranches.class));
                break;

            case R.id.cairo_Btn:
                startActivity(new Intent(Governorates.this, CairoBranches.class));
                break;
            case R.id.october_Btn:
                startActivity(new Intent(Governorates.this, OctoberBranches.class));
                break;
            case R.id.elmansoura_Btn:
                startActivity(new Intent(Governorates.this, ElMansouraBranches.class));
                break;
            case R.id.portsaid_Btn:
                startActivity(new Intent(Governorates.this, PortSaidBranches.class));
                break;
            case R.id.asyut_Btn:
                startActivity(new Intent(Governorates.this, AsyutBranches.class));
                break;
            case R.id.sharm_Btn:
                startActivity(new Intent(Governorates.this, SharmElsheikhBranches.class));
                break;
            case R.id.hurghada_Btn:
                startActivity(new Intent(Governorates.this, HurghadaBranches.class));
                break;
            case R.id.elgouna_Btn:
                startActivity(new Intent(Governorates.this, ElGounaBranches.class));
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