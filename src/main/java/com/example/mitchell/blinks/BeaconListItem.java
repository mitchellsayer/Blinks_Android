package com.example.mitchell.blinks;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by mitchell on 9/2/15.
 */

public class BeaconListItem {

    private String text;
    private Context context;
    private int drawableResId;
    private int drawableResIdBottom;
    private String url = "";

    public BeaconListItem(Context cntxt, String txt, int drawr, int bottomDrawr) {
        text = txt;
        context=cntxt;
        drawableResId=drawr;
        drawableResIdBottom = bottomDrawr;
    }

    public BeaconListItem(Context cntxt, String txt, int drawr, int bottomDrawr, String rl) {
        text = txt;
        context=cntxt;
        drawableResId=drawr;
        drawableResIdBottom = bottomDrawr;
        url = rl;
    }

    public BeaconListItem(Context cntxt, String txt, int drawr) {
        text = txt;
        context=cntxt;
        drawableResId=drawr;
    }

    public BeaconListItem(Context cntxt, String txt, int drawr, String rl) {
        text = txt;
        context=cntxt;
        drawableResId=drawr;
        url = rl;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)

    public LinearLayout getViewItem(int w, int h) {
        LinearLayout spok = new LinearLayout(context);
        spok.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout featureLayout = new RelativeLayout(context);
        int width = w-(w/4);
        int height = (int)(h-(h/3)-(h/16));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(width,height);
        lp.setMargins(w / 8, h / 13, w / 8, 0);
        spok.setLayoutParams(lp);
        featureLayout.setLayoutParams(lp4);
        featureLayout.setBackgroundColor(Color.WHITE);

        TextView t = new TextView(context);
        t.setText(text);
        t.setTextSize(28);
        t.setBackgroundColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
        t.setPadding(0,100,0,0);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(width,(int)(height/4)+height/12);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        t.setLayoutParams(lp2);

        ImageView iv = new ImageView(context);
        iv.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.ALIGN_TOP);
        iv.setLayoutParams(lp3);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableResId);
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        int newHeight = width*imageHeight/imageWidth;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, newHeight, true);
        iv.setImageBitmap(bitmap);



        featureLayout.addView(t);
        featureLayout.addView(iv);
        spok.addView(featureLayout);

        if(drawableResIdBottom!=0) {
            ImageView bottom = new ImageView(context);
            int newbottomheight = h/16;
            Bitmap bottomBitmap = BitmapFactory.decodeResource(context.getResources(), drawableResIdBottom);
            int bottomWidth = bottomBitmap.getWidth();
            int bottomHeight = bottomBitmap.getHeight();
            int newBottomWidth = newbottomheight * bottomWidth / bottomHeight;
            bottomBitmap = Bitmap.createScaledBitmap(bottomBitmap, newBottomWidth, newbottomheight, true);
            bottom.setImageBitmap(bottomBitmap);
            spok.addView(bottom);
        }
        spok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!url.equals("")) {
                    //Intent i = new Intent(context, WebActivity.class);
                    //i.putExtra("Url", url);
                    //context.startActivity(i);
                    WebView browser = new WebView(context);
                    browser.getSettings().setJavaScriptEnabled(true);
                        String value = url;
                        browser.loadUrl(value);
                }
            }
        });

        return spok;
    }

    public ViewGroup getViewItemBookmarks(int w, int h) {
        RelativeLayout featureLayout = new RelativeLayout(context);
        int width = (w/2)-(w/14);
        int height = ((h/2)-(h/7));
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(width,height);
        lp.setMargins(w/20,w/22,0,0);
        featureLayout.setLayoutParams(lp);
        featureLayout.setBackgroundColor(Color.WHITE);

        TextView t = new TextView(context);
        t.setText(text);
        t.setTextSize(14);
        t.setBackgroundColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
        t.setPadding(0,50,0,0);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(width,(int)(height/4)+height/12);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        t.setLayoutParams(lp2);

        ImageView iv = new ImageView(context);
        iv.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        iv.setLayoutParams(lp3);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableResId);

        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        float scaleFactor = (float)width/(float)imageWidth;
        int newHeight = width*imageHeight/imageWidth;

        bitmap = Bitmap.createScaledBitmap(bitmap, width, newHeight, true);

        iv.setImageBitmap(bitmap);

        featureLayout.addView(t);
        featureLayout.addView(iv);
        return featureLayout;
    }

    public String getUrl() {
        if (url!=null) {
            return url;
        } else {
            return "error";
        }
    }
}
