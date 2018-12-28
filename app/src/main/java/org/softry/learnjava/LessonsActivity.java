package org.softry.learnjava;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


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
            } else {
                mLessonsBox.add(new RVA_Lessons.LessonBoxRow(
                        new RVA_Lessons.LessonBox(LessonList.get(i), i),
                        new RVA_Lessons.LessonBox() )
                );
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
        TextView tvComingSoonDesc = findViewById(R.id.tvLessonLockedDesc);
        TextView btnDonate = findViewById(R.id.btnDonate);
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


        if( (Utilities.InArray(SelectedChapter+1, Utilities.ComingSoonChapters))) {
            tvComingSoon.setVisibility(View.VISIBLE);
            //tvComingSoonDesc.setVisibility(View.VISIBLE);
            //btnDonate.setVisibility(View.VISIBLE);
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
    public void onLeftBoxClick(View view, int pos, final int row_index) {
        if(LessonList.get(mLessonsBox.get(row_index).GetLeftLessonBox().GetIdentifier()).IsLocked()) {
			Snackbar.make(view, "Complete the previous lesson to unlock", Snackbar.LENGTH_LONG).setAction("Unlock",  new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO: Show prompt about progress not being saved and watch ad
                            ShowLockedPrompt(view.getContext(), mLessonsBox.get(row_index).GetLeftLessonBox().GetIdentifier());
                        }
                    }).show();
        } else if(Utilities.InArray(mLessonsBox.get(row_index).GetLeftLessonBox().GetIdentifier(), Utilities.ComingSoonLessons)) {
            Snackbar.make(view, "This lesson is coming soon.", Snackbar.LENGTH_LONG).show();
        } else {
            OpenLesson(mLessonsBox.get(row_index).GetLeftLessonBox().GetIdentifier());

        }
    }

    @Override
    public void onRightBoxClick(View view, int pos, final int row_index) {
        if(LessonList.get(mLessonsBox.get(row_index).GetRightLessonBox().GetIdentifier()).IsLocked()) {
			Snackbar.make(view, "Complete the previous lesson to unlock", Snackbar.LENGTH_LONG).setAction("Unlock", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ShowLockedPrompt(view.getContext(), mLessonsBox.get(row_index).GetRightLessonBox().GetIdentifier());
                        }
                    }).show();
        } else if(Utilities.InArray(mLessonsBox.get(row_index).GetRightLessonBox().GetIdentifier(), Utilities.ComingSoonLessons)) {
            Snackbar.make(view, "This lesson is coming soon.", Snackbar.LENGTH_LONG).show();
        } else {
            OpenLesson(mLessonsBox.get(row_index).GetRightLessonBox().GetIdentifier());

        }
    }

    private void OpenLesson(int lesson_index) {
        Intent lessonActivity = new Intent(this, InLessonActivity.class);
        lessonActivity.putExtra(Utilities.SELECTED_LESSON, Integer.toString(lesson_index));
        lessonActivity.putExtra(Utilities.SELECTED_CHAPTER, Integer.toString(SelectedChapter));
        startActivity(lessonActivity);
    }

    private void ShowLockedPrompt(Context c, final int lesson_index) {
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(c);
        dlgAlert.setMessage("You can still read this lesson after you watch an ad. \n\nYour lesson progress won't be saved.");
        dlgAlert.setTitle("Unlock");
        dlgAlert.setPositiveButton("Read lesson", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                OpenLesson(lesson_index);
                dialog.dismiss();
            }
        });
        dlgAlert.setNegativeButton("Go back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
