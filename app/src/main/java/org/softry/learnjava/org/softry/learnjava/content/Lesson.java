package org.softry.learnjava.org.softry.learnjava.content;

import android.content.Context;

public class Lesson {
    private String title, desc;
    private int imgResId;
    private LessonContent lessonContent;
    private int completedPercent; //From 0 to 100
    private boolean locked;
    private boolean comingSoon = false;

    Lesson(String title, String desc, int imgResId, LessonContent lessonContent) {
        this.title = title;
        this.desc = desc;
        this.imgResId = imgResId;
        this.lessonContent = lessonContent;
        this.locked = true;

        this.SetCompletedPercent((int)Math.floor(((float)this.lessonContent.GetReadCount()/(float)this.lessonContent.GetPageCount())*100f));
    }

    Lesson(boolean comingSoon, String title, String desc, int imgResId, LessonContent lessonContent) {
        this(title,desc,imgResId,lessonContent);
        this.comingSoon = comingSoon;
        this.locked = false;
        this.completedPercent = 100;
    }

    public boolean ComingSoon() {
        return this.comingSoon;
    }

    private void SetCompletedPercent(int val) {
        this.completedPercent = Math.max(0, Math.min(100, val));
    }

    public void UnlockLesson() {
        this.locked = false;
    }
    public void LockLesson() {
        this.locked = true;
    }

    public boolean IsLocked() {
        return this.locked;
    }

    public String GetTitle() {
        return this.title;
    }

    public String GetDesc() {
        return this.desc;
    }

    public int GetImageRID() {
        return this.imgResId;
    }

    public int GetCompletedPercent(Context context) {
        this.SetCompletedPercent((int)Math.floor(((float)this.lessonContent.GetReadCount()/(float)this.lessonContent.GetPageCount())*100f));
        if(this.completedPercent == 100) {
            Utilities.UnlockNextLesson(context);
        }
        return this.completedPercent;
    }

    public boolean IsCompleted() {
        this.SetCompletedPercent((int)Math.floor(((float)this.lessonContent.GetReadCount()/(float)this.lessonContent.GetPageCount())*100f));
        return this.completedPercent == 100;
    }

    public LessonContent GetLessonContent() {
        return this.lessonContent;
    }

    public void ResetCompletedPercent(Context context) {
        LessonContent currentLC = this.lessonContent;
        this.lessonContent = new LessonContent(currentLC.content, currentLC.pageTitle, new int[currentLC.content.length]);
        GetCompletedPercent(context);
    }

}