package com.example.newassignment2tickerwatchlist;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;


public class TickerList extends Fragment {
    ListView listview;
    ArrayAdapter<String> adapter;
    ListViewFragment ViewModel;

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String ticker = (String) adapterView.getItemAtPosition(i);
            ViewModel.setSelectedTicker(ticker); // sets the selected ticker
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModel = new ViewModelProvider(getActivity()).get(ListViewFragment.class);
        //observes changes in the listview
        Observer<LinkedList<String>> observer = new Observer<LinkedList<String>>() {
            @Override
            public void onChanged(LinkedList<String> strings) {
                LinkedList<String> tickers = ViewModel.getTickers().getValue();
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tickers);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(clickListener);
                adapter.notifyDataSetChanged();
            }
        };

        ViewModel.getTickers().observe(getViewLifecycleOwner(),observer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        listview = view.findViewById(R.id.listview_id);
        return view;
    }


}