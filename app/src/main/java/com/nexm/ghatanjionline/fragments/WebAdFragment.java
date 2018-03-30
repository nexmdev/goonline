package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nexm.ghatanjionline.R;


public class WebAdFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private WebView webView;
    private String url;

    public WebAdFragment() {
        // Required empty public constructor
    }
    public static  WebAdFragment newInstance(String webUrl){

        WebAdFragment fragment = new WebAdFragment();
        Bundle bundle = new Bundle();
        bundle.putString("URL",webUrl);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("URL");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_ad, container, false);
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.webAdProgressBar);

        webView = (WebView)view.findViewById(R.id.webAdwebView);
        webView.setWebViewClient(new WebViewClient() {

                                     @Override
                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                         super.onPageStarted(view, url, favicon);
                                         progressBar.setVisibility(ProgressBar.VISIBLE);
                                         webView.setVisibility(View.INVISIBLE);
                                     }

                                     @Override public void onPageCommitVisible(WebView view, String url) {
                                         super.onPageCommitVisible(view, url);
                                         progressBar.setVisibility(ProgressBar.GONE);
                                         webView.setVisibility(View.VISIBLE);

                                     }
                                 });
        webView.loadUrl(url);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onWebFragmentClick(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onWebFragmentClick(Uri uri);
    }
}
