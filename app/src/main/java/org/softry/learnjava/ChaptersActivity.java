package org.softry.learnjava;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChaptersActivity extends AppCompatActivity implements RVA_Chapters.ItemClickListener {

    public static final String SELECTED_CHAPTER = "org.softry.learnjava.TAG.SELECTED_CHAPTER";

    private RecyclerView mRecyclerView;
    private final Integer[] comingSoonChapters = {5,6,7};

    private RVA_Chapters GetChaptersRVA() {
        RVA_Chapters rvAdapter;

        rvAdapter = new RVA_Chapters(this, MainActivity.ChapterList);
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

        try {
            InitRV_Chapters();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("myapp_info", "Selected Chapter " + Integer.toString(position));

        if( (!InArray(position+1, comingSoonChapters))) {
            Intent lessonsActivity = new Intent(this, LessonsActivity.class);
            lessonsActivity.putExtra(SELECTED_CHAPTER, Integer.toString(position));
            startActivity(lessonsActivity);
        } else {
            Toast.makeText(view.getContext(),"Coming soon", Toast.LENGTH_LONG).show();
        }
    }

    public static <E>boolean InArray(E element, E[] array) {
        for(E arrayElem : array) {
            if(arrayElem.equals(element)) {
                return true;
            }
        }
        return false;
    }
}
