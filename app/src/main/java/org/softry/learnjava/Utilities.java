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

    public static final Integer[] ComingSoonChapters = {2,3,4,5,6,7,8};

    public static List<Containers.Chapter> ChapterList; //List of chapters in order with chapter's details
    public static List<Containers.Lesson> LessonList; //List of lessons in order with lesson's details

    public static final Map<Integer, Integer[]> ChapterLessonList; //Map of a chapter to a lesson list
    static {
        Map<Integer, Integer[]> aMap = new HashMap<>();
        aMap.put(0, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7}); //Chapter 1
        aMap.put(1, new Integer[]{8, 9, 10, 11 , 12, 13, 14, 15, 16, 17});
        aMap.put(2, new Integer[]{18, 19, 20, 21, 22});
        aMap.put(3, new Integer[]{23, 24, 25, 26, 27});
        ChapterLessonList = Collections.unmodifiableMap(aMap);
    }


    public static Map<Integer, Integer[]> LessonContentList; //Map of a lesson to a list of content Strings
    public static Map<Integer, Integer[]> LessonTitleList; //Map of a lesson to a list of page title Strings

    private static boolean DataLoaded = false;

    private static void MapContentToLessons() {
        //TODO: These should be immutable/constant/final for security reasons
        LessonContentList = new HashMap<>();
        LessonTitleList = new HashMap<>();

        LessonContentList.put(0, new Integer[]{R.string.lesson101_1, R.string.lesson101_2, R.string.lesson101_3,
                R.string.lesson101_4, R.string.lesson101_5, R.string.lesson101_6});

        LessonTitleList.put(0, new Integer[]{R.string.lesson_101_1_title, R.string.lesson_101_2_title, R.string.lesson_101_3_title,
                R.string.lesson_101_4_title,R.string.lesson_101_5_title,R.string.lesson_101_6_title});

        LessonContentList.put(1, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
                R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});

        LessonTitleList.put(1, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
                R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});

        LessonContentList.put(2, new Integer[]{R.string.lesson103_1, R.string.lesson103_2, R.string.lesson103_3,
                R.string.lesson103_4, R.string.lesson103_5, R.string.lesson103_6, R.string.lesson103_7});

        LessonTitleList.put(2, new Integer[]{R.string.lesson_103_1_title, R.string.lesson_103_2_title, R.string.lesson_103_3_title,
                R.string.lesson_103_4_title,R.string.lesson_103_5_title,R.string.lesson_103_6_title, R.string.lesson_103_7_title});

        LessonContentList.put(3, new Integer[]{R.string.lesson104_1, R.string.lesson104_2, R.string.lesson104_3,
                R.string.lesson104_4, R.string.lesson104_5, R.string.lesson104_6, R.string.lesson104_7});

        LessonTitleList.put(3, new Integer[]{R.string.lesson_104_1_title, R.string.lesson_104_2_title, R.string.lesson_104_3_title, R.string.lesson_104_4_title,R.string.lesson_104_5_title,R.string.lesson_104_6_title, R.string.lesson_104_7_title});

        LessonContentList.put(4, new Integer[]{R.string.lesson105_1, R.string.lesson105_2, R.string.lesson105_3,
                R.string.lesson105_4, R.string.lesson105_5, R.string.lesson105_6, R.string.lesson105_7, R.string.lesson105_8, R.string.lesson105_9, R.string.lesson105_10});

        LessonTitleList.put(4, new Integer[]{R.string.lesson_105_1_title, R.string.lesson_105_2_title, R.string.lesson_105_3_title,
                R.string.lesson_105_4_title,R.string.lesson_105_5_title,R.string.lesson_105_6_title,R.string.lesson_105_7_title,R.string.lesson_105_8_title,R.string.lesson_105_9_title,R.string.lesson_105_10_title});

        LessonContentList.put(5, new Integer[]{R.string.lesson106_1, R.string.lesson106_2, R.string.lesson106_3,
                R.string.lesson106_4, R.string.lesson106_5});

        LessonTitleList.put(5, new Integer[]{R.string.lesson_106_1_title, R.string.lesson_106_2_title, R.string.lesson_106_3_title,
                R.string.lesson_106_4_title,R.string.lesson_106_5_title});

        LessonContentList.put(6, new Integer[]{R.string.lesson107_1, R.string.lesson107_2, R.string.lesson107_3,
                R.string.lesson107_4, R.string.lesson107_5, R.string.lesson107_6, R.string.lesson107_7, R.string.lesson107_8, R.string.lesson107_9, R.string.lesson107_10, R.string.lesson107_11});

        LessonTitleList.put(6, new Integer[]{R.string.lesson_107_1_title, R.string.lesson_107_2_title, R.string.lesson_107_3_title,
                R.string.lesson_107_4_title,R.string.lesson_107_5_title,R.string.lesson_107_6_title,R.string.lesson_107_7_title,R.string.lesson_107_8_title,R.string.lesson_107_9_title,R.string.lesson_107_10_title,R.string.lesson_107_11_title});

        LessonContentList.put(7, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
                R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});

        LessonTitleList.put(7, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
                R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});

        LessonContentList.put(8, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
                R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});

        LessonTitleList.put(8, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
                R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});

        LessonContentList.put(9, new Integer[]{R.string.lesson102_1, R.string.lesson102_2, R.string.lesson102_3,
                R.string.lesson102_4, R.string.lesson102_5, R.string.lesson102_6});

        LessonTitleList.put(9, new Integer[]{R.string.lesson_102_1_title, R.string.lesson_102_2_title, R.string.lesson_102_3_title,
                R.string.lesson_102_4_title,R.string.lesson_102_5_title,R.string.lesson_102_6_title});
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
                //readVector[0] = 1;
                LessonList.add(
                        new Containers.Lesson(
                                lessonTitleList[i],
                                lessonDescList[i],
                                lessonImgList.getResourceId(i, R.drawable.first_program),
                                new Containers.LessonContent(context, LessonContentList.get(i), LessonTitleList.get(i), readVector)
                        )
                );
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("myapp", "Error adding lesson " + Integer.toString(i));
            }
        }
    }

    public static void LoadData(Context context) {
        if(DataLoaded == false) {
            MapContentToLessons();
            LoadChapters(context);
            LoadLessons(context);
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
}
