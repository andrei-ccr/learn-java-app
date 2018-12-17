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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static org.softry.learnjava.Utilities.LessonList;

public class LessonsActivity extends AppCompatActivity implements RVA_Lessons.ItemClickListener {

    private List<RVA_Lessons.LessonBoxRow> mLessonsBox;

    public static int SelectedChapter;
    public RecyclerView mRecyclerView;
    public RVA_Lessons rvAdapter;

    int chapterColorId = R.color._chapter1color;

    private void SetLessonBoxList(int chapter) {

        mLessonsBox = new ArrayList<>();

        Integer[] lessonsCurrentChapter = Utilities.ChapterLessonList.get(chapter);
        for(int i=lessonsCurrentChapter[0]; i<lessonsCurrentChapter.length+lessonsCurrentChapter[0]; i+=2) {
            if(Utilities.InArray(i+1, lessonsCurrentChapter)) {
                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox(LessonList.get(i), i),
                        new RVA_Lessons.LessonBox(LessonList.get(i+1), i+1 )
                ));
                Log.e("myapp", "Adding lesson stereo");
            } else {
                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox(LessonList.get(i), i),
                        new RVA_Lessons.LessonBox() )
                );
                Log.e("myapp", "Adding lesson mono");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("myapp", "OnCreate() called");

        try {
            Intent parentActivity = getIntent();
            SelectedChapter = Integer.parseInt(parentActivity.getStringExtra(Utilities.SELECTED_CHAPTER));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Set Theme
        TypedArray chapterThemeList = getResources().obtainTypedArray(R.array.ChaptersColor);
        setTheme(chapterThemeList.getResourceId(SelectedChapter, R.style.AppTheme_ChapterOne));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        TextView tvComingSoon = findViewById(R.id.tvLessonComingSoon);
        ConstraintLayout cLayout = findViewById(R.id.cLayout);
        TextView tvChapterDesc = findViewById(R.id.tvSelectedChapterDesc);

        if(SelectedChapter == 0) {
            chapterColorId = R.color._chapter1color;
        } else if(SelectedChapter == 1) {
            chapterColorId = R.color._chapter2color;
        } else if(SelectedChapter == 2) {
            chapterColorId = R.color._chapter3color;
        } else if(SelectedChapter == 3) {
            chapterColorId = R.color._chapter4color;
        } else if(SelectedChapter == 4) {
            chapterColorId = R.color._chapter5color;
        } else if(SelectedChapter == 5) {
            chapterColorId = R.color._chapter6color;
        } else if(SelectedChapter == 6) {
            chapterColorId = R.color._chapter7color;
        } else if(SelectedChapter == 7) {
            chapterColorId = R.color._chapter8color;
        } else if(SelectedChapter == 8) {
            chapterColorId = R.color._chapter9color;
        }

        cLayout.setBackgroundColor(getResources().getColor(chapterColorId));


        Containers.Chapter thisChapter = Utilities.ChapterList.get(SelectedChapter);
        setTitle(thisChapter.GetName() + " - " + getString(R.string.app_name));
        tvChapterDesc.setText(thisChapter.GetShortDesc());


        if( (Utilities.InArray(SelectedChapter, Utilities.ComingSoonChapters))) {
            tvComingSoon.setVisibility(View.VISIBLE);
        } else {
            SetLessonBoxList(SelectedChapter);
            if (this.mLessonsBox != null) {
                rvAdapter = new RVA_Lessons(this, this.mLessonsBox);
                rvAdapter.setClickListener(this);
                mRecyclerView = findViewById(R.id.rvLessons);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mRecyclerView.setAdapter(rvAdapter);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("myapp", "OnResume called()");
        if(mRecyclerView != null && rvAdapter != null) {
            rvAdapter = new RVA_Lessons(this, this.mLessonsBox);
            rvAdapter.setClickListener(this);
            mRecyclerView.setAdapter(rvAdapter);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i("myapp", "OnRestart called()");
        if(mRecyclerView != null && rvAdapter != null) {
            rvAdapter = new RVA_Lessons(this, this.mLessonsBox);
            rvAdapter.setClickListener(this);
            mRecyclerView.setAdapter(rvAdapter);
        }
    }

    @Override
    public void onLeftBoxClick(View view, int pos, int row_index) {
        Log.i("myapp_info", "Selected Lesson " + Integer.toString(pos));
        Log.i("myapp_info", "Row index " + Integer.toString(row_index));

        if(LessonList.get(mLessonsBox.get(row_index).GetLeftLessonBox().GetIdentifier()).IsLocked()) {
            Toast.makeText(this , "Complete previous lessons to unlock", Toast.LENGTH_LONG);
        } else {

            Intent lessonActivity = new Intent(this, InLessonActivity.class);
            lessonActivity.putExtra(Utilities.SELECTED_LESSON, Integer.toString(mLessonsBox.get(row_index).GetLeftLessonBox().GetIdentifier()));
            lessonActivity.putExtra(Utilities.SELECTED_CHAPTER, Integer.toString(SelectedChapter));
            startActivity(lessonActivity);
        }
    }

    @Override
    public void onRightBoxClick(View view, int pos, int row_index) {
        Log.i("myapp_info", "Selected Lesson " + Integer.toString(pos));
        Log.i("myapp_info", "Row index " + Integer.toString(row_index));

        if(LessonList.get(mLessonsBox.get(row_index).GetRightLessonBox().GetIdentifier()).IsLocked()) {
            Toast.makeText(this , "Complete previous lessons to unlock", Toast.LENGTH_LONG);
        } else {

            Intent lessonActivity = new Intent(this, InLessonActivity.class);
            lessonActivity.putExtra(Utilities.SELECTED_LESSON, Integer.toString(mLessonsBox.get(row_index).GetRightLessonBox().GetIdentifier()));
            lessonActivity.putExtra(Utilities.SELECTED_CHAPTER, Integer.toString(SelectedChapter));
            startActivity(lessonActivity);
        }
    }
}
