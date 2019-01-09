package org.softry.learnjava;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements RVA_Chapters.ItemClickListener {

    private ScrollView containerDashboardTab;
    private LinearLayout containerLearnTab;
    private RecyclerView mRecyclerView; //Chapters list
    private DrawerLayout mDrawerLayout; //Side bar
    private Context context;
    private boolean inDashboard;

    private TextView tvPagesRead, tvOverallProgress, tvSideBarProgress;

    private RVA_Chapters rvAdapter;

    private RVA_Chapters GetChaptersRVA() {
        RVA_Chapters rvAdapter;

        rvAdapter = new RVA_Chapters(this, Utilities.ChapterList);
        rvAdapter.setClickListener(this);
        return rvAdapter;
    }

    private void InitRV_Chapters(){
        rvAdapter = GetChaptersRVA();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(rvAdapter);
    }

	/*private void FixBottomNavigationText() {
		BottomNavigationView mBottomNavigationView = findViewById(R.id.navigation);
		BottomNavigationMenuView menuView = (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);
		for (int i = 0; i < menuView.getChildCount(); i++) {
			BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
			View activeLabel = item.findViewById(R.id.largeLabel);
			if (activeLabel instanceof TextView) {
				activeLabel.setPadding(0, 0, 0, 0);
			}
		}
	}*/
	
    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_learn:
                    containerDashboardTab.setVisibility(View.GONE);
                    containerLearnTab.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    containerLearnTab.setVisibility(View.GONE);
                    containerDashboardTab.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };*/

    @Override
    public void onBackPressed() {
        if(inDashboard) {
            containerDashboardTab.setVisibility(View.GONE);
            containerLearnTab.setVisibility(View.VISIBLE);
            inDashboard = false;
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AdView mAdView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        inDashboard = false;

        //Initialize ads SDK
        MobileAds.initialize(context, getString(R.string.appAdsId));

		//Load data
        Utilities.LoadData(context);

        //Set sidebar handler for item click
		mDrawerLayout = findViewById(R.id.drawerLayout_container);
        NavigationView navigationView = findViewById(R.id.side_bar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(false);

                Intent intent;
                switch(menuItem.getItemId()) {
                    case R.id.side_bar_dashboard:
                        containerLearnTab.setVisibility(View.GONE);
                        containerDashboardTab.setVisibility(View.VISIBLE);
                        inDashboard = true;
                        mDrawerLayout.closeDrawers();
                        break;

                    case R.id.side_bar_settings:
                        intent = new Intent(context, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.side_bar_about:
                        intent = new Intent(context, AboutActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.side_bar_report:
                        intent = new Intent(context, ReportActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.side_bar_disable_ads:
                        intent = new Intent(context, DisableAdsActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        //Get the main containers
        containerLearnTab = findViewById(R.id.container_learnTab);
        containerDashboardTab = findViewById(R.id.container_dashboardTab);

        Button btnClear, btnDisableAds;
        btnClear = findViewById(R.id.btn_clearProgress);
        btnDisableAds = findViewById(R.id.btn_disableAds);

        btnDisableAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisableAdsActivity.class);
                startActivity(intent);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Your entire progress will be erased. Are you sure you want to do this?");
                dlgAlert.setTitle("Start over");
                dlgAlert.setPositiveButton("Yes, erase", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Utilities.DeleteStorage(context);
                        Utilities.ReloadData(context);
                        UpdateStats();
                        rvAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dlgAlert.setNegativeButton("No, go back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();


            }
        });

        //Set the recycler view used to list chapters
        mRecyclerView = findViewById(R.id.rvChapters);
        InitRV_Chapters();

        //Set bottom navigation
        /*BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FixBottomNavigationText();*/

        //Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        tvPagesRead = findViewById(R.id.tvStatsPagesRead);
        tvOverallProgress = findViewById(R.id.tvStatsOverallProgress);
        tvSideBarProgress = navigationView.getHeaderView(0).findViewById(R.id.tv_sidebar_progress);

        UpdateStats();
        mAdView = findViewById(R.id.adView);
        if(Utilities.ShowAds) {
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("80B20B041E29D3D23790297B560D858C").build();//.addTestDevice("80B20B041E29D3D23790297B560D858C")
            mAdView.loadAd(adRequest);
        } else {
            mDrawerLayout.removeView(mAdView);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(rvAdapter != null)
            rvAdapter.notifyDataSetChanged();
        UpdateStats();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        if(rvAdapter != null)
            rvAdapter.notifyDataSetChanged();
        UpdateStats();
    }

    @Override
    public void onChapterClick(View view, int position) {
        //Position 0 and 4 are category names. Add -1 after 0 and -2 after 4 to get correct chapter index. Counting starts at 0
        if((position>=0) && (position<4)) position-=1;
        else if(position>=4) position -=2;

        Log.d("myapp", "Selected Chapter " + Integer.toString(position));

        Intent lessonsActivity = new Intent(this, LessonsActivity.class);
        lessonsActivity.putExtra(Utilities.SELECTED_CHAPTER, Integer.toString(position));
        startActivity(lessonsActivity);

    }

    private void UpdateStats() {
        tvPagesRead.setText(Utilities.GetTotalPagesRead() + " Pages read");
        tvOverallProgress.setText("Overall progress " + Utilities.GetOverallProgress(this) + "%");
        tvSideBarProgress.setText("Overall progress: " + Utilities.GetOverallProgress(this) + "%");
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
