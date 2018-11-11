package org.softry.learnjava;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChaptersActivity extends AppCompatActivity implements RVA_Chapters.ItemClickListener {

    public static final String SELECTED_CHAPTER = "org.softry.learnjava.TAG.SELECTED_CHAPTER";
    private List<RVA_Chapters.Chapter> mChapters;
    private RecyclerView mRecyclerView;

    private void SetChapterList() {
        mChapters = new ArrayList<>();
        mChapters.add(new RVA_Chapters.Chapter(getResources().getString(R.string.chapter1_title),
                getResources().getString(R.string.chapter1_name),
                getResources().getString(R.string.chapter1_desc)));

        mChapters.add(new RVA_Chapters.Chapter(getResources().getString(R.string.chapter2_title),
                getResources().getString(R.string.chapter2_name),
                getResources().getString(R.string.chapter2_desc)));

        mChapters.add(new RVA_Chapters.Chapter(getResources().getString(R.string.chapter3_title),
                getResources().getString(R.string.chapter3_name),
                getResources().getString(R.string.chapter3_desc)));

        mChapters.add(new RVA_Chapters.Chapter(getResources().getString(R.string.chapter4_title),
                getResources().getString(R.string.chapter4_name),
                getResources().getString(R.string.chapter4_desc)));
    }

    private RVA_Chapters GetChaptersRVA() {
        RVA_Chapters rvAdapter;
        if(this.mChapters == null) return null;

        rvAdapter = new RVA_Chapters(this, this.mChapters);
        rvAdapter.setClickListener(this);
        return rvAdapter;
    }

    //TODO: Create exception for null adapter
    private void InitRV_Chapters() throws Exception{
        RVA_Chapters adapter = GetChaptersRVA();
        if (adapter == null) {
            throw new Exception("Null adapter. Call SetChapterList() first.");
        }

        //Set Recycler View divider
        DividerItemDecoration divItemDec = new DividerItemDecoration(getBaseContext(),DividerItemDecoration.VERTICAL);
        divItemDec.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.rv_divider));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(divItemDec);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        mRecyclerView = findViewById(R.id.rvChapters);

        SetChapterList();

        try {
            InitRV_Chapters();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("myapp_info", "Selected Chapter " + Integer.toString(position+1));

        Intent lessonsActivity = new Intent(this, LessonsActivity.class);
        lessonsActivity.putExtra(SELECTED_CHAPTER, Integer.toString(position+1));
        startActivity(lessonsActivity);
    }
}
