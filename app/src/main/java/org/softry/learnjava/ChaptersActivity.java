package org.softry.learnjava;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChaptersActivity extends AppCompatActivity implements RVA_Chapters.ItemClickListener {

    public static final String SELECTED_CHAPTER = "org.softry.learnjava.TAG.SELECTED_CHAPTER";

    private RecyclerView mRecyclerView;
    private final Integer[] comingSoonChapters = {2,3,4,5,6,7};

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        mRecyclerView = findViewById(R.id.rvChapters);

        InitRV_Chapters();

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

    public static void setGrayScale(ImageView v) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0.1f);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

    public static void setImgTint(ImageView v, int colorId) {
        v.setColorFilter(ContextCompat.getColor(v.getContext(), colorId ), PorterDuff.Mode.MULTIPLY);
    }
}
