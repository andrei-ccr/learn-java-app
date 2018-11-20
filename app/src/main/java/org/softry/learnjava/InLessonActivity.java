package org.softry.learnjava;

import android.content.Intent;
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

    private void InitialiseLessonList() throws Exception {
        lessonsIndex = new HashMap<Integer, Integer>();

        for(int i=0, l=101;i<NUMBER_OF_LESSONS;i++,l++) {
            //Each chapter has 8 lessons. Change the prefix for each chapter.
            if((i%8==0) && (i != 0)) {
                l += 100;
                l -= 8;
            }
            lessonsIndex.put(l,i);
            Log.i("myapp", Integer.toString(l) + ", " + Integer.toString(i));
        }

        lessonsList = new ArrayList<>();
        List<Integer> lessonBodyIdList, pageTitleIdList;

        /*** Lesson 101 ***/
        lessonBodyIdList = new ArrayList<>();
        pageTitleIdList = new ArrayList<>();
        lessonBodyIdList.add(R.string.lesson101_1); pageTitleIdList.add(R.string.lesson_101_1_title);
        lessonBodyIdList.add(R.string.lesson101_2); pageTitleIdList.add(R.string.lesson_101_2_title);

        lessonsList.add(new LessonMap(101, new LessonLayout(lessonBodyIdList, pageTitleIdList), 5));

        /*** Lesson 102 ***/
        //TODO: Add other pages for 101 and the other lessons

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        mViewPager = (ViewPager) findViewById(R.id.container);
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

        public static int currentPageNum = 1;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, int currentLesson, LessonLayout lessonLayout) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt(ARG_LESSON_NUMBER, currentLesson);
            args.putInt(ARG_STR_LESSON_BODY, lessonLayout.GetLessonBodyId(sectionNumber-1));
            args.putInt(ARG_STR_PAGE_TITLE, lessonLayout.GetPageTitleId(sectionNumber-1));

            fragment.setArguments(args);

            currentPageNum = sectionNumber;
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_in_lesson, container, false);

            int currentPage = getArguments().getInt(ARG_SECTION_NUMBER);
            int currentLesson = getArguments().getInt(ARG_LESSON_NUMBER);
            int lessonBodyStringId = getArguments().getInt(ARG_STR_LESSON_BODY);
            int pageTitleStringId = getArguments().getInt(ARG_STR_PAGE_TITLE);

            TextView tvLessonBody = rootView.findViewById(R.id.tv_lesson_body);
            TextView tvPageTitle = rootView.findViewById(R.id.tv_lesson_page_title);
            Log.e("myapp", "Returned lesson body string id: " + Integer.toString(lessonBodyStringId));
            if(lessonBodyStringId != -1) {
                tvLessonBody.setText(Html.fromHtml(getString(lessonBodyStringId)));
                tvPageTitle.setText(pageTitleStringId);
            }

            return rootView;
        }
    }


    public class LessonLayout {

        //Order in the list corresponds with the page
        ///private List<Integer> mLessonLayout, mLessonLayoutContainer;
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
         * This returns the title of the page
         * @param page
         * @return
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
            return PlaceholderFragment.newInstance(position + 1, selectedLesson, this.lessonLayout);
        }

        @Override
        public int getCount() {
            return this.mMaxPages;
        }
    }
}
