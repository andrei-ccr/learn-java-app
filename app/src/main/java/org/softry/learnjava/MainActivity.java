package org.softry.learnjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RVA_Chapters.ItemClickListener {

    private ScrollView containerInterviewTab, containerAboutTab, containerDashboardTab;
    private LinearLayout containerLearnTab;
    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private Context context;

    private final Integer[] comingSoonChapters = {5,6,7,8};

    private RVA_Chapters GetChaptersRVA() {
        RVA_Chapters rvAdapter;

        rvAdapter = new RVA_Chapters(this, Utilities.ChapterList);
        rvAdapter.setClickListener(this);
        return rvAdapter;
    }

    private void InitRV_Chapters(){
        RVA_Chapters adapter = GetChaptersRVA();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

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
                    containerDashboardTab.setVisibility(View.GONE);
                    containerLearnTab.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_interview:
                    containerInterviewTab.setVisibility(View.VISIBLE);
                    containerDashboardTab.setVisibility(View.GONE);
                    containerLearnTab.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    containerInterviewTab.setVisibility(View.GONE);
                    containerLearnTab.setVisibility(View.GONE);
                    containerDashboardTab.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

		//Load data
        Utilities.LoadData(context);

		mDrawerLayout = findViewById(R.id.drawerLayout_container);
        NavigationView navigationView = findViewById(R.id.side_bar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(false);
                mDrawerLayout.closeDrawers();
                Intent intent;
                switch(menuItem.getItemId()) {
                    case R.id.side_bar_settings:
                        Log.i("myapp", "Settings selected");
                        break;
                    case R.id.side_bar_about:
                        intent = new Intent(context, AboutActivity.class);
                        startActivity(intent);
                        Log.i("myapp", "About selected");
                        break;
                    case R.id.side_bar_bookmarks:
                        Log.i("myapp", "Bookmarks selected");
                        intent = new Intent(context, BookmarksActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.side_bar_search:
                        Log.i("myapp", "Search selected");
                        break;
                    case R.id.side_bar_disable_ads:
                        Log.i("myapp", "Disable ads selected");
                        break;
                    case R.id.side_bar_unlock_all:
                        Log.i("myapp", "Unlock all selected");
                        break;
                }

                return true;
            }
        });

        //Get the main containers
        containerLearnTab = findViewById(R.id.container_learnTab);
        containerInterviewTab = findViewById(R.id.container_interviewTab);
        containerAboutTab = findViewById(R.id.container_aboutTab);
        containerDashboardTab = findViewById(R.id.container_dashboardTab);

        //Get the recycler view used to list chapters
        mRecyclerView = findViewById(R.id.rvChapters);
        InitRV_Chapters();

        containerInterviewTab.setVisibility(View.GONE);
        containerAboutTab.setVisibility(View.GONE);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FixBottomNavigationText();

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

    }

    @Override
    public void onItemClick(View view, int position) {
        //Position 0 and 4 are category names. Add -1 after 0 and -2 after 4 to get correct chapter index. Counting starts at 0
        if((position>=0) && (position<4)) position-=1;
        else if(position>=4) position -=2;

        Log.i("myapp_info", "Selected Chapter " + Integer.toString(position));

        if( (!Utilities.InArray(position+1, comingSoonChapters))) {
            Intent lessonsActivity = new Intent(this, LessonsActivity.class);
            lessonsActivity.putExtra(Utilities.SELECTED_CHAPTER, Integer.toString(position));
            startActivity(lessonsActivity);
        } else {
            Toast.makeText(view.getContext(),"Coming soon", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
