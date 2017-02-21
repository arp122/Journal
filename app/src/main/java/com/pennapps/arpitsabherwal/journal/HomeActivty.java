package com.pennapps.arpitsabherwal.journal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by arpitsabherwal on 23/01/16.
 */
public class HomeActivty extends Activity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    TextView tvQuote, tvQuoteWriter, tvTitle;
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvQuote = (TextView) findViewById(R.id.tvQuote);
        tvQuoteWriter = (TextView) findViewById(R.id.tvQuoteWriter);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Hind-Regular.ttf");
        tvQuote.setTypeface(type);
        tvTitle.setTypeface(type);

        type = Typeface.createFromAsset(getAssets(), "fonts/LibreBaskerville-Regular.otf");
        tvTitle.setTypeface(type);

        final PanningView panningView = (PanningView) findViewById(R.id.panningViewBackground);
        panningView.startPanning();

        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float sensitivity = 50;
        //Swipe Up Check
        if(e1.getY() - e2.getY() > sensitivity){
            //Setting Image Resource to Up_Arrow on Swipe Up
            Intent intent= new Intent(this,RegistrationActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            return true;
        }
        return false;
    }
}

