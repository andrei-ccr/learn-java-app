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
    public static int SelectedChapter;
    public RecyclerView mRecyclerView;
    public RVA_Lessons rvAdapter;

    private void SetLessonBoxList(int chapter) {
        mLessonsBox = new ArrayList<>();

        Integer[] lessonsCurrentChapter = MainActivity.ChapterLessonList.get(chapter);
        Log.e("myapp", "Showing read status from lesson list (first lesson)");
        for(int i: MainActivity.LessonList.get(0).GetLessonContent().GetAllReadStatus()) {
            Log.e("myapp", Integer.toString(i));
        }

        for(int i=lessonsCurrentChapter[0];i<lessonsCurrentChapter.length-1;i+=2) {
            if(Utilities.InArray(i+1, lessonsCurrentChapter)) {
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
        Log.i("myapp", "OnCreate() called");

        TypedArray chapterThemeList = getResources().obtainTypedArray(R.array.ChaptersColor);

        try {
            Intent parentActivity = getIntent();
            SelectedChapter = Integer.parseInt(parentActivity.getStringExtra(Utilities.SELECTED_CHAPTER));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTheme(chapterThemeList.getResourceId(SelectedChapter,-1));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        ConstraintLayout cLayout = findViewById(R.id.cLayout);
        if(SelectedChapter == 0) {
            cLayout.setBackgroundColor(getResources().getColor(R.color._chapter1color));
        } else if(SelectedChapter == 1) {
            cLayout.setBackgroundColor(getResources().getColor(R.color._chapter2color));
        } else if(SelectedChapter == 2) {
            cLayout.setBackgroundColor(getResources().getColor(R.color._chapter3color));
        } else if(SelectedChapter == 3) {
            cLayout.setBackgroundColor(getResources().getColor(R.color._chapter4color));
        }


        TextView tvChapterDesc = findViewById(R.id.tvSelectedChapterDesc);
        Containers.Chapter thisChapter = MainActivity.ChapterList.get(SelectedChapter);

        this.setTitle(thisChapter.GetName() + " - " + getString(R.string.app_name));
        tvChapterDesc.setText(thisChapter.GetShortDesc());

        SetLessonBoxList(SelectedChapter);

        rvAdapter = new RVA_Lessons(this, this.mLessonsBox);
        rvAdapter.setClickListener(this);
        mRecyclerView = findViewById(R.id.rvLessons);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(rvAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("myapp", "OnResume called()");
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i("myapp", "OnRestart called()");
        rvAdapter.notifyDataSetChanged();
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
