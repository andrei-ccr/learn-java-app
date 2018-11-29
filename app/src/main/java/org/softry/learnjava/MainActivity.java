package org.softry.learnjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvAbout;
    private ScrollView containerLearnTab, containerInterviewTab, containerAboutTab;
    private Button mBtnStart, mBtnActivate;

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
                    containerInterviewTab.setVisibility(View.GONE);
                    containerAboutTab.setVisibility(View.GONE);
                    containerLearnTab.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_interview:
                    containerInterviewTab.setVisibility(View.VISIBLE);
                    containerAboutTab.setVisibility(View.GONE);
                    containerLearnTab.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_about:
                    containerInterviewTab.setVisibility(View.GONE);
                    containerAboutTab.setVisibility(View.VISIBLE);
                    containerLearnTab.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Load all chapters and lessons objects into static lists here for easier access from anywhere

        //Get the main containers
        containerLearnTab = findViewById(R.id.container_learnTab);
        containerInterviewTab = findViewById(R.id.container_interviewTab);
        containerAboutTab = findViewById(R.id.container_aboutTab);

        tvAbout = findViewById(R.id.tvAbout);

        mBtnStart = findViewById(R.id.btn_learn_basic);
        mBtnActivate = findViewById(R.id.btn_learn_full);

        containerInterviewTab.setVisibility(View.GONE);
        containerAboutTab.setVisibility(View.GONE);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FixBottomNavigationText();

        tvAbout.setText("Learn Java\n\n" +
                "Application for learning the Java programming language. Lessons are designed for quick learning.\n\n" +
                "Suitable for both beginners and advanced programmers.");



        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent learnIntent = new Intent(v.getContext(), ChaptersActivity.class);
                startActivity(learnIntent);
            }
        });
    }

}
