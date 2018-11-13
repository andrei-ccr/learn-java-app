package org.softry.learnjava;

import android.content.Intent;
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

        //TODO: Add other chapters lessons
        //TODO: Move all strings to strings.xml
        switch(chapter) {
            case 1:
                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("1. Install Java", "Description", 101),
                        new RVA_Lessons.LessonBox("2. First Program", "Description", 102)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("3. Variables", "Description", 103),
                        new RVA_Lessons.LessonBox("4. User Input", "Description", 104)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("5. Conditionals", "Description", 105),
                        new RVA_Lessons.LessonBox("6. Loops", "Description", 106)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("7. Arrays", "Description", 107),
                        new RVA_Lessons.LessonBox("8. Functions (Methods)", "Description", 108)));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        Intent parentActivity = getIntent();
        int selectedChapter = Integer.parseInt(parentActivity.getStringExtra(ChaptersActivity.SELECTED_CHAPTER));

        TextView tvChapterDesc = findViewById(R.id.tvSelectedChapterDesc);

        switch(selectedChapter) {
            case 1:
                this.setTitle(R.string.chapter1_title);
                tvChapterDesc.setText(R.string.chapter1_short_desc);
                break;
            case 2:
                this.setTitle(R.string.chapter2_title);
                tvChapterDesc.setText(R.string.chapter2_short_desc);
                break;
            case 3:
                this.setTitle(R.string.chapter3_title);
                tvChapterDesc.setText(R.string.chapter3_short_desc);
                break;
            case 4:
                this.setTitle(R.string.chapter4_title);
                tvChapterDesc.setText(R.string.chapter4_short_desc);
                break;
        }

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

        /*Intent lessonActivity = new Intent(this, LessonsActivity.class);
        lessonActivity.putExtra(SELECTED_LESSON, mLessonsBox.get(pos).GetLeftLessonBox().LessonId());
        startActivity(lessonActivity);*/
    }

    @Override
    public void onRightBoxClick(View view, int pos, int row_index) {
        Log.i("myapp_info", "Selected Lesson " + Integer.toString(pos));
        Log.i("myapp_info", "Row index " + Integer.toString(row_index));


        /*Intent lessonActivity = new Intent(this, LessonsActivity.class);
        lessonActivity.putExtra(SELECTED_LESSON, mLessonsBox.get(pos).GetLeftLessonBox().LessonId());
        startActivity(lessonActivity);*/
    }
}
