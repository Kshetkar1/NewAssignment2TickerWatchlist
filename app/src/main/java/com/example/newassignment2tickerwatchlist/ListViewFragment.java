
package com.example.newassignment2tickerwatchlist;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;

public class ListViewFragment extends ViewModel {
    //can change the data it can hold
    MutableLiveData<LinkedList<String>> tickers = new MutableLiveData<>();
    LinkedList<String> tickerList = new LinkedList<>();
    MutableLiveData<String> selectedTicker = new MutableLiveData<>();
    int currentIndex = 0;


    //method: sets the ticker
    public void setSelectedTicker(String ticker){
        selectedTicker.setValue(ticker);
    }

    //method: returns the selectedTicker
    public LiveData<String> getSelectedTicker(){
        return selectedTicker;
    }


    public LiveData<LinkedList<String>> getTickers(){
        //takes data from setTicker and populates the linkedList of the list is empty
        if (tickerList.size() == 0) setTickers();

        return tickers;
    }

    public void setTickers(){
        if(tickerList.size() == 0){
            tickerList.add("AAPL");
            tickerList.add("TSLA");
            tickerList.add("SBUX");
            tickers.setValue(tickerList);
        }
    }

    //check size of the tickerList. see if the ticker its on is already selected or not
    // it will update the ticker already present or add a new ticker

    public void addTickers(String ticker){

        if(tickerList.size() >= 6 && !tickerList.contains(ticker)){
            tickerList.set(currentIndex, ticker);
            currentIndex = (currentIndex + 1) % 6;
        } else if(tickerList.size() < 6 && !tickerList.contains(ticker)) {
            tickerList.add(ticker);
            tickers.setValue(tickerList);
        }
    }


}