package edu.northeastern.numad23sp_shambhavikul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wire up the button
        Button btn = (Button) findViewById(R.id.btnAbtMe);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Shambhavi Kulkarni kulkarni.sham@northeastern.edu", Toast.LENGTH_SHORT)
                //        .show();
                Intent intent = new Intent(MainActivity.this, AboutMe.class);
                startActivity(intent);
            }
        });

        Button btn2 = (Button) findViewById(R.id.clickybtn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        Button btn3 = (Button) findViewById(R.id.btnlink);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LinkCollectorActivity.class);
                startActivity(intent);
            }
        });

        Button btn4 = (Button) findViewById(R.id.btnPrime);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrimeDirectiveActivity.class);
                startActivity(intent);
            }
        });

        Button btn5 = (Button) findViewById(R.id.btnLocation);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
    }
}