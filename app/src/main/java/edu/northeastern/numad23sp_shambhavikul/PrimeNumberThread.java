package edu.northeastern.numad23sp_shambhavikul;

import android.os.Handler;
import android.widget.TextView;

public class PrimeNumberThread implements Runnable {
    private long count;
    private TextView primeNumberView;
    private TextView lastSearch;
    private Handler handler;


    public PrimeNumberThread(TextView view,TextView lastSearch, Handler handler,long count) {
        this.primeNumberView = view;
        this.handler = handler;
        this.lastSearch = lastSearch;
        this.count = count;
    }

    @Override
    public void run() {
        long nextPrime = nextPrime();
        System.out.println("Setting value : " + count);

        this.primeNumberView.setText("Current Prime number is: "+ String.valueOf(nextPrime));


        handler.postDelayed(this, 1000);
    }

    public boolean isPrime(long num){
        long i=2;
        boolean flag = false;
        while (i <= num / 2) {
            if (num % i == 0) {
                flag = true;
                break;
            }
            ++i;
        }
        return !flag;
    }

    public long nextPrime() {
        this.count++;
        while(!isPrime(this.count)) {
            if(this.count%10 ==0){
                this.lastSearch.setText("Last number checked is: "+this.count);
            }
            this.count++;
        }
        return this.count;
    }
}

