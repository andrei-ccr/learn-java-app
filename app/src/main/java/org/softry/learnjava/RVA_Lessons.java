package org.softry.learnjava;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

public class RVA_Lessons extends RecyclerView.Adapter<RVA_Lessons.mViewHolder> {

    public static class LessonBox {
        private Containers.Lesson lesson;
        private int indentifier;

        public LessonBox() {
            this.lesson = null;
            this.indentifier = -1;
        }

        public LessonBox(Containers.Lesson lesson, int identifier) {
            this.lesson = lesson;
            this.indentifier = identifier;
        }

        public int GetIdentifier() {
            return this.indentifier;
        }

        public Containers.Lesson GetLesson() {
            return this.lesson;
        }
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
    private Context context;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lessonTitleL, lessonDescL, lessonTitleR, lessonDescR;
        TextView lessonProgressL, lessonProgressR;
        ImageView lessonImageL, lessonImageR, lessonProgressSymbolL, lessonProgressSymbolR;
        LinearLayout leftLessonBox, rightLessonBox, statusContainerLeft, statusContainerRight, comingSoonLeft, comingSoonRight;

        public mViewHolder(View itemView) {
            super(itemView);

            //Left Box elements
            lessonTitleL = itemView.findViewById(R.id.tvLessonTitleL);
            lessonDescL = itemView.findViewById(R.id.tvLessonDescL);
            lessonImageL = itemView.findViewById(R.id.ivLessonImgL);
            lessonProgressL = itemView.findViewById(R.id.tvLessonProgressL);
            lessonProgressSymbolL = itemView.findViewById(R.id.ivProgressSymbolL);

            statusContainerLeft = itemView.findViewById(R.id.lessonStatusContainerL);
            comingSoonLeft = itemView.findViewById(R.id.lessonComingSoonL);

            //Right Box elements
            lessonTitleR = itemView.findViewById(R.id.tvLessonTitleR);
            lessonDescR = itemView.findViewById(R.id.tvLessonDescR);
            lessonImageR = itemView.findViewById(R.id.ivLessonImgR);
            lessonProgressR = itemView.findViewById(R.id.tvLessonProgressR);
            lessonProgressSymbolR = itemView.findViewById(R.id.ivProgressSymbolR);

            statusContainerRight = itemView.findViewById(R.id.lessonStatusContainerR);
            comingSoonRight = itemView.findViewById(R.id.lessonComingSoonR);

            //Box container (Linear Layout)
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
                }

            }
        }
    }

    RVA_Lessons(Context context, List<LessonBoxRow> entryObjList) {
        this.mInflater = LayoutInflater.from(context);
        this.mLessonBoxRows = entryObjList;
        this.context = context;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.layout_lesson, parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        LessonBoxRow itemElem = mLessonBoxRows.get(position);

        //If right LessonBox is null just hide it
        if((itemElem.GetRightLessonBox() == null ) || (itemElem.GetRightLessonBox().GetLesson() == null)) {
            LinearLayout lessonBox = (LinearLayout)holder.lessonTitleR.getParent();
            lessonBox.setVisibility(View.GONE);
        } else {

            //Set title, description and image of right LessonBox
            holder.lessonTitleR.setText(itemElem.GetRightLessonBox().GetLesson().GetTitle());
            holder.lessonDescR.setText(itemElem.GetRightLessonBox().GetLesson().GetDesc());
            try {
                holder.lessonImageR.setImageDrawable(context.getResources().getDrawable(itemElem.GetRightLessonBox().GetLesson().GetImageRID()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Set Status
            Log.e("myapp", Integer.toString(position));
            if(itemElem.GetRightLessonBox().GetLesson().ComingSoon()) {
                holder.statusContainerRight.setVisibility(View.GONE);
                holder.comingSoonRight.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.rightLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_locked));
                }
            } else {
                holder.statusContainerRight.setVisibility(View.VISIBLE);
                holder.comingSoonRight.setVisibility(View.GONE);

                if(itemElem.GetRightLessonBox().GetLesson().IsLocked()) {
                    holder.lessonProgressSymbolR.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_locked));
                    holder.lessonProgressR.setText(" ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.rightLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_locked));
                    }
                } else {
                    holder.lessonProgressR.setText(itemElem.GetRightLessonBox().GetLesson().GetCompletedProcent() + "%");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.rightLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box));
                    }
                    if (itemElem.GetRightLessonBox().GetLesson().GetCompletedProcent() == 100) {
                        holder.lessonProgressSymbolR.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_check));
                        holder.lessonProgressSymbolR.setColorFilter(this.context.getResources().getColor(R.color._gold));
                        holder.lessonProgressR.setTextColor(this.context.getResources().getColor(R.color._gold));
                        holder.lessonProgressR.setText(" ");

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            holder.rightLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_stroke));
                        }
                    } else {
                        holder.lessonProgressSymbolR.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_progress));
                    }
                } /* END IF Lesson Right isLocked */
            }

        } /* END IF Right Lesson Box exists */

        //Set title, description and image of left LessonBox
        holder.lessonTitleL.setText(itemElem.GetLeftLessonBox().GetLesson().GetTitle());
        holder.lessonDescL.setText(itemElem.GetLeftLessonBox().GetLesson().GetDesc());
        try {
            holder.lessonImageL.setImageDrawable(context.getResources().getDrawable(itemElem.GetLeftLessonBox().GetLesson().GetImageRID()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(itemElem.GetLeftLessonBox().GetLesson().ComingSoon()) {
            holder.statusContainerLeft.setVisibility(View.GONE);
            holder.comingSoonLeft.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.leftLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_locked));
            }
        } else {
            holder.statusContainerLeft.setVisibility(View.VISIBLE);
            holder.comingSoonLeft.setVisibility(View.GONE);
            if (itemElem.GetLeftLessonBox().GetLesson().IsLocked()) {
                holder.lessonProgressSymbolL.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_locked));
                holder.lessonProgressL.setText("");
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.leftLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_locked));
                }
            } else {
                holder.lessonProgressL.setText(itemElem.GetLeftLessonBox().GetLesson().GetCompletedProcent() + "%");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.leftLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box));
                }
                if (itemElem.GetLeftLessonBox().GetLesson().GetCompletedProcent() == 100) {
                    holder.lessonProgressSymbolL.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_check));
                    holder.lessonProgressSymbolL.setColorFilter(this.context.getResources().getColor(R.color._gold));
                    holder.lessonProgressL.setTextColor(this.context.getResources().getColor(R.color._gold));
                    holder.lessonProgressL.setText("");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.leftLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_stroke));
                    }
                } else {
                    holder.lessonProgressSymbolL.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_progress));
                }
            } /* END IF Lesson Left isLocked */
        }


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
