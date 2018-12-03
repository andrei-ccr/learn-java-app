package org.softry.learnjava;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVA_Chapters extends RecyclerView.Adapter  {

    private List<Containers.Chapter> mChapters;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public class chapterBoxViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView chapterNumber, chapterName, chapterDesc, chapterProgress;
        LinearLayout container_chapter_images;

        public chapterBoxViewHolder(View itemView) {
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
    public class categoryTitleViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public categoryTitleViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tvChapterCategory);
        }
    }

    RVA_Chapters(Context context, List<Containers.Chapter> entryObjList) {
        this.mInflater = LayoutInflater.from(context);
        this.mChapters = entryObjList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if((position == 0) || (position == 4)) {
            return 1;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch(viewType) {
            case 0:
                view = mInflater.inflate(R.layout.layout_chapter, parent,false);
                return new chapterBoxViewHolder(view);
            case 1:
                view = mInflater.inflate(R.layout.layout_chapter_category, parent,false);
                return new categoryTitleViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()) {
            case 0:
                chapterBoxViewHolder viewHolder0 = (chapterBoxViewHolder)holder;
                Containers.Chapter itemElem = mChapters.get(position-1);

                /*TypedArray images = this.context.getResources().obtainTypedArray(R.array.lessonImgList);
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
                }*/

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if(position == 0)
                        ((LinearLayout)viewHolder0.chapterDesc.getParent()).setBackground(this.context.getResources().getDrawable(R.drawable.chapter1_box));
                    else if(position == 1)
                        ((LinearLayout)viewHolder0.chapterDesc.getParent()).setBackground(this.context.getResources().getDrawable(R.drawable.chapter2_box));
                    else if(position == 2)
                        ((LinearLayout)viewHolder0.chapterDesc.getParent()).setBackground(this.context.getResources().getDrawable(R.drawable.chapter3_box));

                }
                viewHolder0.chapterNumber.setText(itemElem.GetNumber());
                viewHolder0.chapterName.setText(itemElem.GetName());
                viewHolder0.chapterDesc.setText(itemElem.GetLongDesc());
                viewHolder0.chapterProgress.setText(itemElem.GetProgressStr());
                if(itemElem.GetProgressValue() >= 0 && itemElem.GetProgressValue() < 25) {
                    viewHolder0.chapterProgress.setTextColor((int)R.color._progressRed);
                }
                else if(itemElem.GetProgressValue() >= 25 && itemElem.GetProgressValue() <= 50) {
                    viewHolder0.chapterProgress.setTextColor((int)R.color._progressOrange);
                }
                else if(itemElem.GetProgressValue() >= 51 && itemElem.GetProgressValue() < 80) {
                    viewHolder0.chapterProgress.setTextColor((int)R.color._progressYellow);
                }
                else if(itemElem.GetProgressValue() >= 81 && itemElem.GetProgressValue() <= 100) {
                    viewHolder0.chapterProgress.setTextColor((int)R.color._progressGreen);
                }
                else {
                    viewHolder0.chapterProgress.setTextColor((int)R.color._gray);
                }
                break;
            case 1:
                categoryTitleViewHolder viewHolder1 = (categoryTitleViewHolder)holder;
                if(position==0) viewHolder1.categoryName.setText("-- Level: Beginner --");
                else if(position==4) viewHolder1.categoryName.setText("-- Level: Advanced --");
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mChapters.size()+2;
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
