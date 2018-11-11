package org.softry.learnjava;

import android.content.Intent;
import android.icu.util.MeasureUnit;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private LinearLayout mBoxBasic, mBoxFull;
    private Button mBtnLearn, mBtnActivate;
	

	private void FixBottomNavigationText() {
		BottomNavigationView mBottomNavigationView = findViewById(R.id.navigation);
		BottomNavigationMenuView menuView = (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);
		for (int i = 0; i < menuView.getChildCount(); i++) {
			BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
			View activeLabel = item.findViewById(R.id.largeLabel);
			if (activeLabel instanceof TextView) {
				activeLabel.setPadding(0, 0, 0, 0);
			}
		}
	}
	
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
        mBtnLearn = findViewById(R.id.btn_learn_basic);
        mBtnActivate = findViewById(R.id.btn_learn_full);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FixBottomNavigationText();

        mTextMessage.setVisibility(View.INVISIBLE);

        mBtnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent learnIntent = new Intent(v.getContext(), ChaptersActivity.class);
                startActivity(learnIntent);
            }
        });




    }

}
