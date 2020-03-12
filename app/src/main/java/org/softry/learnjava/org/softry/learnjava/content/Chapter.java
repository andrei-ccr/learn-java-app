package org.softry.learnjava.org.softry.learnjava.content;

import android.content.Context;

public class Chapter {
    private String number;
    private String name;
    private String longDesc;
    private String shortDesc;
    private int progress;
    private Integer[] lessonList;
    private int lessonCount;

    public Chapter(String number, String name, String shortDesc, String longDesc, Integer[] lessonList) {
        this.number = number;
        this.name = name;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.progress = -1; // -1 means not started
        this.lessonList = lessonList;
        if(lessonList != null)
            this.lessonCount = lessonList.length;
    }

    private void UpdateProgress(Context context) {
        double total_lprogress = 0;
        int comingSoonLessons = 0;
        try {
            for(Integer l : this.lessonList) {
                if(Utilities.LessonList.get(l).ComingSoon()) {
                    comingSoonLessons++;
                    continue;
                }
                double lprogress = Utilities.LessonList.get(l).GetCompletedPercent(context) / 100f;
                total_lprogress += lprogress;
            }
        } catch (Exception e) {
            this.progress = -1;
            return;
        }
        if(total_lprogress == 0) this.progress = -1;
        else
            this.progress = (int)((total_lprogress/(this.lessonCount-comingSoonLessons))*100);
    }

        /*public void SetProgress(int value) {
            if(value<0) this.progress = -1;
            else if(value>100) this.progress = 100;
            else this.progress = value;
        }*/

    //public int GetLessonCount() {return this.lessonCount;}
    public String GetNumber(){
        return this.number;
    }

    public String GetName() {
        return this.name;
    }

    public String GetShortDesc() {
        return this.shortDesc;
    }
    public String GetLongDesc() {
        return this.longDesc;
    }

    public String GetProgressStr(Context context) {
        if(GetProgressValue(context) == -1) {
            return "";
        } else if(GetProgressValue(context) >= 100) {
            return "Completed";
        } else {
            return Integer.toString(GetProgressValue(context)) + "%";
        }
    }

    int GetProgressValue(Context context) {
        UpdateProgress(context);
        return this.progress;
    }
}