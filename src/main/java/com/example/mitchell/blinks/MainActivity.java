package com.example.mitchell.blinks;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.eddystone.Eddystone;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String ESTIMOTE_UUID = "677e2255ce171ce7f3946dabcad78ec8";
    private BeaconManager beaconManager;
    private String scanId;
    private List<String> urls = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beaconManager = new BeaconManager(this);

        beaconManager.setEddystoneListener(new BeaconManager.EddystoneListener() {
            @Override
            public void onEddystonesFound(List<Eddystone> eddystones) {
                if (eddystones.size() > 0) {
                    for (Eddystone eddy : eddystones) {
                        if (eddy.isUrl()) {
                            Log.d(getClass().getSimpleName(), "Discovered nearables: " + eddy.url);
                        }
                    }
                }
            }
        });


        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("active");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("bookmarks");
        tab1.setIndicator("Active");
        tab1.setContent(R.id.active);
        tab2.setIndicator("Bookmarks");
        tab2.setContent(R.id.tab);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        int height = size.y;

        final ActiveCard scrollView = (ActiveCard) findViewById(R.id.horizontalScrollView);
        final ArrayList<BeaconListItem> beaconList = new ArrayList<BeaconListItem>();
        beaconList.add(new BeaconListItem(this,"Stonehaus Menu",R.drawable.strawberrys,R.drawable.conejo,"http://www.whsband.org/"));
        beaconList.add(new BeaconListItem(this, "1908 Stone Wall", R.drawable.castle,R.drawable.conejo));
        beaconList.add(new BeaconListItem(this,"Conejo Travel Tours",R.drawable.ctt,R.drawable.conejo));
        beaconList.add(new BeaconListItem(this,"Visit Brendan's During your Stay",R.drawable.brendons,R.drawable.logohotel));
        beaconList.add(new BeaconListItem(this,"New Tasting Room Locals Love!",R.drawable.tastingroom));
        scrollView.setFeatureItems(beaconList, width, height);

        FlowLayout bookmarkScroller = (FlowLayout) findViewById(R.id.scroller);
        bookmarkScroller.addView(new BeaconListItem(this,"Conejo Travel Tours",R.drawable.ctt).getViewItemBookmarks(width, height));
        bookmarkScroller.addView(new BeaconListItem(this,"1980 Stone Wall",R.drawable.castle).getViewItemBookmarks(width, height));
        bookmarkScroller.addView(new BeaconListItem(this,"Free Wifi",R.drawable.wifis).getViewItemBookmarks(width, height));
        bookmarkScroller.addView(new BeaconListItem(this,"New Tasting Room Locals Love!",R.drawable.tastingroom).getViewItemBookmarks(width, height));
        bookmarkScroller.addView(new BeaconListItem(this,"Conejo Travel Tours",R.drawable.strawberrys).getViewItemBookmarks(width, height));
        bookmarkScroller.addView(new BeaconListItem(this,"1980 Stone Wall",R.drawable.castle).getViewItemBookmarks(width, height));
    }

    @Override
    protected void onStart() {
        super.onStart();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                scanId = beaconManager.startEddystoneScanning();
            }
        });
    }

    @Override
    protected void onStop() {

        beaconManager.stopEddystoneScanning(scanId);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        beaconManager.disconnect();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
