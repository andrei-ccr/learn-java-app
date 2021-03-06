package org.softry.learnjava.org.softry.learnjava.content;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import org.json.JSONObject;
import org.softry.learnjava.R;

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

	public static boolean ShowComingSoonLessons = true;
	public static boolean ShowProOnlyChapters = true;
	public static boolean ShowAds = true;

    public static final Integer[] ComingSoonChapters = {4,5,6,7,8,9};
	public static final Integer[] ComingSoonLessons = {14, 18, 19, 20};
	
    public static List<Chapter> ChapterList; //List of chapters in order with chapter's details
    public static List<Lesson> LessonList; //List of lessons in order with lesson's details

    public static final Map<Integer, Integer[]> ChapterLessonList; //Map of a chapter to a lesson list
    static {
        @SuppressLint("UseSparseArrays") Map<Integer, Integer[]> aMap = new HashMap<>();
        aMap.put(0, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7}); //Chapter 1
        aMap.put(1, new Integer[]{8, 9, 10, 11 , 12, 13, 14});
        aMap.put(2, new Integer[]{15, 16, 17, 18 , 19, 20});
        ChapterLessonList = Collections.unmodifiableMap(aMap);
    }


    public static Map<Integer, String[]> LessonContentList; //Map of a lesson to a list of content Strings
    private static Map<Integer, String[]> LessonTitleList; //Map of a lesson to a list of page title Strings

    private static boolean DataLoaded = false;

    @SuppressLint("UseSparseArrays")
    private static void MapContentToLessons(Context context) {
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
		lessonList.recycle();
    }
    private static void LoadChapters(Context context) {
        ChapterList = new ArrayList<>();

        String[] aChapter;
        TypedArray chapterList = context.getResources().obtainTypedArray(R.array.chapterList);

        for(int i=0;i<chapterList.length();i++) {
            aChapter = context.getResources().getStringArray(chapterList.getResourceId(i, R.array.chapter_default));

            ChapterList.add( new Chapter(aChapter[0], aChapter[1], aChapter[2], aChapter[3], ChapterLessonList.get(i)));
        }
        chapterList.recycle();
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

                if(Utilities.InArray(i, Utilities.ComingSoonLessons)) {
                    LessonList.add(
                            new Lesson(true,
                                    lessonTitleList[i],
                                    lessonDescList[i],
                                    lessonImgList.getResourceId(i, R.drawable.first_program),
                                    new LessonContent(LessonContentList.get(i), LessonTitleList.get(i), readVector)
                            )
                    );
                } else {
                    LessonList.add(
                            new Lesson(
                                    lessonTitleList[i],
                                    lessonDescList[i],
                                    lessonImgList.getResourceId(i, R.drawable.first_program),
                                    new LessonContent(LessonContentList.get(i), LessonTitleList.get(i), readVector)
                            )
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("myapp", "Error lesson added in place of lesson " + Integer.toString(i));
                //Add the error lesson
                LessonList.add(
                        new Lesson(
                                " ",
                                " ",
                                R.drawable.first_program,
                                new LessonContent(new String[]{" "}, new String[] {""}, new int[1])
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
            Log.d("myapp", "Couldn't access lock status file. Unlocked first lesson only");
        }

        lessonImgList.recycle();
    }

    public static void LoadData(Context context) {
        if(!DataLoaded) {
			jsonReadStatus = new JSONObject();
			
            MapContentToLessons(context);
            LoadChapters(context);
            LoadLessons(context);

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

    /*public static void SetGrayScale(ImageView v) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0.1f);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

    public static void SetImgTint(ImageView v, int colorId) {
        v.setColorFilter(ContextCompat.getColor(v.getContext(), colorId ), PorterDuff.Mode.MULTIPLY);
    }*/

    public static void RestartLesson(int lesson, Context context) {
        LessonList.get(lesson).ResetCompletedPercent(context);
		SaveReadProgress(lesson, new int[LessonList.get(lesson).GetLessonContent().GetPageCount()], context);
    }


    static void UnlockNextLesson(Context context) {
        Lesson prevL = LessonList.get(0);
        for(Lesson l : LessonList) {
            if(l.equals(LessonList.get(0))) continue;
            if((l.IsLocked() && prevL.IsCompleted()) || (l.IsLocked() && prevL.ComingSoon())) {
                l.UnlockLesson();
                break;
            }
            prevL = l;
        }
		
		//Save new lock status
		SaveLockStatus(context);
    }

    public static int GetTotalPagesRead() {
        int total = 0;
        for(Lesson l : LessonList) {
            total += l.GetLessonContent().GetReadCount();
        }

        return total;
    }

    public static int GetOverallProgress(Context context) {
        double progress;
        double lessonProgress = 0;
        int comingSoonLessons = 0;
        for(Lesson l : LessonList) {
            if(l.ComingSoon()) {
                comingSoonLessons++;
                continue;
            }
            lessonProgress += l.GetCompletedPercent(context)/100f;
        }

        //Ignore lessons that are in Coming Soon state
        lessonProgress /= (LessonList.size() - comingSoonLessons);

        progress = lessonProgress * 100;
        return (int)progress;
    }
	
	public static void SaveReadProgress(int lesson, int[] newReadArray, Context context) {
		StringBuilder sb = new StringBuilder();
		for(Integer i : newReadArray) {
            sb.append(Integer.toString(i));
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
	
	private static void SaveLockStatus(Context context) {
		StringBuilder sb = new StringBuilder();
	
		for(Lesson l : LessonList) {
		    if(l.ComingSoon()) {
		        sb.append(Integer.toString(0));
            } else {
                sb.append(Integer.toString(l.IsLocked() ? 0 : 1));
            }
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

	public static void DeleteStorage(Context context) {
        File file = new File(context.getFilesDir(), FILENAME_PROGRESS_STATUS);
        File file2 = new File(context.getFilesDir(), FILENAME_LOCKED_STATUS);
        boolean delFileRes = file.delete();
        Log.v("myapp", "Deleting progress file result: " + delFileRes);
        delFileRes = file2.delete();
        Log.v("myapp", "Deleting lock status file result: " + delFileRes);

        context.deleteFile(FILENAME_PROGRESS_STATUS);
        context.deleteFile(FILENAME_LOCKED_STATUS);
    }

    /*
    public static int GetScreenWidth() {
        int screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        return screenWidth;
    }
    */
}
