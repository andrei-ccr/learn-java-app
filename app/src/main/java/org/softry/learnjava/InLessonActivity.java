package org.softry.learnjava;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class InLessonActivity extends AppCompatActivity {

    private int selectedLesson, selectedChapter;
    private Containers.LessonContent lessonContent;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Get current lesson
        Intent parentActivity = getIntent();
        selectedLesson = Integer.parseInt(parentActivity.getStringExtra(Utilities.SELECTED_LESSON)); //Returns lesson id (eg.: 0, 1 etc)
        selectedChapter = Integer.parseInt(parentActivity.getStringExtra(Utilities.SELECTED_CHAPTER)); //Returns lesson id (eg.: 0, 1 etc)

        TypedArray chapterThemeList = getResources().obtainTypedArray(R.array.ChaptersColorNoToolbar);
        setTheme(chapterThemeList.getResourceId(selectedChapter, R.style.AppTheme_ChapterOne_NoToolbar));
        chapterThemeList.recycle();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_lesson);
        final Context context = this;

        lessonContent = Utilities.LessonList.get(selectedLesson).GetLessonContent();

        //Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(GetBackgroundColorId());

        //Mark first page as read
        if(!Utilities.LessonList.get(selectedLesson).IsLocked()){
            lessonContent.MarkPageAsRead(0);
            Utilities.SaveReadProgress(selectedLesson, lessonContent.GetAllReadStatus(), context);
        }

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), Utilities.LessonContentList.get(selectedLesson).length);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TextView tvCurrentPage = findViewById(R.id.tvCurrentPage);
        tvCurrentPage.setText(mViewPager.getCurrentItem()+1 + "/" + Utilities.LessonContentList.get(selectedLesson).length);

        AdView mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("80B20B041E29D3D23790297B560D858C").build();//.addTestDevice("80B20B041E29D3D23790297B560D858C")
        mAdView.loadAd(adRequest);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                tvCurrentPage.setText(mViewPager.getCurrentItem()+1 + "/" + Utilities.LessonContentList.get(selectedLesson).length);
            }

            @Override
            public void onPageSelected(int i) {
                if(!Utilities.LessonList.get(selectedLesson).IsLocked()) {
                    lessonContent.MarkPageAsRead(i);
                    Utilities.SaveReadProgress(selectedLesson, lessonContent.GetAllReadStatus(), context);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //TODO: Mark as read when page scroll reaches bottom
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_lesson, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_restart) {
            final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            final Context context = this;
            dlgAlert.setMessage(R.string.dialogLessonProgressText);
            dlgAlert.setTitle(R.string.dialogLessonProgressTitle);
            dlgAlert.setPositiveButton(R.string.btnYes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Utilities.RestartLesson(selectedLesson, context);
                    finish();

                }
            });
            dlgAlert.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            return true;
        } else if(id == R.id.action_report) {
            intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int GetBackgroundColorId() {
        int resId;
        if(selectedChapter == 0)
           resId = getResources().getColor(R.color._chapter1color);
        else if(selectedChapter == 1)
            resId = getResources().getColor(R.color._chapter2color);
        else if(selectedChapter == 2)
            resId = getResources().getColor(R.color._chapter3color);
        else if(selectedChapter == 3)
            resId = getResources().getColor(R.color._chapter4color);
        else if(selectedChapter == 4)
            resId = getResources().getColor(R.color._chapter5color);
        else if(selectedChapter == 5)
            resId = getResources().getColor(R.color._chapter6color);
        else if(selectedChapter == 6)
            resId = getResources().getColor(R.color._chapter7color);
        else if(selectedChapter == 7)
            resId = getResources().getColor(R.color._chapter8color);
        else if(selectedChapter == 8)
            resId = getResources().getColor(R.color._chapter9color);
        else
            resId = getResources().getColor(R.color._chapter1color);

        return resId;

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment arguments.
         */
        private static final String ARG_SECTION_NUMBER = "section_number"; //Current section/page number
        private static final String ARG_LESSON_NUMBER = "lesson_number"; //Current lesson number
        private static final String ARG_STR_PAGE_TITLE = "str_page_title"; //Current page title
        private static final String ARG_STR_LESSON_BODY = "str_lesson_body"; //Current page content
        private static final String ARG_MAX_PAGES = "max_pages"; //Number of pages of current lesson
        private static final String ARG_IMG_ID = "lesson_img_id"; // Current lesson's image id
        private static final String ARG_STR_LESSON_TITLE = "str_lesson_title"; //Current lesson's title

        public static int currentPageNum = 1;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, int currentLesson) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt(ARG_LESSON_NUMBER, currentLesson);

            args.putString(ARG_STR_LESSON_BODY, Utilities.LessonList.get(currentLesson).GetLessonContent().GetContentByPage(sectionNumber));
            args.putString(ARG_STR_PAGE_TITLE, Utilities.LessonList.get(currentLesson).GetLessonContent().GetTitleByPage(sectionNumber));
            args.putInt(ARG_MAX_PAGES, Utilities.LessonList.get(currentLesson).GetLessonContent().GetAllContent().length);
            args.putInt(ARG_IMG_ID, Utilities.LessonList.get(currentLesson).GetImageRID());
            args.putString(ARG_STR_LESSON_TITLE, Utilities.LessonList.get(currentLesson).GetTitle());

            fragment.setArguments(args);

            currentPageNum = sectionNumber;

            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_in_lesson, container, false);
            final int currentPage = getArguments().getInt(ARG_SECTION_NUMBER);

            String lessonBodyStringId = getArguments().getString(ARG_STR_LESSON_BODY);
            String pageTitleStringId = getArguments().getString(ARG_STR_PAGE_TITLE);

            final int maxPages = getArguments().getInt(ARG_MAX_PAGES);
            int lessonImgId = getArguments().getInt(ARG_IMG_ID);
            String lessonTitleString = getArguments().getString(ARG_STR_LESSON_TITLE);

            TextView tvLessonBody = rootView.findViewById(R.id.tv_lesson_body);
            TextView tvPageTitle = rootView.findViewById(R.id.tv_lesson_page_title);
            TextView tvLessonTitle = rootView.findViewById(R.id.tv_lesson_title);
            ImageView ivLessonImage = rootView.findViewById(R.id.iv_lesson_icon);
            Button nextBtn = rootView.findViewById(R.id.btn_continue);

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(currentPage+1 == maxPages) {
                    ((Activity)v.getContext()).finish();
                } else {
                    ViewPager thisVp = (ViewPager) rootView.getParent();
                    thisVp.setCurrentItem(currentPage + 1, true);
                }
                }
            });

            tvLessonBody.setText(Html.fromHtml(lessonBodyStringId));
            tvPageTitle.setText(pageTitleStringId);
            tvLessonTitle.setText(lessonTitleString);
            ivLessonImage.setImageDrawable(getResources().getDrawable(lessonImgId));
            if(currentPage+1 == maxPages) {
                nextBtn.setText(R.string.btn_finish);
            }

            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int mMaxPages;

        public SectionsPagerAdapter(FragmentManager fm, int maxPages) {
            super(fm);
            this.mMaxPages = maxPages;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position, selectedLesson);
        }

        @Override
        public int getCount() {
            return this.mMaxPages;
        }
    }
}
