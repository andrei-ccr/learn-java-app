package org.softry.learnjava;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChaptersActivity extends AppCompatActivity implements RVA_Chapters.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        List<RVA_Chapters.Chapter> mChapters = new ArrayList<>();
        mChapters.add(new RVA_Chapters.Chapter(getResources().getString(R.string.chapter1_title), getResources().getString(R.string.chapter1_name), getResources().getString(R.string.chapter1_desc)));
        mChapters.add(new RVA_Chapters.Chapter(getResources().getString(R.string.chapter2_title), getResources().getString(R.string.chapter2_name), getResources().getString(R.string.chapter2_desc)));

        //Set Recycler View divider
        DividerItemDecoration divItemDec = new DividerItemDecoration(getBaseContext(),DividerItemDecoration.VERTICAL);
        divItemDec.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.rv_divider));

        //Set Recycler View
        RecyclerView recyclerView = findViewById(R.id.rvChapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(divItemDec);

        //Set Recycler View adapter
        RVA_Chapters rvAdapter;
        rvAdapter = new RVA_Chapters(this, mChapters);
        rvAdapter.setClickListener(this);
        recyclerView.setAdapter(rvAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        /*Intent readThisEntry = new Intent(this, ReadJournal.class);
        String entryUid = rvAdapter.getItemUID(position);
        readThisEntry.putExtra(ENTRY_UID_KEY, entryUid);
        startActivity(readThisEntry);*/
    }
}
