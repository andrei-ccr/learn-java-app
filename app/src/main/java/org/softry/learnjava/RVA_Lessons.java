package org.softry.learnjava;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVA_Lessons extends RecyclerView.Adapter<RVA_Lessons.mViewHolder> {

    public static class LessonBox {
        private String name;
        private String desc;
        private int lessonId;

        public LessonBox(String name, String desc, int lessonId) {
            this.name = name;
            this.desc = desc;
            this.lessonId = lessonId;
        }

        public String Name() {
            return this.name;
        }
        public String Desc() {
            return this.desc;
        }

        public int LessonId() {return this.lessonId; }
    }

    public static class LessonBoxRow {
        private LessonBox[] lessonBox;

        public LessonBoxRow(LessonBox leftLessonBox, LessonBox rightLessonBox) {
            this.lessonBox = new LessonBox[2];
            this.lessonBox[0] = leftLessonBox;
            this.lessonBox[1] = rightLessonBox;
        }

        public LessonBox GetLeftLessonBox() {
            return this.lessonBox[0];
        }

        public LessonBox GetRightLessonBox() {
            return this.lessonBox[1];
        }

        public LessonBox GetLessonBox(int pos) {
            if(pos % 2 == 0) return this.lessonBox[0]; //If pos is even, selected box is from left
            else return this.lessonBox[1];
        }
    }

    private List<LessonBoxRow> mLessonBoxRows;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lessonTitleL, lessonDescL, lessonTitleR, lessonDescR;
        LinearLayout leftLessonBox, rightLessonBox;

        public mViewHolder(View itemView) {
            super(itemView);
            lessonTitleL = itemView.findViewById(R.id.tvLessonTitleL);
            lessonDescL = itemView.findViewById(R.id.tvLessonDescL);
            lessonTitleR = itemView.findViewById(R.id.tvLessonTitleR);
            lessonDescR = itemView.findViewById(R.id.tvLessonDescR);

            leftLessonBox = itemView.findViewById(R.id.leftLessonBox);
            rightLessonBox = itemView.findViewById(R.id.rightLessonBox);

            itemView.setOnClickListener(this);
            leftLessonBox.setOnClickListener(this);
            rightLessonBox.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                int position = getAdapterPosition();
                if(view.getId() == R.id.leftLessonBox) {
                   mClickListener.onLeftBoxClick(view, 2*position, position);
                } else if(view.getId() == R.id.rightLessonBox) {
                    mClickListener.onRightBoxClick(view, 2*position+1, position);
                } else {
                    //mClickListener.onItemClick(view, getAdapterPosition());
                }

            }
        }
    }

    RVA_Lessons(Context context, List<LessonBoxRow> entryObjList) {
        this.mInflater = LayoutInflater.from(context);
        this.mLessonBoxRows = entryObjList;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_lesson, parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        LessonBoxRow itemElem = mLessonBoxRows.get(position);

        holder.lessonTitleL.setText(itemElem.GetLeftLessonBox().Name());
        holder.lessonDescL.setText(itemElem.GetLeftLessonBox().Desc());

        holder.lessonTitleR.setText(itemElem.GetRightLessonBox().Name());
        holder.lessonDescR.setText(itemElem.GetRightLessonBox().Desc());

    }

    @Override
    public int getItemCount() {
        return mLessonBoxRows.size();
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
        //void onItemClick(View view, int position);
        void onLeftBoxClick(View view, int position, int row_index);
        void onRightBoxClick(View view, int position, int row_index);
    }
}
