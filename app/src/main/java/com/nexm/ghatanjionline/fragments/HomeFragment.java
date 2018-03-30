package com.nexm.ghatanjionline.fragments;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final WebView webView = (WebView)view.findViewById(R.id.home_webView);
        webView.loadUrl("file:///android_asset/a.html");
        final CustomWebViewClient customWebViewClient = new CustomWebViewClient();
        webView.setWebViewClient(customWebViewClient);

        return view;
    }
    class CustomWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return handleUri(url);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            return handleUri(request.getUrl().toString());
        }

        private boolean handleUri(final String url) {


            // Based on some condition you need to determine if you are going to load the url
            // in your web view itself or in a browser.
            // You can use `host` or `scheme` or any part of the `uri` to decide.
            if (url.matches("p")) {


                // Returning false means that you are going to load this url in the webView itself
                return false;
            } else if (url.matches("file:///android_asset/news")) {
                // Returning true means that you need to handle what to do with the url
                // e.g. open web page in a Browser
                Intent intent = new Intent(getActivity(),ProductActivity.class);
                intent.putExtra("Category","Cars");
                startActivity(intent);
                return true;
            }else if(url.matches("file:///android_asset/mechanic")){

                Intent intent = new Intent(getActivity(),ProductActivity.class);
                intent.putExtra("Category","Electricians");
                startActivity(intent);
                return true;
            }
            return true;
        }

    }

}
