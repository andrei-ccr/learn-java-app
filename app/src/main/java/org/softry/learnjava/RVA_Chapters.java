package org.softry.learnjava;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVA_Chapters extends RecyclerView.Adapter<RVA_Chapters.mViewHolder> {

    private List<Containers.Chapter> mChapters;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView chapterNumber, chapterName, chapterDesc, chapterProgress;
        LinearLayout container_chapter_images;

        public mViewHolder(View itemView) {
            super(itemView);
            chapterNumber = itemView.findViewById(R.id.tvChapterNum);
            chapterName = itemView.findViewById(R.id.tvChapterName);
            chapterDesc = itemView.findViewById(R.id.tvChapterDesc);
            chapterProgress = itemView.findViewById(R.id.tvChapterProgress);
            container_chapter_images = itemView.findViewById(R.id.container_chapter_images);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    RVA_Chapters(Context context, List<Containers.Chapter> entryObjList) {
        this.mInflater = LayoutInflater.from(context);
        this.mChapters = entryObjList;
        this.context = context;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_chapter, parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        Containers.Chapter itemElem = mChapters.get(position);

        TypedArray images = this.context.getResources().obtainTypedArray(R.array.lessonImgList);
        Integer[] chapterLessonList = MainActivity.ChapterLessonList.get(position);
        try {
            for (int i = chapterLessonList[0]; i < chapterLessonList.length; i++) {
                ImageView imgView = new ImageView(this.context);
                imgView.setImageDrawable(this.context.getResources().getDrawable(images.getResourceId(i, R.drawable.first_program)));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
                params.setMargins(8, 8, 8, 8);
                imgView.setLayoutParams(params);

                ChaptersActivity.setGrayScale(imgView);

                holder.container_chapter_images.addView(imgView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("myapp", "Chapter " + Integer.toString(position) + " won't have images due to Exception");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if(position == 0)
                ((LinearLayout)holder.chapterDesc.getParent()).setBackground(this.context.getResources().getDrawable(R.drawable.chapter1_box));
            else if(position == 1)
                ((LinearLayout)holder.chapterDesc.getParent()).setBackground(this.context.getResources().getDrawable(R.drawable.chapter2_box));
            else if(position == 2)
                ((LinearLayout)holder.chapterDesc.getParent()).setBackground(this.context.getResources().getDrawable(R.drawable.chapter3_box));

        }
        holder.chapterNumber.setText(itemElem.GetNumber());
        holder.chapterName.setText(itemElem.GetName());
        holder.chapterDesc.setText(itemElem.GetLongDesc());
        holder.chapterProgress.setText(itemElem.GetProgressStr());
        if(itemElem.GetProgressValue() >= 0 && itemElem.GetProgressValue() < 25) {
            holder.chapterProgress.setTextColor((int)R.color._progressRed);
        }
        else if(itemElem.GetProgressValue() >= 25 && itemElem.GetProgressValue() <= 50) {
            holder.chapterProgress.setTextColor((int)R.color._progressOrange);
        }
        else if(itemElem.GetProgressValue() >= 51 && itemElem.GetProgressValue() < 80) {
            holder.chapterProgress.setTextColor((int)R.color._progressYellow);
        }
        else if(itemElem.GetProgressValue() >= 81 && itemElem.GetProgressValue() <= 100) {
            holder.chapterProgress.setTextColor((int)R.color._progressGreen);
        }
        else {
            holder.chapterProgress.setTextColor((int)R.color._gray);
        }

    }

    @Override
    public int getItemCount() {
        return mChapters.size();
    }


    /*String getEntryText(int id) {
        return mEntryObjList.get(id).getText();
    }

    String getEntryTime(int id) {
        return mEntryObjList.get(id).getTimestamp();
    }*/


    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
