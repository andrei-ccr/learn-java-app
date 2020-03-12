package org.softry.learnjava.org.softry.learnjava.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.softry.learnjava.R;
import org.softry.learnjava.org.softry.learnjava.content.Lesson;

import java.util.List;

public class RVA_Lessons extends RecyclerView.Adapter<RVA_Lessons.mViewHolder> {


    /**
     * Contains information found a in a lesson box:
     * - a Lesson object
     * - a number identifier that should be unique
     */
    public static class LessonBox {
        private Lesson lesson;
        private int identifier;

        public LessonBox() {
            this.lesson = null;
            this.identifier = -1;
        }

        public LessonBox(Lesson lesson, int identifier) {
            this.lesson = lesson;
            this.identifier = identifier;
        }

        public int GetIdentifier() {
            return this.identifier;
        }

        Lesson GetLesson() {
            return this.lesson;
        }
    }


    /**
     * Contains two LessonBox objects, one on the left side
     * and one on the right side
     */
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

        mViewHolder(View itemView) {
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

    public RVA_Lessons(Context context, List<LessonBoxRow> entryObjList) {
        this.mInflater = LayoutInflater.from(context);
        this.mLessonBoxRows = entryObjList;
        this.context = context;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.layout_lesson, parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        LessonBoxRow itemElem = mLessonBoxRows.get(position);
        LessonBox leftLessonBox = itemElem.GetLeftLessonBox(), rightLessonBox = itemElem.GetRightLessonBox();

        //If right LessonBox is null just hide it
        if((rightLessonBox == null ) || (rightLessonBox.GetLesson() == null)) {
            LinearLayout lessonBox = (LinearLayout)holder.lessonTitleR.getParent();
            lessonBox.setVisibility(View.GONE);
        } else {
            //Set title, description and image of right LessonBox
            holder.lessonTitleR.setText(rightLessonBox.GetLesson().GetTitle());
            holder.lessonDescR.setText(rightLessonBox.GetLesson().GetDesc());
            try {
                holder.lessonImageR.setImageDrawable(context.getResources().getDrawable(rightLessonBox.GetLesson().GetImageRID()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Set Status
            if(rightLessonBox.GetLesson().ComingSoon()) {
                holder.statusContainerRight.setVisibility(View.GONE);
                holder.comingSoonRight.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.rightLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_locked));
                }
            } else {
                holder.statusContainerRight.setVisibility(View.VISIBLE);
                holder.comingSoonRight.setVisibility(View.GONE);

                if(rightLessonBox.GetLesson().IsLocked()) {
                    holder.lessonProgressSymbolR.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_locked));
                    holder.lessonProgressR.setText(" ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.rightLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_locked));
                    }
                } else {
                    holder.lessonProgressR.setText(rightLessonBox.GetLesson().GetCompletedPercent(context) + "%");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.rightLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box));
                    }
                    if (rightLessonBox.GetLesson().GetCompletedPercent(context) == 100) {
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
                } /* END IF Right Lesson isLocked */
            } /* END IF Right Lesson coming soon*/
        } /* END IF Right Lesson Box exists */

        //Set title, description and image of left LessonBox
        holder.lessonTitleL.setText(leftLessonBox.GetLesson().GetTitle());
        holder.lessonDescL.setText(leftLessonBox.GetLesson().GetDesc());
        try {
            holder.lessonImageL.setImageDrawable(context.getResources().getDrawable(leftLessonBox.GetLesson().GetImageRID()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(leftLessonBox.GetLesson().ComingSoon()) {
            holder.statusContainerLeft.setVisibility(View.GONE);
            holder.comingSoonLeft.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.leftLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_locked));
            }
        } else {
            holder.statusContainerLeft.setVisibility(View.VISIBLE);
            holder.comingSoonLeft.setVisibility(View.GONE);
            if (leftLessonBox.GetLesson().IsLocked()) {
                holder.lessonProgressSymbolL.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_locked));
                holder.lessonProgressL.setText("");
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.leftLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box_locked));
                }
            } else {
                holder.lessonProgressL.setText(leftLessonBox.GetLesson().GetCompletedPercent(context) + "%");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.leftLessonBox.setBackground(this.context.getResources().getDrawable(R.drawable.white_box));
                }
                if (leftLessonBox.GetLesson().GetCompletedPercent(context) == 100) {
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

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onLeftBoxClick(View view, int position, int row_index);
        void onRightBoxClick(View view, int position, int row_index);
    }
}
