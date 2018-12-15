package org.softry.learnjava;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_lesson);


        lessonContent = Utilities.LessonList.get(selectedLesson).GetLessonContent();

        Utilities.RecentLesson =  selectedLesson;

        //Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(selectedChapter == 0)
            toolbar.setBackgroundColor(getResources().getColor(R.color._chapter1color));

        else if(selectedChapter == 1)
            toolbar.setBackgroundColor(getResources().getColor(R.color._chapter2color));
        else if(selectedChapter == 2)
            toolbar.setBackgroundColor(getResources().getColor(R.color._chapter3color));
        else if(selectedChapter == 3)
            toolbar.setBackgroundColor(getResources().getColor(R.color._chapter4color));
        else if(selectedChapter == 4)
            toolbar.setBackgroundColor(getResources().getColor(R.color._chapter5color));
        else if(selectedChapter == 5)
            toolbar.setBackgroundColor(getResources().getColor(R.color._chapter6color));
        else if(selectedChapter == 6)
            toolbar.setBackgroundColor(getResources().getColor(R.color._chapter7color));
        else if(selectedChapter == 7)
            toolbar.setBackgroundColor(getResources().getColor(R.color._chapter8color));
        else if(selectedChapter == 8)
            toolbar.setBackgroundColor(getResources().getColor(R.color._chapter9color));


        //Mark first page as read
        lessonContent.MarkPageAsRead(0);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), Utilities.LessonContentList.get(selectedLesson).length);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TextView tvCurrentPage = findViewById(R.id.tvCurrentPage);
        tvCurrentPage.setText(mViewPager.getCurrentItem()+1 + "/" + Utilities.LessonContentList.get(selectedLesson).length);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                tvCurrentPage.setText(mViewPager.getCurrentItem()+1 + "/" + Utilities.LessonContentList.get(selectedLesson).length);
            }

            @Override
            public void onPageSelected(int i) {
                lessonContent.MarkPageAsRead(i);

                for(int r : lessonContent.GetAllReadStatus()) {
                    Log.e("myapp", Integer.toString(r));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_in_lesson, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        } /*else if(id == R.id.action_bookmark) {
            Utilities.AddBookmark(selectedLesson, mViewPager.getCurrentItem());
            Toast.makeText(this, "Page bookmarked.", Toast.LENGTH_LONG);
            return true;
        }*/ else if(id == R.id.action_restart) {
            Utilities.RestartLesson(1);
            finish();
            startActivity(this.getIntent());
            return true;
        } /*else if(id == R.id.action_dashboard) {

            return true;
        }*/ else if(id == R.id.action_report) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment arguments.
         */
        private static final String ARG_SECTION_NUMBER = "section_number"; //Current section/page number
        private static final String ARG_LESSON_NUMBER = "lesson_number";
        private static final String ARG_STR_PAGE_TITLE = "str_page_title";
        private static final String ARG_STR_LESSON_BODY = "str_lesson_body";
        private static final String ARG_MAX_PAGES = "max_pages";
        private static final String ARG_IMG_ID = "lesson_img_id";
        private static final String ARG_STR_LESSON_TITLE = "str_lesson_title";

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

            args.putString(ARG_STR_LESSON_BODY, Utilities.LessonContentList.get(currentLesson)[sectionNumber]);
            args.putString(ARG_STR_PAGE_TITLE, Utilities.LessonTitleList.get(currentLesson)[sectionNumber]);
            args.putInt(ARG_MAX_PAGES, Utilities.LessonContentList.get(currentLesson).length);
            args.putInt(ARG_IMG_ID, Utilities.LessonList.get(currentLesson).GetImageRID());
            args.putString(ARG_STR_LESSON_TITLE, Utilities.LessonList.get(currentLesson).GetTitle());

            fragment.setArguments(args);

            currentPageNum = sectionNumber;


            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_in_lesson, container, false);

            final int currentPage = getArguments().getInt(ARG_SECTION_NUMBER);
            int currentLesson = getArguments().getInt(ARG_LESSON_NUMBER);

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
