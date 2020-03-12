package org.softry.learnjava.org.softry.learnjava.content;

import android.content.Context;
import android.util.Log;

public class LessonContent {
	public String[] content, pageTitle;
	private int[] pageRead;

	public LessonContent(String[] content, String[] pageTitle, int[] pageRead) {
		this.content = content;
		this.pageTitle = pageTitle;
		this.pageRead = pageRead;
	}

	public LessonContent(Context context, Integer[] contentRIDs, Integer[] pageTitleRIDs, int[] pageRead){
	    String[] content = new String[contentRIDs.length];
	    String[] pageTitle = new String[pageTitleRIDs.length];
	    for(int i=0;i<contentRIDs.length;i++) {
            content[i] = context.getResources().getString(contentRIDs[i]);
            pageTitle[i] = context.getResources().getString(pageTitleRIDs[i]);
        }

        this.content = content;
        this.pageTitle = pageTitle;
        this.pageRead = pageRead;

    }

	public String GetContentByPage(int page) {
		try {
			return this.content[page];
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("myapp", "Exception when trying to print content from page " + Integer.toString(page));
			return "";
		}
	}

	public String GetTitleByPage(int page) {
		try {
			return this.pageTitle[page];
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("myapp", "Exception when trying to print page title from page " + Integer.toString(page));
			return "";
		}
	}

	public int GetReadStatusByPage(int page) {
		try {
			return this.pageRead[page];
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("myapp", "Exception when trying to print page read status from page " + Integer.toString(page));
			return -1;
		}
	}

	public String[] GetAllContent() {
		return this.content;
	}

	public int[] GetAllReadStatus() {
		return this.pageRead;
	}

	public int GetPageCount() {
		return this.pageRead.length;
	}

	public int GetReadCount() {
		int k = 0;
		for(Integer i : this.pageRead) {
			if(i == 1) k++;
		}
		return k;
	}

	public void MarkPageAsRead(int page) {
		this.pageRead[page] = 1;
	}
}
