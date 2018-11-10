package org.softry.learnjava;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private LinearLayout mBoxBasic, mBoxFull;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_learn:
                    mTextMessage.setVisibility(View.INVISIBLE);

                    mBoxBasic.setVisibility(View.VISIBLE);
                    mBoxFull.setVisibility(View.VISIBLE);

                    return true;
                case R.id.navigation_interview:
                    mTextMessage.setVisibility(View.VISIBLE);
                    mBoxBasic.setVisibility(View.INVISIBLE);
                    mBoxFull.setVisibility(View.INVISIBLE);
                    mTextMessage.setText("Work in progress");
                    return true;
                case R.id.navigation_about:
                    mTextMessage.setVisibility(View.VISIBLE);
                    mBoxBasic.setVisibility(View.INVISIBLE);
                    mBoxFull.setVisibility(View.INVISIBLE);
                    mTextMessage.setText("");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        mBoxBasic = findViewById(R.id.box_learn_basic);
        mBoxFull = findViewById(R.id.box_learn_full);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mTextMessage.setVisibility(View.INVISIBLE);
    }

}
