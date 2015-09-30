package com.example.mitchell.blinks;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by mitchell on 9/2/15.
 */
public class ActiveCard extends HorizontalScrollView {
    private static final int SWIPE_MIN_DISTANCE = 7;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;

    public ArrayList<BeaconListItem> mItems = null;
    private GestureDetector gestureDetector;
    private int activeFeature = 0;


    public ActiveCard(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);
    }

    public ActiveCard(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public ActiveCard(Context context) {
        super(context);
    }

    public void setFeatureItems(final ArrayList<BeaconListItem> items, int w, int h) {
        LinearLayout internalWrapper = new LinearLayout(getContext());
        internalWrapper.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        internalWrapper.setOrientation(LinearLayout.HORIZONTAL);
        addView(internalWrapper);
        this.mItems = items;
        for(int i = 0; i< items.size();i++){
            LinearLayout featureLayout = items.get(i).getViewItem(w,h);
            internalWrapper.addView(featureLayout);
        }
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    int scrollX = getScrollX();
                    int featureWidth = v.getMeasuredWidth();
                    activeFeature = ((scrollX + (featureWidth / 2)) / featureWidth);
                    int scrollTo = activeFeature * featureWidth;
                    smoothScrollTo(scrollTo, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });

        gestureDetector = new GestureDetector(new MyGestureDetector());
    }
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                //right to left
                if(velocityX<0 && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    int featureWidth = getMeasuredWidth();
                    activeFeature = (activeFeature < (mItems.size() - 1))? activeFeature + 1:mItems.size() -1;
                    smoothScrollTo(activeFeature*featureWidth, 0);
                    return true;
                }
                //left to right
                else if (velocityX>0 && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    int featureWidth = getMeasuredWidth();
                    activeFeature = (activeFeature > 0)? activeFeature - 1:0;
                    smoothScrollTo(activeFeature*featureWidth, 0);
                    return true;
                }
            } catch (Exception e) {
                Log.e("Fling", "There was an error processing the Fling event:" + e.getMessage());
            }
            return false;
        }
    }
}

