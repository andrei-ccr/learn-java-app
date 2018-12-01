package org.softry.learnjava;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LessonsActivity extends AppCompatActivity implements RVA_Lessons.ItemClickListener {

    private List<RVA_Lessons.LessonBoxRow> mLessonsBox;
    public static final String SELECTED_LESSON = "org.softry.learnjava.TAG.SELECTED_LESSON";

    private void SetLessonBoxList(int chapter) {
        mLessonsBox = new ArrayList<>();

        Integer[] lessonsCurrentChapter = MainActivity.ChapterLessonList.get(chapter);

        for(int i=lessonsCurrentChapter[0];i<lessonsCurrentChapter.length-1;i+=2) {
            if(ChaptersActivity.InArray(i+1, lessonsCurrentChapter)) {
                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox(MainActivity.LessonList.get(i), i),
                        new RVA_Lessons.LessonBox(MainActivity.LessonList.get(i+1), i+1 )
                ));
            } else {
                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox(MainActivity.LessonList.get(i), i),
                        new RVA_Lessons.LessonBox() )
                );
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TypedArray chapterList = getResources().obtainTypedArray(R.array.chapterList);
        TypedArray chapterThemeList = getResources().obtainTypedArray(R.array.ChaptersColor);

        //TODO: Don't get selectedChapter when coming back from InLessonActivity
        int selectedChapter = 0; //This is a temporary fix until the issue is fixed
        try {
            Intent parentActivity = getIntent();
            selectedChapter = Integer.parseInt(parentActivity.getStringExtra(ChaptersActivity.SELECTED_CHAPTER));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTheme(chapterThemeList.getResourceId(selectedChapter,-1));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        ConstraintLayout cLayout = findViewById(R.id.cLayout);
        if(selectedChapter == 0) {
            cLayout.setBackgroundColor(getResources().getColor(R.color._chapter1color));
        } else if(selectedChapter == 1) {
            cLayout.setBackgroundColor(getResources().getColor(R.color._chapter2color));
        } else if(selectedChapter == 2) {
            cLayout.setBackgroundColor(getResources().getColor(R.color._chapter3color));
        } else if(selectedChapter == 3) {
            cLayout.setBackgroundColor(getResources().getColor(R.color._chapter4color));
        }


        TextView tvChapterDesc = findViewById(R.id.tvSelectedChapterDesc);

        String[] selectedChapterDetails = getResources().getStringArray(chapterList.getResourceId(selectedChapter, 0));
        this.setTitle(selectedChapterDetails[1] + " - " + getString(R.string.app_name));
        tvChapterDesc.setText(selectedChapterDetails[2]);



        SetLessonBoxList(selectedChapter);

        RVA_Lessons rvAdapter = new RVA_Lessons(this, this.mLessonsBox);
        rvAdapter.setClickListener(this);

        RecyclerView mRecyclerView = findViewById(R.id.rvLessons);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(rvAdapter);

    }

    @Override
    public void onLeftBoxClick(View view, int pos, int row_index) {
        Log.i("myapp_info", "Selected Lesson " + Integer.toString(pos));
        Log.i("myapp_info", "Row index " + Integer.toString(row_index));

        Intent lessonActivity = new Intent(this, InLessonActivity.class);
        lessonActivity.putExtra(SELECTED_LESSON, Integer.toString(mLessonsBox.get(row_index).GetLeftLessonBox().GetIdentifier()));
        startActivity(lessonActivity);
    }

    @Override
    public void onRightBoxClick(View view, int pos, int row_index) {
        Log.i("myapp_info", "Selected Lesson " + Integer.toString(pos));
        Log.i("myapp_info", "Row index " + Integer.toString(row_index));

        Intent lessonActivity = new Intent(this, InLessonActivity.class);
        lessonActivity.putExtra(SELECTED_LESSON, Integer.toString(mLessonsBox.get(row_index).GetRightLessonBox().GetIdentifier()));
        startActivity(lessonActivity);
    }
}
