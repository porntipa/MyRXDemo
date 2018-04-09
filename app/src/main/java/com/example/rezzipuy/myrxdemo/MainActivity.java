package com.example.rezzipuy.myrxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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

        //JSON Observable
        jsonFeedObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = feedDataFromUrl("http://codemobiles.com/adhoc/feed/youtube_feed.php?type=json");
                emitter.onNext(result);
                emitter.onComplete();
            }

        }).subscribeOn(Schedulers.newThread());

        // xml Observable
        xmlFeedObservable =Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = feedDataFromUrl("http://codemobiles.com/adhoc/feed/youtube_feed.php?type=xml");
                emitter.onNext(result);
                emitter.onComplete();

            }
        });

    }

    public void onClickZip(View v){
        mTextview.setText("");
        mDescTextView.setText("This allows developer to mix the output of all observables and return in a single object. This makes you see content of xml and json in textview below");

        Observable<String> zipObservable = Observable.zip(jsonFeedObservable, xmlFeedObservable, new BiFunction<String, String, String>() {
            @Override
            public String apply(String jsonResult, String xmlResult) throws Exception {
                return jsonResult + xmlResult;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());


        zipObservable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mTextview.setText(s);
                    }
                });
}


    public void onClickConcat(View v){

       Disposable concatObservable = Observable.concat(xmlFeedObservable, jsonFeedObservable)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mTextview.setText(s);
                    }
                });

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
