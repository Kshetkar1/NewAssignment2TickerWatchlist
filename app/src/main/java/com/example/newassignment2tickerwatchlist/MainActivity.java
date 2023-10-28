
package com.example.newassignment2tickerwatchlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    ListViewFragment customViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String message = intent.getStringExtra("SMS");

      //if the activity is being created then add the tickerList and WebView fragment to it
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.topTicker_id, new TickerList()).commit();
            fragmentManager.beginTransaction().replace(R.id.bottomWeb_id, new WebViewFragment()).commit();
        }

        //manage the data
        customViewModel = new ViewModelProvider(this).get(ListViewFragment.class);

        //sets it up.. sees if it recieved the message, if not then requests for it from the user
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED){
            String[] perms = new String[]{android.Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this,perms, 1000);
        }


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("sms");
        if(!message.contains("Ticker:<<") || !message.contains(">>")){
            recreate();
            Toast.makeText(this, "No valid watchlist entry was found", Toast.LENGTH_SHORT).show();
        } else {
            int tickerBegin = message.lastIndexOf('<');
            int tickerEnd = message.indexOf('>');
            String ticker = message.substring(tickerBegin + 1, tickerEnd).toUpperCase();
            if (isValidTicker(ticker) == false){
                recreate();
                Toast.makeText(this, "The ticker was invalid", Toast.LENGTH_SHORT).show();
            } else {
                recreate();
                customViewModel.addTickers(ticker);
                customViewModel.setSelectedTicker(ticker);
            }
        }
    }

    //takes in ticker to see if has characters.
    public boolean isValidTicker(String ticker){
        for (int i = 0; i < ticker.length(); i++){
            if((Character.isLetter(ticker.charAt(i)) == false)){
                return false;
            }
        }
        return true;
    }



}