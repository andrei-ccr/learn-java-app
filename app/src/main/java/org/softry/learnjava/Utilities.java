package org.softry.learnjava;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Utilities {

    public static final String SELECTED_CHAPTER = "org.softry.learnjava.TAG.SELECTED_CHAPTER";
    public static final String SELECTED_LESSON = "org.softry.learnjava.TAG.SELECTED_LESSON";
	
	private static final String FILENAME_LOCKED_STATUS = "ls_lj.data";
	private static final String FILENAME_PROGRESS_STATUS = "rp_lj.data";
	
	private static JSONObject jsonReadStatus;

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
	private static Context context;

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
        JSONObject auxJsonReadStatus = null;

        try {
            File file = new File(context.getFilesDir(), FILENAME_PROGRESS_STATUS);
            StringBuilder bytesRead = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null) {
                bytesRead.append(line);
            }
            reader.close();


            auxJsonReadStatus = new JSONObject(bytesRead.toString());
            jsonReadStatus = auxJsonReadStatus;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("myapp", "Couldn't access read status");
        }

        for(int i=0; i<lessonTitleList.length;i++) {
            try {
                readVector = new int[LessonContentList.get(i).length];

				if(auxJsonReadStatus == null) {
				    //Couldn't access read status file; create jsonReadStatus object with default values
                    char[] fill = new char[readVector.length];
                    Arrays.fill(fill, '0');
                    jsonReadStatus.put(Integer.toString(i), new String(fill));
                } else {

                    String rv = jsonReadStatus.getString(Integer.toString(i));

                    for (int j = 0; j < rv.length(); j++) {
                        readVector[j] = Character.getNumericValue(rv.charAt(j));
                    }
                }
				
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
                Log.w("myapp", "Error lesson added in place of lesson " + Integer.toString(i));
                //Add the error lesson
                LessonList.add(
                        new Containers.Lesson(
                                " ",
                                " ",
                                R.drawable.first_program,
                                new Containers.LessonContent(new String[]{" "}, new String[] {""}, new int[1])
                        )
                );
            }
        }

        try {
            File file = new File(context.getFilesDir(), FILENAME_LOCKED_STATUS);
            StringBuilder bytesRead = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                bytesRead.append(line);
            }
            reader.close();

            for(int i=0;i<bytesRead.length();i++) {
                if(Character.getNumericValue(bytesRead.charAt(i)) == 1) {
                    LessonList.get(i).UnlockLesson();
                }
            }
        } catch (Exception e) {
            LessonList.get(0).UnlockLesson();
            Log.w("myapp", "Couldn't access lock status file. Unlocked first lesson only");
        }

    }

    public static void LoadData(Context context) {
        if(!DataLoaded) {
			jsonReadStatus = new JSONObject();
			
            MapContentToLessons(context);
            LoadChapters(context);
            LoadLessons(context);
            LoadBookmarks();
			
            RecentLesson = -1;
			Utilities.context = context;
            DataLoaded = true;
        }
    }

    public static void ReloadData(Context context) {
        DataLoaded = false;
        LoadData(context);

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

    public static void RestartLesson(int lesson) {
        LessonList.get(lesson).ResetCompletedProcent();
		SaveReadProgress(lesson, new int[LessonList.get(lesson).GetLessonContent().GetPageCount()]);
    }


    public static void UnlockNextLesson() {
        Log.i("myapp", "UnlockNext called");
        Containers.Lesson prevL = LessonList.get(0);
        for(Containers.Lesson l : LessonList) {
            if(l.equals(LessonList.get(0))) continue;
            if(l.IsLocked() && prevL.IsCompleted()) {
                l.UnlockLesson();
                Log.i("myapp", "Lesson Unlocked");
                break;
            }
            prevL = l;
        }
		
		//Save new lock status
		SaveLockStatus();
    }

    public static int GetTotalPagesRead() {
        int total = 0;
        for(Containers.Lesson l : LessonList) {
            total += l.GetLessonContent().GetReadCount();
        }

        return total;
    }

    public static int GetOverallProgress() {
        double progress = 0;
        double lessonProgress = 0;
        for(Containers.Lesson l : LessonList) {
            lessonProgress += l.GetCompletedProcent()/100f;
        }

        lessonProgress /= LessonList.size();

        progress = lessonProgress * 100;
        return (int)progress;
    }
	
	public static void SaveReadProgress(int lesson, int[] newReadArray) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<newReadArray.length;i++) {
			sb.append(Integer.toString(newReadArray[i]));
		}

		try {
            jsonReadStatus.put(Integer.toString(lesson), sb.toString());

            File file = new File(context.getFilesDir(), FILENAME_PROGRESS_STATUS);
            BufferedWriter writer = new BufferedWriter(new FileWriter((file)));
            writer.write(jsonReadStatus.toString());
            writer.close();
        } catch (Exception e) {
            Log.e("myapp", "Read status failed to save to file");
        }

	}
	
	public static void SaveLockStatus() {
		StringBuilder sb = new StringBuilder();
	
		for(Containers.Lesson l : LessonList) {
			sb.append(Integer.toString( l.IsLocked()?0:1 ));
		}

		try {
            File file = new File(context.getFilesDir(), FILENAME_LOCKED_STATUS);
            BufferedWriter writer = new BufferedWriter(new FileWriter((file)));
            writer.write(sb.toString());
            writer.close();

        } catch (Exception e) {
            Log.e("myapp", "Lock status failed to save to file");
        }
	}

	public static void DeleteStorage() {
        File file = new File(context.getFilesDir(), FILENAME_PROGRESS_STATUS);
        File file2 = new File(context.getFilesDir(), FILENAME_LOCKED_STATUS);
        boolean delFileRes = file.delete();
        Log.v("myapp", "Deleting progress file result: " + delFileRes);
        delFileRes = file2.delete();
        Log.v("myapp", "Deleting lock status file result: " + delFileRes);

        context.deleteFile(FILENAME_PROGRESS_STATUS);
        context.deleteFile(FILENAME_LOCKED_STATUS);
    }
}
