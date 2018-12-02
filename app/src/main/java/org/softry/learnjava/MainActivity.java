package org.softry.learnjava;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static List<Containers.Chapter> ChapterList; //List of chapters in order with chapter's details
	public static List<Containers.Lesson> LessonList; //List of lessons in order with lesson's details
	public static Map<Integer, Integer[]> ChapterLessonList; //Map of a chapter to a lesson list
	public static Map<Integer, Integer[]> LessonContentList; //Map of a lesson to a list of content Strings
	public static Map<Integer, Integer[]> LessonTitleList; //Map of a lesson to a list of page title Strings

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
								
		LessonContentList.put(2, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
								R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});
								
		LessonTitleList.put(2, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
								R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});
								
		LessonContentList.put(3, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
								R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});
								
		LessonTitleList.put(3, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
								R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});
								
		LessonContentList.put(4, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
								R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});
								
		LessonTitleList.put(4, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
								R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});
								
		LessonContentList.put(5, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
								R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});
								
		LessonTitleList.put(5, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
								R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});
								
		LessonContentList.put(6, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
								R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});
								
		LessonTitleList.put(6, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
								R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});

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
		
		for(int i=0; i<lessonTitleList.length;i++) {
			try {
				LessonList.add(
					new Containers.Lesson(
						lessonTitleList[i],
						lessonDescList[i],
						lessonImgList.getResourceId(i, 0),
						new Containers.LessonContent(this, LessonContentList.get(i), LessonTitleList.get(i), new int[LessonContentList.get(i).length])
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
		
		//Load data
		MapLessonsToChapters();
		MapContentToLessons();
		LoadChapters();
		LoadLessons();

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
