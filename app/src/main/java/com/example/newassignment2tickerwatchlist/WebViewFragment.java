
package com.example.newassignment2tickerwatchlist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewFragment extends Fragment {
    WebView webview;
    ListViewFragment myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
       //finds the widget in the layout and assignes it a variahle
        webview = view.findViewById(R.id.webview_id);
        return view;
    }

    //takes URL and loads it
    public void loadUrl(String url){
        webview.loadUrl(url);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        //try super
        super.onActivityCreated(savedInstanceState);

        myViewModel = new ViewModelProvider(getActivity()).get(ListViewFragment.class);
        webview.getSettings().setJavaScriptEnabled(true);
        loadUrl("https://seekingalpha.com/");

       // sets up the user to watch the changes and also updates that speicic URL
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String i) {


                if (i != null){
                    String url = "https://seekingalpha.com/symbol/" + i;
                    loadUrl(url);
                } else {
                    loadUrl("https://seekingalpha.com/");
                }
            }
        };






        myViewModel.getSelectedTicker().observe(getViewLifecycleOwner(),observer);

    }
}
