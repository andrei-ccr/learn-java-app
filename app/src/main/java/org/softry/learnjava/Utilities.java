package org.softry.learnjava;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Utilities {

    public static final String SELECTED_CHAPTER = "org.softry.learnjava.TAG.SELECTED_CHAPTER";
    public static final String SELECTED_LESSON = "org.softry.learnjava.TAG.SELECTED_LESSON";

    public static final Integer[] ComingSoonChapters = {4,5,6,7,8,9};

    public static List<String> BookmarkList;
    public static int RecentLesson;

    public static List<Containers.Chapter> ChapterList; //List of chapters in order with chapter's details
    public static List<Containers.Lesson> LessonList; //List of lessons in order with lesson's details

    public static final Map<Integer, Integer[]> ChapterLessonList; //Map of a chapter to a lesson list
    static {
        Map<Integer, Integer[]> aMap = new HashMap<>();
        aMap.put(0, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7}); //Chapter 1
        aMap.put(1, new Integer[]{8, 9, 10, 11 , 12, 13, 14});
        aMap.put(2, new Integer[]{15, 16, 17, 18 , 19, 20});
        ChapterLessonList = Collections.unmodifiableMap(aMap);
    }


    public static Map<Integer, String[]> LessonContentList; //Map of a lesson to a list of content Strings
    public static Map<Integer, String[]> LessonTitleList; //Map of a lesson to a list of page title Strings

    private static boolean DataLoaded = false;

    private static void MapContentToLessons(Context context) {
        //TODO: These should be immutable/constant/final for security reasons
        LessonContentList = new HashMap<>();
        LessonTitleList = new HashMap<>();

        String[] aLesson;
		TypedArray lessonList = context.getResources().obtainTypedArray(R.array.lessonList);
		
		for(int i=0;i<lessonList.length();i++) {
			aLesson = context.getResources().getStringArray(lessonList.getResourceId(i, R.array.lessonDefault));
			String[] resIds = new String[aLesson.length/2];
			
			//Populate the Content List
			for(int j=0, k=1;j<resIds.length;j++, k+=2) {
				resIds[j] = aLesson[k];
			}
			LessonContentList.put(i, resIds);
			
			resIds = new String[aLesson.length/2];
			
			//Populate the Title List
			for(int j=0, k=0;j<resIds.length;j++, k+=2) {
				resIds[j] = aLesson[k];
			}
			LessonTitleList.put(i, resIds);
			
		}
    }
    private static void LoadChapters(Context context) {
        ChapterList = new ArrayList<>();

        String[] aChapter;
        TypedArray chapterList = context.getResources().obtainTypedArray(R.array.chapterList);

        for(int i=0;i<chapterList.length();i++) {
            aChapter = context.getResources().getStringArray(chapterList.getResourceId(i, R.array.chapter_default));

            ChapterList.add( new Containers.Chapter(aChapter[0], aChapter[1], aChapter[2], aChapter[3], ChapterLessonList.get(i)));
        }
    }
    private static void LoadLessons(Context context) {
        LessonList = new ArrayList<>();
        String[] lessonTitleList = context.getResources().getStringArray(R.array.lessonTitleList);
        String[] lessonDescList = context.getResources().getStringArray(R.array.lessonDescriptionList);
        TypedArray lessonImgList = context.getResources().obtainTypedArray(R.array.lessonImgList);
        int[] readVector;
        for(int i=0; i<lessonTitleList.length;i++) {
            try {
                readVector = new int[LessonContentList.get(i).length];

                LessonList.add(
                        new Containers.Lesson(
                                lessonTitleList[i],
                                lessonDescList[i],
                                lessonImgList.getResourceId(i, R.drawable.first_program),
                                new Containers.LessonContent(LessonContentList.get(i), LessonTitleList.get(i), readVector)
                        )
                );
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("myapp", "Error adding lesson " + Integer.toString(i));
                //Add the error lesson
                readVector = new int[1];
                LessonList.add(
                        new Containers.Lesson(
                                "!!Error!!",
                                "!!Error!!",
                                lessonImgList.getResourceId(i, R.drawable.first_program),
                                new Containers.LessonContent(new String[]{"!!Error!!"}, new String[] {""}, readVector)
                        )
                );
            }
        }

        LessonList.get(0).UnlockLesson();
    }

    public static void LoadData(Context context) {
        if(DataLoaded == false) {
            MapContentToLessons(context);
            LoadChapters(context);
            LoadLessons(context);
            LoadBookmarks();

            RecentLesson = -1;
            DataLoaded = true;
        }
    }

    public static <E>boolean InArray(E element, E[] array) {
        for(E arrayElem : array) {
            if(arrayElem.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static void setGrayScale(ImageView v) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0.1f);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

    public static void setImgTint(ImageView v, int colorId) {
        v.setColorFilter(ContextCompat.getColor(v.getContext(), colorId ), PorterDuff.Mode.MULTIPLY);
    }

    private static void LoadBookmarks() {
        BookmarkList = new ArrayList<>();
    }

    public static void AddBookmark(int lesson, int page) { //TODO
        String bookmarkStr = lesson + "_" + page;
        if(BookmarkList.contains(bookmarkStr)) {
            return;
        }
        BookmarkList.add(bookmarkStr);
    }

    public static boolean BookmarkExists(int lesson, int page) {
        String bookmarkStr = lesson + "_" + page;
        return BookmarkList.contains(bookmarkStr);
    }

    public static void RestartLesson(int lesson) { //TODO
        LessonList.get(lesson).ResetCompletedProcent();
    }


    public static void UnlockNextLesson() {
        Containers.Lesson prevL = LessonList.get(0);
        for(Containers.Lesson l : LessonList) {
            if(l.IsLocked() && prevL.IsCompleted()) {
                l.UnlockLesson();
                break;
            }
            prevL = l;
        }
    }
}
