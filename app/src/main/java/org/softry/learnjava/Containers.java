package org.softry.learnjava;

import android.content.Context;
import android.util.Log;

public class Containers {

	public static class LessonContent {
		private String[] content, pageTitle;
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
		
		public String[] GetAllTitles() {
			return this.pageTitle;
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
	
	public static class Lesson {
		private String title, desc;
		private int imgResId;
		private LessonContent lessonContent;
		private int completedProcent; //From 0 to 100
        private boolean locked;
		
		public Lesson(String title, String desc, int imgResId, LessonContent lessonContent) {
			this.title = title;
			this.desc = desc;
			this.imgResId = imgResId;
			this.lessonContent = lessonContent;
			this.locked = true;
			
			this.SetCompletedProcent((int)Math.floor(((float)this.lessonContent.GetReadCount()/(float)this.lessonContent.GetPageCount())*100f));
		}
		
		private void SetCompletedProcent(int val) {
			this.completedProcent = Math.max(0, Math.min(100, val));
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
		
		public int GetCompletedProcent() {
            this.SetCompletedProcent((int)Math.floor(((float)this.lessonContent.GetReadCount()/(float)this.lessonContent.GetPageCount())*100f));
            if(this.completedProcent == 100) {
                Utilities.UnlockNextLesson();
            }
            return this.completedProcent;
		}

		public boolean IsCompleted() {
            this.SetCompletedProcent((int)Math.floor(((float)this.lessonContent.GetReadCount()/(float)this.lessonContent.GetPageCount())*100f));
            if(this.completedProcent == 100)
                return true;
            return false;
        }
		
		public LessonContent GetLessonContent() {
			return this.lessonContent;
		}

		public void ResetCompletedProcent() {
			LessonContent currentLC = this.lessonContent;
			this.lessonContent = new LessonContent(currentLC.content, currentLC.pageTitle, new int[currentLC.content.length]);
            GetCompletedProcent();
		}

		
	}
	
    public static class Chapter {
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

        private void UpdateProgress() {
            double total_lprogress = 0;
            try {
				for(Integer l : this.lessonList) {
					double lprogress = Utilities.LessonList.get(l).GetCompletedProcent() / 100f;
					total_lprogress += lprogress;
				}
			} catch (Exception e) {
            	this.progress = -1;
            	return;
			}
            //Log.i("myapp", "For " + this.number + " progress formula is " + total_lprogress + "/" + this.lessonCount);
            if(total_lprogress == 0) this.progress = -1;
            else
                this.progress = (int)((total_lprogress/this.lessonCount)*100);
        }

        public void SetProgress(int value) {
            if(value<0) this.progress = -1;
            else if(value>100) this.progress = 100;
            else this.progress = value;
        }

        public int GetLessonCount() {return this.lessonCount;}
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

        public String GetProgressStr() {
            if(GetProgressValue() == -1) {
                return "";
            } else if(GetProgressValue() >= 100) {
               return "Completed";
            } else {
                return Integer.toString(GetProgressValue()) + "%";
            }
        }

        public int GetProgressValue() {
            UpdateProgress();
            return this.progress;
        }
    }
}
