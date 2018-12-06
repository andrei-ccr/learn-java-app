package org.softry.learnjava;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RVA_Chapters.ItemClickListener {

    private TextView tvAbout;
    private ScrollView containerInterviewTab, containerAboutTab, containerDashboardTab;
    private LinearLayout containerLearnTab;
    private Button mBtnStart, mBtnActivate;
    private RecyclerView mRecyclerView;

    private DrawerLayout mDrawerLayout;

    public static final String SELECTED_CHAPTER = "org.softry.learnjava.TAG.SELECTED_CHAPTER";

    private final Integer[] comingSoonChapters = {4,5,6,7};

    private RVA_Chapters GetChaptersRVA() {
        RVA_Chapters rvAdapter;

        rvAdapter = new RVA_Chapters(this, MainActivity.ChapterList);
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
                    containerAboutTab.setVisibility(View.GONE);
                    containerDashboardTab.setVisibility(View.GONE);
                    containerLearnTab.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_interview:
                    containerInterviewTab.setVisibility(View.VISIBLE);
                    containerAboutTab.setVisibility(View.GONE);
                    containerDashboardTab.setVisibility(View.GONE);
                    containerLearnTab.setVisibility(View.GONE);
                    return true;
                /*case R.id.navigation_about:
                    containerInterviewTab.setVisibility(View.GONE);
                    containerAboutTab.setVisibility(View.VISIBLE);
                    containerLearnTab.setVisibility(View.GONE);
                    containerDashboardTab.setVisibility(View.GONE);
                    return true;*/
                case R.id.navigation_dashboard:
                    containerInterviewTab.setVisibility(View.GONE);
                    containerAboutTab.setVisibility(View.GONE);
                    containerLearnTab.setVisibility(View.GONE);
                    containerDashboardTab.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

	public static List<Containers.Chapter> ChapterList; //List of chapters in order with chapter's details
	public static List<Containers.Lesson> LessonList; //List of lessons in order with lesson's details

	public static Map<Integer, Integer[]> ChapterLessonList; //Map of a chapter to a lesson list
	public static Map<Integer, Integer[]> LessonContentList; //Map of a lesson to a list of content Strings
	public static Map<Integer, Integer[]> LessonTitleList; //Map of a lesson to a list of page title Strings

    private static boolean DataLoaded = false;

	private void MapLessonsToChapters() {
		//TODO: This should be immutable/constant/final for security reasons
		ChapterLessonList = new HashMap<>();
		ChapterLessonList.put(0, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7}); //Chapter 1
		ChapterLessonList.put(1, new Integer[]{8, 9, 10, 11 , 12, 13, 14, 15, 16, 17});
		ChapterLessonList.put(2, new Integer[]{18, 19, 20, 21, 22});
		//ChapterLessonList.put(3, new Integer[]{23, 24, 25, 26, 27});
		
	}
	
	private void MapContentToLessons() {
        //TODO: These should be immutable/constant/final for security reasons
	    LessonContentList = new HashMap<>();
	    LessonTitleList = new HashMap<>();

		LessonContentList.put(0, new Integer[]{R.string.lesson101_1, R.string.lesson101_2, R.string.lesson101_3,
								R.string.lesson101_4, R.string.lesson101_5, R.string.lesson101_6});
								
		LessonTitleList.put(0, new Integer[]{R.string.lesson_101_1_title, R.string.lesson_101_2_title, R.string.lesson_101_3_title,
								R.string.lesson_101_4_title,R.string.lesson_101_5_title,R.string.lesson_101_6_title});
								
		LessonContentList.put(1, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
								R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});
								
		LessonTitleList.put(1, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
								R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});
								
		LessonContentList.put(2, new Integer[]{R.string.lesson103_1, R.string.lesson103_2, R.string.lesson103_3,
								R.string.lesson103_4, R.string.lesson103_5, R.string.lesson103_6, R.string.lesson103_7});
								
		LessonTitleList.put(2, new Integer[]{R.string.lesson_103_1_title, R.string.lesson_103_2_title, R.string.lesson_103_3_title,
								R.string.lesson_103_4_title,R.string.lesson_103_5_title,R.string.lesson_103_6_title, R.string.lesson_103_7_title});
								
		LessonContentList.put(3, new Integer[]{R.string.lesson104_1, R.string.lesson104_2, R.string.lesson104_3,
								R.string.lesson104_4, R.string.lesson104_5, R.string.lesson104_6, R.string.lesson104_7});
								
		LessonTitleList.put(3, new Integer[]{R.string.lesson_104_1_title, R.string.lesson_104_2_title, R.string.lesson_104_3_title, R.string.lesson_104_4_title,R.string.lesson_104_5_title,R.string.lesson_104_6_title, R.string.lesson_104_7_title});
								
		LessonContentList.put(4, new Integer[]{R.string.lesson105_1, R.string.lesson105_2, R.string.lesson105_3,
								R.string.lesson105_4, R.string.lesson105_5, R.string.lesson105_6, R.string.lesson105_7, R.string.lesson105_8, R.string.lesson105_9, R.string.lesson105_10});
								
		LessonTitleList.put(4, new Integer[]{R.string.lesson_105_1_title, R.string.lesson_105_2_title, R.string.lesson_105_3_title,
								R.string.lesson_105_4_title,R.string.lesson_105_5_title,R.string.lesson_105_6_title,R.string.lesson_105_7_title,R.string.lesson_105_8_title,R.string.lesson_105_9_title,R.string.lesson_105_10_title});
								
		LessonContentList.put(5, new Integer[]{R.string.lesson106_1, R.string.lesson106_2, R.string.lesson106_3,
								R.string.lesson106_4, R.string.lesson106_5});
								
		LessonTitleList.put(5, new Integer[]{R.string.lesson_106_1_title, R.string.lesson_106_2_title, R.string.lesson_106_3_title,
								R.string.lesson_106_4_title,R.string.lesson_106_5_title});
								
		LessonContentList.put(6, new Integer[]{R.string.lesson107_1, R.string.lesson107_2, R.string.lesson107_3,
								R.string.lesson107_4, R.string.lesson107_5, R.string.lesson107_6, R.string.lesson107_7, R.string.lesson107_8, R.string.lesson107_9, R.string.lesson107_10, R.string.lesson107_11});
								
		LessonTitleList.put(6, new Integer[]{R.string.lesson_107_1_title, R.string.lesson_107_2_title, R.string.lesson_107_3_title,
								R.string.lesson_107_4_title,R.string.lesson_107_5_title,R.string.lesson_107_6_title,R.string.lesson_107_7_title,R.string.lesson_107_8_title,R.string.lesson_107_9_title,R.string.lesson_107_10_title,R.string.lesson_107_11_title});

        LessonContentList.put(7, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
                R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});

        LessonTitleList.put(7, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
                R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});

        LessonContentList.put(8, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
                R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});

        LessonTitleList.put(8, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
                R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});

        LessonContentList.put(9, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
                R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});

        LessonTitleList.put(9, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
                R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});
	}
	
	private void LoadChapters() {
	    ChapterList = new ArrayList<>();

        String[] aChapter;
        TypedArray chapterList = getResources().obtainTypedArray(R.array.chapterList);

        for(int i=0;i<chapterList.length();i++) {
            aChapter = getResources().getStringArray(chapterList.getResourceId(i, 0));
			
            ChapterList.add(
                    new Containers.Chapter(aChapter[0],
                            aChapter[1],
                            aChapter[2],
                            aChapter[3],
							ChapterLessonList.get(i)));
        }
    }
	
	private void LoadLessons() {
		LessonList = new ArrayList<>();
		String[] lessonTitleList = getResources().getStringArray(R.array.lessonTitleList);
		String[] lessonDescList = getResources().getStringArray(R.array.lessonDescriptionList);
		TypedArray lessonImgList = getResources().obtainTypedArray(R.array.lessonImgList);
        int[] readVector;
		for(int i=0; i<lessonTitleList.length;i++) {
			try {
                readVector = new int[LessonContentList.get(i).length];
                //readVector[0] = 1;
				LessonList.add(
					new Containers.Lesson(
						lessonTitleList[i],
						lessonDescList[i],
						lessonImgList.getResourceId(i, R.drawable.first_program),
						new Containers.LessonContent(this, LessonContentList.get(i), LessonTitleList.get(i), readVector)
					)
				);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("myapp", "Error adding lesson " + Integer.toString(i));
			}
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Data should only be loaded once at the app start, not every time this activity is created
		//Load data
        if(DataLoaded == false) {
            MapLessonsToChapters();
            MapContentToLessons();
            LoadChapters();
            LoadLessons();
            DataLoaded = true;
        }

		setTitle("");

		mDrawerLayout = findViewById(R.id.drawerLayout_container);

        //Get the main containers
        containerLearnTab = findViewById(R.id.container_learnTab);
        containerInterviewTab = findViewById(R.id.container_interviewTab);
        containerAboutTab = findViewById(R.id.container_aboutTab);
        containerDashboardTab = findViewById(R.id.container_dashboardTab);

        //Get the recycler view used to list chapters
        mRecyclerView = findViewById(R.id.rvChapters);
        InitRV_Chapters();

        tvAbout = findViewById(R.id.tvAbout);

        mBtnStart = findViewById(R.id.btn_learn_basic);
        mBtnActivate = findViewById(R.id.btn_learn_full);

        containerInterviewTab.setVisibility(View.GONE);
        containerAboutTab.setVisibility(View.GONE);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FixBottomNavigationText();

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

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

    @Override
    public void onItemClick(View view, int position) {
        //Position 0 and 4 are category names. Add -1 after 0 and -2 after 4 to get correct chapter index
        //Counting starts at 0
        if((position>=0) && (position<4)) position-=1;
        else if(position>=4) position -=2;

        Log.i("myapp_info", "Selected Chapter " + Integer.toString(position));

        if( (!Utilities.InArray(position+1, comingSoonChapters))) {
            Intent lessonsActivity = new Intent(this, LessonsActivity.class);
            lessonsActivity.putExtra(SELECTED_CHAPTER, Integer.toString(position));
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
