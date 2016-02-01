package com.xby.fm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {

    private SlidingMenu menu;
    private TextView open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//        menu = (SlidingMenu) findViewById(R.id.slidingMenu);
//        open = (TextView) findViewById(R.id.btn_open);
//        open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                menu.toggle();
//            }
//        });
    }

}
