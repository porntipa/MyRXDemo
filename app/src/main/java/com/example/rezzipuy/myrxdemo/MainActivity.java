package com.example.rezzipuy.myrxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        mTextview = (TextView) findViewById(R.id.textview);
        mDescTextView = (TextView) findViewById(R.id.descTextView);
    }

    public void onClickZip(View v){

    }

    public void onClickConcat(View v){

    }

    public void onClickMerge(View v){

    }

    // general feeding code with okHttp
    public String feedDataFromUrl(final String url) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        return result;
    }

}
