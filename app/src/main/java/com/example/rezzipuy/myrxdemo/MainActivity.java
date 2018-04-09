package com.example.rezzipuy.myrxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private Observable<String> jsonFeedObservable;
    private Observable<String> xmlFeedObservable;
    private TextView mTextview;
    private TextView mDescTextView;
    private Disposable subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickZip(View v){

    }

    public void onClickConcat(View v){

    }

    public void onClickMerge(View v){

    }
}
