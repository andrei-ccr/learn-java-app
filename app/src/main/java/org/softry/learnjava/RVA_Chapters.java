package org.softry.learnjava;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVA_Chapters extends RecyclerView.Adapter<RVA_Chapters.mViewHolder> {

    public static class Chapter {
        private String num;
        private String name;
        private String desc;
        private int progress;

        public Chapter(String num, String name, String desc) {
            this.num = num;
            this.name = name;
            this.desc = desc;
            this.progress = -1; // -1 means not started
        }

        public String Num(){
            return this.num;
        }

        public String Name() {
            return this.name;
        }

        public String Desc() {
            return this.desc;
        }

        public String Progress() {
            if(this.progress == -1) {
                return "Not started.";
            } else {
                return Integer.toString(this.progress) + "%";
            }
        }

        public int progressValue() {
            return this.progress;
        }
    }

    private List<Chapter> mChapters;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView chapterNumber, chapterName, chapterDesc, chapterProgress;

        public mViewHolder(View itemView) {
            super(itemView);
            chapterNumber = itemView.findViewById(R.id.tvChapterNum);
            chapterName = itemView.findViewById(R.id.tvChapterName);
            chapterDesc = itemView.findViewById(R.id.tvChapterDesc);
            chapterProgress = itemView.findViewById(R.id.tvChapterProgress);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    RVA_Chapters(Context context, List<Chapter> entryObjList) {
        this.mInflater = LayoutInflater.from(context);
        this.mChapters = entryObjList;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_chapter, parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        Chapter itemElem = mChapters.get(position);

        holder.chapterNumber.setText(itemElem.Num());
        holder.chapterName.setText(itemElem.Name());
        holder.chapterDesc.setText(itemElem.Desc());
        holder.chapterProgress.setText(itemElem.Progress());
        if(itemElem.progressValue() >= 0 && itemElem.progressValue() < 25) {
            holder.chapterProgress.setTextColor((int)R.color._progressRed);
        }
        else if(itemElem.progressValue() >= 25 && itemElem.progressValue() <= 50) {
            holder.chapterProgress.setTextColor((int)R.color._progressOrange);
        }
        else if(itemElem.progressValue() >= 51 && itemElem.progressValue() < 80) {
            holder.chapterProgress.setTextColor((int)R.color._progressYellow);
        }
        else if(itemElem.progressValue() >= 81 && itemElem.progressValue() <= 100) {
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
