package org.softry.learnjava.org.softry.learnjava.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.softry.learnjava.R;
import org.softry.learnjava.org.softry.learnjava.content.Chapter;
import org.softry.learnjava.org.softry.learnjava.content.Utilities;

import java.util.List;

public class RVA_Chapters extends RecyclerView.Adapter  {

    private List<Chapter> mChapters;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public class chapterBoxViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView chapterNumber, chapterName, chapterDesc, chapterProgress;
        ImageView chapterImage, chapterProgressSymbol;
        LinearLayout container_chapter_color, chapterBoxContainer;

        chapterBoxViewHolder(View itemView) {
            super(itemView);
            chapterNumber = itemView.findViewById(R.id.tvChapterNum);
            chapterName = itemView.findViewById(R.id.tvChapterName);
            chapterDesc = itemView.findViewById(R.id.tvChapterDesc);
            chapterProgress = itemView.findViewById(R.id.tvChapterProgress);
            container_chapter_color = itemView.findViewById(R.id.viewChapterColorLayout);
            chapterImage = itemView.findViewById(R.id.ivChapterImage);
            chapterProgressSymbol = itemView.findViewById(R.id.ivChapterProgressSymbol);
            chapterBoxContainer = itemView.findViewById(R.id.chapterBoxContainer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onChapterClick(view, getAdapterPosition());
        }
    }
    public class categoryTitleViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        categoryTitleViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tvChapterCategory);
        }
    }

    public RVA_Chapters(Context context, List<Chapter> entryObjList) {
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()) {
            case 0:
                position = (position>4)?(position-2):(position-1);
                chapterBoxViewHolder viewHolder0 = (chapterBoxViewHolder)holder;
                Chapter itemElem = mChapters.get(position);


                viewHolder0.chapterImage.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_clone));
                if(position == 2) viewHolder0.chapterImage.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_boxes));

                if(position>=3) {
                    viewHolder0.chapterImage.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_fa_locked));
                    viewHolder0.chapterProgress.setVisibility(View.GONE);
                    viewHolder0.chapterProgressSymbol.setVisibility(View.GONE);
                } else {
                    viewHolder0.chapterProgress.setText(itemElem.GetProgressStr(context));
					viewHolder0.chapterProgress.setVisibility(View.VISIBLE);
					if(itemElem.GetProgressStr(context).equalsIgnoreCase("")) {
                        viewHolder0.chapterProgressSymbol.setVisibility(View.GONE);
                        viewHolder0.chapterProgress.setVisibility(View.GONE);
                    } else {
                        viewHolder0.chapterProgressSymbol.setVisibility(View.VISIBLE);
                        viewHolder0.chapterProgress.setVisibility(View.VISIBLE);
                    }
                }

                //viewHolder0.chapterNumber.setText(itemElem.GetNumber());
                viewHolder0.chapterName.setText(itemElem.GetNumber() + " " + itemElem.GetName());
                viewHolder0.chapterDesc.setText(itemElem.GetLongDesc());


                break;
            case 1:
                categoryTitleViewHolder viewHolder1 = (categoryTitleViewHolder)holder;
                if(position==0) viewHolder1.categoryName.setText(context.getString(R.string.lvl_beginner_str));
                else if(position==4) viewHolder1.categoryName.setText(context.getString(R.string.lvl_advanced_str));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(!Utilities.ShowProOnlyChapters) {
            return 4;
        } else
            return mChapters.size()+2;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onChapterClick(View view, int position);
    }
}
