package edu.northeastern.numad23sp_shambhavikul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PrimeDirectiveActivity extends AppCompatActivity {

    public volatile Boolean stopThread = false;
    private Button findPrime;
    private Button terminateSearch;
    private TextView primeNumberView;
    private TextView lastSearch;
    private CheckBox toggle;
    private Handler mHandler;
    public volatile static long startNumber=2;

    private PrimeNumberThread primeNumberThread;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_directive);

        primeNumberView  = (TextView) findViewById(R.id.prime_textView);
        lastSearch = findViewById(R.id.current_numberText);

        handler = new Handler();

        Button prime_directive = (Button) findViewById(R.id.find_prime);
        if (prime_directive != null) {
            lastSearch.setText("Current number checked is");
            prime_directive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startFindPrimeNumbersThread();
                }
            });
        }

        Button terminate_search = (Button) findViewById(R.id.terminate_search);
        if (terminate_search != null) {
            terminate_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopFindPrimeNumbersThread();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Return Back")
                .setMessage("Are you sure you want terminate search and return back")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopFindPrimeNumbersThread();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }


    private void startFindPrimeNumbersThread() {
        lastSearch.setText("Last number checked is 3");
        primeNumberView.setText("Current Prime number is: ");
        primeNumberThread = new PrimeNumberThread(primeNumberView, lastSearch, handler, startNumber);
        handler.postDelayed(primeNumberThread, 1000);

    }

    private boolean stopFindPrimeNumbersThread() {
        handler.removeCallbacksAndMessages(null);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String textToStore = primeNumberView.getText().toString();
        String textToCheck = lastSearch.getText().toString();
        outState.putString("key", textToStore);
        outState.putString("key1", textToCheck);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String textToStore = primeNumberView.getText().toString();
        String textToCheck = lastSearch.getText().toString();
        textToStore = savedInstanceState.getString("key", "0");
        textToCheck = savedInstanceState.getString("key1","0");
        primeNumberView.setText(textToStore);
        lastSearch.setText(textToCheck);

    }

}