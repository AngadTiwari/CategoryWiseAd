package com.synsoft.categorywisead;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.ads.search.SearchAdView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*// Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").addKeyword("car").addKeyword("flower").addKeyword("bird").build();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                Log.e("Ad", "Ad Close");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                Log.e("Ad", "Ad Loaded");
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();

                Log.e("Ad", "Ad Left App");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();

                Log.e("Ad", "Ad Opened");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                Log.e("Ad", "Ad Loaded");
            }
        });
        adView.loadAd(adRequest);
        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        //Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();*/
        /*com.avocarrot.androidsdk.AvocarrotInterstitial avocarrotInterstitial =
                new com.avocarrot.androidsdk.AvocarrotInterstitial(
                        this,                     *//* reference to your Activity *//*
                        "af55994a9d91593c872e2b4e906495d6d2cc71e8", *//* this is your Avocarrot API Key *//*
                        "17a9337289c30e82bebb654dd734c892ca5e66ff" *//* this is your Avocarrot Placement Key *//*
                );
        avocarrotInterstitial.setSandbox(true);
        avocarrotInterstitial.setLogger(true, "ALL");
        avocarrotInterstitial.loadAndShowAd();*/

        // Create the AvocarrotAdapter that encapsulates your adapter
        List<String> listData=new ArrayList<>();
        listData.add("one");listData.add("two");listData.add("three");
        ListView listView=(ListView)findViewById(R.id.list);
        ListAdapterCustom listAdapterCustom=new ListAdapterCustom(Main2Activity.this, listData);
        com.avocarrot.androidsdk.AvocarrotInstream avocarrotInstream = new com.avocarrot.androidsdk.AvocarrotInstream(
                listAdapterCustom,      /* pass your listAdapter */
        this,                   /* reference to your Activity */
                "af55994a9d91593c872e2b4e906495d6d2cc71e8",       /* replace with your Avocarrot API Key */
                "17a9337289c30e82bebb654dd734c892ca5e66ff"  /* replace with your Avocarrot Placement Key */
        );
        avocarrotInstream.setLogger(true, "ALL");
        avocarrotInstream.setSandbox(true);

        // Bind the created avocarrotInstream adapter to your list instead of your listAdapter
        listView.setAdapter(avocarrotInstream);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
