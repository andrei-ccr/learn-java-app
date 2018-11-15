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
                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("1. Try, Catch, Finally", "Description", 201),
                        new RVA_Lessons.LessonBox("2. Basic File I/O", "Description", 202)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("3. Get Date and Time", "Description", 203),
                        new RVA_Lessons.LessonBox("4. Regular Expressions", "Description", 204)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("5. Advanced Functions (Methods)", "Description", 205),
                        new RVA_Lessons.LessonBox("6. Program Arguments", "Description", 206)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("7. Modifiers", "Description", 207),
                        new RVA_Lessons.LessonBox("8. Exceptions", "Description", 208)));
                break;
            case 3:
                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("1. Basics", "Description", 301),
                        new RVA_Lessons.LessonBox("2. Encapsulation", "Description", 302)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("3. Inheritance", "Description", 303),
                        new RVA_Lessons.LessonBox("4. Overriding", "Description", 304)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("5. Polymorphism", "Description", 305),
                        new RVA_Lessons.LessonBox("6. Abstraction", "Description", 306)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("7. Interfaces", "Description", 307),
                        new RVA_Lessons.LessonBox("8. Packages", "Description", 308)));
                break;
            case 4:
                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("1. Data Structures", "Description", 401),
                        new RVA_Lessons.LessonBox("2. Collections", "Description", 402)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("3. Generics", "Description", 403),
                        new RVA_Lessons.LessonBox("4. Serialization", "Description", 404)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("5. Networking and Emails", "Description", 405),
                        new RVA_Lessons.LessonBox("6. Multithreading", "Description", 406)));

                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox("7. Applets", "Description", 407),
                        new RVA_Lessons.LessonBox("8. Lambda Expressions", "Description", 408)));
                break;
            default:
                return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);


        //TODO: Don't get selectedChapter when coming back from InLessonActivity
        int selectedChapter = 1; //This is a temporary fix until the issue is fixed
        try {
            Intent parentActivity = getIntent();
            selectedChapter = Integer.parseInt(parentActivity.getStringExtra(ChaptersActivity.SELECTED_CHAPTER));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        Intent lessonActivity = new Intent(this, InLessonActivity.class);
        lessonActivity.putExtra(SELECTED_LESSON, mLessonsBox.get(pos).GetLeftLessonBox().LessonId());
        startActivity(lessonActivity);
    }

    @Override
    public void onRightBoxClick(View view, int pos, int row_index) {
        Log.i("myapp_info", "Selected Lesson " + Integer.toString(pos));
        Log.i("myapp_info", "Row index " + Integer.toString(row_index));


        Intent lessonActivity = new Intent(this, InLessonActivity.class);
        lessonActivity.putExtra(SELECTED_LESSON, mLessonsBox.get(pos).GetLeftLessonBox().LessonId());
        startActivity(lessonActivity);
    }
}
