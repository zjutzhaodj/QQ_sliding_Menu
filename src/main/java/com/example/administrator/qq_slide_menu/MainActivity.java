package com.example.administrator.qq_slide_menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import view.SlidingMenu;

public class MainActivity extends Activity {

    private SlidingMenu slidingMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        slidingMenu= (SlidingMenu) findViewById(R.id.slidingMenu);

    }
    public void taggle(View v){
        slidingMenu.taggle();
    }


}
