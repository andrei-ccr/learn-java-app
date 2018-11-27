package org.softry.learnjava;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InLessonActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private int selectedLesson;
    private int maxPages;

    private List<LessonMap> lessonsList;
    private Map<Integer, Integer> lessonsIndex;
    private static final int NUMBER_OF_LESSONS = 32;



    private void InitialiseLessonList() {
        lessonsIndex = new HashMap<>();
        int[] lessonsOrder = {101, 102, 103, 104, 105, 106, 107, 108,
                201,202,203,204,205,206,207,208,
                301,302,303,304,305,306,307,308,
                401,402,403,404,405,406,407,408};

        for(int i=0;i<lessonsOrder.length;i++) {
            Log.i("myapp", Integer.toString(lessonsOrder[i]) + ", " + Integer.toString(i));
            lessonsIndex.put(lessonsOrder[i], i);
        }

        lessonsList = new ArrayList<>();
        List<Integer> lessonBodyIdList, pageTitleIdList;

        /* ### Lesson 101 ### */
        lessonBodyIdList = new ArrayList<>();
        pageTitleIdList = new ArrayList<>();
        lessonBodyIdList.add(R.string.lesson101_1); pageTitleIdList.add(R.string.lesson_101_1_title);
        lessonBodyIdList.add(R.string.lesson101_2); pageTitleIdList.add(R.string.lesson_101_2_title);
        lessonBodyIdList.add(R.string.lesson101_3); pageTitleIdList.add(R.string.lesson_101_3_title);
        lessonBodyIdList.add(R.string.lesson101_4); pageTitleIdList.add(R.string.lesson_101_4_title);
        lessonBodyIdList.add(R.string.lesson101_5); pageTitleIdList.add(R.string.lesson_101_5_title);
        lessonBodyIdList.add(R.string.lesson101_6); pageTitleIdList.add(R.string.lesson_101_6_title);

        lessonsList.add(new LessonMap(101, new LessonLayout(lessonBodyIdList, pageTitleIdList), 6));

        /* ### Lesson 102 ### */
        lessonBodyIdList = new ArrayList<>();
        pageTitleIdList = new ArrayList<>();
        lessonBodyIdList.add(R.string.lesson102_1); pageTitleIdList.add(R.string.lesson_102_1_title);
        lessonBodyIdList.add(R.string.lesson102_2); pageTitleIdList.add(R.string.lesson_102_2_title);
        lessonBodyIdList.add(R.string.lesson102_3); pageTitleIdList.add(R.string.lesson_102_3_title);
        lessonBodyIdList.add(R.string.lesson102_4); pageTitleIdList.add(R.string.lesson_102_4_title);
        lessonBodyIdList.add(R.string.lesson102_5); pageTitleIdList.add(R.string.lesson_102_5_title);
        lessonBodyIdList.add(R.string.lesson102_6); pageTitleIdList.add(R.string.lesson_102_6_title);

        lessonsList.add(new LessonMap(102, new LessonLayout(lessonBodyIdList, pageTitleIdList), 6));

        /* ### Lesson 103 ### */
        lessonBodyIdList = new ArrayList<>();
        pageTitleIdList = new ArrayList<>();
        lessonBodyIdList.add(R.string.lesson103_1); pageTitleIdList.add(R.string.lesson_103_1_title);
        lessonBodyIdList.add(R.string.lesson103_2); pageTitleIdList.add(R.string.lesson_103_2_title);
        lessonBodyIdList.add(R.string.lesson103_3); pageTitleIdList.add(R.string.lesson_103_3_title);
        lessonBodyIdList.add(R.string.lesson103_4); pageTitleIdList.add(R.string.lesson_103_4_title);
        lessonBodyIdList.add(R.string.lesson103_5); pageTitleIdList.add(R.string.lesson_103_5_title);
        lessonBodyIdList.add(R.string.lesson103_6); pageTitleIdList.add(R.string.lesson_103_6_title);
        lessonBodyIdList.add(R.string.lesson103_7); pageTitleIdList.add(R.string.lesson_103_7_title);

        lessonsList.add(new LessonMap(103, new LessonLayout(lessonBodyIdList, pageTitleIdList), 7));

        /* ### Lesson 104 ### */
        lessonBodyIdList = new ArrayList<>();
        pageTitleIdList = new ArrayList<>();
        lessonBodyIdList.add(R.string.lesson104_1); pageTitleIdList.add(R.string.lesson_104_1_title);
        lessonBodyIdList.add(R.string.lesson104_2); pageTitleIdList.add(R.string.lesson_104_2_title);
        lessonBodyIdList.add(R.string.lesson104_3); pageTitleIdList.add(R.string.lesson_104_3_title);
        lessonBodyIdList.add(R.string.lesson104_4); pageTitleIdList.add(R.string.lesson_104_4_title);
        lessonBodyIdList.add(R.string.lesson104_5); pageTitleIdList.add(R.string.lesson_104_5_title);
        lessonBodyIdList.add(R.string.lesson104_6); pageTitleIdList.add(R.string.lesson_104_6_title);
        lessonBodyIdList.add(R.string.lesson104_7); pageTitleIdList.add(R.string.lesson_104_7_title);

        lessonsList.add(new LessonMap(104, new LessonLayout(lessonBodyIdList, pageTitleIdList), 7));

        /* ### Lesson 105 ### */
        lessonBodyIdList = new ArrayList<>();
        pageTitleIdList = new ArrayList<>();
        lessonBodyIdList.add(R.string.lesson105_1); pageTitleIdList.add(R.string.lesson_105_1_title);
        lessonBodyIdList.add(R.string.lesson105_2); pageTitleIdList.add(R.string.lesson_105_2_title);
        lessonBodyIdList.add(R.string.lesson105_3); pageTitleIdList.add(R.string.lesson_105_3_title);
        lessonBodyIdList.add(R.string.lesson105_4); pageTitleIdList.add(R.string.lesson_105_4_title);
        lessonBodyIdList.add(R.string.lesson105_5); pageTitleIdList.add(R.string.lesson_105_5_title);
        lessonBodyIdList.add(R.string.lesson105_6); pageTitleIdList.add(R.string.lesson_105_6_title);
        lessonBodyIdList.add(R.string.lesson105_7); pageTitleIdList.add(R.string.lesson_105_7_title);
        lessonBodyIdList.add(R.string.lesson105_8); pageTitleIdList.add(R.string.lesson_105_8_title);
        lessonBodyIdList.add(R.string.lesson105_9); pageTitleIdList.add(R.string.lesson_105_9_title);
        lessonBodyIdList.add(R.string.lesson105_10); pageTitleIdList.add(R.string.lesson_105_10_title);

        lessonsList.add(new LessonMap(105, new LessonLayout(lessonBodyIdList, pageTitleIdList), 10));

        /* ### Lesson 106 ### */
        lessonBodyIdList = new ArrayList<>();
        pageTitleIdList = new ArrayList<>();
        lessonBodyIdList.add(R.string.lesson106_1); pageTitleIdList.add(R.string.lesson_106_1_title);
        lessonBodyIdList.add(R.string.lesson106_2); pageTitleIdList.add(R.string.lesson_106_2_title);
        lessonBodyIdList.add(R.string.lesson106_3); pageTitleIdList.add(R.string.lesson_106_3_title);
        lessonBodyIdList.add(R.string.lesson106_4); pageTitleIdList.add(R.string.lesson_106_4_title);
        lessonBodyIdList.add(R.string.lesson106_5); pageTitleIdList.add(R.string.lesson_106_5_title);

        lessonsList.add(new LessonMap(106, new LessonLayout(lessonBodyIdList, pageTitleIdList), 5));

        /* ### Lesson 107 ### */
        lessonBodyIdList = new ArrayList<>();
        pageTitleIdList = new ArrayList<>();
        lessonBodyIdList.add(R.string.lesson107_1); pageTitleIdList.add(R.string.lesson_107_1_title);
        lessonBodyIdList.add(R.string.lesson107_2); pageTitleIdList.add(R.string.lesson_107_2_title);
        lessonBodyIdList.add(R.string.lesson107_3); pageTitleIdList.add(R.string.lesson_107_3_title);
        lessonBodyIdList.add(R.string.lesson107_4); pageTitleIdList.add(R.string.lesson_107_4_title);
        lessonBodyIdList.add(R.string.lesson107_5); pageTitleIdList.add(R.string.lesson_107_5_title);
        lessonBodyIdList.add(R.string.lesson107_6); pageTitleIdList.add(R.string.lesson_107_6_title);
        lessonBodyIdList.add(R.string.lesson107_7); pageTitleIdList.add(R.string.lesson_107_7_title);
        lessonBodyIdList.add(R.string.lesson107_8); pageTitleIdList.add(R.string.lesson_107_8_title);
        lessonBodyIdList.add(R.string.lesson107_9); pageTitleIdList.add(R.string.lesson_107_9_title);
        lessonBodyIdList.add(R.string.lesson107_10); pageTitleIdList.add(R.string.lesson_107_10_title);
        lessonBodyIdList.add(R.string.lesson107_11); pageTitleIdList.add(R.string.lesson_107_11_title);

        lessonsList.add(new LessonMap(107, new LessonLayout(lessonBodyIdList, pageTitleIdList), 11));
        //TODO: Add other lessons

    }
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_lesson);

        //Get current lesson
        Intent parentActivity = getIntent();
        selectedLesson = Integer.parseInt(parentActivity.getStringExtra(LessonsActivity.SELECTED_LESSON));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            InitialiseLessonList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.maxPages = 1; //Default to one page per lesson
        LessonMap lm = lessonsList.get(lessonsIndex.get(selectedLesson));
        this.maxPages = lm.GetMaxPages();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), lm.GetLayout(), this.maxPages);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


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
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_LESSON_NUMBER = "lesson_number";
        private static final String ARG_STR_PAGE_TITLE = "str_page_title";
        private static final String ARG_STR_LESSON_BODY = "str_lesson_body";
        private static final String ARG_MAX_PAGES = "max_pages";

        public static int currentPageNum = 1;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, int currentLesson, LessonLayout lessonLayout, int maxPages) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            //TODO:Make Section number start at 0 instead of 1
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt(ARG_LESSON_NUMBER, currentLesson);
            args.putInt(ARG_STR_LESSON_BODY, lessonLayout.GetLessonBodyId(sectionNumber-1));
            args.putInt(ARG_STR_PAGE_TITLE, lessonLayout.GetPageTitleId(sectionNumber-1));
            args.putInt(ARG_MAX_PAGES, maxPages);

            fragment.setArguments(args);

            currentPageNum = sectionNumber;
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_in_lesson, container, false);

            final int currentPage = getArguments().getInt(ARG_SECTION_NUMBER);
            int currentLesson = getArguments().getInt(ARG_LESSON_NUMBER);
            int lessonBodyStringId = getArguments().getInt(ARG_STR_LESSON_BODY);
            int pageTitleStringId = getArguments().getInt(ARG_STR_PAGE_TITLE);
            int maxPages = getArguments().getInt(ARG_MAX_PAGES);

            TextView tvLessonBody = rootView.findViewById(R.id.tv_lesson_body);
            TextView tvPageTitle = rootView.findViewById(R.id.tv_lesson_page_title);
            Button nextBtn = rootView.findViewById(R.id.btn_continue);
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewPager thisVp = (ViewPager)rootView.getParent();
                    thisVp.setCurrentItem(currentPage, true); //TODO: Do currentPage+1 after the section number from 0 to do is solved.
                }
            });

            Log.v("myapp", "Returned lesson body string id: " + Integer.toString(lessonBodyStringId));
            if(lessonBodyStringId != -1) {
                tvLessonBody.setText(Html.fromHtml(getString(lessonBodyStringId)));
                tvPageTitle.setText(pageTitleStringId);
                if(currentPage == maxPages) {
                    nextBtn.setText(R.string.btn_next_lesson);
                }
                //TODO: Get last page
            }

            return rootView;
        }
    }

    public class LessonLayout {

        //Order in the list corresponds with the page
        private List<Integer> mPageTitle, mLessonBody;

        public LessonLayout(List<Integer> mLessonBody, List<Integer> mPageTitle) {
            this.mLessonBody = mLessonBody;
            this.mPageTitle = mPageTitle;
        }

        public int GetLessonBodyId(int page) {
            if(page>= this.mLessonBody.size()) {
                return -1;
            }
            return this.mLessonBody.get(page);
        }

        /**
         * Get the title of the page.
         * @param page Current page number, starting from 0
         * @return Returns an int containing the id of the string corresponding to the page.
         */
        public int GetPageTitleId(int page) {
            if(page >= this.mPageTitle.size()) {
                return -1;
            }
            return this.mPageTitle.get(page);
        }
    }

    public class LessonMap {
        private int mLessonNumber, mMaxPages;
        private LessonLayout lessonLayout;

        public LessonMap(int mLessonNumber, LessonLayout lessonLayout, int mMaxPages) {
            this.mLessonNumber = mLessonNumber;
            this.lessonLayout = lessonLayout;
            this.mMaxPages = mMaxPages;
        }

        public int GetMaxPages() {
            return this.mMaxPages;
        }

        public LessonLayout GetLayout() {
            return this.lessonLayout;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int mMaxPages;
        private LessonLayout lessonLayout;

        public SectionsPagerAdapter(FragmentManager fm, LessonLayout lessonLayout, int maxPages) {
            super(fm);
            this.mMaxPages = maxPages;
            this.lessonLayout = lessonLayout;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, selectedLesson, this.lessonLayout, this.mMaxPages);
        }

        @Override
        public int getCount() {
            return this.mMaxPages;
        }
    }
}
